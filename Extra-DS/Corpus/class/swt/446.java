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

public class IUnknown {

    int address;

    public  IUnknown(int address) {
        this.address = address;
    }

    public int AddRef() {
        return COM.VtblCall(1, address);
    }

    public int getAddress() {
        return address;
    }

    public int QueryInterface(GUID riid, int ppvObject[]) {
        return COM.VtblCall(0, address, riid, ppvObject);
    }

    public int Release() {
        return COM.VtblCall(2, address);
    }
}
