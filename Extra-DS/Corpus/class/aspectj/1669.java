/* *******************************************************************
 * Copyright (c) 2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     PARC     initial implementation 
 * ******************************************************************/
package org.aspectj.ajdt.internal.core.builder;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aspectj.ajdt.internal.compiler.InterimCompilationResult;
import org.aspectj.asm.IHierarchy;
import org.aspectj.asm.IRelationshipMap;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.Message;
import org.aspectj.bridge.SourceLocation;
import org.aspectj.org.eclipse.jdt.core.compiler.CharOperation;
import org.aspectj.org.eclipse.jdt.internal.compiler.CompilationResult;
import org.aspectj.org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.aspectj.org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.aspectj.org.eclipse.jdt.internal.compiler.env.IBinaryField;
import org.aspectj.org.eclipse.jdt.internal.compiler.env.IBinaryMethod;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.CompilerModifiers;
import org.aspectj.org.eclipse.jdt.internal.core.builder.ReferenceCollection;
import org.aspectj.org.eclipse.jdt.internal.core.builder.StringSet;
import org.aspectj.util.FileUtil;
import org.aspectj.weaver.BCException;
import org.aspectj.weaver.IWeaver;
import org.aspectj.weaver.ReferenceType;
import org.aspectj.weaver.ResolvedMember;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.UnresolvedType;
import org.aspectj.weaver.bcel.BcelWeaver;
import org.aspectj.weaver.bcel.BcelWorld;
import org.aspectj.weaver.bcel.UnwovenClassFile;

/**
 * Holds state needed for incremental compilation
 */
public class AjState {

    private AjBuildManager buildManager;

    private boolean couldBeSubsequentIncrementalBuild = false;

    // SECRETAPI static so beware of multi-threading bugs...
    public static IStateListener stateListener = null;

    public static boolean FORCE_INCREMENTAL_DURING_TESTING = false;

    private IHierarchy structureModel;

    private IRelationshipMap relmap;

    private long lastSuccessfulFullBuildTime = -1;

    private Hashtable /* File, long */
    structuralChangesSinceLastFullBuild = new Hashtable();

    private long lastSuccessfulBuildTime = -1;

    private long currentBuildTime = -1;

    private AjBuildConfig buildConfig;

    private boolean batchBuildRequiredThisTime = false;

    /**
	 * Keeps a list of (FQN,Filename) pairs (as ClassFile objects)
	 * for types that resulted from the compilation of the given
	 * File. Note :- the ClassFile objects contain no byte code, 
	 * they are simply a Filename,typename pair.
	 * 
	 * Populated in noteResult and used in addDependentsOf(File)
	 * 
	 * Added by AMC during state refactoring, 1Q06.
	 */
    private /*<File, List<ClassFile>*/
    Map fullyQualifiedTypeNamesResultingFromCompilationUnit = new HashMap();

    /**
	 * Source files defining aspects
	 * 
	 * Populated in noteResult and used in processDeletedFiles
	 * 
	 * Added by AMC during state refactoring, 1Q06.
	 */
    private /*<File>*/
    Set sourceFilesDefiningAspects = new HashSet();

    /**
	 * Populated in noteResult to record the set of types that should be recompiled if
	 * the given file is modified or deleted. 
	 * 
	 * Refered to during addAffectedSourceFiles when calculating incremental compilation set.
	 */
    private /*<File, ReferenceCollection>*/
    Map references = new HashMap();

    /**
	 * Holds UnwovenClassFiles (byte[]s) originating from the given file source. This
	 * could be a jar file, a directory, or an individual .class file. This is an 
	 * *expensive* map. It is cleared immediately following a batch build, and the
	 * cheaper inputClassFilesBySource map is kept for processing of any subsequent
	 * incremental builds.
	 * 
	 * Populated during AjBuildManager.initBcelWorld().
	 * 
	 * Passed into AjCompiler adapter as the set of binary input files to reweave if the
	 * weaver determines a full weave is required.
	 * 
	 * Cleared during initBcelWorld prior to repopulation.
	 * 
	 * Used when a file is deleted during incremental compilation to delete all of the
	 * class files in the output directory that resulted from the weaving of File.
	 * 
	 * Used during getBinaryFilesToCompile when compiling incrementally to determine 
	 * which files should be recompiled if a given input file has changed.
	 * 
	 */
    private /*File, List<UnwovenClassFile>*/
    Map binarySourceFiles = new HashMap();

    /**
	 * Initially a duplicate of the information held in binarySourceFiles, with the
	 * key difference that the values are ClassFiles (type name, File) not UnwovenClassFiles
	 * (which also have all the byte code in them). After a batch build, binarySourceFiles
	 * is cleared, leaving just this much lighter weight map to use in processing 
	 * subsequent incremental builds.
	 */
    private /*<File,List<ClassFile>*/
    Map inputClassFilesBySource = new HashMap();

    /**
	 * Holds structure information on types as they were at the end of the last
	 * build. It would be nice to get rid of this too, but can't see an easy way to do
	 * that right now. 
	 */
    private /*FQN,CompactStructureRepresentation*/
    Map resolvedTypeStructuresFromLastBuild = new HashMap();

    /**
	 * Populated in noteResult to record the set of UnwovenClassFiles (intermediate results)
	 * that originated from compilation of the class with the given fully-qualified name.
	 * 
	 * Used in removeAllResultsOfLastBuild to remove .class files from output directory.
	 *
	 * Passed into StatefulNameEnvironment during incremental compilation to support 
	 * findType lookups.
	 */
    private /*<String, File>*/
    Map classesFromName = new HashMap();

    private List /*File*/
    compiledSourceFiles = new ArrayList();

    private List /*String*/
    resources = new ArrayList();

    private List /*String*/
    aspectNames;

    private ArrayList /*<String>*/
    qualifiedStrings;

    private ArrayList /*<String>*/
    simpleStrings;

    private Set addedFiles;

    private Set deletedFiles;

    private Set /*BinarySourceFile*/
    addedBinaryFiles;

