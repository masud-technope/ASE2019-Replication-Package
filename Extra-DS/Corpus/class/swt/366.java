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
package org.eclipse.swt.internal.image;

final class JPEGStartOfImage extends JPEGFixedSizeSegment {

    public  JPEGStartOfImage() {
        super();
    }

    public  JPEGStartOfImage(byte[] reference) {
        super(reference);
    }

    public  JPEGStartOfImage(LEDataInputStream byteStream) {
        super(byteStream);
    }

    public int signature() {
        return JPEGFileFormat.SOI;
    }

    public int fixedSize() {
        return 2;
    }
}
