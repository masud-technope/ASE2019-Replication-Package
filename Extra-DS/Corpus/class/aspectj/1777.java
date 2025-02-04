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
 *     Mik Kersten	2004-07-26 extended to allow overloading of 
 * 					hierarchy builder
 * ******************************************************************/
package org.aspectj.ajdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.aspectj.ajdt.internal.compiler.ast.AspectDeclaration;
import org.aspectj.ajdt.internal.compiler.ast.AstUtil;
import org.aspectj.ajdt.internal.core.builder.AjBuildManager;
import org.aspectj.bridge.ISourceLocation;
import org.aspectj.bridge.IMessage.Kind;
import org.aspectj.org.eclipse.jdt.core.compiler.CharOperation;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.EmptyStatement;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.Wildcard;
import org.aspectj.org.eclipse.jdt.internal.compiler.impl.Constant;
import org.aspectj.org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.BaseTypes;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.aspectj.org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;
import org.aspectj.weaver.BoundedReferenceType;
import org.aspectj.weaver.ConcreteTypeMunger;
import org.aspectj.weaver.IHasPosition;
import org.aspectj.weaver.Member;
import org.aspectj.weaver.NewFieldTypeMunger;
import org.aspectj.weaver.NewMethodTypeMunger;
import org.aspectj.weaver.ReferenceType;
import org.aspectj.weaver.ResolvedMember;
import org.aspectj.weaver.ResolvedMemberImpl;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.Shadow;
import org.aspectj.weaver.TypeFactory;
import org.aspectj.weaver.TypeVariable;
import org.aspectj.weaver.TypeVariableDeclaringElement;
import org.aspectj.weaver.TypeVariableReference;
import org.aspectj.weaver.UnresolvedType;
import org.aspectj.weaver.UnresolvedTypeVariableReferenceType;
import org.aspectj.weaver.World;
import org.aspectj.weaver.UnresolvedType.TypeKind;

/**
 * @author Jim Hugunin
 */
public class EclipseFactory {

    public static boolean DEBUG = false;

    private AjBuildManager buildManager;

    private LookupEnvironment lookupEnvironment;

    private boolean xSerializableAspects;

    private World world;

    public Collection finishedTypeMungers = null;

    // We can get clashes if we don't treat raw types differently - we end up looking
    // up a raw and getting the generic type (pr115788)
    private /*UnresolvedType, TypeBinding*/
    Map typexToBinding = new HashMap();

    private /*UnresolvedType, TypeBinding*/
    Map rawTypeXToBinding = new HashMap();

    //XXX currently unused
    //	private Map/*TypeBinding, ResolvedType*/ bindingToResolvedTypeX = new HashMap();
    public static EclipseFactory fromLookupEnvironment(LookupEnvironment env) {
        AjLookupEnvironment aenv = (AjLookupEnvironment) env;
        return aenv.factory;
    }

    public static EclipseFactory fromScopeLookupEnvironment(Scope scope) {
        return fromLookupEnvironment(AstUtil.getCompilationUnitScope(scope).environment);
    }

    public  EclipseFactory(LookupEnvironment lookupEnvironment, AjBuildManager buildManager) {
        this.lookupEnvironment = lookupEnvironment;
        this.buildManager = buildManager;
        this.world = buildManager.getWorld();
        this.xSerializableAspects = buildManager.buildConfig.isXserializableAspects();
    }

    public  EclipseFactory(LookupEnvironment lookupEnvironment, World world, boolean xSer) {
        this.lookupEnvironment = lookupEnvironment;
        this.world = world;
        this.xSerializableAspects = xSer;
        this.buildManager = null;
    }

    public World getWorld() {
        return world;
    }

    public void showMessage(Kind kind, String message, ISourceLocation loc1, ISourceLocation loc2) {
        getWorld().showMessage(kind, message, loc1, loc2);
    }

    public ResolvedType fromEclipse(ReferenceBinding binding) {
        if (binding == null)
            return ResolvedType.MISSING;
        //??? this seems terribly inefficient
        //System.err.println("resolving: " + binding.getClass() + ", name = " + getName(binding));
        ResolvedType ret = getWorld().resolve(fromBinding(binding));
        //System.err.println("      got: " + ret);
        return ret;
    }

    public ResolvedType fromTypeBindingToRTX(TypeBinding tb) {
        if (tb == null)
            return ResolvedType.MISSING;
        ResolvedType ret = getWorld().resolve(fromBinding(tb));
        return ret;
    }

    public ResolvedType[] fromEclipse(ReferenceBinding[] bindings) {
        if (bindings == null) {
            return ResolvedType.NONE;
        }
        int len = bindings.length;
        ResolvedType[] ret = new ResolvedType[len];
        for (int i = 0; i < len; i++) {
            ret[i] = fromEclipse(bindings[i]);
        }
        return ret;
    }

    public static String getName(TypeBinding binding) {
        if (binding instanceof TypeVariableBinding) {
            // The first bound may be null - so default to object?
            TypeVariableBinding tvb = (TypeVariableBinding) binding;
            if (tvb.firstBound != null) {
                return getName(tvb.firstBound);
            } else {
                return getName(tvb.superclass);
            }
        }
        if (binding instanceof ReferenceBinding) {
            return new String(CharOperation.concatWith(((ReferenceBinding) binding).compoundName, '.'));
        }
        String packageName = new String(binding.qualifiedPackageName());
        String className = new String(binding.qualifiedSourceName()).replace('.', '$');
        if (packageName.length() > 0) {
            className = packageName + "." + className;
        }
        //XXX doesn't handle arrays correctly (or primitives?)
        return new String(className);
    }

