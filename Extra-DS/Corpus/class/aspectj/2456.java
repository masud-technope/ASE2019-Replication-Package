package org.aspectj.apache.bcel;

import org.aspectj.apache.bcel.classfile.JavaClass;
import org.aspectj.apache.bcel.util.*;
import java.io.*;

/**
 * The repository maintains informations about class interdependencies, e.g.,
 * whether a class is a sub-class of another. Delegates actual class loading
 * to SyntheticRepository with current class path by default.
 *
 * @see org.aspectj.apache.bcel.util.Repository
 * @see org.aspectj.apache.bcel.util.SyntheticRepository
 *
 * @version $Id: Repository.java,v 1.3 2004/11/19 16:45:19 aclement Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Repository {

    private static org.aspectj.apache.bcel.util.Repository _repository = null;

    /** @return currently used repository instance
   */
    public static org.aspectj.apache.bcel.util.Repository getRepository() {
        if (_repository == null)
            _repository = SyntheticRepository.getInstance();
        return _repository;
    }

    /** Set repository instance to be used for class loading
   */
    public static void setRepository(org.aspectj.apache.bcel.util.Repository rep) {
        _repository = rep;
    }

    /** Lookup class somewhere found on your CLASSPATH, or whereever the
   * repository instance looks for it.
   *
   * @return class object for given fully qualified class name, or null
   * if the class could not be found or parsed correctly
   */
    public static JavaClass lookupClass(String class_name) {
        try {
            JavaClass clazz = getRepository().findClass(class_name);
            if (clazz == null) {
                return getRepository().loadClass(class_name);
            } else {
                return clazz;
            }
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /**
   * Try to find class source via getResourceAsStream().
   * @see Class
   * @return JavaClass object for given runtime class
   */
    public static JavaClass lookupClass(Class clazz) {
        try {
            return getRepository().loadClass(clazz);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /** @return class file object for given Java class.
   */
    public static ClassPath.ClassFile lookupClassFile(String class_name) {
        try {
            return ClassPath.getSystemClassPath().getClassFile(class_name);
        } catch (IOException e) {
            return null;
        }
    }

    /** Clear the repository.
   */
    public static void clearCache() {
        getRepository().clear();
    }

    /**
   * Add clazz to repository if there isn't an equally named class already in there.
   *
   * @return old entry in repository
   */
    public static JavaClass addClass(JavaClass clazz) {
        JavaClass old = getRepository().findClass(clazz.getClassName());
        getRepository().storeClass(clazz);
        return old;
    }

    /**
   * Remove class with given (fully qualified) name from repository.
   */
    public static void removeClass(String clazz) {
        getRepository().removeClass(getRepository().findClass(clazz));
    }

    /**
   * Remove given class from repository.
   */
    public static void removeClass(JavaClass clazz) {
        getRepository().removeClass(clazz);
    }

    /**
   * @return list of super classes of clazz in ascending order, i.e.,
   * Object is always the last element
   */
    public static JavaClass[] getSuperClasses(JavaClass clazz) {
        return clazz.getSuperClasses();
    }

    /**
   * @return list of super classes of clazz in ascending order, i.e.,
   * Object is always the last element. return "null", if class
   * cannot be found.
   */
    public static JavaClass[] getSuperClasses(String class_name) {
        JavaClass jc = lookupClass(class_name);
        return (jc == null ? null : getSuperClasses(jc));
    }

    /**
   * @return all interfaces implemented by class and its super
   * classes and the interfaces that those interfaces extend, and so on.
   * (Some people call this a transitive hull).
   */
    public static JavaClass[] getInterfaces(JavaClass clazz) {
        return clazz.getAllInterfaces();
    }

    /**
   * @return all interfaces implemented by class and its super
   * classes and the interfaces that extend those interfaces, and so on
   */
    public static JavaClass[] getInterfaces(String class_name) {
        return getInterfaces(lookupClass(class_name));
    }

    /**
   * Equivalent to runtime "instanceof" operator.
   * @return true, if clazz is an instance of super_class
   */
    public static boolean instanceOf(JavaClass clazz, JavaClass super_class) {
        return clazz.instanceOf(super_class);
    }

    /**
   * @return true, if clazz is an instance of super_class
   */
    public static boolean instanceOf(String clazz, String super_class) {
        return instanceOf(lookupClass(clazz), lookupClass(super_class));
    }

    /**
   * @return true, if clazz is an instance of super_class
   */
    public static boolean instanceOf(JavaClass clazz, String super_class) {
        return instanceOf(clazz, lookupClass(super_class));
    }

    /**
   * @return true, if clazz is an instance of super_class
   */
    public static boolean instanceOf(String clazz, JavaClass super_class) {
        return instanceOf(lookupClass(clazz), super_class);
    }

    /**
   * @return true, if clazz is an implementation of interface inter
   */
    public static boolean implementationOf(JavaClass clazz, JavaClass inter) {
        return clazz.implementationOf(inter);
    }

    /**
   * @return true, if clazz is an implementation of interface inter
   */
    public static boolean implementationOf(String clazz, String inter) {
        return implementationOf(lookupClass(clazz), lookupClass(inter));
    }

    /**
   * @return true, if clazz is an implementation of interface inter
   */
    public static boolean implementationOf(JavaClass clazz, String inter) {
        return implementationOf(clazz, lookupClass(inter));
    }

    /**
   * @return true, if clazz is an implementation of interface inter
   */
    public static boolean implementationOf(String clazz, JavaClass inter) {
        return implementationOf(lookupClass(clazz), inter);
    }
}
