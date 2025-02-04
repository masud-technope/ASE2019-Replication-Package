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
package org.eclipse.swt.graphics;

import org.eclipse.swt.internal.win32.*;

public final class GCData {

    public Device device;

    public int style;

    public Image image;

    public int foreground = -1;

    public int background = -1;

    public int hPen;

    public int lineWidth;

    public int[] dashes;

    public int hBrush;

    public int hFont;

    public int hNullBitmap;

    public int hwnd;

    public PAINTSTRUCT ps;

    public int layout = -1;

    public int alpha = 0xFF;

    public int gdipGraphics;

    public int gdipPen;

    public int gdipBrush;

    public Pattern foregroundPattern;

    public Pattern backgroundPattern;
}
