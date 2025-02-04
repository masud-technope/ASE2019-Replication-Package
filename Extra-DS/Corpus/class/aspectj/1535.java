package org.aspectj.apache.bcel.classfile;

import org.aspectj.apache.bcel.Constants;
import java.io.*;

/**
 * This class is derived from <em>Attribute</em> and declares this class as
 * `synthetic', i.e., it needs special handling.  The JVM specification
 * states "A class member that does not appear in the source code must be
 * marked using a Synthetic attribute."  It may appear in the ClassFile
 * attribute table, a field_info table or a method_info table.  This class
 * is intended to be instantiated from the
 * <em>Attribute.readAttribute()</em> method.
 *
 * @version $Id: Synthetic.java,v 1.2 2004/11/19 16:45:18 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Attribute
 */
public final class Synthetic extends Attribute {

    private byte[] bytes;

    /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use copy() for a physical copy.
   */
    public  Synthetic(Synthetic c) {
        this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
    }

    /**
   * @param name_index Index in constant pool to CONSTANT_Utf8, which
   * should represent the string "Synthetic".
   * @param length Content length in bytes - should be zero.
   * @param bytes Attribute contents
   * @param constant_pool The constant pool this attribute is associated
   * with.
   */
    public  Synthetic(int name_index, int length, byte[] bytes, ConstantPool constant_pool) {
        super(Constants.ATTR_SYNTHETIC, name_index, length, constant_pool);
        this.bytes = bytes;
    }

    /**
   * Construct object from file stream.
   * @param name_index Index in constant pool to CONSTANT_Utf8
   * @param length Content length in bytes
   * @param file Input stream
   * @param constant_pool Array of constants
   * @throws IOException
   */
     Synthetic(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
        this(name_index, length, (byte[]) null, constant_pool);
        if (length > 0) {
            bytes = new byte[length];
            file.readFully(bytes);
            System.err.println("Synthetic attribute with length > 0");
        }
    }

    /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
    public void accept(Visitor v) {
        v.visitSynthetic(this);
    }

    /**
   * Dump source file attribute to file stream in binary format.
   *
   * @param file Output file stream
   * @throws IOException
   */
    public final void dump(DataOutputStream file) throws IOException {
        super.dump(file);
        if (length > 0)
            file.write(bytes, 0, length);
    }

    /**
   * @return data bytes.
   */
    public final byte[] getBytes() {
        return bytes;
    }

    /**
   * @param bytes.
   */
    public final void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
   * @return String representation.
   */
    public final String toString() {
        StringBuffer buf = new StringBuffer("Synthetic");
        if (length > 0)
            buf.append(" " + Utility.toHexString(bytes));
        return buf.toString();
    }

    /**
   * @return deep copy of this attribute
   */
    public Attribute copy(ConstantPool constant_pool) {
        Synthetic c = (Synthetic) clone();
        if (bytes != null)
            c.bytes = (byte[]) bytes.clone();
        c.constant_pool = constant_pool;
        return c;
    }
}
