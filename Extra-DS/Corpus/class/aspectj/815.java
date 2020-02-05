/********************************************************************
 * Copyright (c) 2005 Contributors. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://eclipse.org/legal/epl-v10.html 
 *  
 * Contributors: IBM Corporation - initial API and implementation 
 * 				 Helen Hawkins   - iniital version
 *******************************************************************/
package org.aspectj.tools.ajdoc;

import java.io.File;
import java.io.IOException;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * This class is the super class of all Ajdoc tests. It creates
 * a sandbox directory and provides utility methods for
 * copying over the test projects and running the ajdoc command
 */
public class AjdocTestCase extends TestCase {

    public static final String testdataSrcDir = "../ajdoc/testdata";

    protected static File sandboxDir;

    private static final String SANDBOX_NAME = "ajcSandbox";

    private String docOutdir, projectDir;

    protected void setUp() throws Exception {
        super.setUp();
        docOutdir = null;
        projectDir = null;
        // Create a sandbox in which to work
        createEmptySandbox();
        // create the ajdocworkdingdir in the sandbox
        Main.setOutputWorkingDir(getWorkingDir().getAbsolutePath());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        // reset where ajdocworkingdir is created
        Main.resetOutputWorkingDir();
    }

    // Taken from AjdeInteractionTestbed 
    private void createEmptySandbox() {
        String os = System.getProperty("os.name");
        File tempDir = null;
        // in c:\documents and settings\......... for the results of a failed test.
        if (os.startsWith("Windows")) {
            //Alex: try D first since NTFS on mine while FAT leads to failure..
            tempDir = new File("D:\\temp");
            if (!tempDir.exists()) {
                tempDir = new File("C:\\temp");
                if (!tempDir.exists()) {
                    tempDir.mkdir();
                }
            }
        } else {
            tempDir = new File("/tmp");
        }
        File sandboxRoot = new File(tempDir, SANDBOX_NAME);
        if (!sandboxRoot.exists()) {
            sandboxRoot.mkdir();
        }
        org.aspectj.util.FileUtil.deleteContents(sandboxRoot);
        try {
            sandboxDir = File.createTempFile("ajcTest", ".tmp", sandboxRoot);
            sandboxDir.delete();
            sandboxDir.mkdir();
        } catch (IOException ioEx) {
            throw new AssertionFailedError("Unable to create sandbox directory for test");
        }
    }

    /**
	 * Fill in the working directory with the project files and 
	 * create a doc top level directory in which to generate
	 * the ajdoc output.
	 */
    public void initialiseProject(String projectName) {
        File projectSrc = new File(testdataSrcDir + File.separatorChar + projectName);
        File destination = new File(getWorkingDir(), projectName);
        if (!destination.exists()) {
            destination.mkdir();
        }
        copy(projectSrc, destination);
        projectDir = destination.getAbsolutePath();
        File docDestination = new File(getWorkingDir().toString() + File.separatorChar + projectName, "doc");
        if (!docDestination.exists()) {
            docDestination.mkdir();
        }
        docOutdir = docDestination.getAbsolutePath();
    }

    /**
	 * @return the working directory
	 */
    protected File getWorkingDir() {
        return sandboxDir;
    }

    /**
	 * @return the absolute path of the project directory
	 * for example c:\temp\ajcSandbox\ajcTest15200.tmp\myProject
	 */
    protected String getAbsoluteProjectDir() {
        return projectDir;
    }

    /**
	 * @return the absolute path of the doc output directory
	 * for example c:\temp\ajcSandbox\ajcTest15200.tmp\myProject\doc
	 */
    protected String getAbsolutePathOutdir() {
        return docOutdir;
    }

    /**
	 * Copy the contents of some directory to another location - the
	 * copy is recursive.
	 */
    private void copy(File from, File to) {
        String contents[] = from.list();
        if (contents == null)
            return;
        for (int i = 0; i < contents.length; i++) {
            String string = contents[i];
            File f = new File(from, string);
            File t = new File(to, string);
            if (f.isDirectory()) {
                t.mkdir();
                copy(f, t);
            } else if (f.isFile()) {
                try {
                    org.aspectj.util.FileUtil.copyFile(f, t);
                } catch (IOException e) {
                    throw new AssertionFailedError("Unable to copy " + f + " to " + t);
                }
            }
        }
    }

    /**
	 * Run the ajdoc command with the given visibility argument,
	 * the default source level and the given input files. 
	 */
    public void runAjdoc(String visibility, File[] inputFiles) {
        if (!visibility.equals("public") && !visibility.equals("protected") && !visibility.equals("private")) {
            fail("need to pass 'public','protected' or 'private' visibility to ajdoc");
        }
        if (inputFiles.length == 0) {
            fail("need to pass some files into ajdoc");
        }
        String[] args = new String[5 + inputFiles.length];
        args[0] = "-" + visibility;
        args[1] = "-classpath";
        args[2] = AjdocTests.ASPECTJRT_PATH.getPath();
        args[3] = "-d";
        args[4] = getAbsolutePathOutdir();
        for (int i = 0; i < inputFiles.length; i++) {
            args[5 + i] = inputFiles[i].getAbsolutePath();
        }
        org.aspectj.tools.ajdoc.Main.main(args);
    }

