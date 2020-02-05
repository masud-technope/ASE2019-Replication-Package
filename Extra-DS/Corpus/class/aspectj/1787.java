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

import java.io.DataOutputStream;
import java.io.IOException;
import org.aspectj.apache.bcel.Constants;
import org.aspectj.apache.bcel.classfile.ConstantPool;
import org.aspectj.apache.bcel.classfile.ConstantUtf8;

public class EnumElementValue extends ElementValue {

    // For enum types, these two indices point to the type and value
    private int typeIdx;

    private int valueIdx;

    public  EnumElementValue(int type, int typeIdx, int valueIdx, ConstantPool cpool) {
        super(type, cpool);
        if (type != ENUM_CONSTANT)
            throw new RuntimeException("Only element values of type enum can be built with this ctor");
        this.typeIdx = typeIdx;
        this.valueIdx = valueIdx;
    }

    public void dump(DataOutputStream dos) throws IOException {
        // u1 type of value (ENUM_CONSTANT == 'e')
        dos.writeByte(type);
        // u2
        dos.writeShort(typeIdx);
        // u2
        dos.writeShort(// u2
        valueIdx);
    }

    public String stringifyValue() {
        ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(valueIdx, Constants.CONSTANT_Utf8);
        return cu8.getBytes();
    }

    public String getEnumTypeString() {
        ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(typeIdx, Constants.CONSTANT_Utf8);
        //Utility.signatureToString(cu8.getBytes());
        return cu8.getBytes();
    }

    public String getEnumValueString() {
        ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(valueIdx, Constants.CONSTANT_Utf8);
        return cu8.getBytes();
    }

    public int getValueIndex() {
        return valueIdx;
    }

    public int getTypeIndex() {
        return typeIdx;
    }
}
