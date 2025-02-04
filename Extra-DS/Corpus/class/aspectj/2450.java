/* *******************************************************************
 * Copyright (c) 2003 Contributors.
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Matthew Webster     initial implementation 
 * ******************************************************************/
package org.aspectj.ajde;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.aspectj.util.FileUtil;

public class OutxmlTest extends AjdeTestCase {

    public static final String PROJECT_DIR = "OutxmlTest";

    public static final String BIN_DIR = "bin";

    public static final String OUTJAR_NAME = "/bin/test.jar";

    public static final String DEFAULT_AOPXML_NAME = "META-INF/aop.xml";

    public static final String CUSTOM_AOPXML_NAME = "custom/aop.xml";

    /*
	 * Ensure the output directory is clean
	 */
    protected void setUp() throws Exception {
        super.setUp(PROJECT_DIR);
        FileUtil.deleteContents(openFile(BIN_DIR));
    }

    /*
	 * Clean up afterwards
	 */
    protected void tearDown() throws Exception {
        super.tearDown();
        FileUtil.deleteContents(openFile(BIN_DIR));
        openFile(BIN_DIR).delete();
    }

    /**
	 * Aim: Test "-outxml" option produces the correct xml file
	 * 
	 */
    public void testOutxmlToFile() {
        //		System.out.println("OutxmlTest.testOutxmlToFile() outputpath='" + ideManager.getProjectProperties().getOutputPath() + "'");
        assertTrue("Build failed", doSynchronousBuild("outxml-to-file.lst"));
        assertTrue("Build warnings", ideManager.getCompilationSourceLineTasks().isEmpty());
        File aopxml = openFile(BIN_DIR + "/" + DEFAULT_AOPXML_NAME);
        assertTrue(DEFAULT_AOPXML_NAME + " missing", aopxml.exists());
    }

    /**
	 * Aim: Test "-outxmlfile filename" option produces the correct 
	 * xml file
	 * 
	 */
    public void testOutxmlfileToFile() {
        assertTrue("Build failed", doSynchronousBuild("outxmlfile-to-file.lst"));
        assertTrue("Build warnings", ideManager.getCompilationSourceLineTasks().isEmpty());
        File aopxml = openFile(BIN_DIR + "/" + CUSTOM_AOPXML_NAME);
        assertTrue(CUSTOM_AOPXML_NAME + " missing", aopxml.exists());
    }

    /**
	 * Aim: Test "-outxml" option produces the correct 
	 * xml entry in outjar file
	 * 
	 */
    public void testOutxmlToOutjar() {
        File outjar = openFile(OUTJAR_NAME);
        ideManager.getProjectProperties().setOutJar(outjar.getAbsolutePath());
        assertTrue("Build failed", doSynchronousBuild("outxml-to-outjar.lst"));
        assertTrue("Build warnings", ideManager.getCompilationSourceLineTasks().isEmpty());
        File aopxml = openFile(BIN_DIR + "/" + DEFAULT_AOPXML_NAME);
        assertFalse(DEFAULT_AOPXML_NAME + " should not exisit", aopxml.exists());
        assertJarContainsEntry(outjar, DEFAULT_AOPXML_NAME);
    }

    /**
	 * Aim: Test "-outxmlfile filename" option produces the correct 
	 * xml entry in outjar file
	 * 
	 */
    public void testOutxmlfileToOutjar() {
        //		System.out.println("OutxmlTest.testOutxmlToOutjar() outputpath='" + ideManager.getProjectProperties().getOutputPath() + "'");
        File outjar = openFile(OUTJAR_NAME);
        ideManager.getProjectProperties().setOutJar(outjar.getAbsolutePath());
        assertTrue("Build failed", doSynchronousBuild("outxmlfile-to-outjar.lst"));
        assertTrue("Build warnings", ideManager.getCompilationSourceLineTasks().isEmpty());
        File aopxml = openFile(BIN_DIR + "/" + CUSTOM_AOPXML_NAME);
        assertFalse(CUSTOM_AOPXML_NAME + " should not exisit", aopxml.exists());
        assertJarContainsEntry(outjar, CUSTOM_AOPXML_NAME);
    }

    private void assertJarContainsEntry(File file, String entryName) {
        try {
            JarFile jarFile = new JarFile(file);
            JarEntry jarEntry = jarFile.getJarEntry(entryName);
            assertNotNull(entryName + " missing", jarEntry);
        } catch (IOException ex) {
            fail(ex.toString());
        }
    }
}
