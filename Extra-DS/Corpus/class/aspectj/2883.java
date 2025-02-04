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
import org.aspectj.bridge.ISourceLocation;
import org.aspectj.bridge.MessageUtil;
import org.aspectj.util.FuzzyBoolean;
import org.aspectj.weaver.Checker;
import org.aspectj.weaver.ISourceContext;
import org.aspectj.weaver.IntMap;
import org.aspectj.weaver.Member;
import org.aspectj.weaver.ResolvedMember;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.Shadow;
import org.aspectj.weaver.ShadowMunger;
import org.aspectj.weaver.UnresolvedType;
import org.aspectj.weaver.VersionedDataInputStream;
import org.aspectj.weaver.WeaverMessages;
import org.aspectj.weaver.World;
import org.aspectj.weaver.ast.Literal;
import org.aspectj.weaver.ast.Test;

public class KindedPointcut extends Pointcut {

    Shadow.Kind kind;

    private SignaturePattern signature;

    private int matchKinds;

    // only set after concretization
    private ShadowMunger munger = null;

    public  KindedPointcut(Shadow.Kind kind, SignaturePattern signature) {
        this.kind = kind;
        this.signature = signature;
        this.pointcutKind = KINDED;
        this.matchKinds = kind.bit;
    }

    public  KindedPointcut(Shadow.Kind kind, SignaturePattern signature, ShadowMunger munger) {
        this(kind, signature);
        this.munger = munger;
    }

    public SignaturePattern getSignature() {
        return signature;
    }

    /* (non-Javadoc)
	 * @see org.aspectj.weaver.patterns.Pointcut#couldMatchKinds()
	 */
    public int couldMatchKinds() {
        return matchKinds;
    }

    public boolean couldEverMatchSameJoinPointsAs(KindedPointcut other) {
        if (this.kind != other.kind)
            return false;
        String myName = signature.getName().maybeGetSimpleName();
        String yourName = other.signature.getName().maybeGetSimpleName();
        if (myName != null && yourName != null) {
            if (!myName.equals(yourName)) {
                return false;
            }
        }
        if (signature.getParameterTypes().ellipsisCount == 0) {
            if (other.signature.getParameterTypes().ellipsisCount == 0) {
                if (signature.getParameterTypes().getTypePatterns().length != other.signature.getParameterTypes().getTypePatterns().length) {
                    return false;
                }
            }
        }
        return true;
    }

    public FuzzyBoolean fastMatch(FastMatchInfo info) {
        if (info.getKind() != null) {
            if (info.getKind() != kind)
                return FuzzyBoolean.NO;
        }
        return FuzzyBoolean.MAYBE;
    }

    protected FuzzyBoolean matchInternal(Shadow shadow) {
        if (shadow.getKind() != kind)
            return FuzzyBoolean.NO;
        if (!signature.matches(shadow.getMatchingSignature(), shadow.getIWorld(), this.kind == Shadow.MethodCall)) {
            if (kind == Shadow.MethodCall) {
                warnOnConfusingSig(shadow);
            //warnOnBridgeMethod(shadow);
            }
            return FuzzyBoolean.NO;
        }
        return FuzzyBoolean.YES;
    }

