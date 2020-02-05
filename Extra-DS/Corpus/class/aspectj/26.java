package org.aspectj.apache.bcel.verifier.structurals;

import org.aspectj.apache.bcel.generic.*;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * This class allows easy access to ExceptionHandler objects.
 *
 * @version $Id: ExceptionHandlers.java,v 1.2 2004/11/19 16:45:19 aclement Exp $
 * @author <A HREF="http://www.inf.fu-berlin.de/~ehaase"/>Enver Haase</A>
 */
public class ExceptionHandlers {

    /**
	 * The ExceptionHandler instances.
	 * Key: InstructionHandle objects, Values: HashSet<ExceptionHandler> instances.
	 */
    private Hashtable exceptionhandlers;

    /**
	 * Constructor. Creates a new ExceptionHandlers instance.
	 */
    public  ExceptionHandlers(MethodGen mg) {
        exceptionhandlers = new Hashtable();
        CodeExceptionGen[] cegs = mg.getExceptionHandlers();
        for (int i = 0; i < cegs.length; i++) {
            ExceptionHandler eh = new ExceptionHandler(cegs[i].getCatchType(), cegs[i].getHandlerPC());
            for (InstructionHandle ih = cegs[i].getStartPC(); ih != cegs[i].getEndPC().getNext(); ih = ih.getNext()) {
                HashSet hs;
                hs = (HashSet) exceptionhandlers.get(ih);
                if (hs == null) {
                    hs = new HashSet();
                    exceptionhandlers.put(ih, hs);
                }
                hs.add(eh);
            }
        }
    }

    /**
	 * Returns all the ExceptionHandler instances representing exception
	 * handlers that protect the instruction ih.
	 */
    public ExceptionHandler[] getExceptionHandlers(InstructionHandle ih) {
        HashSet hs = (HashSet) exceptionhandlers.get(ih);
        if (hs == null)
            return new ExceptionHandler[0];
        else {
            ExceptionHandler[] ret = new ExceptionHandler[hs.size()];
            return (ExceptionHandler[]) (hs.toArray(ret));
        }
    }
}
