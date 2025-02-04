package org.aspectj.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.aspectj.apache.bcel.classfile.Attribute;
import org.aspectj.apache.bcel.classfile.Code;
import org.aspectj.apache.bcel.classfile.ConstantValue;
import org.aspectj.apache.bcel.classfile.ExceptionTable;
import org.aspectj.apache.bcel.classfile.Field;
import org.aspectj.apache.bcel.classfile.Method;
import org.aspectj.apache.bcel.classfile.Utility;

/**
 * Convert methods and fields into HTML file.
 *
 * @version $Id: MethodHTML.java,v 1.3 2004/11/22 08:31:27 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * 
 */
final class MethodHTML implements org.aspectj.apache.bcel.Constants {

    // name of current class
    private String class_name;

    // file to write to
    private PrintWriter file;

    private ConstantHTML constant_html;

    private AttributeHTML attribute_html;

     MethodHTML(String dir, String class_name, Method[] methods, Field[] fields, ConstantHTML constant_html, AttributeHTML attribute_html) throws IOException {
        this.class_name = class_name;
        this.attribute_html = attribute_html;
        this.constant_html = constant_html;
        file = new PrintWriter(new FileOutputStream(dir + class_name + "_methods.html"));
        file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
        file.println("<TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH ALIGN=LEFT>Type</TH>" + "<TH ALIGN=LEFT>Field&nbsp;name</TH></TR>");
        for (int i = 0; i < fields.length; i++) writeField(fields[i]);
        file.println("</TABLE>");
        file.println("<TABLE BORDER=0><TR><TH ALIGN=LEFT>Access&nbsp;flags</TH>" + "<TH ALIGN=LEFT>Return&nbsp;type</TH><TH ALIGN=LEFT>Method&nbsp;name</TH>" + "<TH ALIGN=LEFT>Arguments</TH></TR>");
        for (int i = 0; i < methods.length; i++) writeMethod(methods[i], i);
        file.println("</TABLE></BODY></HTML>");
        file.close();
    }

    /**
   * Print field of class.
   *
   * @param field field to print
   * @exception java.io.IOException
   */
    private void writeField(Field field) throws IOException {
        String type = Utility.signatureToString(field.getSignature());
        String name = field.getName();
        String access = Utility.accessToString(field.getAccessFlags());
        Attribute[] attributes;
        access = Utility.replace(access, " ", "&nbsp;");
        file.print("<TR><TD><FONT COLOR=\"#FF0000\">" + access + "</FONT></TD>\n<TD>" + Class2HTML.referenceType(type) + "</TD><TD><A NAME=\"field" + name + "\">" + name + "</A></TD>");
        attributes = field.getAttributes();
        // Write them to the Attributes.html file with anchor "<name>[<i>]"
        for (int i = 0; i < attributes.length; i++) attribute_html.writeAttribute(attributes[i], name + "@" + i);
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getTag() == ATTR_CONSTANT_VALUE) {
                // Default value
                String str = ((ConstantValue) attributes[i]).toString();
                // Reference attribute in _attributes.html
                file.print("<TD>= <A HREF=\"" + class_name + "_attributes.html#" + name + "@" + i + "\" TARGET=\"Attributes\">" + str + "</TD>\n");
                break;
            }
        }
        file.println("</TR>");
    }

    private final void writeMethod(Method method, int method_number) throws IOException {
        // Get raw signature
        String signature = method.getSignature();
        // Get array of strings containing the argument types 
        String[] args = Utility.methodSignatureArgumentTypes(signature, false);
        // Get return type string
        String type = Utility.methodSignatureReturnType(signature, false);
        // Get method name
        String name = method.getName(), html_name;
        // Get method's access flags
        String access = Utility.accessToString(method.getAccessFlags());
        // Get the method's attributes, the Code Attribute in particular
        Attribute[] attributes = method.getAttributes();
        /* HTML doesn't like names like <clinit> and spaces are places to break
     * lines. Both we don't want...
     */
        access = Utility.replace(access, " ", "&nbsp;");
        html_name = Class2HTML.toHTML(name);
        file.print("<TR VALIGN=TOP><TD><FONT COLOR=\"#FF0000\"><A NAME=method" + method_number + ">" + access + "</A></FONT></TD>");
        file.print("<TD>" + Class2HTML.referenceType(type) + "</TD><TD>" + "<A HREF=" + class_name + "_code.html#method" + method_number + " TARGET=Code>" + html_name + "</A></TD>\n<TD>(");
        for (int i = 0; i < args.length; i++) {
            file.print(Class2HTML.referenceType(args[i]));
            if (i < args.length - 1)
                file.print(", ");
        }
        file.print(")</TD></TR>");
        // Check for thrown exceptions
        for (int i = 0; i < attributes.length; i++) {
            attribute_html.writeAttribute(attributes[i], "method" + method_number + "@" + i, method_number);
            byte tag = attributes[i].getTag();
            if (tag == ATTR_EXCEPTIONS) {
                file.print("<TR VALIGN=TOP><TD COLSPAN=2></TD><TH ALIGN=LEFT>throws</TH><TD>");
                int[] exceptions = ((ExceptionTable) attributes[i]).getExceptionIndexTable();
                for (int j = 0; j < exceptions.length; j++) {
                    file.print(constant_html.referenceConstant(exceptions[j]));
                    if (j < exceptions.length - 1)
                        file.print(", ");
                }
                file.println("</TD></TR>");
            } else if (tag == ATTR_CODE) {
                Attribute[] c_a = ((Code) attributes[i]).getAttributes();
                for (int j = 0; j < c_a.length; j++) attribute_html.writeAttribute(c_a[j], "method" + method_number + "@" + i + "@" + j, method_number);
            }
        }
    }
}
