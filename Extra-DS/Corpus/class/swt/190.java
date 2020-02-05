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

public class IConnectionPoint extends IUnknown {

    public  IConnectionPoint(int address) {
        super(address);
    }

    public int Advise(int pUnk, int[] pdwCookie) {
        return COM.VtblCall(5, address, pUnk, pdwCookie);
    }

    public int Unadvise(int dwCookie) {
        return COM.VtblCall(6, address, dwCookie);
    }
}
