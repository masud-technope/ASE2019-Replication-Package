package org.aspectj.apache.bcel.classfile;

import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisibleAnnotations;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisibleParameterAnnotations;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeVisibleAnnotations;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeVisibleParameterAnnotations;

/**
 * Visitor with empty method bodies, can be extended and used in conjunction with the
 * DescendingVisitor class, e.g.
 *
 * By courtesy of David Spencer.
 *
 * @see DescendingVisitor
 * @version $Id: EmptyVisitor.java,v 1.3 2004/11/19 16:45:18 aclement Exp $
 * 
 */
public class EmptyVisitor implements Visitor {

    protected  EmptyVisitor() {
    }

    public void visitCode(Code obj) {
    }

    public void visitCodeException(CodeException obj) {
    }

    public void visitConstantClass(ConstantClass obj) {
    }

    public void visitConstantDouble(ConstantDouble obj) {
    }

    public void visitConstantFieldref(ConstantFieldref obj) {
    }

    public void visitConstantFloat(ConstantFloat obj) {
    }

    public void visitConstantInteger(ConstantInteger obj) {
    }

    public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref obj) {
    }

    public void visitConstantLong(ConstantLong obj) {
    }

    public void visitConstantMethodref(ConstantMethodref obj) {
    }

    public void visitConstantNameAndType(ConstantNameAndType obj) {
    }

    public void visitConstantPool(ConstantPool obj) {
    }

    public void visitConstantString(ConstantString obj) {
    }

    public void visitConstantUtf8(ConstantUtf8 obj) {
    }

    public void visitConstantValue(ConstantValue obj) {
    }

    public void visitDeprecated(Deprecated obj) {
    }

    public void visitExceptionTable(ExceptionTable obj) {
    }

    public void visitField(Field obj) {
    }

    public void visitInnerClass(InnerClass obj) {
    }

    public void visitInnerClasses(InnerClasses obj) {
    }

    public void visitJavaClass(JavaClass obj) {
    }

    public void visitLineNumber(LineNumber obj) {
    }

    public void visitLineNumberTable(LineNumberTable obj) {
    }

    public void visitLocalVariable(LocalVariable obj) {
    }

    public void visitLocalVariableTable(LocalVariableTable obj) {
    }

    public void visitMethod(Method obj) {
    }

    public void visitSignature(Signature obj) {
    }

    public void visitSourceFile(SourceFile obj) {
    }

    public void visitSynthetic(Synthetic obj) {
    }

    public void visitUnknown(Unknown obj) {
    }

    public void visitStackMap(StackMap obj) {
    }

    public void visitStackMapEntry(StackMapEntry obj) {
    }

    // J5SUPPORT:
    public void visitEnclosingMethod(EnclosingMethod obj) {
    }

    public void visitRuntimeVisibleAnnotations(RuntimeVisibleAnnotations attribute) {
    }

    public void visitRuntimeInvisibleAnnotations(RuntimeInvisibleAnnotations attribute) {
    }

    public void visitRuntimeVisibleParameterAnnotations(RuntimeVisibleParameterAnnotations attribute) {
    }

    public void visitRuntimeInvisibleParameterAnnotations(RuntimeInvisibleParameterAnnotations attribute) {
    }

    public void visitAnnotationDefault(AnnotationDefault attribute) {
    }

    public void visitLocalVariableTypeTable(LocalVariableTypeTable obj) {
    }
}
