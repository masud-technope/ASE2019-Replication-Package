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

import org.eclipse.swt.*;

class PngIendChunk extends PngChunk {

     PngIendChunk(byte[] reference) {
        super(reference);
    }

    /**
 * Answer whether the chunk is a valid IEND chunk.
 */
    void validate(PngFileReadState readState, PngIhdrChunk headerChunk) {
        // Or if no IDAT chunk has been read.
        if (!readState.readIHDR || (headerChunk.getMustHavePalette() && !readState.readPLTE) || !readState.readIDAT || readState.readIEND) {
            SWT.error(SWT.ERROR_INVALID_IMAGE);
        } else {
            readState.readIEND = true;
        }
        super.validate(readState, headerChunk);
        // IEND chunks are not allowed to have any data.
        if (getLength() > 0)
            SWT.error(SWT.ERROR_INVALID_IMAGE);
    }
}
