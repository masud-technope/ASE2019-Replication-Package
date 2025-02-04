/*******************************************************************************
 * Copyright (c) 2005 IBM 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *    Andy Clement       initial API and implementation
 *******************************************************************************/
package org.aspectj.weaver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.aspectj.apache.bcel.classfile.annotation.Annotation;
import org.aspectj.apache.bcel.classfile.annotation.ArrayElementValue;
import org.aspectj.apache.bcel.classfile.annotation.ElementNameValuePair;
import org.aspectj.apache.bcel.classfile.annotation.ElementValue;
import org.aspectj.apache.bcel.classfile.annotation.EnumElementValue;
import org.aspectj.apache.bcel.classfile.Utility;

/**
 * AnnotationX instances are holders for an annotation from either Bcel or
 * ASM.  We have this holder so that types about the bcel weaver package 
 * can work with something not bytecode toolkit specific.
 */
public class AnnotationX {

    public static final AnnotationX[] NONE = new AnnotationX[0];

    private Annotation theRealBcelAnnotation;

    private AnnotationAJ theRealASMAnnotation;

    private int mode = -1;

    private static final int MODE_ASM = 1;

    private static final int MODE_BCEL = 2;

    private ResolvedType signature = null;

    // @target meta-annotation related stuff, built lazily
    private boolean lookedForAtTargetAnnotation = false;

    private AnnotationX atTargetAnnotation = null;

    private Set supportedTargets = null;

    public  AnnotationX(Annotation a, World world) {
        theRealBcelAnnotation = a;
        signature = UnresolvedType.forSignature(theRealBcelAnnotation.getTypeSignature()).resolve(world);
        mode = MODE_BCEL;
    }

    public  AnnotationX(AnnotationAJ a, World world) {
        theRealASMAnnotation = a;
        signature = UnresolvedType.forSignature(theRealASMAnnotation.getTypeSignature()).resolve(world);
        mode = MODE_ASM;
    }

    public Annotation getBcelAnnotation() {
        return theRealBcelAnnotation;
    }

    public UnresolvedType getSignature() {
        return signature;
    }

    public String toString() {
        if (mode == MODE_BCEL)
            return theRealBcelAnnotation.toString();
        else
            return theRealASMAnnotation.toString();
    }

    public String getTypeName() {
        if (mode == MODE_BCEL)
            return theRealBcelAnnotation.getTypeName();
        else
            return Utility.signatureToString(theRealASMAnnotation.getTypeSignature());
    }

    public String getTypeSignature() {
        if (mode == MODE_BCEL)
            return theRealBcelAnnotation.getTypeSignature();
        else
            return theRealASMAnnotation.getTypeSignature();
    }

    // @target related helpers
    /**
   * return true if this annotation can target an annotation type
   */
    public boolean allowedOnAnnotationType() {
        ensureAtTargetInitialized();
        // if no target specified, then return true
        if (atTargetAnnotation == null)
            return true;
        return supportedTargets.contains("ANNOTATION_TYPE");
    }

    /**
   * return true if this annotation is marked with @target()
   */
    public boolean specifiesTarget() {
        ensureAtTargetInitialized();
        return atTargetAnnotation != null;
    }

    /**
   * return true if this annotation can target a 'regular' type.
   * A 'regular' type is enum/class/interface - it is *not* annotation.
   */
    public boolean allowedOnRegularType() {
        ensureAtTargetInitialized();
        // if no target specified, then return true
        if (atTargetAnnotation == null)
            return true;
        return supportedTargets.contains("TYPE");
    }

    /** 
   * Use in messages about this annotation
   */
    public String stringify() {
        return signature.getName();
    }

    public String getValidTargets() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (Iterator iter = supportedTargets.iterator(); iter.hasNext(); ) {
            String evalue = (String) iter.next();
            sb.append(evalue);
            if (iter.hasNext())
                sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    // privates
    /**
   * Helper method to retrieve an annotation on an annotation e.g.
   * retrieveAnnotationOnAnnotation(UnresolvedType.AT_TARGET)
   */
    private AnnotationX retrieveAnnotationOnAnnotation(UnresolvedType requiredAnnotationSignature) {
        AnnotationX[] annos = signature.getAnnotations();
        for (int i = 0; i < annos.length; i++) {
            AnnotationX annotationX = annos[i];
            if (annotationX.getSignature().equals(requiredAnnotationSignature))
                return annos[i];
        }
        return null;
    }

    /**
   * Makes sure we have looked for the @target() annotation on this annotation.
   * Calling this method initializes (and caches) the information for later use.
   */
    private void ensureAtTargetInitialized() {
        if (!lookedForAtTargetAnnotation) {
            lookedForAtTargetAnnotation = true;
            atTargetAnnotation = retrieveAnnotationOnAnnotation(UnresolvedType.AT_TARGET);
            if (atTargetAnnotation != null) {
                supportedTargets = atTargetAnnotation.getTargets();
            }
        }
    }

    /**
   * For the @Target annotation, this will return a set of the elementtypes it can be applied to.
   * For non @Target annotations, it returns null.
   */
    public Set getTargets() /* of String */
    {
        if (!signature.equals(UnresolvedType.AT_TARGET))
            return null;
        Set supportedTargets = new HashSet();
        if (mode == MODE_BCEL) {
            List values = getBcelAnnotation().getValues();
            ElementNameValuePair envp = (ElementNameValuePair) values.get(0);
            ArrayElementValue aev = (ArrayElementValue) envp.getValue();
            ElementValue[] evs = aev.getElementValuesArray();
            for (int i = 0; i < evs.length; i++) {
                EnumElementValue ev = (EnumElementValue) evs[i];
                supportedTargets.add(ev.getEnumValueString());
            }
        } else {
            List values = theRealASMAnnotation.getNameValuePairs();
            AnnotationNameValuePair nvp = (AnnotationNameValuePair) values.get(0);
            ArrayAnnotationValue aav = (ArrayAnnotationValue) nvp.getValue();
            AnnotationValue[] avs = aav.getValues();
            for (int i = 0; i < avs.length; i++) {
                AnnotationValue value = avs[i];
                supportedTargets.add(value.stringify());
            }
        }
        return supportedTargets;
    }

    /**
   * @return true if this annotation can be put on a field
   */
    public boolean allowedOnField() {
        ensureAtTargetInitialized();
        // if no target specified, then return true
        if (atTargetAnnotation == null)
            return true;
        return supportedTargets.contains("FIELD");
    }

    public boolean isRuntimeVisible() {
        return theRealBcelAnnotation.isRuntimeVisible();
    }
}