    /**
	 * Some generics notes:
	 * 
	 * Andy 6-May-05
	 * We were having trouble with parameterized types in a couple of places - due to TypeVariableBindings.  When we
	 * see a TypeVariableBinding now we default to either the firstBound if it is specified or java.lang.Object.  Not
	 * sure when/if this gets us unstuck?  It does mean we forget that it is a type variable when going back
	 * the other way from the UnresolvedType and that would seem a bad thing - but I've yet to see the reason we need to
	 * remember the type variable.
	 * Adrian 10-July-05
	 * When we forget it's a type variable we come unstuck when getting the declared members of a parameterized
	 * type - since we don't know it's a type variable we can't replace it with the type parameter.
	 */
    //??? going back and forth between strings and bindings is a waste of cycles
    public UnresolvedType fromBinding(TypeBinding binding) {
        if (binding instanceof HelperInterfaceBinding) {
            return ((HelperInterfaceBinding) binding).getTypeX();
        }
        if (binding == null || binding.qualifiedSourceName() == null) {
            return ResolvedType.MISSING;
        }
        // first piece of generics support!
        if (binding instanceof TypeVariableBinding) {
            TypeVariableBinding tb = (TypeVariableBinding) binding;
            UnresolvedTypeVariableReferenceType utvrt = (UnresolvedTypeVariableReferenceType) fromTypeVariableBinding(tb);
            return utvrt;
        }
        // handle arrays since the component type may need special treatment too...
        if (binding instanceof ArrayBinding) {
            ArrayBinding aBinding = (ArrayBinding) binding;
            UnresolvedType componentType = fromBinding(aBinding.leafComponentType);
            return UnresolvedType.makeArray(componentType, aBinding.dimensions);
        }
        if (binding instanceof WildcardBinding) {
            WildcardBinding eWB = (WildcardBinding) binding;
            UnresolvedType theType = TypeFactory.createTypeFromSignature(CharOperation.charToString(eWB.genericTypeSignature()));
            // Repair the bound
            // e.g. If the bound for the wildcard is a typevariable, e.g. '? extends E' then
            // the type variable in the unresolvedtype will be correct only in name.  In that
            // case let's set it correctly based on the one in the eclipse WildcardBinding
            UnresolvedType theBound = null;
            if (eWB.bound instanceof TypeVariableBinding) {
                theBound = fromTypeVariableBinding((TypeVariableBinding) eWB.bound);
            } else {
                theBound = fromBinding(eWB.bound);
            }
            if (theType.isGenericWildcard() && theType.isSuper())
                theType.setLowerBound(theBound);
            if (theType.isGenericWildcard() && theType.isExtends())
                theType.setUpperBound(theBound);
            return theType;
        }
        if (binding instanceof ParameterizedTypeBinding) {
            if (binding instanceof RawTypeBinding) {
                // special case where no parameters are specified!
                return UnresolvedType.forRawTypeName(getName(binding));
            }
            ParameterizedTypeBinding ptb = (ParameterizedTypeBinding) binding;
            UnresolvedType[] arguments = null;
            if (// null can mean this is an inner type of a Parameterized Type with no bounds of its own (pr100227)
            ptb.arguments != null) {
                arguments = new UnresolvedType[ptb.arguments.length];
                for (int i = 0; i < arguments.length; i++) {
                    arguments[i] = fromBinding(ptb.arguments[i]);
                }
            }
            String baseTypeSignature = null;
            ResolvedType baseType = getWorld().resolve(UnresolvedType.forName(getName(binding)), true);
            if (!baseType.isMissing()) {
                // can legitimately be missing if a bound refers to a type we haven't added to the world yet...
                if (!baseType.isGenericType() && arguments != null)
                    baseType = baseType.getGenericType();
                baseTypeSignature = baseType.getErasureSignature();
            } else {
                baseTypeSignature = UnresolvedType.forName(getName(binding)).getSignature();
            }
            // be type variables that we haven't fixed up yet.
            if (arguments == null)
                arguments = new UnresolvedType[0];
            String parameterizedSig = ResolvedType.PARAMETERIZED_TYPE_IDENTIFIER + CharOperation.charToString(binding.genericTypeSignature()).substring(1);
            return TypeFactory.createUnresolvedParameterizedType(parameterizedSig, baseTypeSignature, arguments);
        }
        // to the forGenericType() method.
        if (binding.isGenericType() && !binding.isParameterizedType() && !binding.isRawType()) {
            TypeVariableBinding[] tvbs = binding.typeVariables();
            TypeVariable[] tVars = new TypeVariable[tvbs.length];
            for (int i = 0; i < tvbs.length; i++) {
                TypeVariableBinding eclipseV = tvbs[i];
                tVars[i] = ((TypeVariableReference) fromTypeVariableBinding(eclipseV)).getTypeVariable();
            }
            //TODO asc generics - temporary guard....
            if (!(binding instanceof SourceTypeBinding))
                throw new RuntimeException("Cant get the generic sig for " + binding.debugName());
            return UnresolvedType.forGenericType(getName(binding), tVars, CharOperation.charToString(((SourceTypeBinding) binding).genericSignature()));
        }
        // LocalTypeBinding have a name $Local$, we can get the real name by using the signature.... 
        if (binding instanceof LocalTypeBinding) {
            LocalTypeBinding ltb = (LocalTypeBinding) binding;
            if (ltb.constantPoolName() != null && ltb.constantPoolName().length > 0) {
                return UnresolvedType.forSignature(new String(binding.signature()));
            } else {
                // anonymous local type yet, report the issue on the enclosing type
                return UnresolvedType.forSignature(new String(ltb.enclosingType.signature()));
            }
        }
        return UnresolvedType.forName(getName(binding));
    }