    private Set /*BinarySourceFile*/
    deletedBinaryFiles;

    private BcelWeaver weaver;

    private BcelWorld world;

    public  AjState(AjBuildManager buildManager) {
        this.buildManager = buildManager;
    }

    public void setCouldBeSubsequentIncrementalBuild(boolean yesThereCould) {
        this.couldBeSubsequentIncrementalBuild = yesThereCould;
    }

    void successfulCompile(AjBuildConfig config, boolean wasFullBuild) {
        buildConfig = config;
        lastSuccessfulBuildTime = currentBuildTime;
        if (stateListener != null)
            stateListener.buildSuccessful(wasFullBuild);
        if (wasFullBuild)
            lastSuccessfulFullBuildTime = currentBuildTime;
    }

    /**
	 * Returns false if a batch build is needed.
	 */
    boolean prepareForNextBuild(AjBuildConfig newBuildConfig) {
        currentBuildTime = System.currentTimeMillis();
        if (!maybeIncremental()) {
            if (listenerDefined())
                getListener().recordDecision("Preparing for build: not going to be incremental because either not in AJDT or incremental deactivated");
            return false;
        }
        if (this.batchBuildRequiredThisTime) {
            this.batchBuildRequiredThisTime = false;
            if (listenerDefined())
                getListener().recordDecision("Preparing for build: not going to be incremental this time because batch build explicitly forced");
            return false;
        }
        if (lastSuccessfulBuildTime == -1 || buildConfig == null) {
            structuralChangesSinceLastFullBuild.clear();
            if (listenerDefined())
                getListener().recordDecision("Preparing for build: not going to be incremental because no successful previous full build");
            return false;
        }
        // we don't support incremental with an outjar yet
        if (newBuildConfig.getOutputJar() != null) {
            structuralChangesSinceLastFullBuild.clear();
            if (listenerDefined())
                getListener().recordDecision("Preparing for build: not going to be incremental because outjar being used");
            return false;
        }
        // has changed, or a jar on a path has been modified
        if (pathChange(buildConfig, newBuildConfig)) {
            // last time we built, .class files and resource files from jars on the
            // inpath will have been copied to the output directory.
            // these all need to be deleted in preparation for the clean build that is
            // coming - otherwise a file that has been deleted from an inpath jar 
            // since the last build will not be deleted from the output directory.
            removeAllResultsOfLastBuild();
            if (stateListener != null)
                stateListener.pathChangeDetected();
            structuralChangesSinceLastFullBuild.clear();
            if (listenerDefined())
                getListener().recordDecision("Preparing for build: not going to be incremental because path change detected (one of classpath/aspectpath/inpath/injars)");
            return false;
        }
        simpleStrings = new ArrayList();
        qualifiedStrings = new ArrayList();
        Set oldFiles = new HashSet(buildConfig.getFiles());
        Set newFiles = new HashSet(newBuildConfig.getFiles());
        addedFiles = new HashSet(newFiles);
        addedFiles.removeAll(oldFiles);
        deletedFiles = new HashSet(oldFiles);
        deletedFiles.removeAll(newFiles);
        Set oldBinaryFiles = new HashSet(buildConfig.getBinaryFiles());
        Set newBinaryFiles = new HashSet(newBuildConfig.getBinaryFiles());
        addedBinaryFiles = new HashSet(newBinaryFiles);
        addedBinaryFiles.removeAll(oldBinaryFiles);
        deletedBinaryFiles = new HashSet(oldBinaryFiles);
        deletedBinaryFiles.removeAll(newBinaryFiles);
        boolean couldStillBeIncremental = processDeletedFiles(deletedFiles);
        if (!couldStillBeIncremental) {
            if (listenerDefined())
                getListener().recordDecision("Preparing for build: not going to be incremental because an aspect was deleted");
            return false;
        }
        if (listenerDefined())
            getListener().recordDecision("Preparing for build: planning to be an incremental build");
        return true;
    }

    /**
	 * Checks if any of the files in the set passed in contains an aspect declaration.  If one is found
	 * then we start the process of batch building, i.e. we remove all the results of the last build,
	 * call any registered listener to tell them whats happened and return false.
	 * 
	 * @return false if we discovered an aspect declaration
	 */
    private boolean processDeletedFiles(Set deletedFiles) {
        for (Iterator iter = deletedFiles.iterator(); iter.hasNext(); ) {
            File aDeletedFile = (File) iter.next();
            if (this.sourceFilesDefiningAspects.contains(aDeletedFile)) {
                removeAllResultsOfLastBuild();
                if (stateListener != null)
                    stateListener.detectedAspectDeleted(aDeletedFile);
                return false;
            }
        }
        return true;
    }

    private Collection getModifiedFiles() {
        return getModifiedFiles(lastSuccessfulBuildTime);
    }

    Collection getModifiedFiles(long lastBuildTime) {
        List ret = new ArrayList();
        //not our job to account for new and deleted files
        for (Iterator i = buildConfig.getFiles().iterator(); i.hasNext(); ) {
            File file = (File) i.next();
            if (!file.exists())
                continue;
            long modTime = file.lastModified();
            // need to add 1000 since lastModTime is only accurate to a second on some (all?) platforms
            if (modTime + 1000 > lastBuildTime) {
                ret.add(file);
            }
        }
        return ret;
    }

    private Collection getModifiedBinaryFiles() {
        return getModifiedBinaryFiles(lastSuccessfulBuildTime);
    }

    Collection getModifiedBinaryFiles(long lastBuildTime) {
        List ret = new ArrayList();
        //not our job to account for new and deleted files
        for (Iterator i = buildConfig.getBinaryFiles().iterator(); i.hasNext(); ) {
            AjBuildConfig.BinarySourceFile bsfile = (AjBuildConfig.BinarySourceFile) i.next();
            File file = bsfile.binSrc;
            if (!file.exists())
                continue;
            long modTime = file.lastModified();
            // need to add 1000 since lastModTime is only accurate to a second on some (all?) platforms
            if (modTime + 1000 >= lastBuildTime) {
                ret.add(bsfile);
            }
        }
        return ret;
    }

