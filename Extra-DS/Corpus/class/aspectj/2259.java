/* *******************************************************************
 * Copyright (c) 2004 IBM Corporation
 * 
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *    Andy Clement     initial implementation 
 *    Heavily based on LocalVariableTable
 * ******************************************************************/
package org.aspectj.apache.bcel.classfile;

import org.aspectj.apache.bcel.Constants;
import java.io.*;

// J5TODO: Needs some testing !
public class LocalVariableTypeTable extends Attribute {

    // Table of local
    private int local_variable_type_table_length;

    // variables
    private LocalVariable[] local_variable_type_table;

    public  LocalVariableTypeTable(LocalVariableTypeTable c) {
        this(c.getNameIndex(), c.getLength(), c.getLocalVariableTypeTable(), c.getConstantPool());
    }

    public  LocalVariableTypeTable(int name_index, int length, LocalVariable[] local_variable_table, ConstantPool constant_pool) {
        super(Constants.ATTR_LOCAL_VARIABLE_TYPE_TABLE, name_index, length, constant_pool);
        setLocalVariableTable(local_variable_table);
    }

     LocalVariableTypeTable(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
        this(nameIdx, len, (LocalVariable[]) null, cpool);
        local_variable_type_table_length = (dis.readUnsignedShort());
        local_variable_type_table = new LocalVariable[local_variable_type_table_length];
        for (int i = 0; i < local_variable_type_table_length; i++) local_variable_type_table[i] = new LocalVariable(dis, cpool);
    }

    public void accept(Visitor v) {
        v.visitLocalVariableTypeTable(this);
    }

    public final void dump(DataOutputStream file) throws IOException {
        super.dump(file);
        file.writeShort(local_variable_type_table_length);
        for (int i = 0; i < local_variable_type_table_length; i++) local_variable_type_table[i].dump(file);
    }

    public final LocalVariable[] getLocalVariableTypeTable() {
        return local_variable_type_table;
    }

    public final LocalVariable getLocalVariable(int index) {
        for (int i = 0; i < local_variable_type_table_length; i++) if (local_variable_type_table[i].getIndex() == index)
            return local_variable_type_table[i];
        return null;
    }

    public final void setLocalVariableTable(LocalVariable[] local_variable_table) {
        this.local_variable_type_table = local_variable_table;
        local_variable_type_table_length = (local_variable_table == null) ? 0 : local_variable_table.length;
    }

    /**
   * @return String representation.
   */
    public final String toString() {
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < local_variable_type_table_length; i++) {
            buf.append(local_variable_type_table[i].toString());
            if (i < local_variable_type_table_length - 1)
                buf.append('\n');
        }
        return buf.toString();
    }

    /**
   * @return deep copy of this attribute
   */
    public Attribute copy(ConstantPool constant_pool) {
        LocalVariableTypeTable c = (LocalVariableTypeTable) clone();
        c.local_variable_type_table = new LocalVariable[local_variable_type_table_length];
        for (int i = 0; i < local_variable_type_table_length; i++) c.local_variable_type_table[i] = local_variable_type_table[i].copy();
        c.constant_pool = constant_pool;
        return c;
    }

    public final int getTableLength() {
        return local_variable_type_table_length;
    }
}
