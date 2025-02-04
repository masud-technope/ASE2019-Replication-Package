/* *******************************************************************
 * Copyright (c) 1999-2001 Xerox Corporation, 
 *               2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Xerox/PARC     initial implementation 
 * ******************************************************************/
package org.aspectj.tools.ajbrowser;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import org.aspectj.ajde.ui.swing.*;

/**
 * @author Mik Kersten
 */
public class BrowserOptionsPanel extends OptionsPanel {

    private static final long serialVersionUID = 4491319302490183151L;

    private JPanel runOptions_panel = new JPanel();

    private JPanel build_panel = new JPanel();

    private FlowLayout flowLayout1 = new FlowLayout();

    private JTextField classToRun_field = new JTextField();

    private JLabel jLabel4 = new JLabel();

    private BorderLayout borderLayout4 = new BorderLayout();

    private JPanel buildPaths_panel = new JPanel();

    private Box compileOptions_box2 = Box.createVerticalBox();

    private JTextField classpath_field = new JTextField();

    private JTextField outputPath_field = new JTextField();

    private JLabel jLabel16 = new JLabel();

    private JLabel jLabel15 = new JLabel();

    private Box compileOptions_box3 = Box.createVerticalBox();

    private BorderLayout borderLayout1 = new BorderLayout();

    private Border border1;

    private TitledBorder titledBorder1;

    private Border border2;

    private Border border3;

    private TitledBorder titledBorder2;

    private Border border4;

    public  BrowserOptionsPanel() {
        try {
            jbInit();
            this.setName("AJBrowser Options");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadOptions() throws IOException {
        outputPath_field.setText(BrowserManager.getDefault().getBrowserProjectProperties().getOutputPath());
        classpath_field.setText(BrowserManager.getDefault().getBrowserProjectProperties().getClasspath());
        classToRun_field.setText(BrowserManager.getDefault().getBrowserProjectProperties().getClassToExecute());
    }

    public void saveOptions() throws IOException {
        BrowserManager.getDefault().getBrowserProjectProperties().setOutputPath(outputPath_field.getText());
        BrowserManager.getDefault().getBrowserProjectProperties().setClasspath(classpath_field.getText());
        BrowserManager.getDefault().getBrowserProjectProperties().setClassToExecute(classToRun_field.getText());
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(156, 156, 158));
        titledBorder1 = new TitledBorder(border1, "ajc Build Paths");
        border2 = BorderFactory.createCompoundBorder(titledBorder1, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        border3 = BorderFactory.createEtchedBorder(Color.white, new Color(156, 156, 158));
        titledBorder2 = new TitledBorder(border3, "Run Options");
        border4 = BorderFactory.createCompoundBorder(titledBorder2, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(borderLayout1);
        build_panel.setLayout(borderLayout4);
        classToRun_field.setFont(new java.awt.Font("SansSerif", 0, 11));
        classToRun_field.setMinimumSize(new Dimension(200, 21));
        classToRun_field.setPreferredSize(new Dimension(250, 21));
        jLabel4.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel4.setText("Fully qualified name for main class to run: ");
        buildPaths_panel.setLayout(flowLayout1);
        runOptions_panel.setBorder(border4);
        buildPaths_panel.setBorder(border2);
        classpath_field.setFont(new java.awt.Font("SansSerif", 0, 11));
        classpath_field.setMinimumSize(new Dimension(100, 21));
        classpath_field.setPreferredSize(new Dimension(150, 21));
        outputPath_field.setPreferredSize(new Dimension(225, 21));
        outputPath_field.setMinimumSize(new Dimension(100, 21));
        outputPath_field.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel16.setText("Classpath (defaults to current directory): ");
        jLabel16.setPreferredSize(new Dimension(200, 25));
        jLabel16.setMaximumSize(new Dimension(400, 25));
        jLabel16.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel15.setMaximumSize(new Dimension(400, 25));
        jLabel15.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel15.setPreferredSize(new Dimension(230, 25));
        jLabel15.setText("Output path (defaults to current directory): ");
        titledBorder1.setTitleFont(new java.awt.Font("Dialog", 0, 11));
        titledBorder2.setTitleFont(new java.awt.Font("Dialog", 0, 11));
        runOptions_panel.add(jLabel4, null);
        runOptions_panel.add(classToRun_field, null);
        build_panel.add(buildPaths_panel, BorderLayout.CENTER);
        build_panel.add(runOptions_panel, BorderLayout.SOUTH);
        compileOptions_box2.add(outputPath_field, null);
        compileOptions_box2.add(classpath_field, null);
        compileOptions_box3.add(jLabel15, null);
        compileOptions_box3.add(jLabel16, null);
        buildPaths_panel.add(compileOptions_box3, null);
        buildPaths_panel.add(compileOptions_box2, null);
        this.add(build_panel, BorderLayout.NORTH);
    }
}
