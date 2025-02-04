/* *******************************************************************
 * Copyright (c) 2002 Palo Alto Research Center, Incorporated (PARC).
 *               2004 contributors
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     PARC     initial implementation
 *     IBM      ongoing maintenance 
 * ******************************************************************/
package org.aspectj.ajdt.internal.compiler.ast;

import org.aspectj.weaver.AdviceKind;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.CastExpression;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.Expression;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.aspectj.org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/**
 * Used to represent any method call to a method named <code>proceed</code>.  During
 * <code>resolvedType</code> it will be determined if this is actually in the body
 * of an <code>around</code> advice and has no receiver (must be a bare proceed call, 
 * see pr 53981), and if not this will be treated like any other
 * MessageSend.
 * 
 * @author Jim Hugunin
 */
public class Proceed extends MessageSend {

    public boolean inInner = false;

    public  Proceed(MessageSend parent) {
        super();
        this.receiver = parent.receiver;
        this.selector = parent.selector;
        this.arguments = parent.arguments;
        this.binding = parent.binding;
        this.codegenBinding = parent.codegenBinding;
        this.syntheticAccessor = parent.syntheticAccessor;
        this.expectedType = parent.expectedType;
        this.nameSourcePosition = parent.nameSourcePosition;
        this.actualReceiverType = parent.actualReceiverType;
        //this.qualifyingType = parent.qualifyingType;
        this.valueCast = parent.valueCast;
        this.typeArguments = parent.typeArguments;
        this.genericTypeArguments = parent.genericTypeArguments;
        this.sourceStart = parent.sourceStart;
        this.sourceEnd = parent.sourceEnd;
    }

    public TypeBinding resolveType(BlockScope scope) {
        // find out if I'm really in an around body or not
        //??? this could in theory be done by the parser, but that appears to be hard
        AdviceDeclaration aroundDecl = findEnclosingAround(scope);
        if (aroundDecl == null) {
            return super.resolveType(scope);
        }
        constant = NotAConstant;
        binding = codegenBinding = aroundDecl.proceedMethodBinding;
        this.actualReceiverType = binding.declaringClass;
        int baseArgCount = 0;
        if (arguments != null) {
            baseArgCount = arguments.length;
            Expression[] newArguments = new Expression[baseArgCount + 1];
            System.arraycopy(arguments, 0, newArguments, 0, baseArgCount);
            arguments = newArguments;
        } else {
            arguments = new Expression[1];
        }
        arguments[baseArgCount] = AstUtil.makeLocalVariableReference(aroundDecl.extraArgument.binding);
        int declaredParameterCount = aroundDecl.getDeclaredParameterCount();
        if (baseArgCount < declaredParameterCount) {
            scope.problemReporter().signalError(this.sourceStart, this.sourceEnd, "too few arguments to proceed, expected " + declaredParameterCount);
            aroundDecl.ignoreFurtherInvestigation = true;
            //binding.returnType;
            return null;
        }
        if (baseArgCount > declaredParameterCount) {
            scope.problemReporter().signalError(this.sourceStart, this.sourceEnd, "too many arguments to proceed, expected " + declaredParameterCount);
            aroundDecl.ignoreFurtherInvestigation = true;
            //binding.returnType;
            return null;
        }
        boolean argsContainCast = false;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof CastExpression)
                argsContainCast = true;
        }
        checkInvocationArguments(scope, null, this.actualReceiverType, binding, this.arguments, binding.parameters, argsContainCast, this);
        for (int i = 0, len = arguments.length; i < len; i++) {
            Expression arg = arguments[i];
            TypeBinding argType = arg.resolveType(scope);
            if (argType != null) {
                TypeBinding paramType = binding.parameters[i];
                if (!argType.isCompatibleWith(paramType)) {
                    scope.problemReporter().typeMismatchError(argType, paramType, arg);
                }
            }
        }
        return binding.returnType;
    }

    private AdviceDeclaration findEnclosingAround(Scope scope) {
        if (scope == null)
            return null;
        if (scope instanceof MethodScope) {
            MethodScope methodScope = (MethodScope) scope;
            ReferenceContext context = methodScope.referenceContext;
            if (context instanceof AdviceDeclaration) {
                AdviceDeclaration adviceDecl = (AdviceDeclaration) context;
                if (adviceDecl.kind == AdviceKind.Around) {
                    // pr 53981 only match "bare" calls to proceed
                    if ((receiver != null) && (!receiver.isThis())) {
                        return null;
                    }
                    adviceDecl.proceedCalls.add(this);
                    return adviceDecl;
                } else {
                    return null;
                }
            }
        } else if (scope instanceof ClassScope) {
            inInner = true;
        }
        return findEnclosingAround(scope.parent);
    }
}
