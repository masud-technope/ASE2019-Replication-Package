/* *******************************************************************
 * Copyright (c) 1999-2001 Xerox Corporation, 
 *               2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Xerox/PARC     initial implementation 
 * ******************************************************************/
package org.aspectj.ajde;

import java.util.EventListener;

/**
 * Compiler listeners get notified of structure model update events.
 *
 * XXX remove, not used
 * 
 * @author Mik Kersten
 */
public interface EditorListener extends EventListener {

    /**
     * @param filePath  full path to the current file being edited
     */
    public void currentFileChanged(String filePath);
}
