/* *******************************************************************
 * Copyright (c) 2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     PARC     initial implementation 
 * ******************************************************************/
package org.aspectj.ajdt.internal.core.builder;

import java.io.File;
import org.aspectj.ajdt.internal.compiler.lookup.EclipseSourceLocation;
import org.aspectj.bridge.ISourceLocation;
import org.aspectj.bridge.SourceLocation;
import org.aspectj.weaver.IHasPosition;
import org.aspectj.weaver.ISourceContext;
import org.aspectj.org.eclipse.jdt.internal.compiler.CompilationResult;

public class EclipseSourceContext implements ISourceContext {

    CompilationResult result;

    int offset = 0;

    public  EclipseSourceContext(CompilationResult result) {
        this.result = result;
    }

    public  EclipseSourceContext(CompilationResult result, int offset) {
        this.result = result;
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    private File getSourceFile() {
        return new File(new String(result.fileName));
    }

    public ISourceLocation makeSourceLocation(IHasPosition position) {
        return new EclipseSourceLocation(result, position.getStart(), position.getEnd());
    }

    public ISourceLocation makeSourceLocation(int line, int offset) {
        SourceLocation sl = new SourceLocation(getSourceFile(), line);
        if (offset > 0) {
            sl.setOffset(offset);
        } else {
            // compute the offset
            //TODO AV - should we do it lazily?
            int[] offsets = result.lineSeparatorPositions;
            int likelyOffset = 0;
            if (line > 0 && line < offsets.length) {
                //1st char of given line is next char after previous end of line
                //FIXME may be need -2
                likelyOffset = offsets[line - 1];
            }
            sl.setOffset(likelyOffset);
        }
        return sl;
    }

    public void tidy() {
        result = null;
    }
}