    /**
	 * Some type variables refer to themselves recursively, this enables us to avoid
	 * recursion problems.
	 */
    private static Map typeVariableBindingsInProgress = new HashMap();

    /**
	 * Convert from the eclipse form of type variable (TypeVariableBinding) to the AspectJ
	 * form (TypeVariable).
	 */
    private UnresolvedType fromTypeVariableBinding(TypeVariableBinding aTypeVariableBinding) {
        // first, check for recursive call to this method for the same tvBinding
        if (typeVariableBindingsInProgress.containsKey(aTypeVariableBinding)) {
            return (UnresolvedType) typeVariableBindingsInProgress.get(aTypeVariableBinding);
        }
        // Check if its a type variable binding that we need to recover to an alias...
        if (typeVariablesForAliasRecovery != null) {
            String aliasname = (String) typeVariablesForAliasRecovery.get(aTypeVariableBinding);
            if (aliasname != null) {
                UnresolvedTypeVariableReferenceType ret = new UnresolvedTypeVariableReferenceType();
                ret.setTypeVariable(new TypeVariable(aliasname));
                return ret;
            }
        }
        if (typeVariablesForThisMember.containsKey(new String(aTypeVariableBinding.sourceName))) {
            return (UnresolvedType) typeVariablesForThisMember.get(new String(aTypeVariableBinding.sourceName));
        }
        // Create the UnresolvedTypeVariableReferenceType for the type variable
        String name = CharOperation.charToString(aTypeVariableBinding.sourceName());
        UnresolvedTypeVariableReferenceType ret = new UnresolvedTypeVariableReferenceType();
        typeVariableBindingsInProgress.put(aTypeVariableBinding, ret);
        TypeVariable tv = new TypeVariable(name);
        ret.setTypeVariable(tv);
        // Dont set any bounds here, you'll get in a recursive mess
        // TODO -- what about lower bounds??
        UnresolvedType superclassType = fromBinding(aTypeVariableBinding.superclass());
        UnresolvedType[] superinterfaces = new UnresolvedType[aTypeVariableBinding.superInterfaces.length];
        for (int i = 0; i < superinterfaces.length; i++) {
            superinterfaces[i] = fromBinding(aTypeVariableBinding.superInterfaces[i]);
        }
        tv.setUpperBound(superclassType);
        tv.setAdditionalInterfaceBounds(superinterfaces);
        tv.setRank(aTypeVariableBinding.rank);
        if (aTypeVariableBinding.declaringElement instanceof MethodBinding) {
            tv.setDeclaringElementKind(TypeVariable.METHOD);
        //			tv.setDeclaringElement(fromBinding((MethodBinding)aTypeVariableBinding.declaringElement);
        } else {
            tv.setDeclaringElementKind(TypeVariable.TYPE);
        //		    //	tv.setDeclaringElement(fromBinding(aTypeVariableBinding.declaringElement));
        }
        if (aTypeVariableBinding.declaringElement instanceof MethodBinding)
            typeVariablesForThisMember.put(new String(aTypeVariableBinding.sourceName), ret);
        typeVariableBindingsInProgress.remove(aTypeVariableBinding);
        return ret;
    }

    public UnresolvedType[] fromBindings(TypeBinding[] bindings) {
        if (bindings == null)
            return UnresolvedType.NONE;
        int len = bindings.length;
        UnresolvedType[] ret = new UnresolvedType[len];
        for (int i = 0; i < len; i++) {
            ret[i] = fromBinding(bindings[i]);
        }
        return ret;
    }

    public static ASTNode astForLocation(IHasPosition location) {
        return new EmptyStatement(location.getStart(), location.getEnd());
    }

    public Collection getDeclareParents() {
        return getWorld().getDeclareParents();
    }

    public Collection getDeclareAnnotationOnTypes() {
        return getWorld().getDeclareAnnotationOnTypes();
    }

    public Collection getDeclareAnnotationOnFields() {
        return getWorld().getDeclareAnnotationOnFields();
    }

    public Collection getDeclareAnnotationOnMethods() {
        return getWorld().getDeclareAnnotationOnMethods();
    }

    public boolean areTypeMungersFinished() {
        return finishedTypeMungers != null;
    }

