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
package org.aspectj.ajde.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.ui.BuildConfigNode;
import org.aspectj.util.ConfigParser;

/**
 * Used for reading and writing build configuration (".lst") files.
 *
 * @author Mik Kersten
 */
class LstBuildConfigFileUpdater {

    /**
     * Adds an entry to a build configuration file.
     */
    public void updateBuildConfigFile(String buildConfigFile, String update, boolean addToConfiguration) {
        List fileContents = readConfigFile(buildConfigFile);
        if (addToConfiguration) {
            fileContents.add(update);
        } else {
            fileContents.remove(update);
        }
        writeConfigFile(buildConfigFile, fileContents);
    }

    /**
     * Adds an entry to multiple build configuration files.
     */
    public void updateBuildConfigFiles(List buildConfigFiles, List filesToUpdate, boolean addToConfiguration) {
        for (int i = 0; i < buildConfigFiles.size(); i++) {
            List fileContents = readConfigFile((String) buildConfigFiles.get(i));
            if (addToConfiguration) {
                for (int j = 0; j < filesToUpdate.size(); j++) {
                    fileContents.add(filesToUpdate.get(j));
                }
            } else {
                for (int k = 0; k < filesToUpdate.size(); k++) {
                    if (fileContents.contains(filesToUpdate.get(k))) {
                        fileContents.remove(filesToUpdate.get(k));
                    }
                }
            }
            writeConfigFile((String) buildConfigFiles.get(i), fileContents);
        }
    }

    /**
     * Checks if an entry exists within a build configuration file.
     */
    public boolean exists(String entry, String configFile) {
        return exists(entry, configFile, "");
    }

    public boolean exists(String entry, String configFile, String rootPath) {
        Iterator it = readConfigFile(configFile).iterator();
        while (it.hasNext()) {
            if ((entry).equals(rootPath + "/" + (String) it.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads the entries of a configuration file.
     */
    public List readConfigFile(String filePath) {
        try {
            File configFile = new File(filePath);
            if (!configFile.exists()) {
                Ajde.getDefault().getErrorHandler().handleWarning("Config file: " + filePath + " does not exist.  Update failed.");
            }
            List fileContents = new ArrayList();
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String line = reader.readLine();
            while (line != null) {
                fileContents.add(line.replace('\\', '/'));
                line = reader.readLine();
            }
            reader.close();
            return fileContents;
        } catch (IOException ioe) {
            Ajde.getDefault().getErrorHandler().handleError("Could not update build config file.", ioe);
        }
        return null;
    }

    public void writeConfigFile(String filePath, List files, List importedNodes) {
        String fileContentsString = "";
        Set includedFiles = new HashSet();
        for (Iterator it = importedNodes.iterator(); it.hasNext(); ) {
            BuildConfigNode node = (BuildConfigNode) it.next();
            fileContentsString += '@' + node.getResourcePath() + "\n";
            String parentPath = new File(filePath).getParent();
            String importedFilePath = parentPath + File.separator + node.getResourcePath();
            includedFiles.addAll(getIncludedFiles(importedFilePath, parentPath));
        }
        for (Iterator it = files.iterator(); it.hasNext(); ) {
            BuildConfigNode node = (BuildConfigNode) it.next();
            if (node.getName().endsWith(".lst") && !node.getResourcePath().startsWith("..")) {
                fileContentsString += '@';
                fileContentsString += node.getResourcePath() + "\n";
            } else {
                if (!includedFiles.contains(node.getResourcePath())) {
                    fileContentsString += node.getResourcePath() + "\n";
                }
            }
        }
        writeFile(fileContentsString, filePath);
    }

    private List getIncludedFiles(String path, String rootPath) {
        try {
            ConfigParser configParser = new ConfigParser();
            configParser.parseConfigFile(new File(path));
            List files = configParser.getFiles();
            List relativeFiles = new ArrayList();
            for (Iterator it = files.iterator(); it.hasNext(); ) {
                relativeFiles.add(relativizePath(((File) it.next()).getPath(), rootPath));
            }
            return relativeFiles;
        } catch (ConfigParser.ParseException pe) {
            return new ArrayList();
        }
    }

    public String relativizePath(String path, String rootPath) {
        path = path.replace('\\', '/');
        rootPath = rootPath.replace('\\', '/');
        int pathIndex = path.indexOf(rootPath);
        if (pathIndex > -1) {
            return path.substring(pathIndex + rootPath.length() + 1);
        } else {
            return path;
        }
    }

    public void writeConfigFile(String filePath, List fileContents) {
        Set contentsSet = new TreeSet(fileContents);
        StringBuffer fileContentsSB = new StringBuffer();
        Iterator it = contentsSet.iterator();
        while (it.hasNext()) {
            fileContentsSB.append(it.next().toString());
            fileContentsSB.append("\n");
        }
        writeFile(fileContentsSB.toString(), filePath);
    }

    private void writeFile(String contents, String filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, false);
            fos.write(contents.getBytes());
        } catch (IOException ioe) {
            Ajde.getDefault().getErrorHandler().handleError("Could not update build config file: " + filePath, ioe);
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException ioe) {
                }
        }
    }
}
