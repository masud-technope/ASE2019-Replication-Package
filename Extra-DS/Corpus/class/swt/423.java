/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.*;
import org.eclipse.swt.internal.ole.win32.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.internal.win32.*;

/**
 * OleControlSite provides a site to manage an embedded ActiveX Control within a container.
 *
 * <p>In addition to the behaviour provided by OleClientSite, this object provides the following: 
 * <ul>
 *	<li>events from the ActiveX control 
 * 	<li>notification of property changes from the ActiveX control
 *	<li>simplified access to well known properties of the ActiveX Control (e.g. font, background color)
 *	<li>expose ambient properties of the container to the ActiveX Control
 * </ul>
 *
 * <p>This object implements the OLE Interfaces IOleControlSite, IDispatch, and IPropertyNotifySink.
 *
 * <p>Note that although this class is a subclass of <code>Composite</code>,
 * it does not make sense to add <code>Control</code> children to it,
 * or set a layout on it.
 * </p><p>
 * <dl>
 *	<dt><b>Styles</b> <dd>BORDER 
 *	<dt><b>Events</b> <dd>Dispose, Move, Resize
 * </dl>
 *
 */
public class OleControlSite extends OleClientSite {

    // interfaces for this container
    private COMObject iOleControlSite;

    private COMObject iDispatch;

    // supporting Property Change attributes
    private OlePropertyChangeSink olePropertyChangeSink;

    // supporting Event Sink attributes
    private OleEventSink[] oleEventSink = new OleEventSink[0];

    private GUID[] oleEventSinkGUID = new GUID[0];

    private int[] oleEventSinkIUnknown = new int[0];

    // supporting information for the Control COM object
    private CONTROLINFO currentControlInfo;

    private int[] sitePropertyIds = new int[0];

    private Variant[] sitePropertyValues = new Variant[0];

    /**
 * Create an OleControlSite child widget using style bits
 * to select a particular look or set of properties.
 *
 * @param parent a composite widget; must be an OleFrame
 * @param style the bitwise OR'ing of widget styles
 * @param progId the unique program identifier which has been registered for this ActiveX Control; 
 *               the value of the ProgID key or the value of the VersionIndependentProgID key specified
 *               in the registry for this Control (for example, the VersionIndependentProgID for 
 *               Internet Explorer is Shell.Explorer)
 *
 *@exception IllegalArgumentException <ul>
 *     <li>ERROR_NULL_ARGUMENT when the parent is null
 *</ul>
 * @exception SWTException <ul>
 *     <li>ERROR_THREAD_INVALID_ACCESS when called from the wrong thread
 *     <li>ERROR_INVALID_CLASSID when the progId does not map to a registered CLSID
 *     <li>ERROR_CANNOT_CREATE_OBJECT when failed to create OLE Object
 *     <li>ERROR_CANNOT_ACCESS_CLASSFACTORY when Class Factory could not be found
 *     <li>ERROR_CANNOT_CREATE_LICENSED_OBJECT when failed to create a licensed OLE Object
 * </ul>
 */
    public  OleControlSite(Composite parent, int style, String progId) {
        super(parent, style);
        try {
            // check for licensing
            appClsid = getClassID(progId);
            if (appClsid == null)
                OLE.error(OLE.ERROR_INVALID_CLASSID);
            int licinfo = getLicenseInfo(appClsid);
            if (licinfo == 0) {
                // Open a storage object
                tempStorage = createTempStorage();
                // Create ole object with storage object
                int[] address = new int[1];
                int result = COM.OleCreate(appClsid, COM.IIDIUnknown, COM.OLERENDER_DRAW, null, 0, tempStorage.getAddress(), address);
                if (result != COM.S_OK)
                    OLE.error(OLE.ERROR_CANNOT_CREATE_OBJECT, result);
                objIUnknown = new IUnknown(address[0]);
            } else {
                // Prepare the ClassFactory
                int[] ppvObject = new int[1];
                try {
                    int result = COM.CoGetClassObject(appClsid, COM.CLSCTX_INPROC_HANDLER | COM.CLSCTX_INPROC_SERVER, 0, COM.IIDIClassFactory2, ppvObject);
                    if (result != COM.S_OK) {
                        OLE.error(OLE.ERROR_CANNOT_ACCESS_CLASSFACTORY, result);
                    }
                    IClassFactory2 classFactory = new IClassFactory2(ppvObject[0]);
                    // Create Com Object
                    ppvObject = new int[1];
                    result = classFactory.CreateInstanceLic(0, 0, COM.IIDIUnknown, licinfo, ppvObject);
                    classFactory.Release();
                    if (result != COM.S_OK)
                        OLE.error(OLE.ERROR_CANNOT_CREATE_LICENSED_OBJECT, result);
                } finally {
                    COM.SysFreeString(licinfo);
                }
                objIUnknown = new IUnknown(ppvObject[0]);
                // Prepare a storage medium
                ppvObject = new int[1];
                if (objIUnknown.QueryInterface(COM.IIDIPersistStorage, ppvObject) == COM.S_OK) {
                    IPersistStorage persist = new IPersistStorage(ppvObject[0]);
                    tempStorage = createTempStorage();
                    persist.InitNew(tempStorage.getAddress());
                    persist.Release();
                }
            }
            // Init sinks
            addObjectReferences();
            // Init site properties
            setSiteProperty(COM.DISPID_AMBIENT_USERMODE, new Variant(true));
            setSiteProperty(COM.DISPID_AMBIENT_UIDEAD, new Variant(false));
            if (COM.OleRun(objIUnknown.getAddress()) == OLE.S_OK)
                state = STATE_RUNNING;
        } catch (SWTError e) {
            dispose();
            disposeCOMInterfaces();
            throw e;
        }
    }

