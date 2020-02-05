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
package org.aspectj.ajdt.internal.compiler.lookup;

import org.aspectj.ajdt.internal.compiler.ast.InterTypeMethodDeclaration;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.aspectj.weaver.AjcMemberMaker;
import org.aspectj.weaver.Member;
import org.aspectj.weaver.ResolvedMember;
import org.aspectj.weaver.ResolvedTypeMunger;
import org.aspectj.weaver.UnresolvedType;

/**
 * A special method binding representing an ITD that pretends to be a 
 * member in some target type for matching purposes.
 */
public class InterTypeMethodBinding extends MethodBinding {

    /** The target type upon which the ITD is declared */
    private ReferenceBinding targetType;

    /** 
	 * This is the 'pretend' method that should be the target of any attempt
	 * to call the ITD'd method.
	 */
    private MethodBinding syntheticMethod;

    public MethodBinding postDispatchMethod;

    public AbstractMethodDeclaration sourceMethod;

    public  InterTypeMethodBinding(EclipseFactory world, ResolvedTypeMunger munger, UnresolvedType withinType, AbstractMethodDeclaration sourceMethod) {
        super();
        ResolvedMember signature = munger.getSignature();
        MethodBinding mb = world.makeMethodBinding(signature, munger.getTypeVariableAliases());
        this.modifiers = mb.modifiers;
        this.selector = mb.selector;
        this.returnType = mb.returnType;
        this.parameters = mb.parameters;
        this.thrownExceptions = mb.thrownExceptions;
        this.typeVariables = mb.typeVariables;
        this.sourceMethod = sourceMethod;
        this.targetType = (ReferenceBinding) world.makeTypeBinding(signature.getDeclaringType());
        this.declaringClass = (ReferenceBinding) world.makeTypeBinding(withinType);
        if (signature.getKind() == Member.METHOD) {
            syntheticMethod = world.makeMethodBinding(AjcMemberMaker.interMethodDispatcher(signature, withinType));
            postDispatchMethod = world.makeMethodBinding(AjcMemberMaker.interMethodBody(signature, withinType));
        } else {
            syntheticMethod = world.makeMethodBinding(AjcMemberMaker.interConstructor(world.getWorld().resolve(signature.getDeclaringType()), signature, withinType));
            postDispatchMethod = syntheticMethod;
        }
    }

    //XXX this is identical to InterTypeFieldBinding
    public boolean canBeSeenBy(TypeBinding receiverType, InvocationSite invocationSite, Scope scope) {
        scope.compilationUnitScope().recordTypeReference(declaringClass);
        if (isPublic())
            return true;
        SourceTypeBinding invocationType = scope.invocationType();
        if (invocationType == declaringClass)
            return true;
        if (isProtected()) {
            throw new RuntimeException("unimplemented");
        }
        //XXX make sure this walks correctly
        if (isPrivate()) {
            if (invocationType != declaringClass) {
                ReferenceBinding outerInvocationType = invocationType;
                ReferenceBinding temp = outerInvocationType.enclosingType();
                while (temp != null) {
                    outerInvocationType = temp;
                    temp = temp.enclosingType();
                }
                ReferenceBinding outerDeclaringClass = declaringClass;
                temp = outerDeclaringClass.enclosingType();
                while (temp != null) {
                    outerDeclaringClass = temp;
                    temp = temp.enclosingType();
                }
                //System.err.println("outer dec: " + 
                if (outerInvocationType != outerDeclaringClass)
                    return false;
            }
            return true;
        }
        // isDefault()
        if (invocationType.fPackage == declaringClass.fPackage)
            return true;
        return false;
    }

    public boolean isFinal() {
        if (sourceMethod == null || !(sourceMethod instanceof InterTypeMethodDeclaration))
            return super.isFinal();
        return ((InterTypeMethodDeclaration) sourceMethod).isFinal();
    }

    public MethodBinding getAccessMethod(boolean staticReference) {
        if (staticReference)
            return postDispatchMethod;
        else
            return syntheticMethod;
    }

    public boolean alwaysNeedsAccessMethod() {
        return true;
    }

    public AbstractMethodDeclaration sourceMethod() {
        return sourceMethod;
    }

    public ReferenceBinding getTargetType() {
        return targetType;
    }

    // override method in MethodBinding to ensure correct behaviour in some of JDTs generics checks.
    public ReferenceBinding getOwningClass() {
        return targetType;
    }

    public String toString() {
        return "InterTypeMethodBinding(" + super.toString() + ", " + getTargetType() + ")";
    }
}
