package org.aspectj.apache.bcel.classfile;

import org.aspectj.apache.bcel.Constants;
import java.io.*;

/**
 * This class represents a table of line numbers for debugging
 * purposes. This attribute is used by the <em>Code</em> attribute. It
 * contains pairs of PCs and line numbers.
 *
 * @version $Id: LineNumberTable.java,v 1.3 2006/02/07 15:15:42 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Code
 *
 * changes:
 *   asc Feb06 Made unpacking lazy
 */
public final class LineNumberTable extends Attribute {

    // if 'isInPackedState' then this data needs unpacking
    private boolean isInPackedState = false;

    private byte[] data;

    private int line_number_table_length;

    // Table of line/numbers pairs
    private LineNumber[] line_number_table;

    /*
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use copy() for a physical copy.
   */
    public  LineNumberTable(LineNumberTable c) {
        this(c.getNameIndex(), c.getLength(), c.getLineNumberTable(), c.getConstantPool());
    }

    /*
   * @param name_index Index of name
   * @param length Content length in bytes
   * @param line_number_table Table of line/numbers pairs
   * @param constant_pool Array of constants
   */
    public  LineNumberTable(int name_index, int length, LineNumber[] line_number_table, ConstantPool constant_pool) {
        super(Constants.ATTR_LINE_NUMBER_TABLE, name_index, length, constant_pool);
        setLineNumberTable(line_number_table);
        isInPackedState = false;
    }

    /**
   * Construct object from file stream.
   * @param name_index Index of name
   * @param length Content length in bytes
   * @param file Input stream
   * @throws IOException
   * @param constant_pool Array of constants
   */
     LineNumberTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
        this(name_index, length, (LineNumber[]) null, constant_pool);
        data = new byte[length];
        int byteReads = file.read(data);
        isInPackedState = true;
    // assert(bytesRead==length)
    }

    // Unpacks the byte array into the table
    private void unpack() {
        if (!isInPackedState)
            return;
        try {
            ByteArrayInputStream bs = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bs);
            line_number_table_length = (dis.readUnsignedShort());
            line_number_table = new LineNumber[line_number_table_length];
            for (int i = 0; i < line_number_table_length; i++) line_number_table[i] = new LineNumber(dis);
            dis.close();
            // throw it away now
            data = null;
        } catch (IOException e) {
            throw new RuntimeException("Unpacking of LineNumberTable attribute failed");
        }
        isInPackedState = false;
    }

    /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
    public void accept(Visitor v) {
        unpack();
        v.visitLineNumberTable(this);
    }

    /**
   * Dump line number table attribute to file stream in binary format.
   *
   * @param file Output file stream
   * @throws IOException
   */
    public final void dump(DataOutputStream file) throws IOException {
        super.dump(file);
        if (isInPackedState) {
            file.write(data);
        } else {
            file.writeShort(line_number_table_length);
            for (int i = 0; i < line_number_table_length; i++) line_number_table[i].dump(file);
        }
    }

    /**
   * @return Array of (pc offset, line number) pairs.
   */
    public final LineNumber[] getLineNumberTable() {
        unpack();
        return line_number_table;
    }

    /**
   * @param line_number_table.
   */
    public final void setLineNumberTable(LineNumber[] line_number_table) {
        this.data = null;
        this.isInPackedState = false;
        this.line_number_table = line_number_table;
        line_number_table_length = (line_number_table == null) ? 0 : line_number_table.length;
    }

    /**
   * @return String representation.
   */
    public final String toString() {
        unpack();
        StringBuffer buf = new StringBuffer();
        StringBuffer line = new StringBuffer();
        for (int i = 0; i < line_number_table_length; i++) {
            line.append(line_number_table[i].toString());
            if (i < line_number_table_length - 1)
                line.append(", ");
            if (line.length() > 72) {
                line.append('\n');
                buf.append(line);
                line.setLength(0);
            }
        }
        buf.append(line);
        return buf.toString();
    }

    /**
   * Map byte code positions to source code lines.
   *
   * @param pos byte code offset
   * @return corresponding line in source code
   */
    public int getSourceLine(int pos) {
        unpack();
        int l = 0, r = line_number_table_length - 1;
        if (// array is empty
        r < 0)
            return -1;
        int min_index = -1, min = -1;
        /* Do a binary search since the array is ordered.
     */
        do {
            int i = (l + r) / 2;
            int j = line_number_table[i].getStartPC();
            if (j == pos)
                return line_number_table[i].getLineNumber();
            else if (// else constrain search area
            pos < j)
                r = i - 1;
            else
                // pos > j
                l = i + 1;
            /* If exact match can't be found (which is the most common case)
       * return the line number that corresponds to the greatest index less
       * than pos.
       */
            if (j < pos && j > min) {
                min = j;
                min_index = i;
            }
        } while (l <= r);
        /* It's possible that we did not find any valid entry for the bytecode
     * offset we were looking for.
     */
        if (min_index < 0)
            return -1;
        return line_number_table[min_index].getLineNumber();
    }

    /**
   * @return deep copy of this attribute
   */
    public Attribute copy(ConstantPool constant_pool) {
        unpack();
        LineNumberTable c = (LineNumberTable) clone();
        c.line_number_table = new LineNumber[line_number_table_length];
        for (int i = 0; i < line_number_table_length; i++) c.line_number_table[i] = line_number_table[i].copy();
        c.constant_pool = constant_pool;
        return c;
    }

    public final int getTableLength() {
        unpack();
        return line_number_table_length;
    }
}