    /**	 
 * Adds the listener to receive events.
 *
 * @param eventID the id of the event
 * 
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul> 
 *	    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void addEventListener(int eventID, OleListener listener) {
        if (listener == null)
            OLE.error(SWT.ERROR_NULL_ARGUMENT);
        GUID riid = getDefaultEventSinkGUID(objIUnknown);
        if (riid != null) {
            addEventListener(objIUnknown.getAddress(), riid, eventID, listener);
        }
    }

    static GUID getDefaultEventSinkGUID(IUnknown unknown) {
        // get Event Sink I/F from IProvideClassInfo2
        int[] ppvObject = new int[1];
        if (unknown.QueryInterface(COM.IIDIProvideClassInfo2, ppvObject) == COM.S_OK) {
            IProvideClassInfo2 pci2 = new IProvideClassInfo2(ppvObject[0]);
            GUID riid = new GUID();
            int result = pci2.GetGUID(COM.GUIDKIND_DEFAULT_SOURCE_DISP_IID, riid);
            pci2.Release();
            if (result == COM.S_OK)
                return riid;
        }
        // get Event Sink I/F from IProvideClassInfo
        if (unknown.QueryInterface(COM.IIDIProvideClassInfo, ppvObject) == COM.S_OK) {
            IProvideClassInfo pci = new IProvideClassInfo(ppvObject[0]);
            int[] ppTI = new int[1];
            int[] ppEI = new int[1];
            int result = pci.GetClassInfo(ppTI);
            pci.Release();
            if (result == COM.S_OK && ppTI[0] != 0) {
                ITypeInfo classInfo = new ITypeInfo(ppTI[0]);
                int[] ppTypeAttr = new int[1];
                result = classInfo.GetTypeAttr(ppTypeAttr);
                if (result == COM.S_OK && ppTypeAttr[0] != 0) {
                    TYPEATTR typeAttribute = new TYPEATTR();
                    COM.MoveMemory(typeAttribute, ppTypeAttr[0], TYPEATTR.sizeof);
                    classInfo.ReleaseTypeAttr(ppTypeAttr[0]);
                    int implMask = COM.IMPLTYPEFLAG_FDEFAULT | COM.IMPLTYPEFLAG_FSOURCE | COM.IMPLTYPEFLAG_FRESTRICTED;
                    int implBits = COM.IMPLTYPEFLAG_FDEFAULT | COM.IMPLTYPEFLAG_FSOURCE;
                    for (int i = 0; i < typeAttribute.cImplTypes; i++) {
                        int[] pImplTypeFlags = new int[1];
                        if (classInfo.GetImplTypeFlags(i, pImplTypeFlags) == COM.S_OK) {
                            if ((pImplTypeFlags[0] & implMask) == implBits) {
                                int[] pRefType = new int[1];
                                if (classInfo.GetRefTypeOfImplType(i, pRefType) == COM.S_OK) {
                                    classInfo.GetRefTypeInfo(pRefType[0], ppEI);
                                }
                            }
                        }
                    }
                }
                classInfo.Release();
                if (ppEI[0] != 0) {
                    ITypeInfo eventInfo = new ITypeInfo(ppEI[0]);
                    ppTypeAttr = new int[1];
                    result = eventInfo.GetTypeAttr(ppTypeAttr);
                    GUID riid = null;
                    if (result == COM.S_OK && ppTypeAttr[0] != 0) {
                        riid = new GUID();
                        COM.MoveMemory(riid, ppTypeAttr[0], GUID.sizeof);
                        eventInfo.ReleaseTypeAttr(ppTypeAttr[0]);
                    }
                    eventInfo.Release();
                    return riid;
                }
            }
        }
        return null;
    }

    /**	 
 * Adds the listener to receive events.
 *
 * @since 2.0
 * 
 * @param automation the automation object that provides the event notification
 * 
 * @param eventID the id of the event
 * 
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul> 
 *	   <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void addEventListener(OleAutomation automation, int eventID, OleListener listener) {
        if (listener == null || automation == null)
            OLE.error(SWT.ERROR_NULL_ARGUMENT);
        int address = automation.getAddress();
        IUnknown unknown = new IUnknown(address);
        GUID riid = getDefaultEventSinkGUID(unknown);
        if (riid != null) {
            addEventListener(address, riid, eventID, listener);
        }
    }

    void addEventListener(int iunknown, GUID guid, int eventID, OleListener listener) {
        if (listener == null || iunknown == 0 || guid == null)
            OLE.error(SWT.ERROR_NULL_ARGUMENT);
        // have we connected to this kind of event sink before?
        int index = -1;
        for (int i = 0; i < oleEventSinkGUID.length; i++) {
            if (COM.IsEqualGUID(oleEventSinkGUID[i], guid)) {
                if (iunknown == oleEventSinkIUnknown[i]) {
                    index = i;
                    break;
                }
            }
        }
        if (index != -1) {
            oleEventSink[index].addListener(eventID, listener);
        } else {
            int oldLength = oleEventSink.length;
            OleEventSink[] newOleEventSink = new OleEventSink[oldLength + 1];
            GUID[] newOleEventSinkGUID = new GUID[oldLength + 1];
            int[] newOleEventSinkIUnknown = new int[oldLength + 1];
            System.arraycopy(oleEventSink, 0, newOleEventSink, 0, oldLength);
            System.arraycopy(oleEventSinkGUID, 0, newOleEventSinkGUID, 0, oldLength);
            System.arraycopy(oleEventSinkIUnknown, 0, newOleEventSinkIUnknown, 0, oldLength);
            oleEventSink = newOleEventSink;
            oleEventSinkGUID = newOleEventSinkGUID;
            oleEventSinkIUnknown = newOleEventSinkIUnknown;
            oleEventSink[oldLength] = new OleEventSink(this, iunknown, guid);
            oleEventSinkGUID[oldLength] = guid;
            oleEventSinkIUnknown[oldLength] = iunknown;
            oleEventSink[oldLength].AddRef();
            oleEventSink[oldLength].connect();
            oleEventSink[oldLength].addListener(eventID, listener);
        }
    }

    protected void addObjectReferences() {
        super.addObjectReferences();
        // Get property change notification from control
        connectPropertyChangeSink();
        // Get access to the Control object
        int[] ppvObject = new int[1];
        if (objIUnknown.QueryInterface(COM.IIDIOleControl, ppvObject) == COM.S_OK) {
            IOleControl objIOleControl = new IOleControl(ppvObject[0]);
            // ask the control for its info in case users
            // need to act on it
            currentControlInfo = new CONTROLINFO();
            objIOleControl.GetControlInfo(currentControlInfo);
            objIOleControl.Release();
        }
    }

    /**	 
 * Adds the listener to receive events.
 *
 * @param propertyID the identifier of the property
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul> 
 *	    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void addPropertyListener(int propertyID, OleListener listener) {
        if (listener == null)
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        olePropertyChangeSink.addListener(propertyID, listener);
    }

    private void connectPropertyChangeSink() {
        olePropertyChangeSink = new OlePropertyChangeSink(this);
        olePropertyChangeSink.AddRef();
        olePropertyChangeSink.connect(objIUnknown);
    }

    protected void createCOMInterfaces() {
        super.createCOMInterfaces();
        // register each of the interfaces that this object implements
        iOleControlSite = new COMObject(new int[] { 2, 0, 0, 0, 1, 1, 3, 2, 1, 0 }) {

            public int method0(int[] args) {
                return QueryInterface(args[0], args[1]);
            }

            public int method1(int[] args) {
                return AddRef();
            }

            public int method2(int[] args) {
                return Release();
            }

            public int method3(int[] args) {
                return OnControlInfoChanged();
            }

            // method4 LockInPlaceActive - not implemented
            // method5 GetExtendedControl - not implemented
            // method6 TransformCoords - not implemented
            // method7 Translate Accelerator - not implemented
            public int method8(int[] args) {
                return OnFocus(args[0]);
            }
        };
        iDispatch = new COMObject(new int[] { 2, 0, 0, 1, 3, 5, 8 }) {

            public int method0(int[] args) {
                return QueryInterface(args[0], args[1]);
            }

            public int method1(int[] args) {
                return AddRef();
            }

            public int method2(int[] args) {
                return Release();
            }

            // method3 GetTypeInfoCount - not implemented
            // method4 GetTypeInfo - not implemented
            // method5 GetIDsOfNames - not implemented
            public int method6(int[] args) {
                return Invoke(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            }
        };
    }

    private void disconnectEventSinks() {
        for (int i = 0; i < oleEventSink.length; i++) {
            OleEventSink sink = oleEventSink[i];
            sink.disconnect();
            sink.Release();
        }
        oleEventSink = new OleEventSink[0];
        oleEventSinkGUID = new GUID[0];
        oleEventSinkIUnknown = new int[0];
    }

    private void disconnectPropertyChangeSink() {
        if (olePropertyChangeSink != null) {
            olePropertyChangeSink.disconnect(objIUnknown);
            olePropertyChangeSink.Release();
        }
        olePropertyChangeSink = null;
    }

    protected void disposeCOMInterfaces() {
        super.disposeCOMInterfaces();
        if (iOleControlSite != null)
            iOleControlSite.dispose();
        iOleControlSite = null;
        if (iDispatch != null)
            iDispatch.dispose();
        iDispatch = null;
    }

    public Color getBackground() {
        if (objIUnknown != null) {
            // !! We are getting the OLE_COLOR - should we change this to the COLORREF value?
            OleAutomation oleObject = new OleAutomation(this);
            Variant varBackColor = oleObject.getProperty(COM.DISPID_BACKCOLOR);
            oleObject.dispose();
            if (varBackColor != null) {
                int[] colorRef = new int[1];
                if (COM.OleTranslateColor(varBackColor.getInt(), getDisplay().hPalette, colorRef) == COM.S_OK)
                    return Color.win32_new(getDisplay(), colorRef[0]);
            }
        }
        return super.getBackground();
    }

    public Font getFont() {
        if (objIUnknown != null) {
            OleAutomation oleObject = new OleAutomation(this);
            Variant varDispFont = oleObject.getProperty(COM.DISPID_FONT);
            oleObject.dispose();
            if (varDispFont != null) {
                OleAutomation iDispFont = varDispFont.getAutomation();
                Variant lfFaceName = iDispFont.getProperty(COM.DISPID_FONT_NAME);
                Variant lfHeight = iDispFont.getProperty(COM.DISPID_FONT_SIZE);
                Variant lfItalic = iDispFont.getProperty(COM.DISPID_FONT_ITALIC);
                //Variant lfCharSet  = iDispFont.getProperty(COM.DISPID_FONT_CHARSET);
                Variant lfBold = iDispFont.getProperty(COM.DISPID_FONT_BOLD);
                iDispFont.dispose();
                if (lfFaceName != null && lfHeight != null && lfItalic != null && lfBold != null) {
                    int style = 3 * lfBold.getInt() + 2 * lfItalic.getInt();
                    Font font = new Font(getShell().getDisplay(), lfFaceName.getString(), lfHeight.getInt(), style);
                    return font;
                }
            }
        }
        return super.getFont();
    }

    public Color getForeground() {
        if (objIUnknown != null) {
            // !! We are getting the OLE_COLOR - should we change this to the COLORREF value?
            OleAutomation oleObject = new OleAutomation(this);
            Variant varForeColor = oleObject.getProperty(COM.DISPID_FORECOLOR);
            oleObject.dispose();
            if (varForeColor != null) {
                int[] colorRef = new int[1];
                if (COM.OleTranslateColor(varForeColor.getInt(), getDisplay().hPalette, colorRef) == COM.S_OK)
                    return Color.win32_new(getDisplay(), colorRef[0]);
            }
        }
        return super.getForeground();
    }

    protected int getLicenseInfo(GUID clsid) {
        int[] ppvObject = new int[1];
        if (COM.CoGetClassObject(clsid, COM.CLSCTX_INPROC_HANDLER | COM.CLSCTX_INPROC_SERVER, 0, COM.IIDIClassFactory2, ppvObject) != COM.S_OK) {
            return 0;
        }
        IClassFactory2 classFactory = new IClassFactory2(ppvObject[0]);
        LICINFO licinfo = new LICINFO();
        if (classFactory.GetLicInfo(licinfo) != COM.S_OK) {
            classFactory.Release();
            return 0;
        }
        int[] pBstrKey = new int[1];
        if (licinfo != null && licinfo.fRuntimeKeyAvail) {
            if (classFactory.RequestLicKey(0, pBstrKey) == COM.S_OK) {
                classFactory.Release();
                return pBstrKey[0];
            }
        }
        classFactory.Release();
        return 0;
    }

    /**
 * 
 * Get the control site property specified by the dispIdMember.
 * 
 * @since 2.1
 * 
 */
    public Variant getSiteProperty(int dispId) {
        for (int i = 0; i < sitePropertyIds.length; i++) {
            if (sitePropertyIds[i] == dispId) {
                return sitePropertyValues[i];
            }
        }
        return null;
    }

