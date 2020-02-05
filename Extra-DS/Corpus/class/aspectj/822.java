package org.aspectj.apache.bcel.verifier.statics;

import org.aspectj.apache.bcel.generic.Type;
import org.aspectj.apache.bcel.verifier.exc.*;
import java.util.Hashtable;

/**
 * A utility class holding the information about
 * the name and the type of a local variable in
 * a given slot (== index). This information
 * often changes in course of byte code offsets.
 *
 * @version $Id: LocalVariableInfo.java,v 1.2 2004/11/19 16:45:19 aclement Exp $
 * @author <A HREF="http://www.inf.fu-berlin.de/~ehaase"/>Enver Haase</A>
 */
public class LocalVariableInfo {

    /** The types database. KEY: String representing the offset integer. */
    private Hashtable types = new Hashtable();

    /** The names database. KEY: String representing the offset integer. */
    private Hashtable names = new Hashtable();

    /**
	 * Adds a name of a local variable and a certain slot to our 'names'
	 * (Hashtable) database.
	 */
    private void setName(int offset, String name) {
        names.put(((Integer.toString(offset))), name);
    }

    /**
	 * Adds a type of a local variable and a certain slot to our 'types'
	 * (Hashtable) database.
	 */
    private void setType(int offset, Type t) {
        types.put(((Integer.toString(offset))), t);
    }

    /**
	 * Returns the type of the local variable that uses this local
	 * variable slot at the given bytecode offset.
	 * Care for legal bytecode offsets yourself, otherwise the return value
	 * might be wrong.
	 * May return 'null' if nothing is known about the type of this local
	 * variable slot at the given bytecode offset.
	 */
    public Type getType(int offset) {
        return (Type) types.get(Integer.toString(offset));
    }

    /**
	 * Returns the name of the local variable that uses this local
	 * variable slot at the given bytecode offset.
	 * Care for legal bytecode offsets yourself, otherwise the return value
	 * might be wrong.
	 * May return 'null' if nothing is known about the type of this local
	 * variable slot at the given bytecode offset.
	 */
    public String getName(int offset) {
        return (String) (names.get(Integer.toString(offset)));
    }

    /**
	 * Adds some information about this local variable (slot).
	 * @throws LocalVariableInfoInconsistentException if the new information conflicts
	 *         with already gathered information.
	 */
    public void add(String name, int startpc, int length, Type t) throws LocalVariableInfoInconsistentException {
        for (// incl/incl-notation!
        int i = startpc; // incl/incl-notation!
        i <= startpc + length; // incl/incl-notation!
        i++) {
            add(i, name, t);
        }
    }

    /**
	 * Adds information about name and type for a given offset.
	 * @throws LocalVariableInfoInconsistentException if the new information conflicts
	 *         with already gathered information.
	 */
    private void add(int offset, String name, Type t) throws LocalVariableInfoInconsistentException {
        if (getName(offset) != null) {
            if (!getName(offset).equals(name)) {
                throw new LocalVariableInfoInconsistentException("At bytecode offset '" + offset + "' a local variable has two different names: '" + getName(offset) + "' and '" + name + "'.");
            }
        }
        if (getType(offset) != null) {
            if (!getType(offset).equals(t)) {
                throw new LocalVariableInfoInconsistentException("At bytecode offset '" + offset + "' a local variable has two different types: '" + getType(offset) + "' and '" + t + "'.");
            }
        }
        setName(offset, name);
        setType(offset, t);
    }
}
