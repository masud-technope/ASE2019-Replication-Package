/* *******************************************************************
 * Copyright (c) 2004 IBM Corporation.
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * ******************************************************************/
package org.aspectj.weaver.patterns;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.aspectj.util.FuzzyBoolean;
import org.aspectj.weaver.AnnotatedElement;
import org.aspectj.weaver.ISourceContext;
import org.aspectj.weaver.VersionedDataInputStream;
import org.aspectj.weaver.World;

/**
 * @author colyer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AndAnnotationTypePattern extends AnnotationTypePattern {

    private AnnotationTypePattern left;

    private AnnotationTypePattern right;

    public  AndAnnotationTypePattern(AnnotationTypePattern left, AnnotationTypePattern right) {
        this.left = left;
        this.right = right;
        setLocation(left.getSourceContext(), left.getStart(), right.getEnd());
    }

    public FuzzyBoolean matches(AnnotatedElement annotated) {
        return left.matches(annotated).and(right.matches(annotated));
    }

    public void resolve(World world) {
        left.resolve(world);
        right.resolve(world);
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.AnnotationTypePattern#resolveBindings(org.aspectj.weaver.patterns.IScope, org.aspectj.weaver.patterns.Bindings, boolean)
	 */
    public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
        left = left.resolveBindings(scope, bindings, allowBinding);
        right = right.resolveBindings(scope, bindings, allowBinding);
        return this;
    }

    public AnnotationTypePattern parameterizeWith(Map typeVariableMap) {
        AnnotationTypePattern newLeft = left.parameterizeWith(typeVariableMap);
        AnnotationTypePattern newRight = right.parameterizeWith(typeVariableMap);
        AndAnnotationTypePattern ret = new AndAnnotationTypePattern(newLeft, newRight);
        ret.copyLocationFrom(this);
        return ret;
    }

    public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
        AnnotationTypePattern p = new AndAnnotationTypePattern(AnnotationTypePattern.read(s, context), AnnotationTypePattern.read(s, context));
        p.readLocation(context, s);
        return p;
    }

    public void write(DataOutputStream s) throws IOException {
        s.writeByte(AnnotationTypePattern.AND);
        left.write(s);
        right.write(s);
        writeLocation(s);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AndAnnotationTypePattern))
            return false;
        AndAnnotationTypePattern other = (AndAnnotationTypePattern) obj;
        return (left.equals(other.left) && right.equals(other.right));
    }

    public int hashCode() {
        int result = 17;
        result = result * 37 + left.hashCode();
        result = result * 37 + right.hashCode();
        return result;
    }

    public String toString() {
        return left.toString() + " " + right.toString();
    }

    public AnnotationTypePattern getLeft() {
        return left;
    }

    public AnnotationTypePattern getRight() {
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
