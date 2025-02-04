package org.aspectj.apache.bcel.generic;

import java.util.ArrayList;
import org.aspectj.apache.bcel.Constants;
import org.aspectj.apache.bcel.classfile.ClassFormatException;
import org.aspectj.apache.bcel.classfile.Utility;

/** 
 * Abstract super class for all possible java types, namely basic types
 * such as int, object types like String and array types, e.g. int[]
 *
 * @version $Id: Type.java,v 1.6 2005/09/21 15:02:04 acolyer Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * 
 * modified:
 * AndyClement 2-mar-05: Removed unnecessary static and optimized
 */
public abstract class Type implements java.io.Serializable {

    protected byte type;

    // signature for the type
    protected String signature;

    /* Predefined constants */
    public static final BasicType VOID = new BasicType(Constants.T_VOID);

    public static final BasicType BOOLEAN = new BasicType(Constants.T_BOOLEAN);

    public static final BasicType INT = new BasicType(Constants.T_INT);

    public static final BasicType SHORT = new BasicType(Constants.T_SHORT);

    public static final BasicType BYTE = new BasicType(Constants.T_BYTE);

    public static final BasicType LONG = new BasicType(Constants.T_LONG);

    public static final BasicType DOUBLE = new BasicType(Constants.T_DOUBLE);

    public static final BasicType FLOAT = new BasicType(Constants.T_FLOAT);

    public static final BasicType CHAR = new BasicType(Constants.T_CHAR);

    public static final ObjectType OBJECT = new ObjectType("java.lang.Object");

    public static final ObjectType STRING = new ObjectType("java.lang.String");

    public static final ObjectType STRINGBUFFER = new ObjectType("java.lang.StringBuffer");

    public static final ObjectType THROWABLE = new ObjectType("java.lang.Throwable");

    public static final Type[] NO_ARGS = new Type[0];

    public static final ReferenceType NULL = new ReferenceType() {
    };

    public static final Type UNKNOWN = new Type(Constants.T_UNKNOWN, "<unknown object>") {
    };

    protected  Type(byte t, String s) {
        type = t;
        signature = s;
    }

    /**
   * @return signature for given type.
   */
    public String getSignature() {
        return signature;
    }

    /**
   * @return type as defined in Constants
   */
    public byte getType() {
        return type;
    }

    /**
   * @return stack size of this type (2 for long and double, 0 for void, 1 otherwise)
   */
    public int getSize() {
        switch(type) {
            case Constants.T_DOUBLE:
            case Constants.T_LONG:
                return 2;
            case Constants.T_VOID:
                return 0;
            default:
                return 1;
        }
    }

    /**
   * @return Type string, e.g. 'int[]'
   */
    public String toString() {
        return ((this.equals(Type.NULL) || (type >= Constants.T_UNKNOWN))) ? signature : Utility.signatureToString(signature, false);
    }

    /**
   * Convert type to Java method signature, e.g. int[] f(java.lang.String x)
   * becomes (Ljava/lang/String;)[I
   *
   * @param return_type what the method returns
   * @param arg_types what are the argument types
   * @return method signature for given type(s).
   */
    public static String getMethodSignature(Type return_type, Type[] arg_types) {
        StringBuffer buf = new StringBuffer("(");
        int length = (arg_types == null) ? 0 : arg_types.length;
        for (int i = 0; i < length; i++) buf.append(arg_types[i].getSignature());
        buf.append(')');
        buf.append(return_type.getSignature());
        return buf.toString();
    }

    // private static int consumed_chars=0; // Remember position in string, see getArgumentTypes
    public static final Type getType(String signature) {
        TypeHolder th = getTypeInternal(signature);
        return th.getType();
    }

    /**
   * Convert signature to a Type object.
   * @param signature signature string such as Ljava/lang/String;
   * @return type object
   */
    public static final TypeHolder getTypeInternal(String signature) throws StringIndexOutOfBoundsException {
        byte type = Utility.typeOfSignature(signature);
        if (type <= Constants.T_VOID) {
            return new TypeHolder(BasicType.getType(type), 1);
        } else if (type == Constants.T_ARRAY) {
            int dim = 0;
            do {
                dim++;
            } while (signature.charAt(dim) == '[');
            // Recurse, but just once, if the signature is ok
            TypeHolder th = getTypeInternal(signature.substring(dim));
            return new TypeHolder(new ArrayType(th.getType(), dim), dim + th.getConsumed());
        } else {
            // type == T_REFERENCE
            // Format is 'Lblahblah;'
            // Look for closing ';'
            int index = signature.indexOf(';');
            if (index < 0)
                throw new ClassFormatException("Invalid signature: " + signature);
            // generics awareness
            int nextAngly = signature.indexOf('<');
            String typeString = null;
            if (nextAngly == -1 || nextAngly > index) {
                typeString = signature.substring(1, index).replace('/', '.');
            } else {
                boolean endOfSigReached = false;
                int posn = nextAngly;
                int genericDepth = 0;
                while (!endOfSigReached) {
                    switch(signature.charAt(posn++)) {
                        case '<':
                            genericDepth++;
                            break;
                        case '>':
                            genericDepth--;
                            break;
                        case ';':
                            if (genericDepth == 0)
                                endOfSigReached = true;
                            break;
                        default:
                    }
                }
                index = posn - 1;
                typeString = signature.substring(1, nextAngly).replace('/', '.');
            }
            // ObjectType doesn't currently store parameterized info
            return new TypeHolder(new ObjectType(typeString), index + 1);
        }
    }

