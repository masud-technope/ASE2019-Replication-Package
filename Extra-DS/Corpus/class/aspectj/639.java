package org.aspectj.apache.bcel.verifier.structurals;

import org.aspectj.apache.bcel.generic.Type;
import org.aspectj.apache.bcel.generic.ReferenceType;
import org.aspectj.apache.bcel.verifier.exc.*;

/**
 * This class implements an array of local variables used for symbolic JVM
 * simulation.
 *
 * @version $Id: LocalVariables.java,v 1.2 2004/11/19 16:45:19 aclement Exp $
 * @author <A HREF="http://www.inf.fu-berlin.de/~ehaase"/>Enver Haase</A>
 */
public class LocalVariables {

    /** The Type[] containing the local variable slots. */
    private Type[] locals;

    /**
	 * Creates a new LocalVariables object.
	 */
    public  LocalVariables(int maxLocals) {
        locals = new Type[maxLocals];
        for (int i = 0; i < maxLocals; i++) {
            locals[i] = Type.UNKNOWN;
        }
    }

    /**
	 * Returns a deep copy of this object; i.e. the clone
	 * operates on a new local variable array.
	 * However, the Type objects in the array are shared.
	 */
    protected Object clone() {
        LocalVariables lvs = new LocalVariables(locals.length);
        for (int i = 0; i < locals.length; i++) {
            lvs.locals[i] = this.locals[i];
        }
        return lvs;
    }

    /**
	 * Returns the type of the local variable slot i.
	 */
    public Type get(int i) {
        return locals[i];
    }

    /**
	 * Returns a (correctly typed) clone of this object.
	 * This is equivalent to ((LocalVariables) this.clone()).
	 */
    public LocalVariables getClone() {
        return (LocalVariables) this.clone();
    }

    /**
	 * Returns the number of local variable slots this
	 * LocalVariables instance has.
	 */
    public int maxLocals() {
        return locals.length;
    }

    /**
	 * Sets a new Type for the given local variable slot.
	 */
    public void set(int i, Type type) {
        if (type == Type.BYTE || type == Type.SHORT || type == Type.BOOLEAN || type == Type.CHAR) {
            throw new AssertionViolatedException("LocalVariables do not know about '" + type + "'. Use Type.INT instead.");
        }
        locals[i] = type;
    }

    /*
	 * Fulfills the general contract of Object.equals().
	 */
    public boolean equals(Object o) {
        if (!(o instanceof LocalVariables))
            return false;
        LocalVariables lv = (LocalVariables) o;
        if (this.locals.length != lv.locals.length)
            return false;
        for (int i = 0; i < this.locals.length; i++) {
            if (!this.locals[i].equals(lv.locals[i])) {
                //System.out.println(this.locals[i]+" is not "+lv.locals[i]);
                return false;
            }
        }
        return true;
    }

    /**
	 * Merges two local variables sets as described in the Java Virtual Machine Specification,
	 * Second Edition, section 4.9.2, page 146.
	 */
    public void merge(LocalVariables lv) {
        if (this.locals.length != lv.locals.length) {
            throw new AssertionViolatedException("Merging LocalVariables of different size?!? From different methods or what?!?");
        }
        for (int i = 0; i < locals.length; i++) {
            merge(lv, i);
        }
    }

    /**
	 * Merges a single local variable.
	 *
	 * @see #merge(LocalVariables)
	 */
    private void merge(LocalVariables lv, int i) {
        // compare vmspec2, 4.9.4, last paragraph.
        if ((!(locals[i] instanceof UninitializedObjectType)) && (lv.locals[i] instanceof UninitializedObjectType)) {
            throw new StructuralCodeConstraintException("Backwards branch with an uninitialized object in the local variables detected.");
        }
        // Even harder, what about _different_ uninitialized object types?!
        if ((!(locals[i].equals(lv.locals[i]))) && (locals[i] instanceof UninitializedObjectType) && (lv.locals[i] instanceof UninitializedObjectType)) {
            throw new StructuralCodeConstraintException("Backwards branch with an uninitialized object in the local variables detected.");
        }
        // If we just didn't know that it was initialized, we have now learned.
        if (locals[i] instanceof UninitializedObjectType) {
            if (!(lv.locals[i] instanceof UninitializedObjectType)) {
                locals[i] = ((UninitializedObjectType) locals[i]).getInitialized();
            }
        }
        if ((locals[i] instanceof ReferenceType) && (lv.locals[i] instanceof ReferenceType)) {
            if (// needed in case of two UninitializedObjectType instances
            !locals[i].equals(lv.locals[i])) {
                Type sup = ((ReferenceType) locals[i]).getFirstCommonSuperclass((ReferenceType) (lv.locals[i]));
                if (sup != null) {
                    locals[i] = sup;
                } else {
                    // We should have checked this in Pass2!
                    throw new AssertionViolatedException("Could not load all the super classes of '" + locals[i] + "' and '" + lv.locals[i] + "'.");
                }
            }
        } else {
            if (!(locals[i].equals(lv.locals[i]))) {
                /*TODO
				if ((locals[i] instanceof org.aspectj.apache.bcel.generic.ReturnaddressType) && (lv.locals[i] instanceof org.aspectj.apache.bcel.generic.ReturnaddressType)){
					//System.err.println("merging "+locals[i]+" and "+lv.locals[i]);
					throw new AssertionViolatedException("Merging different ReturnAddresses: '"+locals[i]+"' and '"+lv.locals[i]+"'.");
				}
*/
                locals[i] = Type.UNKNOWN;
            }
        }
    }

    /**
	 * Returns a String representation of this object.
	 */
    public String toString() {
        String s = new String();
        for (int i = 0; i < locals.length; i++) {
            s += Integer.toString(i) + ": " + locals[i] + "\n";
        }
        return s;
    }

    /**
	 * Replaces all occurences of u in this local variables set
	 * with an "initialized" ObjectType.
	 */
    public void initializeObject(UninitializedObjectType u) {
        for (int i = 0; i < locals.length; i++) {
            if (locals[i] == u) {
                locals[i] = u.getInitialized();
            }
        }
    }
}
