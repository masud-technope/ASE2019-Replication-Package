package org.aspectj.apache.bcel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.aspectj.apache.bcel.classfile.Constant;
import org.aspectj.apache.bcel.classfile.ConstantClass;
import org.aspectj.apache.bcel.classfile.ConstantFieldref;
import org.aspectj.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.aspectj.apache.bcel.classfile.ConstantMethodref;
import org.aspectj.apache.bcel.classfile.ConstantNameAndType;
import org.aspectj.apache.bcel.classfile.ConstantPool;
import org.aspectj.apache.bcel.classfile.ConstantString;
import org.aspectj.apache.bcel.classfile.Method;
import org.aspectj.apache.bcel.classfile.Utility;

/**
 * Convert constant pool into HTML file.
 *
 * @version $Id: ConstantHTML.java,v 1.3 2004/11/22 08:31:27 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * 
 */
final class ConstantHTML implements org.aspectj.apache.bcel.Constants {

    // name of current class
    private String class_name;

    // name of package
    private String class_package;

    // reference to constant pool
    private ConstantPool constant_pool;

    // file to write to
    private PrintWriter file;

    // String to return for cp[i]
    private String[] constant_ref;

    // The constants in the cp
    private Constant[] constants;

    private Method[] methods;

     ConstantHTML(String dir, String class_name, String class_package, Method[] methods, ConstantPool constant_pool) throws IOException {
        this.class_name = class_name;
        this.class_package = class_package;
        this.constant_pool = constant_pool;
        this.methods = methods;
        constants = constant_pool.getConstantPool();
        file = new PrintWriter(new FileOutputStream(dir + class_name + "_cp.html"));
        constant_ref = new String[constants.length];
        constant_ref[0] = "&lt;unknown&gt;";
        file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
        // Loop through constants, constants[0] is reserved
        for (int i = 1; i < constants.length; i++) {
            if (i % 2 == 0)
                file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
            else
                file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
            if (constants[i] != null)
                writeConstant(i);
            file.print("</TD></TR>\n");
        }
        file.println("</TABLE></BODY></HTML>");
        file.close();
    }

    String referenceConstant(int index) {
        return constant_ref[index];
    }