    protected int GetWindow(int phwnd) {
        if (phwnd == 0)
            return COM.E_INVALIDARG;
        if (frame == null) {
            COM.MoveMemory(phwnd, new int[] { 0 }, 4);
            return COM.E_NOTIMPL;
        }
        // Copy the Window's handle into the memory passed in
        COM.MoveMemory(phwnd, new int[] { handle }, 4);
        return COM.S_OK;
    }

    private int Invoke(int dispIdMember, int riid, int lcid, int dwFlags, int pDispParams, int pVarResult, int pExcepInfo, int pArgErr) {
        if (pVarResult == 0 || dwFlags != COM.DISPATCH_PROPERTYGET) {
            if (pExcepInfo != 0)
                COM.MoveMemory(pExcepInfo, new int[] { 0 }, 4);
            if (pArgErr != 0)
                COM.MoveMemory(pArgErr, new int[] { 0 }, 4);
            return COM.DISP_E_MEMBERNOTFOUND;
        }
        Variant result = getSiteProperty(dispIdMember);
        if (result != null) {
            if (pVarResult != 0)
                result.getData(pVarResult);
            return COM.S_OK;
        }
        switch(dispIdMember) {
            // indicate a false result
            case COM.DISPID_AMBIENT_SUPPORTSMNEMONICS:
            case COM.DISPID_AMBIENT_SHOWGRABHANDLES:
            case COM.DISPID_AMBIENT_SHOWHATCHING:
                if (pVarResult != 0)
                    COM.MoveMemory(pVarResult, new int[] { 0 }, 4);
                if (pExcepInfo != 0)
                    COM.MoveMemory(pExcepInfo, new int[] { 0 }, 4);
                if (pArgErr != 0)
                    COM.MoveMemory(pArgErr, new int[] { 0 }, 4);
                return COM.S_FALSE;
            // not implemented
            case COM.DISPID_AMBIENT_OFFLINEIFNOTCONNECTED:
            case COM.DISPID_AMBIENT_BACKCOLOR:
            case COM.DISPID_AMBIENT_FORECOLOR:
            case COM.DISPID_AMBIENT_FONT:
            case COM.DISPID_AMBIENT_LOCALEID:
            case COM.DISPID_AMBIENT_SILENT:
            case COM.DISPID_AMBIENT_MESSAGEREFLECT:
                if (pVarResult != 0)
                    COM.MoveMemory(pVarResult, new int[] { 0 }, 4);
                if (pExcepInfo != 0)
                    COM.MoveMemory(pExcepInfo, new int[] { 0 }, 4);
                if (pArgErr != 0)
                    COM.MoveMemory(pArgErr, new int[] { 0 }, 4);
                return COM.E_NOTIMPL;
            default:
                if (pVarResult != 0)
                    COM.MoveMemory(pVarResult, new int[] { 0 }, 4);
                if (pExcepInfo != 0)
                    COM.MoveMemory(pExcepInfo, new int[] { 0 }, 4);
                if (pArgErr != 0)
                    COM.MoveMemory(pArgErr, new int[] { 0 }, 4);
                return COM.DISP_E_MEMBERNOTFOUND;
        }
    }

