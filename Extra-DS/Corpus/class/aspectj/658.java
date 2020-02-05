package org.aspectj.apache.bcel.classfile;

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
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.apache.bcel.Constants;
import org.aspectj.apache.bcel.classfile.annotation.Annotation;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisibleParameterAnnotations;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeVisibleParameterAnnotations;
import org.aspectj.apache.bcel.generic.Type;

/**
 * This class represents the method info structure, i.e., the representation 
 * for a method in the class. See JVM specification for details.
 * A method has access flags, a name, a signature and a number of attributes.
 *
 * @version $Id: Method.java,v 1.2 2004/11/19 16:45:18 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public final class Method extends FieldOrMethod {

    private boolean parameterAnnotationsOutOfDate;

    // annotations on parameters of this method
    private RuntimeVisibleParameterAnnotations parameterAnnotationsVis;

    private RuntimeInvisibleParameterAnnotations parameterAnnotationsInvis;

    /**
   * Empty constructor, all attributes have to be defined via `setXXX'
   * methods. Use at your own risk.
   */
    public  Method() {
        parameterAnnotationsOutOfDate = true;
    }

    /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   */
    public  Method(Method c) {
        super(c);
        parameterAnnotationsOutOfDate = true;
    }

    /**
   * Construct object from file stream.
   * @param file Input stream
   * @throws IOException
   * @throws ClassFormatException
   */
     Method(DataInputStream file, ConstantPool constant_pool) throws IOException, ClassFormatException {
        super(file, constant_pool);
        parameterAnnotationsOutOfDate = true;
    }

    /**
   * @param access_flags Access rights of method
   * @param name_index Points to field name in constant pool
   * @param signature_index Points to encoded signature
   * @param attributes Collection of attributes
   * @param constant_pool Array of constants
   */
    public  Method(int access_flags, int name_index, int signature_index, Attribute[] attributes, ConstantPool constant_pool) {
        super(access_flags, name_index, signature_index, attributes, constant_pool);
        parameterAnnotationsOutOfDate = true;
    }

    /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
    public void accept(Visitor v) {
        v.visitMethod(this);
    }

    public void setAttributes(Attribute[] attributes) {
        parameterAnnotationsOutOfDate = true;
        super.setAttributes(attributes);
    }

    /**
   * @return Code attribute of method, if any
   */
    public final Code getCode() {
        for (int i = 0; i < attributes_count; i++) if (attributes[i] instanceof Code)
            return (Code) attributes[i];
        return null;
    }

    /**
   * @return ExceptionTable attribute of method, if any, i.e., list all
   * exceptions the method may throw not exception handlers!
   */
    public final ExceptionTable getExceptionTable() {
        for (int i = 0; i < attributes_count; i++) if (attributes[i] instanceof ExceptionTable)
            return (ExceptionTable) attributes[i];
        return null;
    }

    /** @return LocalVariableTable of code attribute if any, i.e. the call is forwarded
   * to the Code atribute.
   */
    public final LocalVariableTable getLocalVariableTable() {
        Code code = getCode();
        if (code != null)
            return code.getLocalVariableTable();
        else
            return null;
    }

    /** @return LineNumberTable of code attribute if any, i.e. the call is forwarded
   * to the Code atribute.
   */
    public final LineNumberTable getLineNumberTable() {
        Code code = getCode();
        if (code != null)
            return code.getLineNumberTable();
        else
            return null;
    }

    /**
   * Return string representation close to declaration format,
   * `public static void main(String[] args) throws IOException', e.g.
   *
   * @return String representation of the method.
   */
    public final String toString() {
        ConstantUtf8 c;
        // Short cuts to constant pool
        String name, signature, access;
        StringBuffer buf;
        access = Utility.accessToString(access_flags);
        // Get name and signature from constant pool
        c = (ConstantUtf8) constant_pool.getConstant(signature_index, Constants.CONSTANT_Utf8);
        signature = c.getBytes();
        c = (ConstantUtf8) constant_pool.getConstant(name_index, Constants.CONSTANT_Utf8);
        name = c.getBytes();
        signature = Utility.methodSignatureToString(signature, name, access, true, getLocalVariableTable());
        buf = new StringBuffer(signature);
        for (int i = 0; i < attributes_count; i++) {
            Attribute a = attributes[i];
            if (!((a instanceof Code) || (a instanceof ExceptionTable)))
                buf.append(" [" + a.toString() + "]");
        }
        ExceptionTable e = getExceptionTable();
        if (e != null) {
            String str = e.toString();
            if (!str.equals(""))
                buf.append("\n\t\tthrows " + str);
        }
        return buf.toString();
    }

    /**
   * @return deep copy of this method
   */
    public final Method copy(ConstantPool constant_pool) {
        return (Method) copy_(constant_pool);
    }

    /**
   * @return return type of method
   */
    public Type getReturnType() {
        return Type.getReturnType(getSignature());
    }

    /**
   * @return array of method argument types
   */
    public Type[] getArgumentTypes() {
        return Type.getArgumentTypes(getSignature());
    }

    private void ensureParameterAnnotationsUnpacked() {
        if (parameterAnnotationsOutOfDate) {
            // Find attributes that contain annotation data
            Attribute[] attrs = getAttributes();
            List accumulatedAnnotations = new ArrayList();
            for (int i = 0; i < attrs.length; i++) {
                Attribute attribute = attrs[i];
                if (attribute instanceof RuntimeVisibleParameterAnnotations) {
                    parameterAnnotationsVis = (RuntimeVisibleParameterAnnotations) attribute;
                }
                if (attribute instanceof RuntimeInvisibleParameterAnnotations) {
                    parameterAnnotationsInvis = (RuntimeInvisibleParameterAnnotations) attribute;
                }
            }
            parameterAnnotationsOutOfDate = false;
        }
    }

    public Annotation[] getAnnotationsOnParameter(int i) {
        ensureParameterAnnotationsUnpacked();
        Annotation[] visibleOnes = new Annotation[0];
        if (parameterAnnotationsVis != null)
            visibleOnes = parameterAnnotationsVis.getAnnotationsOnParameter(i);
        Annotation[] invisibleOnes = new Annotation[0];
        if (parameterAnnotationsInvis != null)
            invisibleOnes = parameterAnnotationsInvis.getAnnotationsOnParameter(i);
        Annotation[] complete = new Annotation[visibleOnes.length + invisibleOnes.length];
        System.arraycopy(visibleOnes, 0, complete, 0, visibleOnes.length);
        System.arraycopy(invisibleOnes, 0, complete, visibleOnes.length, invisibleOnes.length);
        return complete;
    }
}
