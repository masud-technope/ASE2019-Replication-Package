package org.aspectj.apache.bcel.classfile;

import org.aspectj.apache.bcel.Constants;
import java.io.*;
import java.util.zip.*;

/**
 * Wrapper class that parses a given Java .class file. The method <A
 * href ="#parse">parse</A> returns a <A href ="JavaClass.html">
 * JavaClass</A> object on success. When an I/O error or an
 * inconsistency occurs an appropiate exception is propagated back to
 * the caller.
 *
 * The structure and the names comply, except for a few conveniences,
 * exactly with the <A href="ftp://java.sun.com/docs/specs/vmspec.ps">
 * JVM specification 1.0</a>. See this paper for
 * further details about the structure of a bytecode file.
 *
 * @version $Id: ClassParser.java,v 1.4 2005/10/14 08:39:32 aclement Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A> 
 */
public final class ClassParser {

    private DataInputStream file;

    private ZipFile zip;

    private String file_name;

    private int class_name_index, superclass_name_index;

    // Compiler version
    private int major, minor;

    // Access rights of parsed class
    private int access_flags;

    // Names of implemented interfaces
    private int[] interfaces;

    // collection of constants
    private ConstantPool constant_pool;

    // class fields, i.e., its variables
    private Field[] fields;

    // methods defined in the class
    private Method[] methods;

    // attributes defined in the class
    private Attribute[] attributes;

    // Loaded from zip file
    private boolean is_zip;

    private static final int BUFSIZE = 8192;

    /**
   * Parse class from the given stream.
   *
   * @param file Input stream
   * @param file_name File name
   */
    public  ClassParser(InputStream file, String file_name) {
        this.file_name = file_name;
        // Not a very clean solution ...
        String clazz = file.getClass().getName();
        is_zip = clazz.startsWith("java.util.zip.") || clazz.startsWith("java.util.jar.");
        if (// Is already a data stream
        file instanceof DataInputStream)
            this.file = (DataInputStream) file;
        else
            this.file = new DataInputStream(new BufferedInputStream(file, BUFSIZE));
    }

    /** Parse class from given .class file.
   *
   * @param file_name file name
   * @throws IOException
   */
    public  ClassParser(String file_name) throws IOException {
        is_zip = false;
        this.file_name = file_name;
        file = new DataInputStream(new BufferedInputStream(new FileInputStream(file_name), BUFSIZE));
    }

    /** Parse class from given .class file in a ZIP-archive
   *
   * @param file_name file name
   * @throws IOException
   */
    public  ClassParser(String zip_file, String file_name) throws IOException {
        is_zip = true;
        zip = new ZipFile(zip_file);
        ZipEntry entry = zip.getEntry(file_name);
        this.file_name = file_name;
        file = new DataInputStream(new BufferedInputStream(zip.getInputStream(entry), BUFSIZE));
    }

    /**
   * Parse the given Java class file and return an object that represents
   * the contained data, i.e., constants, methods, fields and commands.
   * A <em>ClassFormatException</em> is raised, if the file is not a valid
   * .class file. (This does not include verification of the byte code as it
   * is performed by the java interpreter).
   *
   * @return Class object representing the parsed class file
   * @throws  IOException
   * @throws  ClassFormatException
   */
    public JavaClass parse() throws IOException, ClassFormatException {
        /****************** Read headers ********************************/
        // Check magic tag of class file
        readID();
        // Get compiler version
        readVersion();
        /****************** Read constant pool and related **************/
        // Read constant pool entries
        readConstantPool();
        // Get class information
        readClassInfo();
        // Get interface information, i.e., implemented interfaces
        readInterfaces();
        /****************** Read class fields and methods ***************/
        // Read class fields, i.e., the variables of the class
        readFields();
        // Read class methods, i.e., the functions in the class
        readMethods();
        // Read class attributes
        readAttributes();
        // Check for unknown variables
        //Unknown[] u = Unknown.getUnknownAttributes();
        //for(int i=0; i < u.length; i++)
        //  System.err.println("WARNING: " + u[i]);
        // Everything should have been read now
        //      if(file.available() > 0) {
        //        int bytes = file.available();
        //        byte[] buf = new byte[bytes];
        //        file.read(buf);
        //        if(!(is_zip && (buf.length == 1))) {
        //  	System.err.println("WARNING: Trailing garbage at end of " + file_name);
        //  	System.err.println(bytes + " extra bytes: " + Utility.toHexString(buf));
        //        }
        //      }
        // Read everything of interest, so close the file
        file.close();
        if (zip != null)
            zip.close();
        // Return the information we have gathered in a new object
        return new JavaClass(class_name_index, superclass_name_index, file_name, major, minor, access_flags, constant_pool, interfaces, fields, methods, attributes, is_zip ? JavaClass.ZIP : JavaClass.FILE);
    }

