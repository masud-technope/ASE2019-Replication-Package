package org.aspectj.apache.bcel.generic;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache BCEL" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache BCEL", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
import java.io.*;
import org.aspectj.apache.bcel.util.ByteSequence;

/** 
 * LDC - Push item from constant pool.
 *
 * <PRE>Stack: ... -&gt; ..., item</PRE>
 *
 * @version $Id: LDC.java,v 1.2 2004/11/19 16:45:18 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class LDC extends CPInstruction implements PushInstruction, ExceptionThrower, TypedInstruction {

    /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
     LDC() {
    }

    public  LDC(int index) {
        super(org.aspectj.apache.bcel.Constants.LDC_W, index);
        setSize();
    }

    // Adjust to proper size
    protected final void setSize() {
        if (index <= org.aspectj.apache.bcel.Constants.MAX_BYTE) {
            // Fits in one byte?
            opcode = org.aspectj.apache.bcel.Constants.LDC;
            length = 2;
        } else {
            opcode = org.aspectj.apache.bcel.Constants.LDC_W;
            length = 3;
        }
    }

    /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
    public void dump(DataOutputStream out) throws IOException {
        out.writeByte(opcode);
        if (length == 2)
            out.writeByte(index);
        else
            // Applies for LDC_W
            out.writeShort(index);
    }

    /**
   * Set the index to constant pool and adjust size.
   */
    public final void setIndex(int index) {
        super.setIndex(index);
        setSize();
    }

    /**
   * Read needed data (e.g. index) from file.
   */
    protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
        length = 2;
        index = bytes.readUnsignedByte();
    }

    public Object getValue(ConstantPoolGen cpg) {
        org.aspectj.apache.bcel.classfile.Constant c = cpg.getConstantPool().getConstant(index);
        switch(c.getTag()) {
            case org.aspectj.apache.bcel.Constants.CONSTANT_String:
                int i = ((org.aspectj.apache.bcel.classfile.ConstantString) c).getStringIndex();
                c = cpg.getConstantPool().getConstant(i);
                return ((org.aspectj.apache.bcel.classfile.ConstantUtf8) c).getBytes();
            case org.aspectj.apache.bcel.Constants.CONSTANT_Float:
                return new Float(((org.aspectj.apache.bcel.classfile.ConstantFloat) c).getBytes());
            case org.aspectj.apache.bcel.Constants.CONSTANT_Integer:
                return new Integer(((org.aspectj.apache.bcel.classfile.ConstantInteger) c).getBytes());
            default:
                // Never reached
                throw new RuntimeException("Unknown or invalid constant type at " + index);
        }
    }

    public Type getType(ConstantPoolGen cpg) {
        switch(cpg.getConstantPool().getConstant(index).getTag()) {
            case org.aspectj.apache.bcel.Constants.CONSTANT_String:
                return Type.STRING;
            case org.aspectj.apache.bcel.Constants.CONSTANT_Float:
                return Type.FLOAT;
            case org.aspectj.apache.bcel.Constants.CONSTANT_Integer:
                return Type.INT;
            default:
                // Never reached
                throw new RuntimeException("Unknown or invalid constant type at " + index);
        }
    }

    public Class[] getExceptions() {
        return org.aspectj.apache.bcel.ExceptionConstants.EXCS_STRING_RESOLUTION;
    }

    /**
   * Call corresponding visitor method(s). The order is:
   * Call visitor methods of implemented interfaces first, then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor object
   */
    public void accept(Visitor v) {
        v.visitStackProducer(this);
        v.visitPushInstruction(this);
        v.visitExceptionThrower(this);
        v.visitTypedInstruction(this);
        v.visitCPInstruction(this);
        v.visitLDC(this);
    }
}