    public void finishTypeMungers() {
        // make sure that type mungers are
        Collection ret = new ArrayList();
        Collection baseTypeMungers = getWorld().getCrosscuttingMembersSet().getTypeMungers();
        // XXX by Andy: why do we mix up the mungers here? it means later we know about two sets
        // and the late ones are a subset of the complete set? (see pr114436)
        baseTypeMungers.addAll(getWorld().getCrosscuttingMembersSet().getLateTypeMungers());
        for (Iterator i = baseTypeMungers.iterator(); i.hasNext(); ) {
            ConcreteTypeMunger munger = (ConcreteTypeMunger) i.next();
            EclipseTypeMunger etm = makeEclipseTypeMunger(munger);
            if (etm != null)
                ret.add(etm);
        }
        finishedTypeMungers = ret;
    }

    public EclipseTypeMunger makeEclipseTypeMunger(ConcreteTypeMunger concrete) {
        if (concrete.getMunger() != null && EclipseTypeMunger.supportsKind(concrete.getMunger().getKind())) {
            AbstractMethodDeclaration method = null;
            if (concrete instanceof EclipseTypeMunger) {
                method = ((EclipseTypeMunger) concrete).getSourceMethod();
            }
            EclipseTypeMunger ret = new EclipseTypeMunger(this, concrete.getMunger(), concrete.getAspectType(), method);
            if (ret.getSourceLocation() == null) {
                ret.setSourceLocation(concrete.getSourceLocation());
            }
            return ret;
        } else {
            return null;
        }
    }

    public Collection getTypeMungers() {
        //??? assert finishedTypeMungers != null
        return finishedTypeMungers;
    }

    public ResolvedMember makeResolvedMember(MethodBinding binding) {
        return makeResolvedMember(binding, binding.declaringClass);
    }

    public ResolvedMember makeResolvedMember(MethodBinding binding, Shadow.Kind shadowKind) {
        Member.Kind memberKind = binding.isConstructor() ? Member.CONSTRUCTOR : Member.METHOD;
        if (shadowKind == Shadow.AdviceExecution)
            memberKind = Member.ADVICE;
        return makeResolvedMember(binding, binding.declaringClass, memberKind);
    }

    /** 
     * Conversion from a methodbinding (eclipse) to a resolvedmember (aspectj) is now done
     * in the scope of some type variables.  Before converting the parts of a methodbinding
     * (params, return type) we store the type variables in this structure, then should any
     * component of the method binding refer to them, we grab them from the map.
     */
    private Map typeVariablesForThisMember = new HashMap();

    /**
	 * This is a map from typevariablebindings (eclipsey things) to the names the user
	 * originally specified in their ITD.  For example if the target is 'interface I<N extends Number> {}'
	 * and the ITD was 'public void I<X>.m(List<X> lxs) {}' then this map would contain a pointer
	 * from the eclipse type 'N extends Number' to the letter 'X'.
	 */
    private Map typeVariablesForAliasRecovery;

    /**
	 * Construct a resolvedmember from a methodbinding.  The supplied map tells us about any
	 * typevariablebindings that replaced typevariables whilst the compiler was resolving types - 
	 * this only happens if it is a generic itd that shares type variables with its target type.
	 */
    public ResolvedMember makeResolvedMemberForITD(MethodBinding binding, TypeBinding declaringType, /*TypeVariableBinding > original alias name*/
    Map recoveryAliases) {
        ResolvedMember result = null;
        try {
            typeVariablesForAliasRecovery = recoveryAliases;
            result = makeResolvedMember(binding, declaringType);
        } finally {
            typeVariablesForAliasRecovery = null;
        }
        return result;
    }

    public ResolvedMember makeResolvedMember(MethodBinding binding, TypeBinding declaringType) {
        return makeResolvedMember(binding, declaringType, binding.isConstructor() ? Member.CONSTRUCTOR : Member.METHOD);
    }

    public ResolvedMember makeResolvedMember(MethodBinding binding, TypeBinding declaringType, Member.Kind memberKind) {
        //System.err.println("member for: " + binding + ", " + new String(binding.declaringClass.sourceName));
        // Convert the type variables and store them
        UnresolvedType[] ajTypeRefs = null;
        typeVariablesForThisMember.clear();
        // This is the set of type variables available whilst building the resolved member...
        if (binding.typeVariables != null) {
            ajTypeRefs = new UnresolvedType[binding.typeVariables.length];
            for (int i = 0; i < binding.typeVariables.length; i++) {
                ajTypeRefs[i] = fromBinding(binding.typeVariables[i]);
                typeVariablesForThisMember.put(new String(binding.typeVariables[i].sourceName), /*new Integer(binding.typeVariables[i].rank),*/
                ajTypeRefs[i]);
            }
        }
        // AMC these next two lines shouldn't be needed once we sort out generic types properly in the world map
        ResolvedType realDeclaringType = world.resolve(fromBinding(declaringType));
        if (realDeclaringType.isRawType())
            realDeclaringType = realDeclaringType.getGenericType();
        ResolvedMemberImpl ret = new ResolvedMemberImpl(memberKind, realDeclaringType, binding.modifiers, fromBinding(binding.returnType), new String(binding.selector), fromBindings(binding.parameters), fromBindings(binding.thrownExceptions));
        if (binding.isVarargs()) {
            ret.setVarargsMethod();
        }
        if (ajTypeRefs != null) {
            TypeVariable[] tVars = new TypeVariable[ajTypeRefs.length];
            for (int i = 0; i < ajTypeRefs.length; i++) {
                tVars[i] = ((TypeVariableReference) ajTypeRefs[i]).getTypeVariable();
            }
            ret.setTypeVariables(tVars);
        }
        typeVariablesForThisMember.clear();
        ret.resolve(world);
        return ret;
    }