    private int OnControlInfoChanged() {
        int[] ppvObject = new int[1];
        if (objIUnknown.QueryInterface(COM.IIDIOleControl, ppvObject) == COM.S_OK) {
            IOleControl objIOleControl = new IOleControl(ppvObject[0]);
            // ask the control for its info in case users
            // need to act on it
            currentControlInfo = new CONTROLINFO();
            objIOleControl.GetControlInfo(currentControlInfo);
            objIOleControl.Release();
        }
        return COM.S_OK;
    }

    void onFocusIn(Event e) {
        if (objIOleInPlaceObject == null)
            return;
        doVerb(OLE.OLEIVERB_UIACTIVATE);
        if (isFocusControl())
            return;
        int[] phwnd = new int[1];
        objIOleInPlaceObject.GetWindow(phwnd);
        if (phwnd[0] == 0)
            return;
        OS.SetFocus(phwnd[0]);
    }

    void onFocusOut(Event e) {
        if (objIOleInPlaceObject != null) {
            objIOleInPlaceObject.UIDeactivate();
        }
    }

    private int OnFocus(int fGotFocus) {
        return COM.S_OK;
    }

    protected int OnUIDeactivate(int fUndoable) {
        // controls don't need to do anything for
        // border space or menubars
        state = STATE_INPLACEACTIVE;
        return COM.S_OK;
    }

