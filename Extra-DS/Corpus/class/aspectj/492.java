/*******************************************************************************
 * Copyright (c) 2004 IBM 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *    Andy Clement - initial API and implementation
 *******************************************************************************/
package org.aspectj.weaver;

/**
 * Some useful weaver constants. 
 *
 * Current uses:
 * 1. Holds values that are necessary for working with 1.5 code but
 *    which don't exist in a 1.4 world.  
 */
public interface Constants {

    public static final int ACC_BRIDGE = 0x0040;

    public static final int ACC_VARARGS = 0x0080;

    public static final String RUNTIME_LEVEL_12 = "1.2";

    public static final String RUNTIME_LEVEL_15 = "1.5";

    // Default for 1.5.0
    public static final String RUNTIME_LEVEL_DEFAULT = RUNTIME_LEVEL_15;
}
