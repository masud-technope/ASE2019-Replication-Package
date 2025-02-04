/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.OS;

public class ITypeInfo extends IUnknown {

    public  ITypeInfo(int address) {
        super(address);
    }

    public int GetDocumentation(int index, String[] name, String[] docString, int[] pdwHelpContext, String[] helpFile) {
        int[] pBstrName = null;
        if (name != null)
            pBstrName = new int[1];
        int[] pBstrDocString = null;
        if (docString != null)
            pBstrDocString = new int[1];
        int[] pBstrHelpFile = null;
        if (helpFile != null)
            pBstrHelpFile = new int[1];
        int rc = COM.VtblCall(12, address, index, pBstrName, pBstrDocString, pdwHelpContext, pBstrHelpFile);
        if (name != null && pBstrName[0] != 0) {
            int size = COM.SysStringByteLen(pBstrName[0]);
            if (size > 0) {
                // get the unicode character array from the global memory and create a String
                // add one to avoid rounding errors
                char[] buffer = new char[(size + 1) / 2];
                COM.MoveMemory(buffer, pBstrName[0], size);
                name[0] = new String(buffer);
                int subindex = name[0].indexOf("\0");
                if (subindex > 0)
                    name[0] = name[0].substring(0, subindex);
            }
            COM.SysFreeString(pBstrName[0]);
        }
        if (docString != null && pBstrDocString[0] != 0) {
            int size = COM.SysStringByteLen(pBstrDocString[0]);
            if (size > 0) {
                // get the unicode character array from the global memory and create a String
                // add one to avoid rounding errors
                char[] buffer = new char[(size + 1) / 2];
                COM.MoveMemory(buffer, pBstrDocString[0], size);
                docString[0] = new String(buffer);
                int subindex = docString[0].indexOf("\0");
                if (subindex > 0)
                    docString[0] = docString[0].substring(0, subindex);
            }
            COM.SysFreeString(pBstrDocString[0]);
        }
        if (helpFile != null && pBstrHelpFile[0] != 0) {
            int size = COM.SysStringByteLen(pBstrHelpFile[0]);
            if (size > 0) {
                // get the unicode character array from the global memory and create a String
                // add one to avoid rounding errors
                char[] buffer = new char[(size + 1) / 2];
                COM.MoveMemory(buffer, pBstrHelpFile[0], size);
                helpFile[0] = new String(buffer);
                int subindex = helpFile[0].indexOf("\0");
                if (subindex > 0)
                    helpFile[0] = helpFile[0].substring(0, subindex);
            }
            COM.SysFreeString(pBstrHelpFile[0]);
        }
        return rc;
    }

    public int GetFuncDesc(int index, int[] ppFuncDesc) {
        return COM.VtblCall(5, address, index, ppFuncDesc);
    }

    public int GetIDsOfNames(String[] rgszNames, int cNames, int[] pMemId) {
        char[] buffer;
        int size = rgszNames.length;
        // create an array to hold the addresses
        int hHeap = OS.GetProcessHeap();
        int ppNames = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, size * 4);
        int[] memTracker = new int[size];
        try {
            for (int i = 0; i < size; i++) {
                // create a null terminated array of char for each String
                int nameSize = rgszNames[i].length();
                buffer = new char[nameSize + 1];
                rgszNames[i].getChars(0, nameSize, buffer, 0);
                // get the address of the start of the array of char
                int pName = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, buffer.length * 2);
                OS.MoveMemory(pName, buffer, buffer.length * 2);
                // copy the address to the array of addresses
                COM.MoveMemory(ppNames + 4 * i, new int[] { pName }, 4);
                // keep track of the Global Memory so we can free it
                memTracker[i] = pName;
            }
            return COM.VtblCall(10, address, ppNames, cNames, pMemId);
        } finally {
            // free the memory
            for (int i = 0; i < memTracker.length; i++) {
                OS.HeapFree(hHeap, 0, memTracker[i]);
            }
            OS.HeapFree(hHeap, 0, ppNames);
        }
    }

    public int GetImplTypeFlags(int index, int[] pImplTypeFlags) {
        return COM.VtblCall(9, address, index, pImplTypeFlags);
    }

    public int GetNames(int memid, String[] names, int cMaxNames, int[] pcNames) {
        int nameSize = names.length;
        int[] rgBstrNames = new int[nameSize];
        int rc = COM.VtblCall(7, address, memid, rgBstrNames, nameSize, pcNames);
        if (rc == COM.S_OK) {
            for (int i = 0; i < pcNames[0]; i++) {
                int size = COM.SysStringByteLen(rgBstrNames[i]);
                if (size > 0) {
                    // get the unicode character array from the global memory and create a String
                    // add one to avoid rounding errors
                    char[] buffer = new char[(size + 1) / 2];
                    COM.MoveMemory(buffer, rgBstrNames[i], size);
                    names[i] = new String(buffer);
                    int subindex = names[i].indexOf("\0");
                    if (subindex > 0)
                        names[i] = names[i].substring(0, subindex);
                }
                COM.SysFreeString(rgBstrNames[i]);
            }
        }
        return rc;
    }

    public int GetRefTypeInfo(int hRefType, int[] ppTInfo) {
        return COM.VtblCall(14, address, hRefType, ppTInfo);
    }

    public int GetRefTypeOfImplType(int index, int[] pRefType) {
        return COM.VtblCall(8, address, index, pRefType);
    }

    public int GetTypeAttr(int[] ppTypeAttr) {
        return COM.VtblCall(3, address, ppTypeAttr);
    }

    public int GetVarDesc(int index, int[] ppVarDesc) {
        return COM.VtblCall(6, address, index, ppVarDesc);
    }

    public int ReleaseFuncDesc(int pFuncDesc) {
        return COM.VtblCall(20, address, pFuncDesc);
    }

    public int ReleaseTypeAttr(int pTypeAttr) {
        return COM.VtblCall(19, address, pTypeAttr);
    }

    public int ReleaseVarDesc(int pVarDesc) {
        return COM.VtblCall(21, address, pVarDesc);
    }
}