    /**
	 * Run the ajdoc command with the default visibility 
	 * and source level and the given input files. 
	 */
    public void runAjdoc(File[] inputFiles) {
        if (inputFiles.length == 0) {
            fail("need to pass some files into ajdoc");
        }
        String[] args = new String[4 + inputFiles.length];
        args[0] = "-classpath";
        args[1] = AjdocTests.ASPECTJRT_PATH.getPath();
        args[2] = "-d";
        args[3] = getAbsolutePathOutdir();
        for (int i = 0; i < inputFiles.length; i++) {
            args[4 + i] = inputFiles[i].getAbsolutePath();
        }
        org.aspectj.tools.ajdoc.Main.main(args);
    }

    /**
	 * Run the ajdoc command with the given visibility argument,
	 * the given source level argument and the given input files. 
	 */
    public void runAjdoc(String visibility, String sourceLevel, File[] inputFiles) {
        if (!visibility.equals("public") && !visibility.equals("protected") && !visibility.equals("private")) {
            fail("need to pass 'public','protected' or 'private' visibility to ajdoc");
        }
        if (!sourceLevel.equals("1.3") && !sourceLevel.equals("1.4") && !sourceLevel.equals("1.5")) {
            fail("need to pass ajdoc '1.3', '1.4', or '1.5' as the source level");
        }
        if (inputFiles.length == 0) {
            fail("need to pass some files into ajdoc");
        }
        for (int i = 0; i < inputFiles.length; i++) {
            if (!inputFiles[i].exists()) {
                fail(inputFiles[i].getAbsolutePath() + " does not exist");
            }
        }
        String[] args = new String[7 + inputFiles.length];
        args[0] = "-" + visibility;
        args[1] = "-source";
        args[2] = sourceLevel;
        args[3] = "-classpath";
        args[4] = AjdocTests.ASPECTJRT_PATH.getPath();
        args[5] = "-d";
        args[6] = getAbsolutePathOutdir();
        for (int i = 0; i < inputFiles.length; i++) {
            args[7 + i] = inputFiles[i].getAbsolutePath();
        }
        org.aspectj.tools.ajdoc.Main.main(args);
    }

    /**
	 * Run the ajdoc command with the given visibility argument,
	 * the default source level and the given input directories. 
	 */
    public void runAjdoc(String visibility, String[] directoryNames) {
        if (!visibility.equals("public") && !visibility.equals("protected") && !visibility.equals("private")) {
            fail("need to pass 'public','protected' or 'private' visibility to ajdoc");
        }
        if (directoryNames.length == 0) {
            fail("need to pass some directories into ajdoc");
        }
        String[] args = new String[7 + directoryNames.length];
        args[0] = "-" + visibility;
        args[1] = "-classpath";
        args[2] = AjdocTests.ASPECTJRT_PATH.getPath();
        args[3] = "-d";
        args[4] = getAbsolutePathOutdir();
        args[5] = "-sourcepath";
        args[6] = getAbsoluteProjectDir();
        for (int i = 0; i < directoryNames.length; i++) {
            args[7 + i] = directoryNames[i];
        }
        org.aspectj.tools.ajdoc.Main.main(args);
    }

    /**
	 * Run the ajdoc command with the default visibility and
	 * source level and the given input directories. 
	 */
    public void runAjdoc(String[] directoryNames) {
        if (directoryNames.length == 0) {
            fail("need to pass some directories into ajdoc");
        }
        String[] args = new String[6 + directoryNames.length];
        args[0] = "-classpath";
        args[1] = AjdocTests.ASPECTJRT_PATH.getPath();
        args[2] = "-d";
        args[3] = getAbsolutePathOutdir();
        args[4] = "-sourcepath";
        args[5] = getAbsoluteProjectDir();
        for (int i = 0; i < directoryNames.length; i++) {
            args[6 + i] = directoryNames[i];
        }
        org.aspectj.tools.ajdoc.Main.main(args);
    }

    /**
	 * Run the ajdoc command with the given visibility argument,
	 * the default source level and the given input directories. 
	 */
    public void runAjdoc(String visibility, String lstFile) {
        if (!visibility.equals("public") && !visibility.equals("protected") && !visibility.equals("private")) {
            fail("need to pass 'public','protected' or 'private' visibility to ajdoc");
        }
        String[] args = new String[8];
        args[0] = "-" + visibility;
        args[1] = "-classpath";
        args[2] = AjdocTests.ASPECTJRT_PATH.getPath();
        args[3] = "-d";
        args[4] = getAbsolutePathOutdir();
        args[5] = "-sourcepath";
        args[6] = getAbsoluteProjectDir();
        args[7] = "@" + getAbsoluteProjectDir() + File.separatorChar + lstFile;
        org.aspectj.tools.ajdoc.Main.main(args);
    }
}
