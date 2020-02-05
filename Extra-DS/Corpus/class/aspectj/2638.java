/* *******************************************************************
 * Copyright (c) 2004 IBM
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Andy Clement -     initial implementation 
 * ******************************************************************/
package org.aspectj.apache.bcel.generic.annotation;

import java.io.DataOutputStream;
import java.io.IOException;
import org.aspectj.apache.bcel.classfile.ConstantUtf8;
import org.aspectj.apache.bcel.classfile.annotation.ElementNameValuePair;
import org.aspectj.apache.bcel.classfile.annotation.ElementValue;
import org.aspectj.apache.bcel.generic.ConstantPoolGen;

public class ElementNameValuePairGen {

    private int nameIdx;

    private ElementValueGen value;

    private ConstantPoolGen cpool;

    public  ElementNameValuePairGen(ElementNameValuePair nvp, ConstantPoolGen cpool, boolean copyPoolEntries) {
        this.cpool = cpool;
        //		}
        if (copyPoolEntries) {
            nameIdx = cpool.addUtf8(nvp.getNameString());
        } else {
            nameIdx = nvp.getNameIndex();
        }
        value = ElementValueGen.copy(nvp.getValue(), cpool, copyPoolEntries);
    }

    /**
	 * Retrieve an immutable version of this ElementNameValuePairGen
	 */
    public ElementNameValuePair getElementNameValuePair() {
        ElementValue immutableValue = value.getElementValue();
        return new ElementNameValuePair(nameIdx, immutableValue, cpool.getConstantPool());
    }

    protected  ElementNameValuePairGen(int idx, ElementValueGen value, ConstantPoolGen cpool) {
        this.nameIdx = idx;
        this.value = value;
        this.cpool = cpool;
    }

    public  ElementNameValuePairGen(String name, ElementValueGen value, ConstantPoolGen cpool) {
        this.nameIdx = cpool.addUtf8(name);
        this.value = value;
        this.cpool = cpool;
    }

    protected void dump(DataOutputStream dos) throws IOException {
        // u2 name of the element
        dos.writeShort(nameIdx);
        value.dump(dos);
    }

    public int getNameIndex() {
        return nameIdx;
    }

    public final String getNameString() {
        //	  ConstantString cu8 = (ConstantString)cpool.getConstant(nameIdx);
        return ((ConstantUtf8) cpool.getConstant(nameIdx)).getBytes();
    }

    public final ElementValueGen getValue() {
        return value;
    }

    public String toString() {
        return "ElementNameValuePair:[" + getNameString() + "=" + value.stringifyValue() + "]";
    }
}
