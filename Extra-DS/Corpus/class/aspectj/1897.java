/* *******************************************************************
 * Copyright (c) 2004 IBM Corporation
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Matthew Webster, Adrian Colyer, 
 *     Martin Lippert     initial implementation 
 * ******************************************************************/
package org.aspectj.weaver.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.aspectj.bridge.AbortException;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.IMessageHandler;
import org.aspectj.bridge.Message;
import org.aspectj.bridge.MessageUtil;
import org.aspectj.bridge.MessageWriter;
import org.aspectj.bridge.Version;
import org.aspectj.bridge.IMessage.Kind;
import org.aspectj.util.FileUtil;
import org.aspectj.util.LangUtil;
import org.aspectj.weaver.IClassFileProvider;
import org.aspectj.weaver.IWeaveRequestor;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.bcel.BcelObjectType;
import org.aspectj.weaver.bcel.BcelWeaver;
import org.aspectj.weaver.bcel.BcelWorld;
import org.aspectj.weaver.bcel.UnwovenClassFile;

/**
 * This adaptor allows the AspectJ compiler to be embedded in an existing
 * system to facilitate load-time weaving. It provides an interface for a
 * weaving class loader to provide a classpath to be woven by a set of
 * aspects. A callback is supplied to allow a class loader to define classes
 * generated by the compiler during the weaving process.
 * <p>
 * A weaving class loader should create a <code>WeavingAdaptor</code> before
 * any classes are defined, typically during construction. The set of aspects 
 * passed to the adaptor is fixed for the lifetime of the adaptor although the
 * classpath can be augmented. A system property can be set to allow verbose
 * weaving messages to be written to the console.
 *
 */
public class WeavingAdaptor {

    /**
	 * System property used to turn on verbose weaving messages 
	 */
    public static final String WEAVING_ADAPTOR_VERBOSE = "aj.weaving.verbose";

    public static final String SHOW_WEAVE_INFO_PROPERTY = "org.aspectj.weaver.showWeaveInfo";

    protected boolean enabled = true;

    protected boolean verbose = getVerbose();

    protected BcelWorld bcelWorld;

    protected BcelWeaver weaver;

    private IMessageHandler messageHandler;

    private WeavingAdaptorMessageHandler messageHolder;

    protected GeneratedClassHandler generatedClassHandler;

    protected Map generatedClasses = new HashMap();

    protected  WeavingAdaptor() {
        createMessageHandler();
    }

    /**
	 * Construct a WeavingAdaptor with a reference to a weaving class loader. The
	 * adaptor will automatically search the class loader hierarchy to resolve
	 * classes. The adaptor will also search the hierarchy for WeavingClassLoader
	 * instances to determine the set of aspects to be used ofr weaving. 
     * @param loader instance of <code>ClassLoader</code>
	 */
    public  WeavingAdaptor(WeavingClassLoader loader) {
        //		System.err.println("? WeavingAdaptor.<init>(" + loader +"," + aspectURLs.length + ")");
        generatedClassHandler = loader;
        init(getFullClassPath((ClassLoader) loader), getFullAspectPath((ClassLoader) loader));
    }

    /**
	 * Construct a WeavingAdator with a reference to a
	 * <code>GeneratedClassHandler</code>, a full search path for resolving 
	 * classes and a complete set of aspects. The search path must include
	 * classes loaded by the class loader constructing the WeavingAdaptor and
	 * all its parents in the hierarchy.   
	 * @param handler <code>GeneratedClassHandler</code>
     * @param classURLs the URLs from which to resolve classes
     * @param aspectURLs the aspects used to weave classes defined by this class loader
	 */
    public  WeavingAdaptor(GeneratedClassHandler handler, URL[] classURLs, URL[] aspectURLs) {
        //		System.err.println("? WeavingAdaptor.<init>()");
        generatedClassHandler = handler;
        init(FileUtil.makeClasspath(classURLs), FileUtil.makeClasspath(aspectURLs));
    }

