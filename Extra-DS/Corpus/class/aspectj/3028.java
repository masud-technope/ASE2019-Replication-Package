import java.io.*;
import java.util.*;
import org.apache.tools.ant.*;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.types.*;

/**
 * Tests the AJC2 ant task.
 */
public class AjcTaskTester2 extends AntTaskTester {

    protected static final String TEST_CLASSES = "test-classes";

    protected static final String TEST_SOURCES = "../src";

    protected File buildDir = null;

    /**
     * We use <code>"tests/ant/etc/ajc2.xml"</code>.
     */
    public String getAntFile() {
        return "tests/ant/etc/ajc2.xml";
    }

    /**
     * Put {@link #TEST_CLASSES} and {@link #TEST_SOURCES}
     * into the user properties.
     */
    protected Map getUserProperties() {
        Map userProps = new HashMap();
        userProps.put("ant.test.classes", TEST_CLASSES);
        userProps.put("ant.test.sources", TEST_SOURCES);
        return userProps;
    }

    ////// Begin tests //////////////////////////////////////////////
    public void test1() {
        wantClasses("One");
    }

    public void test2() {
        wantClasses("One,Two");
    }

    public void test3() {
        wantClasses("One,Two,Three");
    }

    public void test4() {
        wantClasses("One");
    }

    public void test4b() {
        wantClasses("One");
    }

    public void test5() {
        wantClasses("One,Two");
    }

    public void test5b() {
        wantClasses("One,Two");
    }

    public void test6() {
        wantClasses("One,Two,Three");
    }

    public void test6b() {
        wantClasses("One,Two,Three");
    }

    public void test8() {
        wantClasses("One");
    }

    public void test9() {
        wantClasses("One");
    }

    public void test10() {
        wantClasses("One");
    }

    public void test11() {
        wantClasses("One");
    }

    public void test12() {
        wantClasses("");
    }

    public void test13() {
        wantClasses("One");
    }

    public void fail1(BuildException be) {
    }

    public void fail2(BuildException be) {
    }

    public void fail3(BuildException be) {
    }

    ////// End tests ////////////////////////////////////////////////
    /**
     * Make the build dir -- e.g. call {@link #makeBuildDir}
     */
    protected void beforeEveryTask() {
        makeBuildDir();
    }

    /**
     * Assert classes and clear build dir.
     *
     * @see #checkClasses()
     * @see #clearBuildDir()
     */
    protected void afterEveryTask() {
        checkClasses();
        clearBuildDir();
    }

    /**
     * Expect the classes found in
     * <code>classNamesWithoutExtensions</code>
     *
     * @param classNamesWithoutExtensions Array of class names without
     *                                    extensions we want to see.
     * @see   #wantClasses(List)
     */
    protected void wantClasses(String[] classNamesWithoutExtensions) {
        List list = new Vector();
        for (int i = 0; i < classNamesWithoutExtensions.length; i++) {
            list.add(classNamesWithoutExtensions[i]);
        }
        wantClasses(list);
    }

    /**
     * Expect the classes found in
     * <code>classNamesWithoutExtensions</code>
     *
     * @param classNamesWithoutExtensions String of class names without
     *                                    extensions we want to see separated
     *                                    by <code> </code>, <code>,</code>, or
     *                                    <code>;</code>.
     * @see   #wantClasses(List)
     */
    protected void wantClasses(String classNamesWithoutExtensions) {
        StringTokenizer tok = new StringTokenizer(classNamesWithoutExtensions, ",;");
        List list = new Vector();
        while (tok.hasMoreTokens()) {
            list.add(tok.nextToken());
        }
        wantClasses(list);
    }

    /**
     * Expected each class name found in
     * <code>classNamesWithoutExtensions</code>.
     *
     * @param classNamesWithoutExtensions List of class names without
     *                                    exntensions.
     * @see   #want(Object)
     */
    protected void wantClasses(List classNamesWithoutExtensions) {
        Iterator iter = classNamesWithoutExtensions.iterator();
        while (iter.hasNext()) {
            String className = iter.next() + "";
            className = className.replace('.', '/').replace('\\', '/');
            want(className + ".class");
        }
    }

    protected void checkClasses() {
        Iterator iter = wants.iterator();
        while (iter.hasNext()) {
            String className = iter.next() + "";
            File file = new File(buildDir, className);
            if (file != null && file.exists()) {
                have(className);
            }
        }
    }

    protected void init() {
        buildDir = new File(project.getBaseDir(), TEST_CLASSES);
    }

    protected void makeBuildDir() {
        try {
            Mkdir mkdir = (Mkdir) project.createTask("mkdir");
            mkdir.setDir(buildDir);
            mkdir.execute();
        } catch (BuildException be) {
            be.printStackTrace();
        }
    }

    protected void clearBuildDir() {
        try {
            Delete delete = (Delete) project.createTask("delete");
            FileSet fileset = new FileSet();
            fileset.setDir(buildDir);
            fileset.setIncludes("**");
            delete.addFileset(fileset);
            delete.execute();
        } catch (BuildException be) {
            be.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AjcTaskTester2().runTests(args);
    }
}
