/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.browser;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

/**
 * A <code>LocationEvent</code> is sent by a {@link Browser} to
 * {@link LocationListener}'s when the <code>Browser</code>
 * navigates to a different URL. This notification typically 
 * occurs when the application navigates to a new location with 
 * {@link Browser#setUrl(String)} or when the user activates a
 * hyperlink.
 * 
 * @since 3.0
 */
public class LocationEvent extends TypedEvent {

    /** current location */
    public String location;

    /**
	 * A flag indicating whether the location opens in the top frame
	 * or not.
	 */
    public boolean top;

    /**
	 * A flag indicating whether the location loading should be allowed.
	 * Setting this field to <code>false</code> will cancel the operation.
	 */
    public boolean doit;

    static final long serialVersionUID = 3906644198244299574L;

     LocationEvent(Widget w) {
        super(w);
    }
}
