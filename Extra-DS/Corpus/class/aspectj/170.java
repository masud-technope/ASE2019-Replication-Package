/* *******************************************************************
 * Copyright (c) 2004 IBM Corporation.
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * ******************************************************************/
package org.aspectj.weaver.internal.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.Shadow;
import org.aspectj.weaver.World;
import org.aspectj.weaver.ast.Literal;
import org.aspectj.weaver.ast.Test;
import org.aspectj.weaver.patterns.AbstractPatternNodeVisitor;
import org.aspectj.weaver.patterns.ArgsAnnotationPointcut;
import org.aspectj.weaver.patterns.ArgsPointcut;
import org.aspectj.weaver.patterns.CflowPointcut;
import org.aspectj.weaver.patterns.ExposedState;
import org.aspectj.weaver.patterns.IfPointcut;
import org.aspectj.weaver.patterns.NotAnnotationTypePattern;
import org.aspectj.weaver.patterns.NotPointcut;
import org.aspectj.weaver.patterns.Pointcut;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.aspectj.weaver.patterns.ThisOrTargetPointcut;
import org.aspectj.weaver.reflect.ReflectionFastMatchInfo;
import org.aspectj.weaver.reflect.ReflectionShadow;
import org.aspectj.weaver.reflect.ShadowMatchImpl;
import org.aspectj.weaver.tools.DefaultMatchingContext;
import org.aspectj.weaver.tools.MatchingContext;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 * Map from weaver.tools interface to internal Pointcut implementation...
 */
public class PointcutExpressionImpl implements PointcutExpression {

    private World world;

    private Pointcut pointcut;

    private String expression;

    private PointcutParameter[] parameters;

    private MatchingContext matchContext = new DefaultMatchingContext();

    public  PointcutExpressionImpl(Pointcut pointcut, String expression, PointcutParameter[] params, World inWorld) {
        this.pointcut = pointcut;
        this.expression = expression;
        this.world = inWorld;
        this.parameters = params;
        if (this.parameters == null)
            this.parameters = new PointcutParameter[0];
    }