    public ResolvedMember makeResolvedMember(FieldBinding binding) {
        return makeResolvedMember(binding, binding.declaringClass);
    }

    public ResolvedMember makeResolvedMember(FieldBinding binding, TypeBinding receiverType) {
        // AMC these next two lines shouldn't be needed once we sort out generic types properly in the world map
        ResolvedType realDeclaringType = world.resolve(fromBinding(receiverType));
        if (realDeclaringType.isRawType())
            realDeclaringType = realDeclaringType.getGenericType();
        return new ResolvedMemberImpl(Member.FIELD, realDeclaringType, binding.modifiers, world.resolve(fromBinding(binding.type)), new String(binding.name), UnresolvedType.NONE);
    }

    public TypeBinding makeTypeBinding(UnresolvedType typeX) {
        TypeBinding ret = null;
        // looking up type variables can get us into trouble
        if (!typeX.isTypeVariableReference()) {
            if (typeX.isRawType()) {
                ret = (TypeBinding) rawTypeXToBinding.get(typeX);
            } else {
                ret = (TypeBinding) typexToBinding.get(typeX);
            }
        }
        if (ret == null) {
            ret = makeTypeBinding1(typeX);
            if (!(typeX instanceof BoundedReferenceType) && !(typeX instanceof UnresolvedTypeVariableReferenceType)) {
                if (typeX.isRawType()) {
                    rawTypeXToBinding.put(typeX, ret);
                } else {
                    typexToBinding.put(typeX, ret);
                }
            }
        }
        if (ret == null) {
            System.out.println("can't find: " + typeX);
        }
        return ret;
    }

    // When converting a parameterized type from our world to the eclipse world, these get set so that
    // resolution of the type parameters may known in what context it is occurring (pr114744)
    private ReferenceBinding baseTypeForParameterizedType;

    private int indexOfTypeParameterBeingConverted;

    private TypeBinding makeTypeBinding1(UnresolvedType typeX) {
        if (typeX.isPrimitiveType()) {
            if (typeX == ResolvedType.BOOLEAN)
                return BaseTypes.BooleanBinding;
            if (typeX == ResolvedType.BYTE)
                return BaseTypes.ByteBinding;
            if (typeX == ResolvedType.CHAR)
                return BaseTypes.CharBinding;
            if (typeX == ResolvedType.DOUBLE)
                return BaseTypes.DoubleBinding;
            if (typeX == ResolvedType.FLOAT)
                return BaseTypes.FloatBinding;
            if (typeX == ResolvedType.INT)
                return BaseTypes.IntBinding;
            if (typeX == ResolvedType.LONG)
                return BaseTypes.LongBinding;
            if (typeX == ResolvedType.SHORT)
                return BaseTypes.ShortBinding;
            if (typeX == ResolvedType.VOID)
                return BaseTypes.VoidBinding;
            throw new RuntimeException("weird primitive type " + typeX);
        } else if (typeX.isArray()) {
            int dim = 0;
            while (typeX.isArray()) {
                dim++;
                typeX = typeX.getComponentType();
            }
            return lookupEnvironment.createArrayType(makeTypeBinding(typeX), dim);
        } else if (typeX.isParameterizedType()) {
            // Converting back to a binding from a UnresolvedType
            UnresolvedType[] typeParameters = typeX.getTypeParameters();
            ReferenceBinding baseTypeBinding = lookupBinding(typeX.getBaseName());
            TypeBinding[] argumentBindings = new TypeBinding[typeParameters.length];
            baseTypeForParameterizedType = baseTypeBinding;
            for (int i = 0; i < argumentBindings.length; i++) {
                indexOfTypeParameterBeingConverted = i;
                argumentBindings[i] = makeTypeBinding(typeParameters[i]);
            }
            indexOfTypeParameterBeingConverted = 0;
            baseTypeForParameterizedType = null;
            ParameterizedTypeBinding ptb = lookupEnvironment.createParameterizedType(baseTypeBinding, argumentBindings, baseTypeBinding.enclosingType());
            return ptb;
        } else if (typeX.isTypeVariableReference()) {
            //			return makeTypeVariableBinding((TypeVariableReference)typeX);
            return makeTypeVariableBindingFromAJTypeVariable(((TypeVariableReference) typeX).getTypeVariable());
        } else if (typeX.isRawType()) {
            ReferenceBinding baseTypeBinding = lookupBinding(typeX.getBaseName());
            RawTypeBinding rtb = lookupEnvironment.createRawType(baseTypeBinding, baseTypeBinding.enclosingType());
            return rtb;
        } else if (typeX.isGenericWildcard()) {
            // translate from boundedreferencetype to WildcardBinding
            BoundedReferenceType brt = (BoundedReferenceType) typeX;
            // Work out 'kind' for the WildcardBinding
            int boundkind = Wildcard.UNBOUND;
            TypeBinding bound = null;
            if (brt.isExtends()) {
                boundkind = Wildcard.EXTENDS;
                bound = makeTypeBinding(brt.getUpperBound());
            } else if (brt.isSuper()) {
                boundkind = Wildcard.SUPER;
                bound = makeTypeBinding(brt.getLowerBound());
            }
            TypeBinding[] otherBounds = null;
            if (brt.getAdditionalBounds() != null && brt.getAdditionalBounds().length != 0)
                otherBounds = makeTypeBindings(brt.getAdditionalBounds());
            WildcardBinding wb = lookupEnvironment.createWildcard(baseTypeForParameterizedType, indexOfTypeParameterBeingConverted, bound, otherBounds, boundkind);
            return wb;
        } else {
            return lookupBinding(typeX.getName());
        }
    }