    private boolean classFileChangedInDirSinceLastBuild(File dir) {
        // Is another process building into that directory?
        AjState state = IncrementalStateManager.findStateManagingOutputLocation(dir);
        File[] classFiles = FileUtil.listFiles(dir, new FileFilter() {

            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".class");
            }
        });
        for (int i = 0; i < classFiles.length; i++) {
            long modTime = classFiles[i].lastModified();
            if ((modTime + 1000) >= lastSuccessfulBuildTime) {
                // structurally changed or not
                if (state != null) {
                    boolean realChange = state.hasStructuralChangedSince(classFiles[i], lastSuccessfulBuildTime);
                    if (realChange)
                        return true;
                } else {
                    // no state object to ask so it must have changed
                    return true;
                }
            }
        }
        return false;
    }

    /**
	 * Determine if a file has changed since a given time, using the local information
	 * recorded in the structural changes data structure.
	 * 
	 * file is the file we are wondering about
	 * lastSBT is the last build time for the state asking the question
	 */
    private boolean hasStructuralChangedSince(File file, long lastSuccessfulBuildTime) {
        //long lastModTime = file.lastModified();
        Long l = (Long) structuralChangesSinceLastFullBuild.get(file.getAbsolutePath());
        long strucModTime = -1;
        if (l != null)
            strucModTime = l.longValue();
        else
            strucModTime = this.lastSuccessfulFullBuildTime;
        // 'strucModTime'-> the last time the class was structurally changed
        return (strucModTime > lastSuccessfulBuildTime);
    }

    private boolean pathChange(AjBuildConfig oldConfig, AjBuildConfig newConfig) {
        boolean changed = false;
        List oldClasspath = oldConfig.getClasspath();
        List newClasspath = newConfig.getClasspath();
        if (stateListener != null)
            stateListener.aboutToCompareClasspaths(oldClasspath, newClasspath);
        if (changed(oldClasspath, newClasspath, true, oldConfig.getOutputDir()))
            return true;
        List oldAspectpath = oldConfig.getAspectpath();
        List newAspectpath = newConfig.getAspectpath();
        if (changed(oldAspectpath, newAspectpath, true, oldConfig.getOutputDir()))
            return true;
        List oldInJars = oldConfig.getInJars();
        List newInJars = newConfig.getInJars();
        if (changed(oldInJars, newInJars, false, oldConfig.getOutputDir()))
            return true;
        List oldInPath = oldConfig.getInpath();
        List newInPath = newConfig.getInpath();
        if (changed(oldInPath, newInPath, false, oldConfig.getOutputDir()))
            return true;
        return changed;
    }

    private boolean changed(List oldPath, List newPath, boolean checkClassFiles, File oldOutputLocation) {
        if (oldPath == null)
            oldPath = new ArrayList();
        if (newPath == null)
            newPath = new ArrayList();
        try {
            if (oldOutputLocation != null) {
                oldOutputLocation = oldOutputLocation.getCanonicalFile();
            }
        } catch (IOException /* we did our best...*/
        ex) {
        }
        if (oldPath.size() != newPath.size()) {
            return true;
        }
        for (int i = 0; i < oldPath.size(); i++) {
            if (!oldPath.get(i).equals(newPath.get(i))) {
                return true;
            }
            // String on classpath, File on other paths
            Object o = oldPath.get(i);
            File f = null;
            if (o instanceof String) {
                f = new File((String) o);
            } else {
                f = (File) o;
            }
            if (f.exists() && !f.isDirectory() && (f.lastModified() >= lastSuccessfulBuildTime)) {
                return true;
            }
            if (f.exists() && f.isDirectory() && checkClassFiles && !(f.equals(oldOutputLocation))) {
                boolean b = classFileChangedInDirSinceLastBuild(f);
                if (b && stateListener != null)
                    stateListener.detectedClassChangeInThisDir(f);
                if (b)
                    return true;
            }
        }
        return false;
    }

    public List getFilesToCompile(boolean firstPass) {
        List thisTime = new ArrayList();
        if (firstPass) {
            compiledSourceFiles = new ArrayList();
            Collection modifiedFiles = getModifiedFiles();
            //System.out.println("modified: " + modifiedFiles);
            thisTime.addAll(modifiedFiles);
            //??? eclipse IncrementalImageBuilder appears to do this
            //		for (Iterator i = modifiedFiles.iterator(); i.hasNext();) {
            //			File file = (File) i.next();
            //			addDependentsOf(file);
            //		}
            thisTime.addAll(addedFiles);
            deleteClassFiles();
            deleteResources();
            addAffectedSourceFiles(thisTime, thisTime);
        } else {
            addAffectedSourceFiles(thisTime, compiledSourceFiles);
        }
        compiledSourceFiles = thisTime;
        return thisTime;
    }

    private boolean maybeIncremental() {
        return (FORCE_INCREMENTAL_DURING_TESTING || this.couldBeSubsequentIncrementalBuild);
    }

    public Map getBinaryFilesToCompile(/* String -> List<ucf> */
    boolean firstTime) {
        if (lastSuccessfulBuildTime == -1 || buildConfig == null || !maybeIncremental()) {
            return binarySourceFiles;
        }
        // else incremental...
        Map toWeave = new HashMap();
        if (firstTime) {
            List addedOrModified = new ArrayList();
            addedOrModified.addAll(addedBinaryFiles);
            addedOrModified.addAll(getModifiedBinaryFiles());
            for (Iterator iter = addedOrModified.iterator(); iter.hasNext(); ) {
                AjBuildConfig.BinarySourceFile bsf = (AjBuildConfig.BinarySourceFile) iter.next();
                UnwovenClassFile ucf = createUnwovenClassFile(bsf);
                if (ucf == null)
                    continue;
                List ucfs = new ArrayList();
                ucfs.add(ucf);
                addDependentsOf(ucf.getClassName());
                binarySourceFiles.put(bsf.binSrc.getPath(), ucfs);
                List cfs = new ArrayList(1);
                cfs.add(getClassFileFor(ucf));
                this.inputClassFilesBySource.put(bsf.binSrc.getPath(), cfs);
                toWeave.put(bsf.binSrc.getPath(), ucfs);
            }
            deleteBinaryClassFiles();
        } else {
        // return empty set... we've already done our bit.
        }
        return toWeave;
    }

    /**
	 * Called when a path change is about to trigger a full build, but
	 * we haven't cleaned up from the last incremental build...
	 */
    private void removeAllResultsOfLastBuild() {
        // remove all binarySourceFiles, and all classesFromName...
        for (Iterator iter = this.inputClassFilesBySource.values().iterator(); iter.hasNext(); ) {
            List cfs = (List) iter.next();
            for (Iterator iterator = cfs.iterator(); iterator.hasNext(); ) {
                ClassFile cf = (ClassFile) iterator.next();
                cf.deleteFromFileSystem();
            }
        }
        for (Iterator iterator = classesFromName.values().iterator(); iterator.hasNext(); ) {
            File f = (File) iterator.next();
            new ClassFile("", f).deleteFromFileSystem();
        }
        for (Iterator iter = resources.iterator(); iter.hasNext(); ) {
            String resource = (String) iter.next();
            new File(buildConfig.getOutputDir(), resource).delete();
        }
    }

    private void deleteClassFiles() {
        for (Iterator i = deletedFiles.iterator(); i.hasNext(); ) {
            File deletedFile = (File) i.next();
            addDependentsOf(deletedFile);
            List cfs = (List) this.fullyQualifiedTypeNamesResultingFromCompilationUnit.get(deletedFile);
            this.fullyQualifiedTypeNamesResultingFromCompilationUnit.remove(deletedFile);
            if (cfs != null) {
                for (Iterator iter = cfs.iterator(); iter.hasNext(); ) {
                    ClassFile cf = (ClassFile) iter.next();
                    deleteClassFile(cf);
                }
            }
        }
    }

    private void deleteBinaryClassFiles() {
        // range of bsf is ucfs, domain is files (.class and jars) in inpath/jars
        for (Iterator iter = deletedBinaryFiles.iterator(); iter.hasNext(); ) {
            AjBuildConfig.BinarySourceFile deletedFile = (AjBuildConfig.BinarySourceFile) iter.next();
            List cfs = (List) this.inputClassFilesBySource.get(deletedFile.binSrc.getPath());
            for (Iterator iterator = cfs.iterator(); iterator.hasNext(); ) {
                deleteClassFile((ClassFile) iterator.next());
            }
            this.inputClassFilesBySource.remove(deletedFile.binSrc.getPath());
        }
    }

    private void deleteResources() {
        List oldResources = new ArrayList();
        oldResources.addAll(resources);
        // with incremental compilation
        for (Iterator i = buildConfig.getInpath().iterator(); i.hasNext(); ) {
            File inPathElement = (File) i.next();
            if (inPathElement.isDirectory() && AjBuildManager.COPY_INPATH_DIR_RESOURCES) {
                deleteResourcesFromDirectory(inPathElement, oldResources);
            }
        }
        if (buildConfig.getSourcePathResources() != null) {
            for (Iterator i = buildConfig.getSourcePathResources().keySet().iterator(); i.hasNext(); ) {
                String resource = (String) i.next();
                maybeDeleteResource(resource, oldResources);
            }
        }
        // oldResources need to be deleted...
        for (Iterator iter = oldResources.iterator(); iter.hasNext(); ) {
            String victim = (String) iter.next();
            File f = new File(buildConfig.getOutputDir(), victim);
            if (f.exists()) {
                f.delete();
            }
            resources.remove(victim);
        }
    }

    private void maybeDeleteResource(String resName, List oldResources) {
        if (resources.contains(resName)) {
            oldResources.remove(resName);
            File source = new File(buildConfig.getOutputDir(), resName);
            if ((source != null) && (source.exists()) && (source.lastModified() >= lastSuccessfulBuildTime)) {
                // will ensure it is re-copied
                resources.remove(resName);
            }
        }
    }

    private void deleteResourcesFromDirectory(File dir, List oldResources) {
        File[] files = FileUtil.listFiles(dir, new FileFilter() {

            public boolean accept(File f) {
                boolean accept = !(f.isDirectory() || f.getName().endsWith(".class"));
                return accept;
            }
        });
        // For each file, add it either as a real .class file or as a resource
        for (int i = 0; i < files.length; i++) {
            // ASSERT: files[i].getAbsolutePath().startsWith(inFile.getAbsolutePath()
            // or we are in trouble...
            String filename = files[i].getAbsolutePath().substring(dir.getAbsolutePath().length() + 1);
            maybeDeleteResource(filename, oldResources);
        }
    }

    private void deleteClassFile(ClassFile cf) {
        classesFromName.remove(cf.fullyQualifiedTypeName);
        weaver.deleteClassFile(cf.fullyQualifiedTypeName);
        cf.deleteFromFileSystem();
    }

    private UnwovenClassFile createUnwovenClassFile(AjBuildConfig.BinarySourceFile bsf) {
        UnwovenClassFile ucf = null;
        try {
            ucf = weaver.addClassFile(bsf.binSrc, bsf.fromInPathDirectory, buildConfig.getOutputDir());
        } catch (IOException ex) {
            IMessage msg = new Message("can't read class file " + bsf.binSrc.getPath(), new SourceLocation(bsf.binSrc, 0), false);
            buildManager.handler.handleMessage(msg);
        }
        return ucf;
    }

    public void noteResult(InterimCompilationResult result) {
        if (!maybeIncremental()) {
            return;
        }
        File sourceFile = new File(result.fileName());
        CompilationResult cr = result.result();
        if (result != null) {
            references.put(sourceFile, new ReferenceCollection(cr.qualifiedReferences, cr.simpleNameReferences));
        }
        UnwovenClassFile[] unwovenClassFiles = result.unwovenClassFiles();
        for (int i = 0; i < unwovenClassFiles.length; i++) {
            File lastTimeRound = (File) classesFromName.get(unwovenClassFiles[i].getClassName());
            recordClassFile(unwovenClassFiles[i], lastTimeRound);
            classesFromName.put(unwovenClassFiles[i].getClassName(), new File(unwovenClassFiles[i].getFilename()));
        }
        // need to do this before types are deleted from the World...
        recordWhetherCompilationUnitDefinedAspect(sourceFile, cr);
        deleteTypesThatWereInThisCompilationUnitLastTimeRoundButHaveBeenDeletedInThisIncrement(sourceFile, unwovenClassFiles);
        recordFQNsResultingFromCompilationUnit(sourceFile, result);
    }

    /**
	 * Currently unused, if we ditch classesFromName, we might need this.... (in noteResult)
	 * @param file
	 * @return
	 */
    private UnwovenClassFile maybeGetExistingClassFileFor(UnwovenClassFile classFile) {
        File existing = new File(classFile.getFilename());
        if (!existing.exists()) {
            return null;
        } else {
            try {
                return new UnwovenClassFile(classFile.getFilename(), FileUtil.readAsByteArray(existing));
            } catch (IOException ex) {
                throw new IllegalStateException("Unable to read contents of '" + classFile.getFilename() + "' " + "from last compile cycle");
            }
        }
    }

    /**
	 * @param sourceFile
	 * @param unwovenClassFiles
	 */
    private void deleteTypesThatWereInThisCompilationUnitLastTimeRoundButHaveBeenDeletedInThisIncrement(File sourceFile, UnwovenClassFile[] unwovenClassFiles) {
        List classFiles = (List) this.fullyQualifiedTypeNamesResultingFromCompilationUnit.get(sourceFile);
        if (classFiles != null) {
            for (int i = 0; i < unwovenClassFiles.length; i++) {
                // deleting also deletes types from the weaver... don't do this if they are
                // still present this time around...
                removeFromClassFilesIfPresent(unwovenClassFiles[i].getClassName(), classFiles);
            }
            for (Iterator iter = classFiles.iterator(); iter.hasNext(); ) {
                ClassFile cf = (ClassFile) iter.next();
                deleteClassFile(cf);
            }
        }
    }

    private void removeFromClassFilesIfPresent(String className, List classFiles) {
        ClassFile victim = null;
        for (Iterator iter = classFiles.iterator(); iter.hasNext(); ) {
            ClassFile cf = (ClassFile) iter.next();
            if (cf.fullyQualifiedTypeName.equals(className)) {
                victim = cf;
                break;
            }
        }
        if (victim != null) {
            classFiles.remove(victim);
        }
    }

    /**
	 * Record the fully-qualified names of the types that were declared in the given
	 * source file.
	 * 
	 * @param sourceFile, the compilation unit
	 * @param icr, the CompilationResult from compiling it
	 */
    private void recordFQNsResultingFromCompilationUnit(File sourceFile, InterimCompilationResult icr) {
        List classFiles = new ArrayList();
        UnwovenClassFile[] types = icr.unwovenClassFiles();
        for (int i = 0; i < types.length; i++) {
            classFiles.add(new ClassFile(types[i].getClassName(), new File(types[i].getFilename())));
        }
        this.fullyQualifiedTypeNamesResultingFromCompilationUnit.put(sourceFile, classFiles);
    }

    /**
	 * If this compilation unit defined an aspect, we need to know in case it is
	 * modified in a future increment.
	 * 
	 * @param sourceFile
	 * @param cr
	 */
    private void recordWhetherCompilationUnitDefinedAspect(File sourceFile, CompilationResult cr) {
        this.sourceFilesDefiningAspects.remove(sourceFile);
        if (cr != null) {
            Map compiledTypes = cr.compiledTypes;
            if (compiledTypes != null) {
                for (Iterator iterator = compiledTypes.keySet().iterator(); iterator.hasNext(); ) {
                    char[] className = (char[]) iterator.next();
                    String typeName = new String(className).replace('/', '.');
                    if (typeName.indexOf(IWeaver.SYNTHETIC_CLASS_POSTFIX) == -1) {
                        ResolvedType rt = world.resolve(typeName);
                        if (rt.isMissing()) {
                            throw new IllegalStateException("Type '" + rt.getSignature() + "' not found in world!");
                        }
                        if (rt.isAspect()) {
                            this.sourceFilesDefiningAspects.add(sourceFile);
                            break;
                        }
                    }
                }
            }
        }
    }

    private UnwovenClassFile removeFromPreviousIfPresent(UnwovenClassFile cf, InterimCompilationResult previous) {
        if (previous == null)
            return null;
        UnwovenClassFile[] unwovenClassFiles = previous.unwovenClassFiles();
        for (int i = 0; i < unwovenClassFiles.length; i++) {
            UnwovenClassFile candidate = unwovenClassFiles[i];
            if ((candidate != null) && candidate.getFilename().equals(cf.getFilename())) {
                unwovenClassFiles[i] = null;
                return candidate;
            }
        }
        return null;
    }

    private void recordClassFile(UnwovenClassFile thisTime, File lastTime) {
        if (simpleStrings == null) {
            // batch build
            // record resolved type for structural comparisions in future increments
            // this records a second reference to a structure already held in memory
            // by the world.
            ResolvedType rType = world.resolve(thisTime.getClassName());
            if (!rType.isMissing()) {
                try {
                    ClassFileReader reader = new ClassFileReader(thisTime.getBytes(), null);
                    this.resolvedTypeStructuresFromLastBuild.put(thisTime.getClassName(), new CompactStructureRepresentation(reader));
                } catch (ClassFormatException cfe) {
                    throw new BCException("Unexpected problem processing class", cfe);
                }
            }
            return;
        }
        CompactStructureRepresentation existingStructure = (CompactStructureRepresentation) this.resolvedTypeStructuresFromLastBuild.get(thisTime.getClassName());
        ReferenceType newResolvedType = (ReferenceType) world.resolve(thisTime.getClassName());
        if (!newResolvedType.isMissing()) {
            try {
                ClassFileReader reader = new ClassFileReader(thisTime.getBytes(), null);
                this.resolvedTypeStructuresFromLastBuild.put(thisTime.getClassName(), new CompactStructureRepresentation(reader));
            } catch (ClassFormatException cfe) {
                throw new BCException("Unexpected problem processing class", cfe);
            }
        }
        if (lastTime == null) {
            addDependentsOf(thisTime.getClassName());
            return;
        }
        if (newResolvedType.isMissing()) {
            return;
        }
        world.ensureAdvancedConfigurationProcessed();
        byte[] newBytes = thisTime.getBytes();
        try {
            ClassFileReader reader = new ClassFileReader(newBytes, lastTime.getAbsolutePath().toCharArray());
            // ignore local types since they're only visible inside a single method
            if (!(reader.isLocal() || reader.isAnonymous())) {
                if (hasStructuralChanges(reader, existingStructure)) {
                    if (world.forDEBUG_structuralChangesCode)
                        System.err.println("Detected a structural change in " + thisTime.getFilename());
                    structuralChangesSinceLastFullBuild.put(thisTime.getFilename(), new Long(currentBuildTime));
                    addDependentsOf(new String(reader.getName()).replace('/', '.'));
                }
            }
        } catch (ClassFormatException e) {
            addDependentsOf(thisTime.getClassName());
        }
    }

    private static final char[][] EMPTY_CHAR_ARRAY = new char[0][];

    private static final char[] BRACKET_V = { ')', 'V' };

    /**
	 * Compare the class structure of the new intermediate (unwoven) class with the
	 * existingResolvedType of the same class that we have in the world, looking for
	 * any structural differences (and ignoring aj members resulting from weaving....)
	 * 
	 * Some notes from Andy... lot of problems here, which I've eventually resolved
	 * by building the compactstructure based on a classfilereader, rather than on a
	 * ResolvedType.  There are accessors for inner types and funky fields that the
	 * compiler creates to support the language - for non-static inner types it
	 * also mangles ctors to be prefixed with an instance of the surrounding type.
	 * 
	 * Warning : long but boring method implementation... 
	 * @param reader
	 * @param existingType
	 * @return
	 */
    private boolean hasStructuralChanges(ClassFileReader reader, CompactStructureRepresentation existingType) {
        if (existingType == null) {
            return true;
        }
        // modifiers
        if (!modifiersEqual(reader.getModifiers(), existingType.modifiers)) {
            return true;
        }
        // generic signature
        if (!CharOperation.equals(reader.getGenericSignature(), existingType.genericSignature)) {
            return true;
        }
        // superclass name
        if (!CharOperation.equals(reader.getSuperclassName(), existingType.superclassName)) {
            return true;
        }
        // interfaces
        char[][] existingIfs = existingType.interfaces;
        char[][] newIfsAsChars = reader.getInterfaceNames();
        // damn I'm lazy...
        if (newIfsAsChars == null) {
            newIfsAsChars = EMPTY_CHAR_ARRAY;
        }
        if (existingIfs == null) {
            existingIfs = EMPTY_CHAR_ARRAY;
        }
        if (existingIfs.length != newIfsAsChars.length)
            return true;
        new_interface_loop: for (int i = 0; i < newIfsAsChars.length; i++) {
            for (int j = 0; j < existingIfs.length; j++) {
                if (CharOperation.equals(existingIfs[j], newIfsAsChars[i])) {
                    continue new_interface_loop;
                }
            }
            return true;
        }
        // fields
        MemberStructure[] existingFields = existingType.fields;
        IBinaryField[] newFields = reader.getFields();
        if (newFields == null) {
            newFields = new IBinaryField[0];
        }
        //		}
        if (newFields.length != existingFields.length)
            return true;
        new_field_loop: for (int i = 0; i < newFields.length; i++) {
            IBinaryField field = newFields[i];
            char[] fieldName = field.getName();
            for (int j = 0; j < existingFields.length; j++) {
                if (CharOperation.equals(existingFields[j].name, fieldName)) {
                    if (!modifiersEqual(field.getModifiers(), existingFields[j].modifiers)) {
                        return true;
                    }
                    if (!CharOperation.equals(existingFields[j].signature, field.getTypeName())) {
                        return true;
                    }
                    continue new_field_loop;
                }
            }
            return true;
        }
        // methods
        MemberStructure[] existingMethods = existingType.methods;
        IBinaryMethod[] newMethods = reader.getMethods();
        if (newMethods == null) {
            newMethods = new IBinaryMethod[0];
        }
        //		}
        if (newMethods.length != existingMethods.length)
            return true;
        new_method_loop: for (int i = 0; i < newMethods.length; i++) {
            IBinaryMethod method = newMethods[i];
            char[] methodName = method.getSelector();
            for (int j = 0; j < existingMethods.length; j++) {
                if (CharOperation.equals(existingMethods[j].name, methodName)) {
                    // candidate match
                    if (!CharOperation.equals(method.getMethodDescriptor(), existingMethods[j].signature)) {
                        // might be overloading
                        continue;
                    } else {
                        // matching sigs
                        if (!modifiersEqual(method.getModifiers(), existingMethods[j].modifiers)) {
                            return true;
                        }
                        continue new_method_loop;
                    }
                }
            }
            // (no match found)
            return true;
        }
        return false;
    }

    private boolean modifiersEqual(int eclipseModifiers, int resolvedTypeModifiers) {
        resolvedTypeModifiers = resolvedTypeModifiers & CompilerModifiers.AccJustFlag;
        eclipseModifiers = eclipseModifiers & CompilerModifiers.AccJustFlag;
        //		}
        return (eclipseModifiers == resolvedTypeModifiers);
    }

    private static StringSet makeStringSet(List strings) {
        StringSet ret = new StringSet(strings.size());
        for (Iterator iter = strings.iterator(); iter.hasNext(); ) {
            String element = (String) iter.next();
            ret.add(element);
        }
        return ret;
    }

    private String stringifyList(List l) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (Iterator iter = l.iterator(); iter.hasNext(); ) {
            Object el = (Object) iter.next();
            sb.append(el);
            if (iter.hasNext())
                sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    protected void addAffectedSourceFiles(List addTo, List lastTimeSources) {
        if (qualifiedStrings.isEmpty() && simpleStrings.isEmpty())
            return;
        if (listenerDefined())
            getListener().recordDecision("Examining whether any other files now need compilation based just compiling: '" + stringifyList(lastTimeSources) + "'");
        // the qualifiedStrings are of the form 'p1/p2' & the simpleStrings are just 'X'
        char[][][] qualifiedNames = ReferenceCollection.internQualifiedNames(makeStringSet(qualifiedStrings));
        // if a well known qualified name was found then we can skip over these
        if (qualifiedNames.length < qualifiedStrings.size())
            qualifiedNames = null;
        char[][] simpleNames = ReferenceCollection.internSimpleNames(makeStringSet(simpleStrings));
        // if a well known name was found then we can skip over these
        if (simpleNames.length < simpleStrings.size())
            simpleNames = null;
        for (Iterator i = references.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry) i.next();
            ReferenceCollection refs = (ReferenceCollection) entry.getValue();
            if (refs != null && refs.includes(qualifiedNames, simpleNames)) {
                File file = (File) entry.getKey();
                if (file.exists()) {
                    if (!//??? O(n**2)
                    lastTimeSources.contains(//??? O(n**2)
                    file)) {
                        if (listenerDefined()) {
                            getListener().recordDecision("Need to recompile '" + file.getName().toString() + "'");
                        }
                        addTo.add(file);
                    }
                }
            }
        }
        // XXX Promote addTo to a Set - then we don't need this rubbish? but does it need to be ordered?
        if (addTo.size() > 0) {
            for (Iterator iter = lastTimeSources.iterator(); iter.hasNext(); ) {
                Object element = (Object) iter.next();
                if (!addTo.contains(element))
                    addTo.add(element);
            }
        }
        qualifiedStrings.clear();
        simpleStrings.clear();
    }

    protected void addDependentsOf(String qualifiedTypeName) {
        int lastDot = qualifiedTypeName.lastIndexOf('.');
        String typeName;
        if (lastDot != -1) {
            String packageName = qualifiedTypeName.substring(0, lastDot).replace('.', '/');
            if (//??? O(n**2)
            !qualifiedStrings.contains(packageName)) {
                qualifiedStrings.add(packageName);
            }
            typeName = qualifiedTypeName.substring(lastDot + 1);
        } else {
            qualifiedStrings.add("");
            typeName = qualifiedTypeName;
        }
        int memberIndex = typeName.indexOf('$');
        if (memberIndex > 0)
            typeName = typeName.substring(0, memberIndex);
        if (//??? O(n**2)
        !simpleStrings.contains(typeName)) {
            simpleStrings.add(typeName);
        }
    //System.err.println("adding: " + qualifiedTypeName);
    }

    protected void addDependentsOf(File sourceFile) {
        List cfs = (List) this.fullyQualifiedTypeNamesResultingFromCompilationUnit.get(sourceFile);
        if (cfs != null) {
            for (Iterator iter = cfs.iterator(); iter.hasNext(); ) {
                ClassFile cf = (ClassFile) iter.next();
                addDependentsOf(cf.fullyQualifiedTypeName);
            }
        }
    }

    public void setStructureModel(IHierarchy model) {
        structureModel = model;
    }

    public IHierarchy getStructureModel() {
        return structureModel;
    }

    public void setWeaver(BcelWeaver bw) {
        weaver = bw;
    }

    public BcelWeaver getWeaver() {
        return weaver;
    }

    public void setWorld(BcelWorld bw) {
        world = bw;
    }

    public BcelWorld getBcelWorld() {
        return world;
    }

    public void setRelationshipMap(IRelationshipMap irm) {
        relmap = irm;
    }

    public IRelationshipMap getRelationshipMap() {
        return relmap;
    }

    public int getNumberOfStructuralChangesSinceLastFullBuild() {
        return structuralChangesSinceLastFullBuild.size();
    }

    /** Returns last time we did a full or incremental build. */
    public long getLastBuildTime() {
        return lastSuccessfulBuildTime;
    }

    /** Returns last time we did a full build */
    public long getLastFullBuildTime() {
        return lastSuccessfulFullBuildTime;
    }

    /**
	 * @return Returns the buildConfig.
	 */
    public AjBuildConfig getBuildConfig() {
        return this.buildConfig;
    }

    public void clearBinarySourceFiles() {
        this.binarySourceFiles = new HashMap();
    }

    public void recordBinarySource(String fromPathName, List unwovenClassFiles) {
        this.binarySourceFiles.put(fromPathName, unwovenClassFiles);
        if (this.maybeIncremental()) {
            List simpleClassFiles = new LinkedList();
            for (Iterator iter = unwovenClassFiles.iterator(); iter.hasNext(); ) {
                UnwovenClassFile ucf = (UnwovenClassFile) iter.next();
                ClassFile cf = getClassFileFor(ucf);
                simpleClassFiles.add(cf);
            }
            this.inputClassFilesBySource.put(fromPathName, simpleClassFiles);
        }
    }

    /**
	 * @param ucf
	 * @return
	 */
    private ClassFile getClassFileFor(UnwovenClassFile ucf) {
        return new ClassFile(ucf.getClassName(), new File(ucf.getFilename()));
    }

    public Map getBinarySourceMap() {
        return this.binarySourceFiles;
    }

    public Map getClassNameToFileMap() {
        return this.classesFromName;
    }

    public boolean hasResource(String resourceName) {
        return this.resources.contains(resourceName);
    }

    public void recordResource(String resourceName) {
        this.resources.add(resourceName);
    }

    /**
	 * @return Returns the addedFiles.
	 */
    public Set getAddedFiles() {
        return this.addedFiles;
    }

    /**
	 * @return Returns the deletedFiles.
	 */
    public Set getDeletedFiles() {
        return this.deletedFiles;
    }

    public void forceBatchBuildNextTimeAround() {
        this.batchBuildRequiredThisTime = true;
    }

    public boolean requiresFullBatchBuild() {
        return this.batchBuildRequiredThisTime;
    }

    private static class ClassFile {

        public String fullyQualifiedTypeName;

        public File locationOnDisk;

        public  ClassFile(String fqn, File location) {
            this.fullyQualifiedTypeName = fqn;
            this.locationOnDisk = location;
        }

        public void deleteFromFileSystem() {
            String namePrefix = locationOnDisk.getName();
            namePrefix = namePrefix.substring(0, namePrefix.lastIndexOf('.'));
            final String targetPrefix = namePrefix + IWeaver.CLOSURE_CLASS_PREFIX;
            File dir = locationOnDisk.getParentFile();
            if (dir != null) {
                File[] weaverGenerated = dir.listFiles(new FilenameFilter() {

                    public boolean accept(File dir, String name) {
                        return name.startsWith(targetPrefix);
                    }
                });
                if (weaverGenerated != null) {
                    for (int i = 0; i < weaverGenerated.length; i++) {
                        weaverGenerated[i].delete();
                    }
                }
            }
            locationOnDisk.delete();
        }
    }

    private static class CompactStructureRepresentation {

        char[] className;

        int modifiers;

        char[] genericSignature;

        char[] superclassName;

        char[][] interfaces;

        MemberStructure[] fields;

        MemberStructure[] methods;

        public  CompactStructureRepresentation(ClassFileReader cfr) {
            // slashes...
            this.className = cfr.getName();
            this.modifiers = cfr.getModifiers();
            this.genericSignature = cfr.getGenericSignature();
            //			if (this.genericSignature.length == 0) {
            //				this.genericSignature = null;
            //			}
            // slashes...
            this.superclassName = cfr.getSuperclassName();
            interfaces = cfr.getInterfaceNames();
            IBinaryField[] rFields = cfr.getFields();
            this.fields = new MemberStructure[rFields == null ? 0 : rFields.length];
            if (rFields != null) {
                for (int i = 0; i < rFields.length; i++) {
                    this.fields[i] = new MemberStructure();
                    this.fields[i].name = rFields[i].getName();
                    this.fields[i].modifiers = rFields[i].getModifiers();
                    this.fields[i].signature = rFields[i].getTypeName();
                }
            }
            IBinaryMethod[] rMethods = cfr.getMethods();
            this.methods = new MemberStructure[rMethods == null ? 0 : rMethods.length];
            if (rMethods != null) {
                for (int i = 0; i < rMethods.length; i++) {
                    this.methods[i] = new MemberStructure();
                    this.methods[i].name = rMethods[i].getSelector();
                    this.methods[i].modifiers = rMethods[i].getModifiers();
                    //				StringBuffer sig = new StringBuffer();
                    //				sig.append("(");
                    //				UnresolvedType[] pTypes = rMethods[i].getMethodDescriptor();
                    //				for (int j = 0; j < pTypes.length; j++) {
                    //					sig.append(pTypes[j].getSignature());
                    //				}
                    //				sig.append(")");
                    //				sig.append(rMethods[i].getReturnType().getSignature());
                    // sig.toString().toCharArray();
                    this.methods[i].signature = rMethods[i].getMethodDescriptor();
                }
            }
        }

        public  CompactStructureRepresentation(ResolvedType forType) {
            this.className = forType.getName().replace('.', '/').toCharArray();
            this.modifiers = forType.getModifiers();
            this.genericSignature = forType.getGenericSignature().toCharArray();
            if (this.genericSignature.length == 0) {
                this.genericSignature = null;
            }
            this.superclassName = forType.getSuperclass().getName().replace('.', '/').toCharArray();
            ResolvedType[] rTypes = forType.getDeclaredInterfaces();
            this.interfaces = new char[rTypes.length][];
            for (int i = 0; i < rTypes.length; i++) {
                this.interfaces[i] = rTypes[i].getName().replace('.', '/').toCharArray();
            }
            ResolvedMember[] rFields = forType.getDeclaredFields();
            this.fields = new MemberStructure[rFields.length];
            for (int i = 0; i < rFields.length; i++) {
                this.fields[i] = new MemberStructure();
                this.fields[i].name = rFields[i].getName().toCharArray();
                this.fields[i].modifiers = rFields[i].getModifiers();
                this.fields[i].signature = rFields[i].getReturnType().getSignature().toCharArray();
            }
            ResolvedMember[] rMethods = forType.getDeclaredMethods();
            this.methods = new MemberStructure[rMethods.length];
            for (int i = 0; i < rMethods.length; i++) {
                this.methods[i] = new MemberStructure();
                this.methods[i].name = rMethods[i].getName().toCharArray();
                this.methods[i].modifiers = rMethods[i].getModifiers();
                StringBuffer sig = new StringBuffer();
                sig.append("(");
                UnresolvedType[] pTypes = rMethods[i].getParameterTypes();
                for (int j = 0; j < pTypes.length; j++) {
                    sig.append(pTypes[j].getSignature());
                }
                sig.append(")");
                sig.append(rMethods[i].getReturnType().getSignature());
                this.methods[i].signature = sig.toString().toCharArray();
            }
        }
    }

    private static class MemberStructure {

        char[] name;

        int modifiers;

        char[] signature;
    }

    public void wipeAllKnowledge() {
        buildManager.state = null;
        buildManager.setStructureModel(null);
    }

    public List getAspectNames() {
        return aspectNames;
    }

    public void initializeAspectNamesList() {
        this.aspectNames = new LinkedList();
    }

    // Will allow us to record decisions made during incremental processing, hopefully aid in debugging
    public boolean listenerDefined() {
        return stateListener != null;
    }

    public IStateListener getListener() {
        return stateListener;
    }
}
