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

import org.aspectj.ajdt.internal.compiler.ast.AspectDeclaration;
import org.aspectj.weaver.AjcMemberMaker;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

public class PrivilegedFieldBinding extends FieldBinding {

    public SimpleSyntheticAccessMethodBinding reader;

    public SimpleSyntheticAccessMethodBinding writer;

    public FieldBinding baseField;

    public  PrivilegedFieldBinding(AspectDeclaration inAspect, FieldBinding baseField) {
        super(baseField, baseField.declaringClass);
        this.reader = new SimpleSyntheticAccessMethodBinding(inAspect.factory.makeMethodBinding(AjcMemberMaker.privilegedAccessMethodForFieldGet(inAspect.typeX, inAspect.factory.makeResolvedMember(baseField))));
        this.writer = new SimpleSyntheticAccessMethodBinding(inAspect.factory.makeMethodBinding(AjcMemberMaker.privilegedAccessMethodForFieldSet(inAspect.typeX, inAspect.factory.makeResolvedMember(baseField))));
        this.constant = ASTNode.NotAConstant;
        this.baseField = baseField;
    }

    public boolean canBeSeenBy(TypeBinding receiverType, InvocationSite invocationSite, Scope scope) {
        return true;
    }

    public SyntheticMethodBinding getAccessMethod(boolean isReadAccess) {
        if (baseField.alwaysNeedsAccessMethod(isReadAccess)) {
            return baseField.getAccessMethod(isReadAccess);
        }
        if (isReadAccess)
            return reader;
        else
            return writer;
    }

    public boolean alwaysNeedsAccessMethod(boolean isReadAccess) {
        return true;
    }

    public FieldBinding getFieldBindingForLookup() {
        return baseField;
    }

    public String toString() {
        return "PrivilegedWrapper(" + baseField + ")";
    }
    //	public ReferenceBinding getTargetType() {
    //		return introducedField.declaringClass;
    //	}
}
