package org.aspectj.apache.bcel.generic;

/**
 * Implement this interface if you're interested in changes to a MethodGen object
 * and register yourself with addObserver().
 *
 * @version $Id: MethodObserver.java,v 1.2 2004/11/19 16:45:18 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface MethodObserver {

    public void notify(MethodGen method);
}
