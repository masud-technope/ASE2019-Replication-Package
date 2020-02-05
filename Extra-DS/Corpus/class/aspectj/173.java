/* *******************************************************************
 * Copyright (c) 2003 Contributors.
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Mik Kersten     initial implementation 
 * ******************************************************************/
package org.aspectj.asm;

import java.io.File;
import org.aspectj.bridge.ISourceLocation;

/**
 * Adapter used to uniquely identify program element handles.  Can be
 * implemented and overridden in @see{AsmManager} in order to provide
 * IDE-specific mechanisms of identifying elements.  For example, AJDT
 * uses workspace-relative paths that are understood by its JavaCore 
 * class.  
 * 
 * @author Mik Kersten
 */
public interface IElementHandleProvider {

    /**
     * @return  a String uniquely identifying this element
     */
    public String createHandleIdentifier(ISourceLocation location);

    /**
     * @return  a String uniquely identifying this element
     */
    public String createHandleIdentifier(File sourceFile, int line, int column, int offset);

    /**
     * NOTE: this is necessary for the current implementation to look up nodes, 
     * but we may want to consider removing it.
     * 
     * @return a String corresponding to the  
     */
    public String getFileForHandle(String handle);

    /**
     * NOTE: this is necessary for the current implementation to look up nodes, 
     * but we may want to consider removing it.
     * 
     * @return the line number corresponding to this handel
     */
    public int getLineNumberForHandle(String handle);

    public int getOffSetForHandle(String handle);
}