    private List getFullClassPath(ClassLoader loader) {
        List list = new LinkedList();
        for (; loader != null; loader = loader.getParent()) {
            if (loader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) loader).getURLs();
                list.addAll(0, FileUtil.makeClasspath(urls));
            } else {
                warn("cannot determine classpath");
            }
        }
        list.addAll(0, makeClasspath(System.getProperty("sun.boot.class.path")));
        return list;
    }

    private List getFullAspectPath(ClassLoader loader) {
        List list = new LinkedList();
        for (; loader != null; loader = loader.getParent()) {
            if (loader instanceof WeavingClassLoader) {
                URL[] urls = ((WeavingClassLoader) loader).getAspectURLs();
                list.addAll(0, FileUtil.makeClasspath(urls));
            }
        }
        return list;
    }

    private static boolean getVerbose() {
        return Boolean.getBoolean(WEAVING_ADAPTOR_VERBOSE);
    }

    private void init(List classPath, List aspectPath) {
        createMessageHandler();
        info("using classpath: " + classPath);
        info("using aspectpath: " + aspectPath);
        bcelWorld = new BcelWorld(classPath, messageHandler, null);
        bcelWorld.setXnoInline(false);
        bcelWorld.getLint().loadDefaultProperties();
        if (LangUtil.is15VMOrGreater()) {
            bcelWorld.setBehaveInJava5Way(true);
        }
        weaver = new BcelWeaver(bcelWorld);
        registerAspectLibraries(aspectPath);
    }

    private void createMessageHandler() {
        messageHolder = new WeavingAdaptorMessageHandler(new PrintWriter(System.err));
        messageHandler = messageHolder;
        if (verbose)
            messageHandler.dontIgnore(IMessage.INFO);
        if (Boolean.getBoolean(SHOW_WEAVE_INFO_PROPERTY))
            messageHandler.dontIgnore(IMessage.WEAVEINFO);
        //$NON-NLS-1$
        info("AspectJ Weaver Version " + Version.text + " built on " + Version.time_text);
    }

    protected IMessageHandler getMessageHandler() {
        return messageHandler;
    }

    protected void setMessageHandler(IMessageHandler mh) {
        if (messageHolder != null) {
            messageHolder.flushMessages();
            messageHolder = null;
        }
        messageHandler = mh;
        bcelWorld.setMessageHandler(mh);
    }

    /**
	 * Appends URL to path used by the WeavingAdptor to resolve classes
	 * @param url to be appended to search path
	 */
    public void addURL(URL url) {
        File libFile = new File(url.getPath());
        try {
            weaver.addLibraryJarFile(libFile);
        } catch (IOException ex) {
            warn("bad library: '" + libFile + "'");
        }
    }

    /**
	 * Weave a class using aspects previously supplied to the adaptor.
	 * @param name the name of the class
	 * @param bytes the class bytes
	 * @return the woven bytes
     * @exception IOException weave failed
	 */
    public byte[] weaveClass(String name, byte[] bytes) throws IOException {
        if (enabled) {
            if (shouldWeave(name, bytes)) {
                info("weaving '" + name + "'");
                bytes = getWovenBytes(name, bytes);
            } else if (shouldWeaveAnnotationStyleAspect(name, bytes)) {
                // an @AspectJ aspect needs to be at least munged by the aspectOf munger
                info("weaving '" + name + "'");
                bytes = getAtAspectJAspectBytes(name, bytes);
            }
        }
        return bytes;
    }

    /**
     * @param name
     * @return true if should weave (but maybe we still need to munge it for @AspectJ aspectof support)
     */
    private boolean shouldWeave(String name, byte[] bytes) {
        name = name.replace('/', '.');
        boolean b = !generatedClasses.containsKey(name) && shouldWeaveName(name);
        return b && accept(name, bytes);
    //        && shouldWeaveAnnotationStyleAspect(name);
    //        // we recall shouldWeaveAnnotationStyleAspect as we need to add aspectOf methods for @Aspect anyway
    //        //FIXME AV - this is half ok as the aspect will be weaved by others. In theory if the aspect
    //        // is excluded from include/exclude config we should only weave late type mungers for aspectof
    //        return b && (accept(name) || shouldWeaveAnnotationStyleAspect(name));
    }

    //ATAJ
    protected boolean accept(String name, byte[] bytes) {
        return true;
    }

    protected boolean shouldDump(String name, boolean before) {
        return false;
    }

    private boolean shouldWeaveName(String name) {
        return /*(name.startsWith("org.apache.bcel.")//FIXME AV why ? bcel is wrapped in org.aspectj.
                ||*/
        !((name.startsWith("org.aspectj.") || name.startsWith("java.") || name.startsWith("javax.")) || //|| name.startsWith("$Proxy")//JDK proxies//FIXME AV is that 1.3 proxy ? fe. ataspect.$Proxy0 is a java5 proxy...
        name.startsWith(//JDK reflect
        "sun.reflect."));
    }

    /**
     * We allow @AJ aspect weaving so that we can add aspectOf() as part of the weaving
     * (and not part of the source compilation)
     *
     * @param name
     * @param bytes bytecode (from classloader), allow to NOT lookup stuff on disk again during resolve
     * @return true if @Aspect
     */
    private boolean shouldWeaveAnnotationStyleAspect(String name, byte[] bytes) {
        // we reuse bytes[] here to do a fast lookup for @Aspect annotation
        return bcelWorld.isAnnotationStyleAspect(name, bytes);
    }

    /**
	 * Weave a set of bytes defining a class. 
	 * @param name the name of the class being woven
	 * @param bytes the bytes that define the class
	 * @return byte[] the woven bytes for the class
	 * @throws IOException
	 */
    private byte[] getWovenBytes(String name, byte[] bytes) throws IOException {
        WeavingClassFileProvider wcp = new WeavingClassFileProvider(name, bytes);
        weaver.weave(wcp);
        return wcp.getBytes();
    }

    /**
     * Weave a set of bytes defining a class for only what is needed to turn @AspectJ aspect
     * in a usefull form ie with aspectOf method - see #113587
     * @param name the name of the class being woven
     * @param bytes the bytes that define the class
     * @return byte[] the woven bytes for the class
     * @throws IOException
     */
    private byte[] getAtAspectJAspectBytes(String name, byte[] bytes) throws IOException {
        WeavingClassFileProvider wcp = new WeavingClassFileProvider(name, bytes);
        wcp.setApplyAtAspectJMungersOnly();
        weaver.weave(wcp);
        return wcp.getBytes();
    }

    private void registerAspectLibraries(List aspectPath) {
        //		System.err.println("? WeavingAdaptor.registerAspectLibraries(" + aspectPath + ")");
        for (Iterator i = aspectPath.iterator(); i.hasNext(); ) {
            String libName = (String) i.next();
            addAspectLibrary(libName);
        }
        weaver.prepareForWeave();
    }

    /*
	 * Register an aspect library with this classloader for use during
	 * weaving. This class loader will also return (unmodified) any of the
	 * classes in the library in response to a <code>findClass()</code> request.
	 * The library is not required to be on the weavingClasspath given when this
	 * classloader was constructed. 
	 * @param aspectLibraryJarFile a jar file representing an aspect library
	 * @throws IOException
	 */
    private void addAspectLibrary(String aspectLibraryName) {
        File aspectLibrary = new File(aspectLibraryName);
        if (aspectLibrary.isDirectory() || (aspectLibrary.isFile() && FileUtil.hasZipSuffix(aspectLibraryName))) {
            try {
                info("adding aspect library: '" + aspectLibrary + "'");
                weaver.addLibraryJarFile(aspectLibrary);
            } catch (IOException ex) {
                error("exception adding aspect library: '" + ex + "'");
            }
        } else {
            error("bad aspect library: '" + aspectLibrary + "'");
        }
    }

    private static List makeClasspath(String cp) {
        List ret = new ArrayList();
        if (cp != null) {
            StringTokenizer tok = new StringTokenizer(cp, File.pathSeparator);
            while (tok.hasMoreTokens()) {
                ret.add(tok.nextToken());
            }
        }
        return ret;
    }

    protected boolean info(String message) {
        return MessageUtil.info(messageHandler, message);
    }

    protected boolean warn(String message) {
        return MessageUtil.warn(messageHandler, message);
    }

    protected boolean warn(String message, Throwable th) {
        return messageHandler.handleMessage(new Message(message, IMessage.WARNING, th, null));
    }

    protected boolean error(String message) {
        return MessageUtil.error(messageHandler, message);
    }

    /**
	 * Dump the given bytcode in _dump/... (dev mode)
	 *
	 * @param name
	 * @param b
	 * @param before whether we are dumping before weaving
	 * @throws Throwable
	 */
    protected void dump(String name, byte[] b, boolean before) {
        String dirName = "_ajdump";
        if (before)
            dirName = dirName + File.separator + "_before";
        String className = name.replace('.', '/');
        final File dir;
        if (className.indexOf('/') > 0) {
            dir = new File(dirName + File.separator + className.substring(0, className.lastIndexOf('/')));
        } else {
            dir = new File(dirName);
        }
        dir.mkdirs();
        String fileName = dirName + File.separator + className + ".class";
        try {
            //	    	System.out.println("WeavingAdaptor.dump() fileName=" + new File(fileName).getAbsolutePath());
            FileOutputStream os = new FileOutputStream(fileName);
            os.write(b);
            os.close();
        } catch (IOException ex) {
            warn("unable to dump class " + name + " in directory " + dirName, ex);
        }
    }

    /**
	 * Processes messages arising from weaver operations. 
	 * Tell weaver to abort on any message more severe than warning.
	 */
    protected class WeavingAdaptorMessageHandler extends MessageWriter {

        private Set ignoring = new HashSet();

        private IMessage.Kind failKind;

        private boolean accumulating = true;

        private List messages = new ArrayList();

        public  WeavingAdaptorMessageHandler(PrintWriter writer) {
            super(writer, true);
            ignore(IMessage.WEAVEINFO);
            ignore(IMessage.INFO);
            this.failKind = IMessage.ERROR;
        }

        public boolean handleMessage(IMessage message) throws AbortException {
            addMessage(message);
            boolean result = super.handleMessage(message);
            if (0 <= message.getKind().compareTo(failKind)) {
                throw new AbortException(message);
            }
            return true;
        }

        public boolean isIgnoring(Kind kind) {
            return ((null != kind) && (ignoring.contains(kind)));
        }

        /**
		 * Set a message kind to be ignored from now on
		 */
        public void ignore(IMessage.Kind kind) {
            if ((null != kind) && (!ignoring.contains(kind))) {
                ignoring.add(kind);
            }
        }

        /**
		 * Remove a message kind from the list of those ignored from now on.
		 */
        public void dontIgnore(IMessage.Kind kind) {
            if (null != kind) {
                ignoring.remove(kind);
                if (kind.equals(IMessage.INFO))
                    accumulating = false;
            }
        }

        private void addMessage(IMessage message) {
            if (accumulating && isIgnoring(message.getKind())) {
                messages.add(message);
            }
        }

        public void flushMessages() {
            for (Iterator iter = messages.iterator(); iter.hasNext(); ) {
                IMessage message = (IMessage) iter.next();
                super.handleMessage(message);
            }
            accumulating = false;
            messages.clear();
        }
    }

    private class WeavingClassFileProvider implements IClassFileProvider {

        private UnwovenClassFile unwovenClass;

        private List unwovenClasses = new ArrayList();

        /* List<UnovenClassFile> */
        private UnwovenClassFile wovenClass;

        private boolean isApplyAtAspectJMungersOnly = false;

        private BcelObjectType delegate;

        public  WeavingClassFileProvider(String name, byte[] bytes) {
            this.unwovenClass = new UnwovenClassFile(name, bytes);
            this.unwovenClasses.add(unwovenClass);
            if (shouldDump(name.replace('/', '.'), true)) {
                dump(name, bytes, true);
            }
            delegate = bcelWorld.addSourceObjectType(unwovenClass.getJavaClass());
        }

        public void setApplyAtAspectJMungersOnly() {
            isApplyAtAspectJMungersOnly = true;
        }

        public boolean isApplyAtAspectJMungersOnly() {
            return isApplyAtAspectJMungersOnly;
        }

        public byte[] getBytes() {
            if (wovenClass != null)
                return wovenClass.getBytes();
            else
                return unwovenClass.getBytes();
        }

        public Iterator getClassFileIterator() {
            return unwovenClasses.iterator();
        }

        public IWeaveRequestor getRequestor() {
            return new IWeaveRequestor() {

                public void acceptResult(UnwovenClassFile result) {
                    if (wovenClass == null) {
                        wovenClass = result;
                        String name = result.getClassName();
                        if (shouldDump(name.replace('/', '.'), false)) {
                            dump(name, result.getBytes(), false);
                        }
                    } else /* Classes generated by weaver e.g. around closure advice */
                    {
                        String className = result.getClassName();
                        generatedClasses.put(className, result);
                        generatedClassHandler.acceptClass(className, result.getBytes());
                    }
                }

                public void processingReweavableState() {
                }

                public void addingTypeMungers() {
                }

                public void weavingAspects() {
                }

                public void weavingClasses() {
                }

                public void weaveCompleted() {
                    if (delegate != null)
                        delegate.weavingCompleted();
                    ResolvedType.resetPrimitives();
                }
            };
        }
    }
}