    /**
   * Convert return value of a method (signature) to a Type object.
   *
   * @param signature signature string such as (Ljava/lang/String;)V
   * @return return type
   */
    public static Type getReturnType(String signature) {
        try {
            // Read return type after `)'
            int index = signature.lastIndexOf(')') + 1;
            return getType(signature.substring(index));
        } catch (StringIndexOutOfBoundsException // Should never occur
        e) {
            throw new ClassFormatException("Invalid method signature: " + signature);
        }
    }

    /**
   * Convert arguments of a method (signature) to an array of Type objects.
   * @param signature signature string such as (Ljava/lang/String;)V
   * @return array of argument types
   */
    public static Type[] getArgumentTypes(String signature) {
        ArrayList vec = new ArrayList();
        int index;
        Type[] types;
        try {
            // Read all declarations between for `(' and `)'
            if (signature.charAt(0) != '(')
                throw new ClassFormatException("Invalid method signature: " + signature);
            // current string position
            index = 1;
            while (signature.charAt(index) != ')') {
                TypeHolder th = getTypeInternal(signature.substring(index));
                vec.add(th.getType());
                // update position
                index += th.getConsumed();
            }
        } catch (StringIndexOutOfBoundsException // Should never occur
        e) {
            throw new ClassFormatException("Invalid method signature: " + signature);
        }
        types = new Type[vec.size()];
        vec.toArray(types);
        return types;
    }

    /** Convert runtime java.lang.Class to BCEL Type object.
   * @param cl Java class
   * @return corresponding Type object
   */
    public static Type getType(java.lang.Class cl) {
        if (cl == null) {
            throw new IllegalArgumentException("Class must not be null");
        }
        /* That's an amazingly easy case, because getName() returns
     * the signature. That's what we would have liked anyway.
     */
        if (cl.isArray()) {
            return getType(cl.getName());
        } else if (cl.isPrimitive()) {
            if (cl == Integer.TYPE) {
                return INT;
            } else if (cl == Void.TYPE) {
                return VOID;
            } else if (cl == Double.TYPE) {
                return DOUBLE;
            } else if (cl == Float.TYPE) {
                return FLOAT;
            } else if (cl == Boolean.TYPE) {
                return BOOLEAN;
            } else if (cl == Byte.TYPE) {
                return BYTE;
            } else if (cl == Short.TYPE) {
                return SHORT;
            } else if (cl == Byte.TYPE) {
                return BYTE;
            } else if (cl == Long.TYPE) {
                return LONG;
            } else if (cl == Character.TYPE) {
                return CHAR;
            } else {
                throw new IllegalStateException("Ooops, what primitive type is " + cl);
            }
        } else {
            // "Real" class
            return new ObjectType(cl.getName());
        }
    }

    public static String getSignature(java.lang.reflect.Method meth) {
        StringBuffer sb = new StringBuffer("(");
        // avoid clone
        Class[] params = meth.getParameterTypes();
        for (int j = 0; j < params.length; j++) {
            sb.append(getType(params[j]).getSignature());
        }
        sb.append(")");
        sb.append(getType(meth.getReturnType()).getSignature());
        return sb.toString();
    }

    public static String getSignature(java.lang.reflect.Constructor cons) {
        StringBuffer sb = new StringBuffer("(");
        // avoid clone
        Class[] params = cons.getParameterTypes();
        for (int j = 0; j < params.length; j++) {
            sb.append(getType(params[j]).getSignature());
        }
        sb.append(")V");
        return sb.toString();
    }

    public static class TypeHolder {

        private Type t;

        private int consumed;

        public Type getType() {
            return t;
        }

        public int getConsumed() {
            return consumed;
        }

        public  TypeHolder(Type t, int i) {
            this.t = t;
            this.consumed = i;
        }
    }
}
