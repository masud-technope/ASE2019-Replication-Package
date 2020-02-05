/* *******************************************************************
 * Copyright (c) 2004 IBM
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Andy Clement -     initial implementation {date}
 * ******************************************************************/
package org.aspectj.apache.bcel.classfile.annotation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.apache.bcel.classfile.ConstantPool;

public abstract class ElementValue {

    protected int type;

    protected ConstantPool cpool;

    public String toString() {
        return stringifyValue();
    }

    protected  ElementValue(int type, ConstantPool cpool) {
        this.type = type;
        this.cpool = cpool;
    }

    public int getElementValueType() {
        return type;
    }

    public abstract String stringifyValue();

    public abstract void dump(DataOutputStream dos) throws IOException;

    public static final int STRING = 's';

    public static final int ENUM_CONSTANT = 'e';

    public static final int CLASS = 'c';

    public static final int ANNOTATION = '@';

    public static final int ARRAY = '[';

    public static final int PRIMITIVE_INT = 'I';

    public static final int PRIMITIVE_BYTE = 'B';

    public static final int PRIMITIVE_CHAR = 'C';

    public static final int PRIMITIVE_DOUBLE = 'D';

    public static final int PRIMITIVE_FLOAT = 'F';

    public static final int PRIMITIVE_LONG = 'J';

    public static final int PRIMITIVE_SHORT = 'S';

    public static final int PRIMITIVE_BOOLEAN = 'Z';

    public static ElementValue readElementValue(DataInputStream dis, ConstantPool cpool) throws IOException {
        int type = dis.readUnsignedByte();
        switch(type) {
            case // byte
            'B':
                return new SimpleElementValue(PRIMITIVE_BYTE, dis.readUnsignedShort(), cpool);
            case // char
            'C':
                return new SimpleElementValue(PRIMITIVE_CHAR, dis.readUnsignedShort(), cpool);
            case // double
            'D':
                return new SimpleElementValue(PRIMITIVE_DOUBLE, dis.readUnsignedShort(), cpool);
            case // float
            'F':
                return new SimpleElementValue(PRIMITIVE_FLOAT, dis.readUnsignedShort(), cpool);
            case // int
            'I':
                return new SimpleElementValue(PRIMITIVE_INT, dis.readUnsignedShort(), cpool);
            case // long
            'J':
                return new SimpleElementValue(PRIMITIVE_LONG, dis.readUnsignedShort(), cpool);
            case // short
            'S':
                return new SimpleElementValue(PRIMITIVE_SHORT, dis.readUnsignedShort(), cpool);
            case // boolean
            'Z':
                return new SimpleElementValue(PRIMITIVE_BOOLEAN, dis.readUnsignedShort(), cpool);
            case // String
            's':
                return new SimpleElementValue(STRING, dis.readUnsignedShort(), cpool);
            case // Enum constant
            'e':
                return new EnumElementValue(ENUM_CONSTANT, dis.readUnsignedShort(), dis.readUnsignedShort(), cpool);
            case // Class
            'c':
                return new ClassElementValue(CLASS, dis.readUnsignedShort(), cpool);
            // the same as the 'super annotation' in which we are contained?
            case // Annotation
            '@':
                return new AnnotationElementValue(ANNOTATION, Annotation.read(dis, cpool, true), cpool);
            case // Array
            '[':
                int numArrayVals = dis.readUnsignedShort();
                List arrayVals = new ArrayList();
                ElementValue[] evalues = new ElementValue[numArrayVals];
                for (int j = 0; j < numArrayVals; j++) {
                    evalues[j] = ElementValue.readElementValue(dis, cpool);
                }
                return new ArrayElementValue(ARRAY, evalues, cpool);
            default:
                throw new RuntimeException("Unexpected element value kind in annotation: " + type);
        }
    }

    public String toShortString() {
        StringBuffer result = new StringBuffer();
        result.append(stringifyValue());
        return result.toString();
    }
}
