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
package org.aspectj.weaver.patterns;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.aspectj.util.FuzzyBoolean;
import org.aspectj.weaver.ISourceContext;
import org.aspectj.weaver.IntMap;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.Shadow;
import org.aspectj.weaver.VersionedDataInputStream;
import org.aspectj.weaver.ast.Test;

public class AndPointcut extends Pointcut {

    // exposed for testing
    Pointcut left, right;

    private int couldMatchKinds;

    public  AndPointcut(Pointcut left, Pointcut right) {
        super();
        this.left = left;
        this.right = right;
        this.pointcutKind = AND;
        setLocation(left.getSourceContext(), left.getStart(), right.getEnd());
        couldMatchKinds = left.couldMatchKinds() & right.couldMatchKinds();
    }

    public int couldMatchKinds() {
        return couldMatchKinds;
    }

    public FuzzyBoolean fastMatch(FastMatchInfo type) {
        return left.fastMatch(type).and(right.fastMatch(type));
    }

    protected FuzzyBoolean matchInternal(Shadow shadow) {
        FuzzyBoolean leftMatch = left.match(shadow);
        if (leftMatch.alwaysFalse())
            return leftMatch;
        return leftMatch.and(right.match(shadow));
    }

    public String toString() {
        return "(" + left.toString() + " && " + right.toString() + ")";
    }

    public boolean equals(Object other) {
        if (!(other instanceof AndPointcut))
            return false;
        AndPointcut o = (AndPointcut) other;
        return o.left.equals(left) && o.right.equals(right);
    }

    public int hashCode() {
        int result = 19;
        result = 37 * result + left.hashCode();
        result = 37 * result + right.hashCode();
        return result;
    }

    public void resolveBindings(IScope scope, Bindings bindings) {
        left.resolveBindings(scope, bindings);
        right.resolveBindings(scope, bindings);
    }

    public void write(DataOutputStream s) throws IOException {
        s.writeByte(Pointcut.AND);
        left.write(s);
        right.write(s);
        writeLocation(s);
    }

    public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
        AndPointcut ret = new AndPointcut(Pointcut.read(s, context), Pointcut.read(s, context));
        ret.readLocation(context, s);
        return ret;
    }

    protected Test findResidueInternal(Shadow shadow, ExposedState state) {
        return Test.makeAnd(left.findResidue(shadow, state), right.findResidue(shadow, state));
    }

    public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
        AndPointcut ret = new AndPointcut(left.concretize(inAspect, declaringType, bindings), right.concretize(inAspect, declaringType, bindings));
        ret.copyLocationFrom(this);
        return ret;
    }

    public Pointcut parameterizeWith(Map typeVariableMap) {
        AndPointcut ret = new AndPointcut(left.parameterizeWith(typeVariableMap), right.parameterizeWith(typeVariableMap));
        ret.copyLocationFrom(this);
        return ret;
    }

    public Pointcut getLeft() {
        return left;
    }

    public Pointcut getRight() {
        return right;
    }

    public Object accept(PatternNodeVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public Object traverse(PatternNodeVisitor visitor, Object data) {
        Object ret = accept(visitor, data);
        left.traverse(visitor, ret);
        right.traverse(visitor, ret);
        return ret;
    }
}