    /**
   * Read information about the attributes of the class.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readAttributes() throws IOException, ClassFormatException {
        int attributes_count;
        attributes_count = file.readUnsignedShort();
        attributes = new Attribute[attributes_count];
        for (int i = 0; i < attributes_count; i++) attributes[i] = Attribute.readAttribute(file, constant_pool);
    }

    /**
   * Read information about the class and its super class.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readClassInfo() throws IOException, ClassFormatException {
        access_flags = file.readUnsignedShort();
        /* Interfaces are implicitely abstract, the flag should be set
     * according to the JVM specification.
     */
        if ((access_flags & Constants.ACC_INTERFACE) != 0)
            access_flags |= Constants.ACC_ABSTRACT;
        // don't police it like this... leave higher level verification code to check it.
        //    if(((access_flags & Constants.ACC_ABSTRACT) != 0) && 
        //       ((access_flags & Constants.ACC_FINAL)    != 0 ))
        //      throw new ClassFormatException("Class can't be both final and abstract");
        class_name_index = file.readUnsignedShort();
        superclass_name_index = file.readUnsignedShort();
    }

    /**
   * Read constant pool entries.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readConstantPool() throws IOException, ClassFormatException {
        try {
            constant_pool = new ConstantPool(file);
        } catch (ClassFormatException cfe) {
            if (file_name != null) {
                String newmessage = "File: '" + file_name + "': " + cfe.getMessage();
                throw new ClassFormatException(newmessage);
            }
            throw cfe;
        }
    }

    /**
   * Read information about the fields of the class, i.e., its variables.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readFields() throws IOException, ClassFormatException {
        int fields_count;
        fields_count = file.readUnsignedShort();
        fields = new Field[fields_count];
        for (int i = 0; i < fields_count; i++) fields[i] = new Field(file, constant_pool);
    }

    /******************** Private utility methods **********************/
    /**
   * Check whether the header of the file is ok.
   * Of course, this has to be the first action on successive file reads.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readID() throws IOException, ClassFormatException {
        int magic = 0xCAFEBABE;
        if (file.readInt() != magic)
            throw new ClassFormatException(file_name + " is not a Java .class file");
    }

    /**
   * Read information about the interfaces implemented by this class.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readInterfaces() throws IOException, ClassFormatException {
        int interfaces_count;
        interfaces_count = file.readUnsignedShort();
        interfaces = new int[interfaces_count];
        for (int i = 0; i < interfaces_count; i++) interfaces[i] = file.readUnsignedShort();
    }

    /**
   * Read information about the methods of the class.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readMethods() throws IOException, ClassFormatException {
        int methods_count;
        methods_count = file.readUnsignedShort();
        methods = new Method[methods_count];
        for (int i = 0; i < methods_count; i++) methods[i] = new Method(file, constant_pool);
    }

    /**
   * Read major and minor version of compiler which created the file.
   * @throws  IOException
   * @throws  ClassFormatException
   */
    private final void readVersion() throws IOException, ClassFormatException {
        minor = file.readUnsignedShort();
        major = file.readUnsignedShort();
    }
}