    private ReferenceBinding lookupBinding(String sname) {
        char[][] name = CharOperation.splitOn('.', sname.toCharArray());
        ReferenceBinding rb = lookupEnvironment.getType(name);
        return rb;
    }

    public TypeBinding[] makeTypeBindings(UnresolvedType[] types) {
        int len = types.length;
        TypeBinding[] ret = new TypeBinding[len];
        for (int i = 0; i < len; i++) {
            ret[i] = makeTypeBinding(types[i]);
        }
        return ret;
    }

    // just like the code above except it returns an array of ReferenceBindings
    private ReferenceBinding[] makeReferenceBindings(UnresolvedType[] types) {
        int len = types.length;
        ReferenceBinding[] ret = new ReferenceBinding[len];
        for (int i = 0; i < len; i++) {
            ret[i] = (ReferenceBinding) makeTypeBinding(types[i]);
        }
        return ret;
    }

    // field related
    public FieldBinding makeFieldBinding(NewFieldTypeMunger nftm) {
        return internalMakeFieldBinding(nftm.getSignature(), nftm.getTypeVariableAliases());
    }

    /**
	 * Convert a resolvedmember into an eclipse field binding
	 */
    public FieldBinding makeFieldBinding(ResolvedMember member, List aliases) {
        return internalMakeFieldBinding(member, aliases);
    }

    /**
	 * Convert a resolvedmember into an eclipse field binding
	 */
    public FieldBinding makeFieldBinding(ResolvedMember member) {
        return internalMakeFieldBinding(member, null);
    }

    /**
	 * Take a normal AJ member and convert it into an eclipse fieldBinding.
	 * Taking into account any aliases that it may include due to being
	 * a generic itd.  Any aliases are put into the typeVariableToBinding
	 * map so that they will be substituted as appropriate in the returned
	 * fieldbinding.
	 */
    public FieldBinding internalMakeFieldBinding(ResolvedMember member, List aliases) {
        typeVariableToTypeBinding.clear();
        TypeVariableBinding[] tvbs = null;
        ReferenceBinding declaringType = (ReferenceBinding) makeTypeBinding(member.getDeclaringType());
        // If there are aliases, place them in the map
        if (aliases != null && aliases.size() > 0) {
            int i = 0;
            for (Iterator iter = aliases.iterator(); iter.hasNext(); ) {
                String element = (String) iter.next();
                typeVariableToTypeBinding.put(element, declaringType.typeVariables()[i++]);
            }
        }
        currentType = declaringType;
        FieldBinding fb = new FieldBinding(member.getName().toCharArray(), makeTypeBinding(member.getReturnType()), member.getModifiers(), currentType, Constant.NotAConstant);
        typeVariableToTypeBinding.clear();
        currentType = null;
        return fb;
    }

    private ReferenceBinding currentType = null;

    // method binding related
    public MethodBinding makeMethodBinding(NewMethodTypeMunger nmtm) {
        return internalMakeMethodBinding(nmtm.getSignature(), nmtm.getTypeVariableAliases());
    }

    /**
	 * Convert a resolvedmember into an eclipse method binding.
	 */
    public MethodBinding makeMethodBinding(ResolvedMember member, List aliases) {
        return internalMakeMethodBinding(member, aliases);
    }

    /**
     * Creates a method binding for a resolvedmember taking into account type variable aliases -
     * this variant can take an aliasTargetType and should be used when the alias target type
     * cannot be retrieved from the resolvedmember.
     */
    public MethodBinding makeMethodBinding(ResolvedMember member, List aliases, UnresolvedType aliasTargetType) {
        return internalMakeMethodBinding(member, aliases, aliasTargetType);
    }

    /**
	 * Convert a resolvedmember into an eclipse method binding.
	 */
    public MethodBinding makeMethodBinding(ResolvedMember member) {
        // there are no aliases
        return internalMakeMethodBinding(member, null);
    }

    public MethodBinding internalMakeMethodBinding(ResolvedMember member, List aliases) {
        return internalMakeMethodBinding(member, aliases, member.getDeclaringType());
    }

