package org.aspectj.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.aspectj.apache.bcel.classfile.Attribute;
import org.aspectj.apache.bcel.classfile.Code;
import org.aspectj.apache.bcel.classfile.CodeException;
import org.aspectj.apache.bcel.classfile.ConstantPool;
import org.aspectj.apache.bcel.classfile.ConstantUtf8;
import org.aspectj.apache.bcel.classfile.ConstantValue;
import org.aspectj.apache.bcel.classfile.ExceptionTable;
import org.aspectj.apache.bcel.classfile.InnerClass;
import org.aspectj.apache.bcel.classfile.InnerClasses;
import org.aspectj.apache.bcel.classfile.LineNumber;
import org.aspectj.apache.bcel.classfile.LineNumberTable;
import org.aspectj.apache.bcel.classfile.LocalVariable;
import org.aspectj.apache.bcel.classfile.LocalVariableTable;
import org.aspectj.apache.bcel.classfile.SourceFile;
import org.aspectj.apache.bcel.classfile.Utility;

/**
 * Convert found attributes into HTML file.
 *
 * @version $Id: AttributeHTML.java,v 1.3 2004/11/22 08:31:27 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * 
 */
final class AttributeHTML implements org.aspectj.apache.bcel.Constants {

    // name of current class
    private String class_name;

    // file to write to
    private PrintWriter file;

    private int attr_count = 0;

    private ConstantHTML constant_html;

    private ConstantPool constant_pool;

     AttributeHTML(String dir, String class_name, ConstantPool constant_pool, ConstantHTML constant_html) throws IOException {
        this.class_name = class_name;
        this.constant_pool = constant_pool;
        this.constant_html = constant_html;
        file = new PrintWriter(new FileOutputStream(dir + class_name + "_attributes.html"));
        file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
    }

    private final String codeLink(int link, int method_number) {
        return "<A HREF=\"" + class_name + "_code.html#code" + method_number + "@" + link + "\" TARGET=Code>" + link + "</A>";
    }

    final void close() {
        file.println("</TABLE></BODY></HTML>");
        file.close();
    }

    final void writeAttribute(Attribute attribute, String anchor) throws IOException {
        writeAttribute(attribute, anchor, 0);
    }