    protected int QueryInterface(int riid, int ppvObject) {
        int result = super.QueryInterface(riid, ppvObject);
        if (result == COM.S_OK)
            return result;
        if (riid == 0 || ppvObject == 0)
            return COM.E_INVALIDARG;
        GUID guid = new GUID();
        COM.MoveMemory(guid, riid, GUID.sizeof);
        if (COM.IsEqualGUID(guid, COM.IIDIOleControlSite)) {
            COM.MoveMemory(ppvObject, new int[] { iOleControlSite.getAddress() }, 4);
            AddRef();
            return COM.S_OK;
        }
        if (COM.IsEqualGUID(guid, COM.IIDIDispatch)) {
            COM.MoveMemory(ppvObject, new int[] { iDispatch.getAddress() }, 4);
            AddRef();
            return COM.S_OK;
        }
        COM.MoveMemory(ppvObject, new int[] { 0 }, 4);
        return COM.E_NOINTERFACE;
    }

    protected int Release() {
        int result = super.Release();
        if (result == 0) {
            for (int i = 0; i < sitePropertyIds.length; i++) {
                sitePropertyValues[i].dispose();
            }
            sitePropertyIds = new int[0];
            sitePropertyValues = new Variant[0];
        }
        return result;
    }

    protected void releaseObjectInterfaces() {
        disconnectEventSinks();
        disconnectPropertyChangeSink();
        super.releaseObjectInterfaces();
    }

