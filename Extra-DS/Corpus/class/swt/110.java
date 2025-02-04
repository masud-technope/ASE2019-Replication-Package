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
package org.eclipse.swt.internal;

import java.util.Hashtable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.internal.win32.*;

/*
 * Wraps Win32 API used to bidi enable the StyledText widget.
 */
public class BidiUtil {

    // Keyboard language ids
    public static final int KEYBOARD_NON_BIDI = 0;

    public static final int KEYBOARD_BIDI = 1;

    // bidi flag
    static int isBidiPlatform = -1;

    // getRenderInfo flag values
    public static final int CLASSIN = 1;

    public static final int LINKBEFORE = 2;

    public static final int LINKAFTER = 4;

    // variables used for providing a listener mechanism for keyboard language 
    // switching 
    static Hashtable languageMap = new Hashtable();

    static Hashtable keyMap = new Hashtable();

    static Hashtable oldProcMap = new Hashtable();

    /*
	 * This code is intentionally commented.  In order
	 * to support CLDC, .class cannot be used because
	 * it does not compile on some Java compilers when
	 * they are targeted for CLDC.
	 */
    //	static Callback callback = new Callback (BidiUtil.class, "windowProc", 4);
    //$NON-NLS-1$
    static final String CLASS_NAME = "org.eclipse.swt.internal.BidiUtil";

    static Callback callback;

