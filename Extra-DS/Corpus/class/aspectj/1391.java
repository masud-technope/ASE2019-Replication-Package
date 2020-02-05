/*******************************************************************************
 * Copyright (c) 2005 Contributors.
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://eclipse.org/legal/epl-v10.html 
 * 
 * Contributors:
 *   Matthew Webster         initial implementation
 *******************************************************************************/
package org.aspectj.systemtest.ajc150.ltw;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import junit.framework.Test;
import org.aspectj.testing.XMLBasedAjcTestCase;
import org.aspectj.weaver.tools.WeavingAdaptor;

public class LTWTests extends org.aspectj.testing.XMLBasedAjcTestCase {

    public static Test suite() {
        return XMLBasedAjcTestCase.loadSuite(LTWTests.class);
    }

    protected File getSpecFile() {
        return new File("../tests/src/org/aspectj/systemtest/ajc150/ltw/ltw.xml");
    }

    public void test001() {
        runTest("Ensure 1st aspect is rewoven when weaving 2nd aspect");
    }

    public void testOutxmlFile() {
        runTest("Ensure valid aop.xml file is generated");
    }

    public void testOutxmlJar() {
        runTest("Ensure valid aop.xml is generated for -outjar");
    }

    public void testNoAopxml() {
        setSystemProperty(WeavingAdaptor.WEAVING_ADAPTOR_VERBOSE, "true");
        runTest("Ensure no weaving without visible aop.xml");
    }

    public void testDefineConcreteAspect() {
        runTest("Define concrete sub-aspect using aop.xml");
    }

    public void testDeclareAbstractAspect() {
        //		setSystemProperty(WeavingAdaptor.WEAVING_ADAPTOR_VERBOSE,"true");
        //		setSystemProperty(WeavingAdaptor.SHOW_WEAVE_INFO_PROPERTY,"true");
        runTest("Use abstract aspect for ITD using aop.xml");
    }

    public void testAspectsInclude() {
        runTest("Ensure a subset of inherited aspects is used for weaving");
    }

    public void testAspectsIncludeWithLintWarning() {
        runTest("Ensure weaver lint warning issued when an aspect is not used for weaving");
    }

    public void testXlintfileEmpty() {
        runTest("Empty Xlint.properties file");
    }

    public void testXlintfileMissing() {
        runTest("Warning with missing Xlint.properties file");
    }

    public void testXlintWarningAdviceDidNotMatchSuppressed() {
        runTest("Warning when advice doesn't match suppressed for LTW");
    }

    public void testXlintfile() {
        runTest("Override suppressing of warning when advice doesn't match using -Xlintfile");
    }

    public void testXlintDefault() {
        runTest("Warning when advice doesn't match using -Xlint:default");
    }

    public void testXlintWarning() {
        runTest("Override suppressing of warning when advice doesn't match using -Xlint:warning");
    }

    /*
  	 * Allow system properties to be set and restored
  	 * TODO maw move to XMLBasedAjcTestCase or RunSpec
  	 */
    private static final String NULL = "null";

    private Properties savedProperties;

    protected void setSystemProperty(String key, String value) {
        Properties systemProperties = System.getProperties();
        copyProperty(key, systemProperties, savedProperties);
        systemProperties.setProperty(key, value);
    }

    private static void copyProperty(String key, Properties from, Properties to) {
        String value = from.getProperty(key, NULL);
        to.setProperty(key, value);
    }

    protected void setUp() throws Exception {
        super.setUp();
        savedProperties = new Properties();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        /* Restore system properties */
        Properties systemProperties = System.getProperties();
        for (Enumeration enu = savedProperties.keys(); enu.hasMoreElements(); ) {
            String key = (String) enu.nextElement();
            String value = savedProperties.getProperty(key);
            if (value == NULL)
                systemProperties.remove(key);
            else
                systemProperties.setProperty(key, value);
        }
    }
}