    final void writeAttribute(Attribute attribute, String anchor, int method_number) throws IOException {
        byte tag = attribute.getTag();
        int index;
        if (// Don't know what to do about this one
        tag == ATTR_UNKNOWN)
            return;
        // Increment number of attributes found so far
        attr_count++;
        if (attr_count % 2 == 0)
            file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
        else
            file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
        file.println("<H4><A NAME=\"" + anchor + "\">" + attr_count + " " + ATTRIBUTE_NAMES[tag] + "</A></H4>");
        /* Handle different attributes
     */
        switch(tag) {
            case ATTR_CODE:
                Code c = (Code) attribute;
                // Some directly printable values
                file.print("<UL><LI>Maximum stack size = " + c.getMaxStack() + "</LI>\n<LI>Number of local variables = " + c.getMaxLocals() + "</LI>\n<LI><A HREF=\"" + class_name + "_code.html#method" + method_number + "\" TARGET=Code>Byte code</A></LI></UL>\n");
                // Get handled exceptions and list them
                CodeException[] ce = c.getExceptionTable();
                int len = ce.length;
                if (len > 0) {
                    file.print("<P><B>Exceptions handled</B><UL>");
                    for (int i = 0; i < len; i++) {
                        // Index in constant pool
                        int catch_type = ce[i].getCatchType();
                        file.print("<LI>");
                        if (catch_type != 0)
                            // Create Link to _cp.html
                            file.print(constant_html.referenceConstant(catch_type));
                        else
                            file.print("Any Exception");
                        file.print("<BR>(Ranging from lines " + codeLink(ce[i].getStartPC(), method_number) + " to " + codeLink(ce[i].getEndPC(), method_number) + ", handled at line " + codeLink(ce[i].getHandlerPC(), method_number) + ")</LI>");
                    }
                    file.print("</UL>");
                }
                break;
            case ATTR_CONSTANT_VALUE:
                index = ((ConstantValue) attribute).getConstantValueIndex();
                // Reference _cp.html
                file.print("<UL><LI><A HREF=\"" + class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">Constant value index(" + index + ")</A></UL>\n");
                break;
            case ATTR_SOURCE_FILE:
                index = ((SourceFile) attribute).getSourceFileIndex();
                // Reference _cp.html
                file.print("<UL><LI><A HREF=\"" + class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">Source file index(" + index + ")</A></UL>\n");
                break;
            case ATTR_EXCEPTIONS:
                // List thrown exceptions
                int[] indices = ((ExceptionTable) attribute).getExceptionIndexTable();
                file.print("<UL>");
                for (int i = 0; i < indices.length; i++) file.print("<LI><A HREF=\"" + class_name + "_cp.html#cp" + indices[i] + "\" TARGET=\"ConstantPool\">Exception class index(" + indices[i] + ")</A>\n");
                file.print("</UL>\n");
                break;
            case ATTR_LINE_NUMBER_TABLE:
                LineNumber[] line_numbers = ((LineNumberTable) attribute).getLineNumberTable();
                // List line number pairs
                file.print("<P>");
                for (int i = 0; i < line_numbers.length; i++) {
                    file.print("(" + line_numbers[i].getStartPC() + ",&nbsp;" + line_numbers[i].getLineNumber() + ")");
                    if (i < line_numbers.length - 1)
                        // breakable
                        file.print(", ");
                }
                break;
            case ATTR_LOCAL_VARIABLE_TABLE:
                LocalVariable[] vars = ((LocalVariableTable) attribute).getLocalVariableTable();
                // List name, range and type
                file.print("<UL>");
                for (int i = 0; i < vars.length; i++) {
                    index = vars[i].getSignatureIndex();
                    String signature = ((ConstantUtf8) constant_pool.getConstant(index, CONSTANT_Utf8)).getBytes();
                    signature = Utility.signatureToString(signature, false);
                    int start = vars[i].getStartPC();
                    int end = (start + vars[i].getLength());
                    file.println("<LI>" + Class2HTML.referenceType(signature) + "&nbsp;<B>" + vars[i].getName() + "</B> in slot %" + vars[i].getIndex() + "<BR>Valid from lines " + "<A HREF=\"" + class_name + "_code.html#code" + method_number + "@" + start + "\" TARGET=Code>" + start + "</A> to " + "<A HREF=\"" + class_name + "_code.html#code" + method_number + "@" + end + "\" TARGET=Code>" + end + "</A></LI>");
                }
                file.print("</UL>\n");
                break;
            case ATTR_INNER_CLASSES:
                InnerClass[] classes = ((InnerClasses) attribute).getInnerClasses();
                // List inner classes
                file.print("<UL>");
                for (int i = 0; i < classes.length; i++) {
                    String name, access;
                    index = classes[i].getInnerNameIndex();
                    if (index > 0)
                        name = ((ConstantUtf8) constant_pool.getConstant(index, CONSTANT_Utf8)).getBytes();
                    else
                        name = "&lt;anonymous&gt;";
                    access = Utility.accessToString(classes[i].getInnerAccessFlags());
                    file.print("<LI><FONT COLOR=\"#FF0000\">" + access + "</FONT> " + constant_html.referenceConstant(classes[i].getInnerClassIndex()) + " in&nbsp;class " + constant_html.referenceConstant(classes[i].getOuterClassIndex()) + " named " + name + "</LI>\n");
                }
                file.print("</UL>\n");
                break;
            default:
                // Such as Unknown attribute or Deprecated
                file.print("<P>" + attribute.toString());
        }
        file.println("</TD></TR>");
        file.flush();
    }
}