    static {
        try {
            //$NON-NLS-1$
            callback = new Callback(Class.forName(CLASS_NAME), "windowProc", 4);
            if (callback.getAddress() == 0)
                SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);
        } catch (ClassNotFoundException e) {
        }
    }

    // GetCharacterPlacement constants
    static final int GCP_REORDER = 0x0002;

    static final int GCP_GLYPHSHAPE = 0x0010;

    static final int GCP_LIGATE = 0x0020;

    static final int GCP_CLASSIN = 0x00080000;

    static final byte GCPCLASS_ARABIC = 2;

    static final byte GCPCLASS_HEBREW = 2;

    static final byte GCPCLASS_LOCALNUMBER = 4;

    static final byte GCPCLASS_LATINNUMBER = 5;

    static final int GCPGLYPH_LINKBEFORE = 0x8000;

    static final int GCPGLYPH_LINKAFTER = 0x4000;

    // ExtTextOut constants
    static final int ETO_CLIPPED = 0x4;

    static final int ETO_GLYPH_INDEX = 0x0010;

    // Windows primary language identifiers
    static final int LANG_ARABIC = 0x01;

    static final int LANG_HEBREW = 0x0d;

    // code page identifiers
    //$NON-NLS-1$
    static final String CD_PG_HEBREW = "1255";

    //$NON-NLS-1$
    static final String CD_PG_ARABIC = "1256";

    // ActivateKeyboard constants
    static final int HKL_NEXT = 1;

    static final int HKL_PREV = 0;

    /*
	 * Public character class constants are the same as Windows 
	 * platform constants. 
	 * Saves conversion of class array in getRenderInfo to arbitrary 
	 * constants for now.
	 */
    public static final int CLASS_HEBREW = GCPCLASS_ARABIC;

    public static final int CLASS_ARABIC = GCPCLASS_HEBREW;

    public static final int CLASS_LOCALNUMBER = GCPCLASS_LOCALNUMBER;

    public static final int CLASS_LATINNUMBER = GCPCLASS_LATINNUMBER;

    public static final int REORDER = GCP_REORDER;

    public static final int LIGATE = GCP_LIGATE;

    public static final int GLYPHSHAPE = GCP_GLYPHSHAPE;

    /**
 * Adds a language listener. The listener will get notified when the language of
 * the keyboard changes (via Alt-Shift on Win platforms).  Do this by creating a 
 * window proc for the Control so that the window messages for the Control can be
 * monitored.
 * <p>
 *
 * @param hwnd the handle of the Control that is listening for keyboard language 
 *  changes
 * @param runnable the code that should be executed when a keyboard language change
 *  occurs
 */
    public static void addLanguageListener(int hwnd, Runnable runnable) {
        languageMap.put(new Integer(hwnd), runnable);
        subclass(hwnd);
    }

    /**
 * Proc used for OS.EnumSystemLanguageGroups call during isBidiPlatform test.
 */
    static int EnumSystemLanguageGroupsProc(int lpLangGrpId, int lpLangGrpIdString, int lpLangGrpName, int options, int lParam) {
        if (lpLangGrpId == OS.LGRPID_HEBREW) {
            isBidiPlatform = 1;
            return 0;
        }
        if (lpLangGrpId == OS.LGRPID_ARABIC) {
            isBidiPlatform = 1;
            return 0;
        }
        return 1;
    }

    /**
 * Wraps the ExtTextOut function.
 * <p>
 *
 * @param gc the gc to use for rendering
 * @param renderBuffer the glyphs to render as an array of characters
 * @param renderDx the width of each glyph in renderBuffer
 * @param x x position to start rendering
 * @param y y position to start rendering
 */
    public static void drawGlyphs(GC gc, char[] renderBuffer, int[] renderDx, int x, int y) {
        int length = renderDx.length;
        if (!OS.IsWinCE && OS.WIN32_VERSION >= OS.VERSION(4, 10)) {
            if (OS.GetLayout(gc.handle) != 0) {
                reverse(renderDx);
                //fixes bug 40006
                renderDx[length - 1]--;
                reverse(renderBuffer);
            }
        }
        // render transparently to avoid overlapping segments. fixes bug 40006
        int oldBkMode = OS.SetBkMode(gc.handle, OS.TRANSPARENT);
        OS.ExtTextOutW(gc.handle, x, y, ETO_GLYPH_INDEX, null, renderBuffer, renderBuffer.length, renderDx);
        OS.SetBkMode(gc.handle, oldBkMode);
    }

    /**
 * Return ordering and rendering information for the given text.  Wraps the GetFontLanguageInfo
 * and GetCharacterPlacement functions.
 * <p>
 * 
 * @param gc the GC to use for measuring of this line, input parameter
 * @param text text that bidi data should be calculated for, input parameter
 * @param order an array of integers representing the visual position of each character in
 *  the text array, output parameter
 * @param classBuffer an array of integers representing the type (e.g., ARABIC, HEBREW, 
 *  LOCALNUMBER) of each character in the text array, input/output parameter
 * @param dx an array of integers representing the pixel width of each glyph in the returned
 *  glyph buffer, output paramteter
 * @param flags an integer representing rendering flag information, input parameter
 * @param offsets text segments that should be measured and reordered separately, input 
 *  parameter. See org.eclipse.swt.custom.BidiSegmentEvent for details.
 * @return buffer with the glyphs that should be rendered for the given text
 */
    public static char[] getRenderInfo(GC gc, String text, int[] order, byte[] classBuffer, int[] dx, int flags, int[] offsets) {
        int fontLanguageInfo = OS.GetFontLanguageInfo(gc.handle);
        int hHeap = OS.GetProcessHeap();
        int[] lpCs = new int[8];
        int cs = OS.GetTextCharset(gc.handle);
        boolean isRightOriented = false;
        if (!OS.IsWinCE && OS.WIN32_VERSION >= OS.VERSION(4, 10)) {
            isRightOriented = OS.GetLayout(gc.handle) != 0;
        }
        OS.TranslateCharsetInfo(cs, lpCs, OS.TCI_SRCCHARSET);
        TCHAR textBuffer = new TCHAR(lpCs[1], text, false);
        int byteCount = textBuffer.length();
        boolean linkBefore = (flags & LINKBEFORE) == LINKBEFORE;
        boolean linkAfter = (flags & LINKAFTER) == LINKAFTER;
        GCP_RESULTS result = new GCP_RESULTS();
        result.lStructSize = GCP_RESULTS.sizeof;
        result.nGlyphs = byteCount;
        int lpOrder = result.lpOrder = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount * 4);
        int lpDx = result.lpDx = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount * 4);
        int lpClass = result.lpClass = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount);
        int lpGlyphs = result.lpGlyphs = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount * 2);
        // set required dwFlags
        int dwFlags = 0;
        int glyphFlags = 0;
        // Always reorder.  We assume that if we are calling this function we're
        // on a platform that supports bidi.  Fixes 20690.
        dwFlags |= GCP_REORDER;
        if ((fontLanguageInfo & GCP_LIGATE) == GCP_LIGATE) {
            dwFlags |= GCP_LIGATE;
            glyphFlags |= 0;
        }
        if ((fontLanguageInfo & GCP_GLYPHSHAPE) == GCP_GLYPHSHAPE) {
            dwFlags |= GCP_GLYPHSHAPE;
            if (linkBefore) {
                glyphFlags |= GCPGLYPH_LINKBEFORE;
            }
            if (linkAfter) {
                glyphFlags |= GCPGLYPH_LINKAFTER;
            }
        }
        byte[] lpGlyphs2;
        if (linkBefore || linkAfter) {
            lpGlyphs2 = new byte[2];
            lpGlyphs2[0] = (byte) glyphFlags;
            lpGlyphs2[1] = (byte) (glyphFlags >> 8);
        } else {
            lpGlyphs2 = new byte[] { (byte) glyphFlags };
        }
        OS.MoveMemory(result.lpGlyphs, lpGlyphs2, lpGlyphs2.length);
        if ((flags & CLASSIN) == CLASSIN) {
            // set classification values for the substring
            dwFlags |= GCP_CLASSIN;
            OS.MoveMemory(result.lpClass, classBuffer, classBuffer.length);
        }
        char[] glyphBuffer = new char[result.nGlyphs];
        int glyphCount = 0;
        for (int i = 0; i < offsets.length - 1; i++) {
            int offset = offsets[i];
            int length = offsets[i + 1] - offsets[i];
            // The number of glyphs expected is <= length (segment length);
            // the actual number returned may be less in case of Arabic ligatures.
            result.nGlyphs = length;
            TCHAR textBuffer2 = new TCHAR(lpCs[1], text.substring(offset, offset + length), false);
            OS.GetCharacterPlacement(gc.handle, textBuffer2, textBuffer2.length(), 0, result, dwFlags);
            if (dx != null) {
                int[] dx2 = new int[result.nGlyphs];
                OS.MoveMemory(dx2, result.lpDx, dx2.length * 4);
                if (isRightOriented) {
                    reverse(dx2);
                }
                System.arraycopy(dx2, 0, dx, glyphCount, dx2.length);
            }
            if (order != null) {
                int[] order2 = new int[length];
                OS.MoveMemory(order2, result.lpOrder, order2.length * 4);
                translateOrder(order2, glyphCount, isRightOriented);
                System.arraycopy(order2, 0, order, offset, length);
            }
            if (classBuffer != null) {
                byte[] classBuffer2 = new byte[length];
                OS.MoveMemory(classBuffer2, result.lpClass, classBuffer2.length);
                System.arraycopy(classBuffer2, 0, classBuffer, offset, length);
            }
            char[] glyphBuffer2 = new char[result.nGlyphs];
            OS.MoveMemory(glyphBuffer2, result.lpGlyphs, glyphBuffer2.length * 2);
            if (isRightOriented) {
                reverse(glyphBuffer2);
            }
            System.arraycopy(glyphBuffer2, 0, glyphBuffer, glyphCount, glyphBuffer2.length);
            glyphCount += glyphBuffer2.length;
            // We concatenate successive results of calls to GCP.
            // For Arabic, it is the only good method since the number of output
            // glyphs might be less than the number of input characters.
            // This assumes that the whole line is built by successive adjacent
            // segments without overlapping.
            result.lpOrder += length * 4;
            result.lpDx += length * 4;
            result.lpClass += length;
            result.lpGlyphs += glyphBuffer2.length * 2;
        }
        /* Free the memory that was allocated. */
        OS.HeapFree(hHeap, 0, lpGlyphs);
        OS.HeapFree(hHeap, 0, lpClass);
        OS.HeapFree(hHeap, 0, lpDx);
        OS.HeapFree(hHeap, 0, lpOrder);
        return glyphBuffer;
    }

    /**
 * Return bidi ordering information for the given text.  Does not return rendering 
 * information (e.g., glyphs, glyph distances).  Use this method when you only need 
 * ordering information.  Doing so will improve performance.  Wraps the 
 * GetFontLanguageInfo and GetCharacterPlacement functions.
 * <p>
 * 
 * @param gc the GC to use for measuring of this line, input parameter
 * @param text text that bidi data should be calculated for, input parameter
 * @param order an array of integers representing the visual position of each character in
 *  the text array, output parameter
 * @param classBuffer an array of integers representing the type (e.g., ARABIC, HEBREW, 
 *  LOCALNUMBER) of each character in the text array, input/output parameter
 * @param flags an integer representing rendering flag information, input parameter
 * @param offsets text segments that should be measured and reordered separately, input 
 *  parameter. See org.eclipse.swt.custom.BidiSegmentEvent for details.
 */
    public static void getOrderInfo(GC gc, String text, int[] order, byte[] classBuffer, int flags, int[] offsets) {
        int fontLanguageInfo = OS.GetFontLanguageInfo(gc.handle);
        int hHeap = OS.GetProcessHeap();
        int[] lpCs = new int[8];
        int cs = OS.GetTextCharset(gc.handle);
        OS.TranslateCharsetInfo(cs, lpCs, OS.TCI_SRCCHARSET);
        TCHAR textBuffer = new TCHAR(lpCs[1], text, false);
        int byteCount = textBuffer.length();
        boolean isRightOriented = false;
        if (!OS.IsWinCE && OS.WIN32_VERSION >= OS.VERSION(4, 10)) {
            isRightOriented = OS.GetLayout(gc.handle) != 0;
        }
        GCP_RESULTS result = new GCP_RESULTS();
        result.lStructSize = GCP_RESULTS.sizeof;
        result.nGlyphs = byteCount;
        int lpOrder = result.lpOrder = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount * 4);
        int lpClass = result.lpClass = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount);
        // set required dwFlags, these values will affect how the text gets rendered and
        // ordered
        int dwFlags = 0;
        // Always reorder.  We assume that if we are calling this function we're
        // on a platform that supports bidi.  Fixes 20690.
        dwFlags |= GCP_REORDER;
        if ((fontLanguageInfo & GCP_LIGATE) == GCP_LIGATE) {
            dwFlags |= GCP_LIGATE;
        }
        if ((fontLanguageInfo & GCP_GLYPHSHAPE) == GCP_GLYPHSHAPE) {
            dwFlags |= GCP_GLYPHSHAPE;
        }
        if ((flags & CLASSIN) == CLASSIN) {
            // set classification values for the substring, classification values
            // can be specified on input
            dwFlags |= GCP_CLASSIN;
            OS.MoveMemory(result.lpClass, classBuffer, classBuffer.length);
        }
        int glyphCount = 0;
        for (int i = 0; i < offsets.length - 1; i++) {
            int offset = offsets[i];
            int length = offsets[i + 1] - offsets[i];
            // The number of glyphs expected is <= length (segment length);
            // the actual number returned may be less in case of Arabic ligatures.
            result.nGlyphs = length;
            TCHAR textBuffer2 = new TCHAR(lpCs[1], text.substring(offset, offset + length), false);
            OS.GetCharacterPlacement(gc.handle, textBuffer2, textBuffer2.length(), 0, result, dwFlags);
            if (order != null) {
                int[] order2 = new int[length];
                OS.MoveMemory(order2, result.lpOrder, order2.length * 4);
                translateOrder(order2, glyphCount, isRightOriented);
                System.arraycopy(order2, 0, order, offset, length);
            }
            if (classBuffer != null) {
                byte[] classBuffer2 = new byte[length];
                OS.MoveMemory(classBuffer2, result.lpClass, classBuffer2.length);
                System.arraycopy(classBuffer2, 0, classBuffer, offset, length);
            }
            glyphCount += result.nGlyphs;
            // We concatenate successive results of calls to GCP.
            // For Arabic, it is the only good method since the number of output
            // glyphs might be less than the number of input characters.
            // This assumes that the whole line is built by successive adjacent
            // segments without overlapping.
            result.lpOrder += length * 4;
            result.lpClass += length;
        }
        /* Free the memory that was allocated. */
        OS.HeapFree(hHeap, 0, lpClass);
        OS.HeapFree(hHeap, 0, lpOrder);
    }

    /**
 * Return bidi attribute information for the font in the specified gc.  
 * <p>
 *
 * @param gc the gc to query
 * @return bitwise OR of the REORDER, LIGATE and GLYPHSHAPE flags
 * 	defined by this class.
 */
    public static int getFontBidiAttributes(GC gc) {
        int fontStyle = 0;
        int fontLanguageInfo = OS.GetFontLanguageInfo(gc.handle);
        if (((fontLanguageInfo & GCP_REORDER) != 0)) {
            fontStyle |= REORDER;
        }
        if (((fontLanguageInfo & GCP_LIGATE) != 0)) {
            fontStyle |= LIGATE;
        }
        if (((fontLanguageInfo & GCP_GLYPHSHAPE) != 0)) {
            fontStyle |= GLYPHSHAPE;
        }
        return fontStyle;
    }

    /**
 * Return the active keyboard language type.  
 * <p>
 *
 * @return an integer representing the active keyboard language (KEYBOARD_BIDI,
 *  KEYBOARD_NON_BIDI)
 */
    public static int getKeyboardLanguage() {
        int layout = OS.GetKeyboardLayout(0);
        // only interested in low 2 bytes, which is the primary
        // language identifier
        layout = layout & 0x000000FF;
        if (layout == LANG_HEBREW)
            return KEYBOARD_BIDI;
        if (layout == LANG_ARABIC)
            return KEYBOARD_BIDI;
        // return non-bidi for all other languages
        return KEYBOARD_NON_BIDI;
    }

    /**
 * Return the languages that are installed for the keyboard.  
 * <p>
 *
 * @return integer array with an entry for each installed language
 */
    static int[] getKeyboardLanguageList() {
        int maxSize = 10;
        int[] tempList = new int[maxSize];
        int size = OS.GetKeyboardLayoutList(maxSize, tempList);
        int[] list = new int[size];
        System.arraycopy(tempList, 0, list, 0, size);
        return list;
    }

    /**
 * Return whether or not the platform supports a bidi language.  Determine this
 * by looking at the languages that are installed.  
 * <p>
 *
 * @return true if bidi is supported, false otherwise. Always 
 * 	false on Windows CE.
 */
    public static boolean isBidiPlatform() {
        if (OS.IsWinCE)
            return false;
        // already set
        if (isBidiPlatform != -1)
            return isBidiPlatform == 1;
        isBidiPlatform = 0;
        // languages, but only install the Thai keyboard).
        if (!isKeyboardBidi())
            return false;
        Callback callback = null;
        try {
            //$NON-NLS-1$
            callback = new Callback(Class.forName(CLASS_NAME), "EnumSystemLanguageGroupsProc", 5);
            int lpEnumSystemLanguageGroupsProc = callback.getAddress();
            if (lpEnumSystemLanguageGroupsProc == 0)
                SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);
            OS.EnumSystemLanguageGroups(lpEnumSystemLanguageGroupsProc, OS.LGRPID_INSTALLED, 0);
            callback.dispose();
        } catch (ClassNotFoundException e) {
            if (callback != null)
                callback.dispose();
        }
        if (isBidiPlatform == 1)
            return true;
        // need to look at system code page for NT & 98 platforms since EnumSystemLanguageGroups is
        // not supported for these platforms
        String codePage = String.valueOf(OS.GetACP());
        if (CD_PG_ARABIC.equals(codePage) || CD_PG_HEBREW.equals(codePage)) {
            isBidiPlatform = 1;
        }
        return isBidiPlatform == 1;
    }

    /**
 * Return whether or not the keyboard supports input of a bidi language.  Determine this
 * by looking at the languages that are installed for the keyboard.  
 * <p>
 *
 * @return true if bidi is supported, false otherwise.
 */
    public static boolean isKeyboardBidi() {
        int[] list = getKeyboardLanguageList();
        for (int i = 0; i < list.length; i++) {
            int id = list[i] & 0x000000FF;
            if ((id == LANG_ARABIC) || (id == LANG_HEBREW)) {
                return true;
            }
        }
        return false;
    }

    /**
 * Removes the specified language listener.
 * <p>
 *
 * @param hwnd the handle of the Control that is listening for keyboard language changes
 */
    public static void removeLanguageListener(int hwnd) {
        languageMap.remove(new Integer(hwnd));
        unsubclass(hwnd);
    }

    /**
 * Switch the keyboard language to the specified language type.  We do
 * not distinguish between mulitple bidi or multiple non-bidi languages, so
 * set the keyboard to the first language of the given type.
 * <p>
 *
 * @param language integer representing language. One of 
 * 	KEYBOARD_BIDI, KEYBOARD_NON_BIDI.
 */
    public static void setKeyboardLanguage(int language) {
        // don't switch the keyboard if it doesn't need to be
        if (language == getKeyboardLanguage())
            return;
        if (language == KEYBOARD_BIDI) {
            // get the list of active languages
            int[] list = getKeyboardLanguageList();
            // set to first bidi language
            for (int i = 0; i < list.length; i++) {
                int id = list[i] & 0x000000FF;
                if ((id == LANG_ARABIC) || (id == LANG_HEBREW)) {
                    OS.ActivateKeyboardLayout(list[i], 0);
                    return;
                }
            }
        } else {
            // get the list of active languages
            int[] list = getKeyboardLanguageList();
            // hebrew or arabic)
            for (int i = 0; i < list.length; i++) {
                int id = list[i] & 0x000000FF;
                if ((id != LANG_HEBREW) && (id != LANG_ARABIC)) {
                    OS.ActivateKeyboardLayout(list[i], 0);
                    return;
                }
            }
        }
    }

    /**
 * Sets the orientation (writing order) of the specified control. Text will 
 * be right aligned for right to left writing order.
 * <p>
 * 
 * @param hwnd the handle of the Control to change the orientation of
 * @param orientation one of SWT.RIGHT_TO_LEFT or SWT.LEFT_TO_RIGHT
 * @return true if the orientation was changed, false if the orientation 
 * 	could not be changed
 */
    public static boolean setOrientation(int hwnd, int orientation) {
        if (OS.IsWinCE)
            return false;
        if (OS.WIN32_VERSION < OS.VERSION(4, 10))
            return false;
        int bits = OS.GetWindowLong(hwnd, OS.GWL_EXSTYLE);
        if ((orientation & SWT.RIGHT_TO_LEFT) != 0) {
            bits |= OS.WS_EX_LAYOUTRTL;
        } else {
            bits &= ~OS.WS_EX_LAYOUTRTL;
        }
        OS.SetWindowLong(hwnd, OS.GWL_EXSTYLE, bits);
        return true;
    }

    /**
 * Override the window proc.
 * 
 * @param hwnd control to override the window proc of
 */
    static void subclass(int hwnd) {
        Integer key = new Integer(hwnd);
        if (oldProcMap.get(key) == null) {
            int oldProc = OS.GetWindowLong(hwnd, OS.GWL_WNDPROC);
            oldProcMap.put(key, new Integer(oldProc));
            OS.SetWindowLong(hwnd, OS.GWL_WNDPROC, callback.getAddress());
        }
    }

    /**
 *  Reverse the character array.  Used for right orientation.
 * 
 * @param charArray character array to reverse
 */
    static void reverse(char[] charArray) {
        int length = charArray.length;
        for (int i = 0; i <= (length - 1) / 2; i++) {
            char tmp = charArray[i];
            charArray[i] = charArray[length - 1 - i];
            charArray[length - 1 - i] = tmp;
        }
    }

    /**
 *  Reverse the integer array.  Used for right orientation.
 * 
 * @param intArray integer array to reverse
 */
    static void reverse(int[] intArray) {
        int length = intArray.length;
        for (int i = 0; i <= (length - 1) / 2; i++) {
            int tmp = intArray[i];
            intArray[i] = intArray[length - 1 - i];
            intArray[length - 1 - i] = tmp;
        }
    }

    /**
 * Adjust the order array so that it is relative to the start of the line.  Also reverse the order array if the orientation
 * is to the right.
 * 
 * @param orderArray  integer array of order values to translate
 * @param glyphCount  number of glyphs that have been processed for the current line
 * @param isRightOriented  flag indicating whether or not current orientation is to the right
*/
    static void translateOrder(int[] orderArray, int glyphCount, boolean isRightOriented) {
        int maxOrder = 0;
        int length = orderArray.length;
        if (isRightOriented) {
            for (int i = 0; i < length; i++) {
                maxOrder = Math.max(maxOrder, orderArray[i]);
            }
        }
        for (int i = 0; i < length; i++) {
            if (isRightOriented)
                orderArray[i] = maxOrder - orderArray[i];
            orderArray[i] += glyphCount;
        }
    }

    /**
 * Remove the overridden the window proc.
 * 
 * @param hwnd control to remove the window proc override for
 */
    static void unsubclass(int hwnd) {
        Integer key = new Integer(hwnd);
        if (languageMap.get(key) == null && keyMap.get(key) == null) {
            Integer proc = (Integer) oldProcMap.remove(key);
            if (proc == null)
                return;
            OS.SetWindowLong(hwnd, OS.GWL_WNDPROC, proc.intValue());
        }
    }

    /**
 * Window proc to intercept keyboard language switch event (WS_INPUTLANGCHANGE)
 * and widget orientation changes.
 * Run the Control's registered runnable when the keyboard language is switched.
 * 
 * @param hwnd handle of the control that is listening for the keyboard language
 *  change event
 * @param msg window message
 */
    static int windowProc(int hwnd, int msg, int wParam, int lParam) {
        Integer key = new Integer(hwnd);
        switch(msg) {
            case /*OS.WM_INPUTLANGCHANGE*/
            0x51:
                Runnable runnable = (Runnable) languageMap.get(key);
                if (runnable != null)
                    runnable.run();
                break;
        }
        Integer oldProc = (Integer) oldProcMap.get(key);
        return OS.CallWindowProc(oldProc.intValue(), hwnd, msg, wParam, lParam);
    }
}
