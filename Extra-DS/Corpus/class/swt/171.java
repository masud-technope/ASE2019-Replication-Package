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
package org.eclipse.swt.internal.win32;

public class CREATESTRUCT {

    public int lpCreateParams;

    public int hInstance;

    public int hMenu;

    public int hwndParent;

    public int cy;

    public int cx;

    public int y;

    public int x;

    public int style;

    public int lpszName;

    public int lpszClass;

    public int dwExStyle;

    public static final int sizeof = 48;
}