    /**
	 * Take a normal AJ member and convert it into an eclipse methodBinding.
	 * Taking into account any aliases that it may include due to being a 
	 * generic ITD.  Any aliases are put into the typeVariableToBinding
	 * map so that they will be substituted as appropriate in the returned 
	 * methodbinding
	 */
    public MethodBinding internalMakeMethodBinding(ResolvedMember member, List aliases, UnresolvedType aliasTargetType) {
        typeVariableToTypeBinding.clear();
        TypeVariableBinding[] tvbs = null;
        if (member.getTypeVariables() != null) {
            if (member.getTypeVariables().length == 0) {
                tvbs = MethodBinding.NoTypeVariables;
            } else {
                tvbs = makeTypeVariableBindingsFromAJTypeVariables(member.getTypeVariables());
            // QQQ do we need to bother fixing up the declaring element here?
            }
        }
        ReferenceBinding declaringType = (ReferenceBinding) makeTypeBinding(member.getDeclaringType());
        // If there are aliases, place them in the map
        if (aliases != null && aliases.size() != 0) {
            int i = 0;
            ReferenceBinding aliasTarget = (ReferenceBinding) makeTypeBinding(aliasTargetType);
            for (Iterator iter = aliases.iterator(); iter.hasNext(); ) {
                String element = (String) iter.next();
                typeVariableToTypeBinding.put(element, aliasTarget.typeVariables()[i++]);
            }
        }
        currentType = declaringType;
        MethodBinding mb = new MethodBinding(member.getModifiers(), member.getName().toCharArray(), makeTypeBinding(member.getReturnType()), makeTypeBindings(member.getParameterTypes()), makeReferenceBindings(member.getExceptions()), declaringType);
        if (tvbs != null)
            mb.typeVariables = tvbs;
        typeVariableToTypeBinding.clear();
        currentType = null;
        return mb;
    }

    /**
	 * Convert a bunch of type variables in one go, from AspectJ form to Eclipse form.
	 */
    //	private TypeVariableBinding[] makeTypeVariableBindings(UnresolvedType[] typeVariables) {
    //		int len = typeVariables.length;
    //		TypeVariableBinding[] ret = new TypeVariableBinding[len];
    //		for (int i = 0; i < len; i++) {
    //			ret[i] = makeTypeVariableBinding((TypeVariableReference)typeVariables[i]);
    //		}
    //		return ret;
    //	}
    private TypeVariableBinding[] makeTypeVariableBindingsFromAJTypeVariables(TypeVariable[] typeVariables) {
        int len = typeVariables.length;
        TypeVariableBinding[] ret = new TypeVariableBinding[len];
        for (int i = 0; i < len; i++) {
            ret[i] = makeTypeVariableBindingFromAJTypeVariable(typeVariables[i]);
        }
        return ret;
    }

    // only accessed through private methods in this class.  Ensures all type variables we encounter
    // map back to the same type binding - this is important later when Eclipse code is processing
    // a methodbinding trying to come up with possible bindings for the type variables.
    // key is currently the name of the type variable...is that ok?
    private Map typeVariableToTypeBinding = new HashMap();

    /**
	 * Converts from an TypeVariableReference to a TypeVariableBinding.  A TypeVariableReference
	 * in AspectJ world holds a TypeVariable and it is this type variable that is converted
	 * to the TypeVariableBinding.
	 */
    private TypeVariableBinding makeTypeVariableBinding(TypeVariableReference tvReference) {
        TypeVariable tv = tvReference.getTypeVariable();
        TypeVariableBinding tvBinding = (TypeVariableBinding) typeVariableToTypeBinding.get(tv.getName());
        if (currentType != null) {
            TypeVariableBinding tvb = currentType.getTypeVariable(tv.getName().toCharArray());
            if (tvb != null)
                return tvb;
        }
        if (tvBinding == null) {
            Binding declaringElement = null;
            // this will cause an infinite loop or NPE... not required yet luckily.
            //		  if (tVar.getDeclaringElement() instanceof Member) {
            //			declaringElement = makeMethodBinding((ResolvedMember)tVar.getDeclaringElement());
            //		  } else {
            //			declaringElement = makeTypeBinding((UnresolvedType)tVar.getDeclaringElement());
            //		  }
            tvBinding = new TypeVariableBinding(tv.getName().toCharArray(), declaringElement, tv.getRank());
            typeVariableToTypeBinding.put(tv.getName(), tvBinding);
            tvBinding.superclass = (ReferenceBinding) makeTypeBinding(tv.getUpperBound());
            tvBinding.firstBound = (ReferenceBinding) makeTypeBinding(tv.getFirstBound());
            if (tv.getAdditionalInterfaceBounds() == null) {
                tvBinding.superInterfaces = TypeVariableBinding.NoSuperInterfaces;
            } else {
                TypeBinding tbs[] = makeTypeBindings(tv.getAdditionalInterfaceBounds());
                ReferenceBinding[] rbs = new ReferenceBinding[tbs.length];
                for (int i = 0; i < tbs.length; i++) {
                    rbs[i] = (ReferenceBinding) tbs[i];
                }
                tvBinding.superInterfaces = rbs;
            }
        }
        return tvBinding;
    }

