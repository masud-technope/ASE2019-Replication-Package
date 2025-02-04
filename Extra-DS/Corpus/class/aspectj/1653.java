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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.aspectj.bridge.MessageUtil;
import org.aspectj.util.FuzzyBoolean;
import org.aspectj.weaver.AjcMemberMaker;
import org.aspectj.weaver.AnnotatedElement;
import org.aspectj.weaver.BCException;
import org.aspectj.weaver.ISourceContext;
import org.aspectj.weaver.IntMap;
import org.aspectj.weaver.Member;
import org.aspectj.weaver.NameMangler;
import org.aspectj.weaver.NewFieldTypeMunger;
import org.aspectj.weaver.ResolvedMember;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.Shadow;
import org.aspectj.weaver.ShadowMunger;
import org.aspectj.weaver.UnresolvedType;
import org.aspectj.weaver.VersionedDataInputStream;
import org.aspectj.weaver.WeaverMessages;
import org.aspectj.weaver.ast.Literal;
import org.aspectj.weaver.ast.Test;
import org.aspectj.weaver.ast.Var;
import org.aspectj.weaver.bcel.BcelTypeMunger;

/**
 * @annotation(@Foo) or @annotation(foo)
 * 
 * Matches any join point where the subject of the join point has an
 * annotation matching the annotationTypePattern:
 * 
 * Join Point Kind          Subject
 * ================================
 * method call              the target method
 * method execution         the method
 * constructor call         the constructor
 * constructor execution    the constructor
 * get                      the target field
 * set                      the target field
 * adviceexecution          the advice
 * initialization           the constructor
 * preinitialization        the constructor
 * staticinitialization     the type being initialized
 * handler                  the declared type of the handled exception
 */
public class AnnotationPointcut extends NameBindingPointcut {

    private ExactAnnotationTypePattern annotationTypePattern;

    // only set after concretization
    private ShadowMunger munger = null;

    private String declarationText;

    public  AnnotationPointcut(ExactAnnotationTypePattern type) {
        super();
        this.annotationTypePattern = type;
        this.pointcutKind = Pointcut.ANNOTATION;
        buildDeclarationText();
    }

    public  AnnotationPointcut(ExactAnnotationTypePattern type, ShadowMunger munger) {
        this(type);
        this.munger = munger;
        buildDeclarationText();
    }

    public ExactAnnotationTypePattern getAnnotationTypePattern() {
        return annotationTypePattern;
    }

    public int couldMatchKinds() {
        return Shadow.ALL_SHADOW_KINDS_BITS;
    }