    public Pointcut getUnderlyingPointcut() {
        return this.pointcut;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.tools.PointcutExpression#setMatchingContext(org.aspectj.weaver.tools.MatchingContext)
	 */
    public void setMatchingContext(MatchingContext aMatchContext) {
        this.matchContext = aMatchContext;
    }

    public boolean couldMatchJoinPointsInType(Class aClass) {
        ResolvedType matchType = world.resolve(aClass.getName());
        ReflectionFastMatchInfo info = new ReflectionFastMatchInfo(matchType, null, this.matchContext);
        return pointcut.fastMatch(info).maybeTrue();
    }

    public boolean mayNeedDynamicTest() {
        HasPossibleDynamicContentVisitor visitor = new HasPossibleDynamicContentVisitor();
        pointcut.traverse(visitor, null);
        return visitor.hasDynamicContent();
    }

    private ExposedState getExposedState() {
        return new ExposedState(parameters.length);
    }

    public ShadowMatch matchesMethodExecution(Method aMethod) {
        return matchesExecution(aMethod);
    }

    public ShadowMatch matchesConstructorExecution(Constructor aConstructor) {
        return matchesExecution(aConstructor);
    }

    private ShadowMatch matchesExecution(Member aMember) {
        Shadow s = ReflectionShadow.makeExecutionShadow(world, aMember, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aMember);
        sm.setWithinCode(null);
        sm.setWithinType(aMember.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesStaticInitialization(Class aClass) {
        Shadow s = ReflectionShadow.makeStaticInitializationShadow(world, aClass, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(null);
        sm.setWithinCode(null);
        sm.setWithinType(aClass);
        return sm;
    }

    public ShadowMatch matchesAdviceExecution(Method aMethod) {
        Shadow s = ReflectionShadow.makeAdviceExecutionShadow(world, aMethod, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aMethod);
        sm.setWithinCode(null);
        sm.setWithinType(aMethod.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesInitialization(Constructor aConstructor) {
        Shadow s = ReflectionShadow.makeInitializationShadow(world, aConstructor, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aConstructor);
        sm.setWithinCode(null);
        sm.setWithinType(aConstructor.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesPreInitialization(Constructor aConstructor) {
        Shadow s = ReflectionShadow.makePreInitializationShadow(world, aConstructor, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aConstructor);
        sm.setWithinCode(null);
        sm.setWithinType(aConstructor.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesMethodCall(Method aMethod, Member withinCode) {
        Shadow s = ReflectionShadow.makeCallShadow(world, aMethod, withinCode, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aMethod);
        sm.setWithinCode(withinCode);
        sm.setWithinType(withinCode.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesMethodCall(Method aMethod, Class callerType) {
        Shadow s = ReflectionShadow.makeCallShadow(world, aMethod, callerType, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aMethod);
        sm.setWithinCode(null);
        sm.setWithinType(callerType);
        return sm;
    }

    public ShadowMatch matchesConstructorCall(Constructor aConstructor, Class callerType) {
        Shadow s = ReflectionShadow.makeCallShadow(world, aConstructor, callerType, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aConstructor);
        sm.setWithinCode(null);
        sm.setWithinType(callerType);
        return sm;
    }

    public ShadowMatch matchesConstructorCall(Constructor aConstructor, Member withinCode) {
        Shadow s = ReflectionShadow.makeCallShadow(world, aConstructor, withinCode, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aConstructor);
        sm.setWithinCode(withinCode);
        sm.setWithinType(withinCode.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesHandler(Class exceptionType, Class handlingType) {
        Shadow s = ReflectionShadow.makeHandlerShadow(world, exceptionType, handlingType, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(null);
        sm.setWithinCode(null);
        sm.setWithinType(handlingType);
        return sm;
    }

    public ShadowMatch matchesHandler(Class exceptionType, Member withinCode) {
        Shadow s = ReflectionShadow.makeHandlerShadow(world, exceptionType, withinCode, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(null);
        sm.setWithinCode(withinCode);
        sm.setWithinType(withinCode.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesFieldGet(Field aField, Class withinType) {
        Shadow s = ReflectionShadow.makeFieldGetShadow(world, aField, withinType, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aField);
        sm.setWithinCode(null);
        sm.setWithinType(withinType);
        return sm;
    }

    public ShadowMatch matchesFieldGet(Field aField, Member withinCode) {
        Shadow s = ReflectionShadow.makeFieldGetShadow(world, aField, withinCode, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aField);
        sm.setWithinCode(withinCode);
        sm.setWithinType(withinCode.getDeclaringClass());
        return sm;
    }

    public ShadowMatch matchesFieldSet(Field aField, Class withinType) {
        Shadow s = ReflectionShadow.makeFieldSetShadow(world, aField, withinType, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aField);
        sm.setWithinCode(null);
        sm.setWithinType(withinType);
        return sm;
    }

    public ShadowMatch matchesFieldSet(Field aField, Member withinCode) {
        Shadow s = ReflectionShadow.makeFieldSetShadow(world, aField, withinCode, this.matchContext);
        ShadowMatchImpl sm = getShadowMatch(s);
        sm.setSubject(aField);
        sm.setWithinCode(withinCode);
        sm.setWithinType(withinCode.getDeclaringClass());
        return sm;
    }

    private ShadowMatchImpl getShadowMatch(Shadow forShadow) {
        org.aspectj.util.FuzzyBoolean match = pointcut.match(forShadow);
        Test residueTest = Literal.TRUE;
        ExposedState state = getExposedState();
        if (match.maybeTrue()) {
            residueTest = pointcut.findResidue(forShadow, state);
        }
        ShadowMatchImpl sm = new ShadowMatchImpl(match, residueTest, state, parameters);
        sm.setMatchingContext(this.matchContext);
        return sm;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.tools.PointcutExpression#getPointcutExpression()
	 */
    public String getPointcutExpression() {
        return expression;
    }

    private static class HasPossibleDynamicContentVisitor extends AbstractPatternNodeVisitor {

        private boolean hasDynamicContent = false;

        public boolean hasDynamicContent() {
            return hasDynamicContent;
        }

        public Object visit(ArgsAnnotationPointcut node, Object data) {
            hasDynamicContent = true;
            return null;
        }

        public Object visit(ArgsPointcut node, Object data) {
            hasDynamicContent = true;
            return null;
        }

        public Object visit(CflowPointcut node, Object data) {
            hasDynamicContent = true;
            return null;
        }

        public Object visit(IfPointcut node, Object data) {
            hasDynamicContent = true;
            return null;
        }

        public Object visit(NotAnnotationTypePattern node, Object data) {
            return node.getNegatedPattern().accept(this, data);
        }

        public Object visit(NotPointcut node, Object data) {
            return node.getNegatedPointcut().accept(this, data);
        }

        public Object visit(ThisOrTargetAnnotationPointcut node, Object data) {
            hasDynamicContent = true;
            return null;
        }

        public Object visit(ThisOrTargetPointcut node, Object data) {
            hasDynamicContent = true;
            return null;
        }
    }

    public static class Handler implements Member {

        private Class decClass;

        private Class exType;

        public  Handler(Class decClass, Class exType) {
            this.decClass = decClass;
            this.exType = exType;
        }

        public int getModifiers() {
            return 0;
        }

        public Class getDeclaringClass() {
            return decClass;
        }

        public String getName() {
            return null;
        }

        public Class getHandledExceptionType() {
            return exType;
        }

        public boolean isSynthetic() {
            return false;
        }
    }
}
