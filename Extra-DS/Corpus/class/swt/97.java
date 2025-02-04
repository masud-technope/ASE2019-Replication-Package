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

/**
 * Instances of this class describe device-independent paths.
 *
 * @see Path
 * 
 * @since 3.1
 */
public class PathData {

    /**
	 * The type of each points. 
	 */
    public byte[] types;

    /**
	 * The points of a path.
	 */
    public float[] points;
}