    private TypeVariableBinding makeTypeVariableBindingFromAJTypeVariable(TypeVariable tv) {
        TypeVariableBinding tvBinding = (TypeVariableBinding) typeVariableToTypeBinding.get(tv.getName());
        if (currentType != null) {
            TypeVariableBinding tvb = currentType.getTypeVariable(tv.getName().toCharArray());
            if (tvb != null)
                return tvb;
        }
        if (tvBinding == null) {
            Binding declaringElement = null;
            // this will cause an infinite loop or NPE... not required yet luckily.
            //		  if (tVar.getDeclaringElement() instanceof Member) {
            //			declaringElement = makeMethodBinding((ResolvedMember)tVar.getDeclaringElement());
            //		  } else {
            //			declaringElement = makeTypeBinding((UnresolvedType)tVar.getDeclaringElement());
            //		  }
            tvBinding = new TypeVariableBinding(tv.getName().toCharArray(), declaringElement, tv.getRank());
            typeVariableToTypeBinding.put(tv.getName(), tvBinding);
            tvBinding.superclass = (ReferenceBinding) makeTypeBinding(tv.getUpperBound());
            tvBinding.firstBound = (ReferenceBinding) makeTypeBinding(tv.getFirstBound());
            if (tv.getAdditionalInterfaceBounds() == null) {
                tvBinding.superInterfaces = TypeVariableBinding.NoSuperInterfaces;
            } else {
                TypeBinding tbs[] = makeTypeBindings(tv.getAdditionalInterfaceBounds());
                ReferenceBinding[] rbs = new ReferenceBinding[tbs.length];
                for (int i = 0; i < tbs.length; i++) {
                    rbs[i] = (ReferenceBinding) tbs[i];
                }
                tvBinding.superInterfaces = rbs;
            }
        }
        return tvBinding;
    }

    public MethodBinding makeMethodBindingForCall(Member member) {
        return new MethodBinding(member.getCallsiteModifiers(), member.getName().toCharArray(), makeTypeBinding(member.getReturnType()), makeTypeBindings(member.getParameterTypes()), new ReferenceBinding[0], (ReferenceBinding) makeTypeBinding(member.getDeclaringType()));
    }

    public void finishedCompilationUnit(CompilationUnitDeclaration unit) {
        if ((buildManager != null) && buildManager.doGenerateModel()) {
            AjBuildManager.getAsmHierarchyBuilder().buildStructureForCompilationUnit(unit, buildManager.getStructureModel(), buildManager.buildConfig);
        }
    }

    public void addTypeBinding(TypeBinding binding) {
        typexToBinding.put(fromBinding(binding), binding);
    }

    public Shadow makeShadow(ASTNode location, ReferenceContext context) {
        return EclipseShadow.makeShadow(this, location, context);
    }

    public Shadow makeShadow(ReferenceContext context) {
        return EclipseShadow.makeShadow(this, (ASTNode) context, context);
    }

    public void addSourceTypeBinding(SourceTypeBinding binding, CompilationUnitDeclaration unit) {
        TypeDeclaration decl = binding.scope.referenceContext;
        // Deal with the raw/basic type to give us an entry in the world type map
        UnresolvedType simpleTx = null;
        if (binding.isGenericType()) {
            simpleTx = UnresolvedType.forRawTypeName(getName(binding));
        } else if (binding.isLocalType()) {
            LocalTypeBinding ltb = (LocalTypeBinding) binding;
            if (ltb.constantPoolName() != null && ltb.constantPoolName().length > 0) {
                simpleTx = UnresolvedType.forSignature(new String(binding.signature()));
            } else {
                simpleTx = UnresolvedType.forName(getName(binding));
            }
        } else {
            simpleTx = UnresolvedType.forName(getName(binding));
        }
        ReferenceType name = getWorld().lookupOrCreateName(simpleTx);
        // pr125405
        if (!binding.isRawType() && !binding.isGenericType() && name.getTypekind() == TypeKind.RAW) {
            name.demoteToSimpleType();
        }
        EclipseSourceType t = new EclipseSourceType(name, this, binding, decl, unit);
        // give it the same delegate and link it to the raw type
        if (binding.isGenericType()) {
            // fully aware of any generics info
            UnresolvedType complexTx = fromBinding(binding);
            ResolvedType cName = world.resolve(complexTx, true);
            ReferenceType complexName = null;
            if (!cName.isMissing()) {
                complexName = (ReferenceType) cName;
                complexName = (ReferenceType) complexName.getGenericType();
                if (complexName == null)
                    complexName = new ReferenceType(complexTx, world);
            } else {
                complexName = new ReferenceType(complexTx, world);
            }
            name.setGenericType(complexName);
            complexName.setDelegate(t);
        }
        name.setDelegate(t);
        if (decl instanceof AspectDeclaration) {
            ((AspectDeclaration) decl).typeX = name;
            ((AspectDeclaration) decl).concreteName = t;
        }
        ReferenceBinding[] memberTypes = binding.memberTypes;
        for (int i = 0, length = memberTypes.length; i < length; i++) {
            addSourceTypeBinding((SourceTypeBinding) memberTypes[i], unit);
        }
    }

    // XXX this doesn't feel like it belongs here, but it breaks a hard dependency on
    // exposing AjBuildManager (needed by AspectDeclaration).
    public boolean isXSerializableAspects() {
        return xSerializableAspects;
    }

    public ResolvedMember fromBinding(MethodBinding binding) {
        return new ResolvedMemberImpl(Member.METHOD, fromBinding(binding.declaringClass), binding.modifiers, fromBinding(binding.returnType), CharOperation.charToString(binding.selector), fromBindings(binding.parameters));
    }

    public TypeVariableDeclaringElement fromBinding(Binding declaringElement) {
        if (declaringElement instanceof TypeBinding) {
            return fromBinding(((TypeBinding) declaringElement));
        } else {
            return fromBinding((MethodBinding) declaringElement);
        }
    }

    public void cleanup() {
        this.typexToBinding.clear();
        this.rawTypeXToBinding.clear();
        this.finishedTypeMungers = null;
    }
}