    private void writeConstant(int index) {
        byte tag = constants[index].getTag();
        int class_index, name_index;
        String ref;
        // The header is always the same
        file.println("<H4> <A NAME=cp" + index + ">" + index + "</A> " + CONSTANT_NAMES[tag] + "</H4>");
        /* For every constant type get the needed parameters and print them appropiately 
     */
        switch(tag) {
            case CONSTANT_InterfaceMethodref:
            case CONSTANT_Methodref:
                // Get class_index and name_and_type_index, depending on type
                if (tag == CONSTANT_Methodref) {
                    ConstantMethodref c = (ConstantMethodref) constant_pool.getConstant(index, CONSTANT_Methodref);
                    class_index = c.getClassIndex();
                    name_index = c.getNameAndTypeIndex();
                } else {
                    ConstantInterfaceMethodref c1 = (ConstantInterfaceMethodref) constant_pool.getConstant(index, CONSTANT_InterfaceMethodref);
                    class_index = c1.getClassIndex();
                    name_index = c1.getNameAndTypeIndex();
                }
                // Get method name and its class
                String method_name = constant_pool.constantToString(name_index, CONSTANT_NameAndType);
                String html_method_name = Class2HTML.toHTML(method_name);
                // Partially compacted class name, i.e., / -> .
                String method_class = constant_pool.constantToString(class_index, CONSTANT_Class);
                // I.e., remove java.lang.
                String short_method_class = Utility.compactClassName(method_class);
                // I.e., remove java.lang.
                short_method_class = Utility.compactClassName(method_class);
                // Remove class package prefix
                short_method_class = Utility.compactClassName(short_method_class, class_package + ".", true);
                // Get method signature
                ConstantNameAndType c2 = (ConstantNameAndType) constant_pool.getConstant(name_index, CONSTANT_NameAndType);
                String signature = constant_pool.constantToString(c2.getSignatureIndex(), CONSTANT_Utf8);
                // Get array of strings containing the argument types
                String[] args = Utility.methodSignatureArgumentTypes(signature, false);
                // Get return type string
                String type = Utility.methodSignatureReturnType(signature, false);
                String ret_type = Class2HTML.referenceType(type);
                StringBuffer buf = new StringBuffer("(");
                for (int i = 0; i < args.length; i++) {
                    buf.append(Class2HTML.referenceType(args[i]));
                    if (i < args.length - 1)
                        buf.append(",&nbsp;");
                }
                buf.append(")");
                String arg_types = buf.toString();
                if (// Method is local to class
                method_class.equals(class_name))
                    ref = "<A HREF=\"" + class_name + "_code.html#method" + getMethodNumber(method_name + signature) + "\" TARGET=Code>" + html_method_name + "</A>";
                else
                    ref = "<A HREF=\"" + method_class + ".html" + "\" TARGET=_top>" + short_method_class + "</A>." + html_method_name;
                constant_ref[index] = ret_type + "&nbsp;<A HREF=\"" + class_name + "_cp.html#cp" + class_index + "\" TARGET=Constants>" + short_method_class + "</A>.<A HREF=\"" + class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + html_method_name + "</A>&nbsp;" + arg_types;
                file.println("<P><TT>" + ret_type + "&nbsp;" + ref + arg_types + "&nbsp;</TT>\n<UL>" + "<LI><A HREF=\"#cp" + class_index + "\">Class index(" + class_index + ")</A>\n" + "<LI><A HREF=\"#cp" + name_index + "\">NameAndType index(" + name_index + ")</A></UL>");
                break;
            case CONSTANT_Fieldref:
                // Get class_index and name_and_type_index
                ConstantFieldref c3 = (ConstantFieldref) constant_pool.getConstant(index, CONSTANT_Fieldref);
                class_index = c3.getClassIndex();
                name_index = c3.getNameAndTypeIndex();
                // Get method name and its class (compacted)
                String field_class = constant_pool.constantToString(class_index, CONSTANT_Class);
                // I.e., remove java.lang.
                String short_field_class = Utility.compactClassName(field_class);
                // Remove class package prefix
                short_field_class = Utility.compactClassName(short_field_class, class_package + ".", true);
                String field_name = constant_pool.constantToString(name_index, CONSTANT_NameAndType);
                if (// Field is local to class
                field_class.equals(class_name))
                    ref = "<A HREF=\"" + field_class + "_methods.html#field" + field_name + "\" TARGET=Methods>" + field_name + "</A>";
                else
                    ref = "<A HREF=\"" + field_class + ".html\" TARGET=_top>" + short_field_class + "</A>." + field_name + "\n";
                constant_ref[index] = "<A HREF=\"" + class_name + "_cp.html#cp" + class_index + "\" TARGET=Constants>" + short_field_class + "</A>.<A HREF=\"" + class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + field_name + "</A>";
                file.println("<P><TT>" + ref + "</TT><BR>\n" + "<UL>" + "<LI><A HREF=\"#cp" + class_index + "\">Class(" + class_index + ")</A><BR>\n" + "<LI><A HREF=\"#cp" + name_index + "\">NameAndType(" + name_index + ")</A></UL>");
                break;
            case CONSTANT_Class:
                ConstantClass c4 = (ConstantClass) constant_pool.getConstant(index, CONSTANT_Class);
                name_index = c4.getNameIndex();
                // / -> .
                String class_name2 = constant_pool.constantToString(index, tag);
                // I.e., remove java.lang.
                String short_class_name = Utility.compactClassName(class_name2);
                // Remove class package prefix
                short_class_name = Utility.compactClassName(short_class_name, class_package + ".", true);
                ref = "<A HREF=\"" + class_name2 + ".html\" TARGET=_top>" + short_class_name + "</A>";
                constant_ref[index] = "<A HREF=\"" + class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + short_class_name + "</A>";
                file.println("<P><TT>" + ref + "</TT><UL>" + "<LI><A HREF=\"#cp" + name_index + "\">Name index(" + name_index + ")</A></UL>\n");
                break;
            case CONSTANT_String:
                ConstantString c5 = (ConstantString) constant_pool.getConstant(index, CONSTANT_String);
                name_index = c5.getStringIndex();
                String str = Class2HTML.toHTML(constant_pool.constantToString(index, tag));
                file.println("<P><TT>" + str + "</TT><UL>" + "<LI><A HREF=\"#cp" + name_index + "\">Name index(" + name_index + ")</A></UL>\n");
                break;
            case CONSTANT_NameAndType:
                ConstantNameAndType c6 = (ConstantNameAndType) constant_pool.getConstant(index, CONSTANT_NameAndType);
                name_index = c6.getNameIndex();
                int signature_index = c6.getSignatureIndex();
                file.println("<P><TT>" + Class2HTML.toHTML(constant_pool.constantToString(index, tag)) + "</TT><UL>" + "<LI><A HREF=\"#cp" + name_index + "\">Name index(" + name_index + ")</A>\n" + "<LI><A HREF=\"#cp" + signature_index + "\">Signature index(" + signature_index + ")</A></UL>\n");
                break;
            default:
                file.println("<P><TT>" + Class2HTML.toHTML(constant_pool.constantToString(index, tag)) + "</TT>\n");
        }
    // switch
    }

    private final int getMethodNumber(String str) {
        for (int i = 0; i < methods.length; i++) {
            String cmp = methods[i].getName() + methods[i].getSignature();
            if (cmp.equals(str))
                return i;
        }
        return -1;
    }
}
