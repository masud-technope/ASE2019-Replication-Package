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
import java.util.List;

/**
 * @author Mik Kersten
 * @deprecated BuildConfigListener XXX unused - delete ConfigurationListener
 */
public interface ConfigurationListener extends EventListener {

    /**
	 * Called when the current configuration has changed.
	 * 
	 * @param	configFilePath	the path to the new current configuration file
	 */
    public void currConfigChanged(String configFilePath);

    /**
	 * Called when items are added to or deleted from the configurations list.
	 */
    public void configsListUpdated(List configsList);
}