    /**	 
 * Removes the listener.
 *
 * @param eventID the event identifier
 * 
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul>
 *	    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void removeEventListener(int eventID, OleListener listener) {
        checkWidget();
        if (listener == null)
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        GUID riid = getDefaultEventSinkGUID(objIUnknown);
        if (riid != null) {
            removeEventListener(objIUnknown.getAddress(), riid, eventID, listener);
        }
    }

    /**	 
 * Removes the listener.
 *
 * @since 2.0
 * @deprecated - use OleControlSite.removeEventListener(OleAutomation, int, OleListener)
 * 
 * @param automation the automation object that provides the event notification
 * 
 * @param guid the identifier of the events COM interface
 * 
 * @param eventID the event identifier
 * 
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul>
 *	    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void removeEventListener(OleAutomation automation, GUID guid, int eventID, OleListener listener) {
        checkWidget();
        if (automation == null || listener == null || guid == null)
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        removeEventListener(automation.getAddress(), guid, eventID, listener);
    }

    /**	 
 * Removes the listener.
 *
 * @since 2.0
 * 
 * @param automation the automation object that provides the event notification
 * 
 * @param eventID the event identifier
 * 
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul>
 *	    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void removeEventListener(OleAutomation automation, int eventID, OleListener listener) {
        checkWidget();
        if (automation == null || listener == null)
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        int address = automation.getAddress();
        IUnknown unknown = new IUnknown(address);
        GUID riid = getDefaultEventSinkGUID(unknown);
        if (riid != null) {
            removeEventListener(address, riid, eventID, listener);
        }
    }

    void removeEventListener(int iunknown, GUID guid, int eventID, OleListener listener) {
        if (listener == null || guid == null)
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        for (int i = 0; i < oleEventSink.length; i++) {
            if (COM.IsEqualGUID(oleEventSinkGUID[i], guid)) {
                if (iunknown == oleEventSinkIUnknown[i]) {
                    oleEventSink[i].removeListener(eventID, listener);
                    if (!oleEventSink[i].hasListeners()) {
                        //free resources associated with event sink
                        oleEventSink[i].disconnect();
                        oleEventSink[i].Release();
                        int oldLength = oleEventSink.length;
                        if (oldLength == 1) {
                            oleEventSink = new OleEventSink[0];
                            oleEventSinkGUID = new GUID[0];
                            oleEventSinkIUnknown = new int[0];
                        } else {
                            OleEventSink[] newOleEventSink = new OleEventSink[oldLength - 1];
                            System.arraycopy(oleEventSink, 0, newOleEventSink, 0, i);
                            System.arraycopy(oleEventSink, i + 1, newOleEventSink, i, oldLength - i - 1);
                            oleEventSink = newOleEventSink;
                            GUID[] newOleEventSinkGUID = new GUID[oldLength - 1];
                            System.arraycopy(oleEventSinkGUID, 0, newOleEventSinkGUID, 0, i);
                            System.arraycopy(oleEventSinkGUID, i + 1, newOleEventSinkGUID, i, oldLength - i - 1);
                            oleEventSinkGUID = newOleEventSinkGUID;
                            int[] newOleEventSinkIUnknown = new int[oldLength - 1];
                            System.arraycopy(oleEventSinkIUnknown, 0, newOleEventSinkIUnknown, 0, i);
                            System.arraycopy(oleEventSinkIUnknown, i + 1, newOleEventSinkIUnknown, i, oldLength - i - 1);
                            oleEventSinkIUnknown = newOleEventSinkIUnknown;
                        }
                    }
                    return;
                }
            }
        }
    }

    /**	 
 * Removes the listener.
 *
 * @param listener the listener
 *
 * @exception IllegalArgumentException <ul>
 *	    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
    public void removePropertyListener(int propertyID, OleListener listener) {
        if (listener == null)
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        olePropertyChangeSink.removeListener(propertyID, listener);
    }

    public void setBackground(Color color) {
        super.setBackground(color);
        //set the background of the ActiveX Control
        if (objIUnknown != null) {
            OleAutomation oleObject = new OleAutomation(this);
            oleObject.setProperty(COM.DISPID_BACKCOLOR, new Variant(color.handle));
            oleObject.dispose();
        }
    }

    public void setFont(Font font) {
        super.setFont(font);
        //set the font of the ActiveX Control
        if (objIUnknown != null) {
            OleAutomation oleObject = new OleAutomation(this);
            Variant varDispFont = oleObject.getProperty(COM.DISPID_FONT);
            oleObject.dispose();
            if (varDispFont != null) {
                OleAutomation iDispFont = varDispFont.getAutomation();
                FontData[] fdata = font.getFontData();
                iDispFont.setProperty(COM.DISPID_FONT_NAME, new Variant(fdata[0].getName()));
                iDispFont.setProperty(COM.DISPID_FONT_SIZE, new Variant(fdata[0].getHeight()));
                iDispFont.setProperty(COM.DISPID_FONT_ITALIC, new Variant(fdata[0].getStyle() & SWT.ITALIC));
                //iDispFont.setProperty(COM.DISPID_FONT_CHARSET, new Variant(fdata[0].getCharset));
                iDispFont.setProperty(COM.DISPID_FONT_BOLD, new Variant((fdata[0].getStyle() & SWT.BOLD)));
                iDispFont.dispose();
            }
        }
        return;
    }

    public void setForeground(Color color) {
        super.setForeground(color);
        //set the foreground of the ActiveX Control
        if (objIUnknown != null) {
            OleAutomation oleObject = new OleAutomation(this);
            oleObject.setProperty(COM.DISPID_FORECOLOR, new Variant(color.handle));
            oleObject.dispose();
        }
    }

    /**
 * Sets the control site property specified by the dispIdMember to a new value.
 * The value will be disposed by the control site when it is no longer required
 * using Variant.dispose.  Passing a value of null will clear the dispId value.
 * 
 * @param dispId the ID of the property as specified by the IDL of the ActiveX Control
 * @param value The new value for the property as expressed in a Variant.
 *
 * @since 2.1
 */
    public void setSiteProperty(int dispId, Variant value) {
        for (int i = 0; i < sitePropertyIds.length; i++) {
            if (sitePropertyIds[i] == dispId) {
                if (sitePropertyValues[i] != null) {
                    sitePropertyValues[i].dispose();
                }
                if (value != null) {
                    sitePropertyValues[i] = value;
                } else {
                    int oldLength = sitePropertyIds.length;
                    int[] newSitePropertyIds = new int[oldLength - 1];
                    Variant[] newSitePropertyValues = new Variant[oldLength - 1];
                    System.arraycopy(sitePropertyIds, 0, newSitePropertyIds, 0, i);
                    System.arraycopy(sitePropertyIds, i + 1, newSitePropertyIds, i, oldLength - i - 1);
                    System.arraycopy(sitePropertyValues, 0, newSitePropertyValues, 0, i);
                    System.arraycopy(sitePropertyValues, i + 1, newSitePropertyValues, i, oldLength - i - 1);
                    sitePropertyIds = newSitePropertyIds;
                    sitePropertyValues = newSitePropertyValues;
                }
                return;
            }
        }
        int oldLength = sitePropertyIds.length;
        int[] newSitePropertyIds = new int[oldLength + 1];
        Variant[] newSitePropertyValues = new Variant[oldLength + 1];
        System.arraycopy(sitePropertyIds, 0, newSitePropertyIds, 0, oldLength);
        System.arraycopy(sitePropertyValues, 0, newSitePropertyValues, 0, oldLength);
        newSitePropertyIds[oldLength] = dispId;
        newSitePropertyValues[oldLength] = value;
        sitePropertyIds = newSitePropertyIds;
        sitePropertyValues = newSitePropertyValues;
    }
}