    //	private void warnOnBridgeMethod(Shadow shadow) {
    //		if (shadow.getIWorld().getLint().noJoinpointsForBridgeMethods.isEnabled()) {
    //			ResolvedMember rm = shadow.getSignature().resolve(shadow.getIWorld());
    //			if (rm!=null) {
    //             	int shadowModifiers = rm.getModifiers(); //shadow.getSignature().getModifiers(shadow.getIWorld());
    //			    if (ResolvedType.hasBridgeModifier(shadowModifiers)) {
    //				  shadow.getIWorld().getLint().noJoinpointsForBridgeMethods.signal(new String[]{},getSourceLocation(),
    //						new ISourceLocation[]{shadow.getSourceLocation()});
    //			    }
    //            }
    //        }
    //	}
    private void warnOnConfusingSig(Shadow shadow) {
        // Don't do all this processing if we don't need to !
        if (!shadow.getIWorld().getLint().unmatchedSuperTypeInCall.isEnabled())
            return;
        // no warnings for declare error/warning
        if (munger instanceof Checker)
            return;
        World world = shadow.getIWorld();
        // warning never needed if the declaring type is any
        UnresolvedType exactDeclaringType = signature.getDeclaringType().getExactType();
        ResolvedType shadowDeclaringType = shadow.getSignature().getDeclaringType().resolve(world);
        if (signature.getDeclaringType().isStar() || ResolvedType.isMissing(exactDeclaringType) || exactDeclaringType.resolve(world).isMissing())
            return;
        // warning not needed if match type couldn't ever be the declaring type
        if (!shadowDeclaringType.isAssignableFrom(exactDeclaringType.resolve(world))) {
            return;
        }
        // if the method in the declaring type is *not* visible to the
        // exact declaring type then warning not needed.
        ResolvedMember rm = shadow.getSignature().resolve(world);
        // this will be reported elsewhere.
        if (rm == null)
            return;
        int shadowModifiers = rm.getModifiers();
        if (!ResolvedType.isVisible(shadowModifiers, shadowDeclaringType, exactDeclaringType.resolve(world))) {
            return;
        }
        if (!signature.getReturnType().matchesStatically(shadow.getSignature().getReturnType().resolve(world))) {
            // XXX Put out another XLINT in this case?
            return;
        }
        // PR60015 - Don't report the warning if the declaring type is object and 'this' is an interface
        if (exactDeclaringType.resolve(world).isInterface() && shadowDeclaringType.equals(world.resolve("java.lang.Object"))) {
            return;
        }
        SignaturePattern nonConfusingPattern = new SignaturePattern(signature.getKind(), signature.getModifiers(), signature.getReturnType(), TypePattern.ANY, signature.getName(), signature.getParameterTypes(), signature.getThrowsPattern(), signature.getAnnotationPattern());
        if (nonConfusingPattern.matches(shadow.getSignature(), shadow.getIWorld(), true)) {
            shadow.getIWorld().getLint().unmatchedSuperTypeInCall.signal(new String[] { shadow.getSignature().getDeclaringType().toString(), signature.getDeclaringType().toString() }, this.getSourceLocation(), new ISourceLocation[] { shadow.getSourceLocation() });
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof KindedPointcut))
            return false;
        KindedPointcut o = (KindedPointcut) other;
        return o.kind == this.kind && o.signature.equals(this.signature);
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + kind.hashCode();
        result = 37 * result + signature.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(kind.getSimpleName());
        buf.append("(");
        buf.append(signature.toString());
        buf.append(")");
        return buf.toString();
    }

    public void postRead(ResolvedType enclosingType) {
        signature.postRead(enclosingType);
    }

    public void write(DataOutputStream s) throws IOException {
        s.writeByte(Pointcut.KINDED);
        kind.write(s);
        signature.write(s);
        writeLocation(s);
    }

    public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
        Shadow.Kind kind = Shadow.Kind.read(s);
        SignaturePattern sig = SignaturePattern.read(s, context);
        KindedPointcut ret = new KindedPointcut(kind, sig);
        ret.readLocation(context, s);
        return ret;
    }

    // XXX note: there is no namebinding in any kinded pointcut.
    // still might want to do something for better error messages
    // We want to do something here to make sure we don't sidestep the parameter
    // list in capturing type identifiers.
    public void resolveBindings(IScope scope, Bindings bindings) {
        if (kind == Shadow.Initialization) {
        //			scope.getMessageHandler().handleMessage(
        //				MessageUtil.error(
        //					"initialization unimplemented in 1.1beta1",
        //					this.getSourceLocation()));
        }
        signature = signature.resolveBindings(scope, bindings);
        if (kind == Shadow.ConstructorExecution) {
            // Bug fix 60936
            if (signature.getDeclaringType() != null) {
                World world = scope.getWorld();
                UnresolvedType exactType = signature.getDeclaringType().getExactType();
                if (signature.getKind() == Member.CONSTRUCTOR && !ResolvedType.isMissing(exactType) && exactType.resolve(world).isInterface() && !signature.getDeclaringType().isIncludeSubtypes()) {
                    world.getLint().noInterfaceCtorJoinpoint.signal(exactType.toString(), getSourceLocation());
                }
            }
        }
        // no parameterized types
        if (kind == Shadow.StaticInitialization) {
            HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getDeclaringType().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.NO_STATIC_INIT_JPS_FOR_PARAMETERIZED_TYPES), getSourceLocation()));
            }
        }
        // no parameterized types in declaring type position
        if ((kind == Shadow.FieldGet) || (kind == Shadow.FieldSet)) {
            HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getDeclaringType().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.GET_AND_SET_DONT_SUPPORT_DEC_TYPE_PARAMETERS), getSourceLocation()));
            }
            // fields can't have a void type!
            UnresolvedType returnType = signature.getReturnType().getExactType();
            if (returnType == ResolvedType.VOID) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.FIELDS_CANT_HAVE_VOID_TYPE), getSourceLocation()));
            }
        }
        // no throwable parameterized types
        if ((kind == Shadow.Initialization) || (kind == Shadow.PreInitialization)) {
            HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getDeclaringType().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.NO_INIT_JPS_FOR_PARAMETERIZED_TYPES), getSourceLocation()));
            }
            visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getThrowsPattern().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.NO_GENERIC_THROWABLES), getSourceLocation()));
            }
        }
        // no throwable parameterized types
        if ((kind == Shadow.MethodExecution) || (kind == Shadow.ConstructorExecution)) {
            HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getDeclaringType().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.EXECUTION_DOESNT_SUPPORT_PARAMETERIZED_DECLARING_TYPES), getSourceLocation()));
            }
            visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getThrowsPattern().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.NO_GENERIC_THROWABLES), getSourceLocation()));
            }
        }
        // no throwable parameterized types
        if ((kind == Shadow.MethodCall) || (kind == Shadow.ConstructorCall)) {
            HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getDeclaringType().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.CALL_DOESNT_SUPPORT_PARAMETERIZED_DECLARING_TYPES), getSourceLocation()));
            }
            visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
            signature.getThrowsPattern().traverse(visitor, null);
            if (visitor.wellHasItThen()) {
                scope.message(MessageUtil.error(WeaverMessages.format(WeaverMessages.NO_GENERIC_THROWABLES), getSourceLocation()));
            }
            if (!scope.getWorld().isJoinpointArrayConstructionEnabled() && kind == Shadow.ConstructorCall && signature.getDeclaringType().isArray()) {
                scope.message(MessageUtil.warn(WeaverMessages.format(WeaverMessages.NO_NEWARRAY_JOINPOINTS_BY_DEFAULT), getSourceLocation()));
            }
        }
    }

    protected Test findResidueInternal(Shadow shadow, ExposedState state) {
        return match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
    }

    public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
        Pointcut ret = new KindedPointcut(kind, signature, bindings.getEnclosingAdvice());
        ret.copyLocationFrom(this);
        return ret;
    }

    public Pointcut parameterizeWith(Map typeVariableMap) {
        Pointcut ret = new KindedPointcut(kind, signature.parameterizeWith(typeVariableMap), munger);
        ret.copyLocationFrom(this);
        return ret;
    }

    public Shadow.Kind getKind() {
        return kind;
    }

    public Object accept(PatternNodeVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