    public Pointcut parameterizeWith(Map typeVariableMap) {
        AnnotationPointcut ret = new AnnotationPointcut((ExactAnnotationTypePattern) annotationTypePattern.parameterizeWith(typeVariableMap));
        ret.copyLocationFrom(this);
        return ret;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.Pointcut#fastMatch(org.aspectj.weaver.patterns.FastMatchInfo)
	 */
    public FuzzyBoolean fastMatch(FastMatchInfo info) {
        if (info.getKind() == Shadow.StaticInitialization) {
            return annotationTypePattern.fastMatches(info.getType());
        } else {
            return FuzzyBoolean.MAYBE;
        }
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.Pointcut#match(org.aspectj.weaver.Shadow)
	 */
    protected FuzzyBoolean matchInternal(Shadow shadow) {
        AnnotatedElement toMatchAgainst = null;
        Member member = shadow.getSignature();
        ResolvedMember rMember = member.resolve(shadow.getIWorld());
        if (rMember == null) {
            if (member.getName().startsWith(NameMangler.PREFIX)) {
                return FuzzyBoolean.NO;
            }
            shadow.getIWorld().getLint().unresolvableMember.signal(member.toString(), getSourceLocation());
            return FuzzyBoolean.NO;
        }
        Shadow.Kind kind = shadow.getKind();
        if (kind == Shadow.StaticInitialization) {
            toMatchAgainst = rMember.getDeclaringType().resolve(shadow.getIWorld());
        } else if ((kind == Shadow.ExceptionHandler)) {
            toMatchAgainst = rMember.getParameterTypes()[0].resolve(shadow.getIWorld());
        } else {
            toMatchAgainst = rMember;
            // FIXME asc perf cache the result of discovering the member that contains the real annotations
            if (rMember.isAnnotatedElsewhere()) {
                if (kind == Shadow.FieldGet || kind == Shadow.FieldSet) {
                    // FIXME asc should include supers with getInterTypeMungersIncludingSupers?
                    List mungers = rMember.getDeclaringType().resolve(shadow.getIWorld()).getInterTypeMungers();
                    for (Iterator iter = mungers.iterator(); iter.hasNext(); ) {
                        BcelTypeMunger typeMunger = (BcelTypeMunger) iter.next();
                        if (typeMunger.getMunger() instanceof NewFieldTypeMunger) {
                            ResolvedMember fakerm = typeMunger.getSignature();
                            if (fakerm.equals(member)) {
                                ResolvedMember ajcMethod = AjcMemberMaker.interFieldInitializer(fakerm, typeMunger.getAspectType());
                                ResolvedMember rmm = findMethod(typeMunger.getAspectType(), ajcMethod);
                                toMatchAgainst = rmm;
                            }
                        }
                    }
                }
            }
        }
        annotationTypePattern.resolve(shadow.getIWorld());
        return annotationTypePattern.matches(toMatchAgainst);
    }

    private ResolvedMember findMethod(ResolvedType aspectType, ResolvedMember ajcMethod) {
        ResolvedMember decMethods[] = aspectType.getDeclaredMethods();
        for (int i = 0; i < decMethods.length; i++) {
            ResolvedMember member = decMethods[i];
            if (member.equals(ajcMethod))
                return member;
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.Pointcut#resolveBindings(org.aspectj.weaver.patterns.IScope, org.aspectj.weaver.patterns.Bindings)
	 */
    protected void resolveBindings(IScope scope, Bindings bindings) {
        if (!scope.getWorld().isInJava5Mode()) {
            scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.ATANNOTATION_ONLY_SUPPORTED_AT_JAVA5_LEVEL), getSourceLocation()));
            return;
        }
        annotationTypePattern = (ExactAnnotationTypePattern) annotationTypePattern.resolveBindings(scope, bindings, true);
    // must be either a Var, or an annotation type pattern
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.Pointcut#concretize1(org.aspectj.weaver.ResolvedType, org.aspectj.weaver.IntMap)
	 */
    protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
        ExactAnnotationTypePattern newType = (ExactAnnotationTypePattern) annotationTypePattern.remapAdviceFormals(bindings);
        Pointcut ret = new AnnotationPointcut(newType, bindings.getEnclosingAdvice());
        ret.copyLocationFrom(this);
        return ret;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.Pointcut#findResidue(org.aspectj.weaver.Shadow, org.aspectj.weaver.patterns.ExposedState)
	 */
    protected Test findResidueInternal(Shadow shadow, ExposedState state) {
        if (annotationTypePattern instanceof BindingAnnotationTypePattern) {
            BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern) annotationTypePattern;
            UnresolvedType annotationType = btp.getAnnotationType();
            Var var = shadow.getKindedAnnotationVar(annotationType);
            // if we weren't going to find the annotation
            if (var == null)
                throw new BCException("Impossible! annotation=[" + annotationType + "]  shadow=[" + shadow + " at " + shadow.getSourceLocation() + //return Literal.FALSE;
                "]    pointcut is at [" + //return Literal.FALSE;
                getSourceLocation() + "]");
            state.set(btp.getFormalIndex(), var);
        }
        if (matchInternal(shadow).alwaysTrue())
            return Literal.TRUE;
        else
            return Literal.FALSE;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.NameBindingPointcut#getBindingAnnotationTypePatterns()
	 */
    public List getBindingAnnotationTypePatterns() {
        if (annotationTypePattern instanceof BindingAnnotationTypePattern) {
            List l = new ArrayList();
            l.add(annotationTypePattern);
            return l;
        } else
            return Collections.EMPTY_LIST;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.NameBindingPointcut#getBindingTypePatterns()
	 */
    public List getBindingTypePatterns() {
        return Collections.EMPTY_LIST;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.PatternNode#write(java.io.DataOutputStream)
	 */
    public void write(DataOutputStream s) throws IOException {
        s.writeByte(Pointcut.ANNOTATION);
        annotationTypePattern.write(s);
        writeLocation(s);
    }

    public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
        AnnotationTypePattern type = AnnotationTypePattern.read(s, context);
        AnnotationPointcut ret = new AnnotationPointcut((ExactAnnotationTypePattern) type);
        ret.readLocation(context, s);
        return ret;
    }

    public boolean equals(Object other) {
        if (!(other instanceof AnnotationPointcut))
            return false;
        AnnotationPointcut o = (AnnotationPointcut) other;
        return o.annotationTypePattern.equals(this.annotationTypePattern);
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + annotationTypePattern.hashCode();
        return result;
    }

    public void buildDeclarationText() {
        StringBuffer buf = new StringBuffer();
        buf.append("@annotation(");
        String annPatt = annotationTypePattern.toString();
        buf.append(annPatt.startsWith("@") ? annPatt.substring(1) : annPatt);
        buf.append(")");
        this.declarationText = buf.toString();
    }

    public String toString() {
        return this.declarationText;
    }

    public Object accept(PatternNodeVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
