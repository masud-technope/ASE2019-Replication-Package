copyright palo alto center incorporated parc rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors parc initial implementation org aspectj ajdt internal compiler ast java lang reflect modifier org aspectj ajdt internal compiler lookup eclipse factory eclipsefactory org aspectj ajdt internal compiler lookup eclipse type munger eclipsetypemunger org aspectj org eclipse jdt internal compiler class file classfile org aspectj org eclipse jdt internal compiler compilation result compilationresult org aspectj org eclipse jdt internal compiler ast argument org aspectj org eclipse jdt internal compiler ast array allocation expression arrayallocationexpression org aspectj org eclipse jdt internal compiler ast array initializer arrayinitializer org aspectj org eclipse jdt internal compiler ast compilation unit declaration compilationunitdeclaration org aspectj org eclipse jdt internal compiler ast expression org aspectj org eclipse jdt internal compiler ast return statement returnstatement org aspectj org eclipse jdt internal compiler ast statement org aspectj org eclipse jdt internal compiler ast type reference typereference org aspectj org eclipse jdt internal compiler codegen code stream codestream org aspectj org eclipse jdt internal compiler lookup array binding arraybinding org aspectj org eclipse jdt internal compiler lookup class scope classscope org aspectj org eclipse jdt internal compiler lookup field binding fieldbinding org aspectj org eclipse jdt internal compiler lookup method binding methodbinding org aspectj org eclipse jdt internal compiler lookup type binding typebinding org aspectj org eclipse jdt internal compiler parser parser org aspectj weaver aj attribute ajattribute org aspectj weaver ajc member maker ajcmembermaker org aspectj weaver member org aspectj weaver name mangler namemangler org aspectj weaver new field type munger newfieldtypemunger org aspectj weaver resolved member resolvedmember org aspectj weaver resolved member impl resolvedmemberimpl org aspectj weaver resolved type resolvedtype org aspectj weaver shadow org aspectj weaver unresolved type unresolvedtype inter type field declaration return type returntype encodes type field selector encodes statements resolution filled initializer author jim hugunin inter type field declaration intertypefielddeclaration inter type declaration intertypedeclaration expression initialization type binding typebinding real field type realfieldtype inter type field declaration intertypefielddeclaration compilation result compilationresult result type reference typereference on type ontype result on type ontype parse statements parsestatements parser parser compilation unit declaration compilationunitdeclaration unit don body parse get prefix getprefix name mangler namemangler prefix inter field interfield to char array tochararray resolve on type resolveontype class scope classscope class scope classscope resolve on type resolveontype class scope classscope ignore further investigation ignorefurtherinvestigation modifier is static isstatic declared modifiers declaredmodifiers on type binding ontypebinding is interface isinterface scope problem reporter problemreporter signal error signalerror source start sourcestart source end sourceend inter type field supported ignore further investigation ignorefurtherinvestigation modifier is static isstatic declared modifiers declaredmodifiers type variable aliases typevariablealiases type variable aliases typevariablealiases size on type binding ontypebinding is generic type isgenerictype scope problem reporter problemreporter signal error signalerror source start sourcestart source end sourceend intertype field declarations refer type variables target type resolve class scope classscope upper scope upperscope munger ignore further investigation ignorefurtherinvestigation ignore further investigation ignorefurtherinvestigation eclipse factory eclipsefactory eclipse factory eclipsefactory from scope lookup environment fromscopelookupenvironment upper scope upperscope resolved member resolvedmember sig munger get signature getsignature unresolved type unresolvedtype aspect type aspecttype from binding frombinding upper scope upperscope reference context referencecontext binding sig get return type getreturntype resolved type resolvedtype sig get return type getreturntype is array isarray sig get return type getreturntype get component type getcomponenttype resolved type resolvedtype upper scope upperscope problem reporter problemreporter signal error signalerror source start sourcestart source end sourceend field type initialization initialization array initializer arrayinitializer system err println initializer initialization array allocation expression arrayallocationexpression aae array allocation expression arrayallocationexpression aae initializer array initializer arrayinitializer initialization array binding arraybinding array type arraytype array binding arraybinding make type binding maketypebinding sig get return type getreturntype aae type ast util astutil make type reference maketypereference array type arraytype leaf component type leafcomponenttype aae source start sourcestart initialization source start sourcestart aae source end sourceend initialization source end sourceend aae dimensions expression array type arraytype dimensions initialization aae initialization statements statement return statement returnstatement on type binding ontypebinding is interface isinterface method binding methodbinding write method writemethod make method binding makemethodbinding ajc member maker ajcmembermaker inter field set dispatcher interfieldsetdispatcher sig aspect type aspecttype munger get type variable aliases gettypevariablealiases method casts shadow field join point modifier is static isstatic declared modifiers declaredmodifiers statements statement known message send knownmessagesend write method writemethod ast util astutil make name reference makenamereference write method writemethod declaring class declaringclass expression initialization statements statement known message send knownmessagesend write method writemethod ast util astutil make name reference makenamereference write method writemethod declaring class declaringclass expression ast util astutil make local variable reference makelocalvariablereference arguments binding initialization xxx broken logic write fields method binding methodbinding write method writemethod make method binding makemethodbinding ajc member maker ajcmembermaker inter field interface setter interfieldinterfacesetter sig sig get declaring type getdeclaringtype resolve get world getworld aspect type aspecttype munger get type variable aliases gettypevariablealiases modifier is static isstatic declared modifiers declaredmodifiers statements statement known message send knownmessagesend write method writemethod ast util astutil make name reference makenamereference write method writemethod declaring class declaringclass expression initialization statements statement known message send knownmessagesend write method writemethod ast util astutil make local variable reference makelocalvariablereference arguments binding expression initialization resolve upper scope upperscope set initialization setinitialization expression initialization initialization initialization resolve statements resolvestatements resolve statements resolvestatements initialization method scope methodscope initialization scope initializationscope scope type binding typebinding field type fieldtype real field type realfieldtype type binding typebinding initialization type initializationtype initialization set expected type setexpectedtype field type fieldtype needed method invocation initialization array initializer arrayinitializer initialization type initializationtype initialization resolve type expecting resolvetypeexpecting initialization scope initializationscope field type fieldtype array initializer arrayinitializer initialization binding array binding arraybinding initialization type initializationtype initialization compute conversion computeconversion initialization scope initializationscope field type fieldtype initialization type initializationtype system err println initialization system err println sasuages initialization resolved type resolvedtype initialization type initializationtype initialization resolve type resolvetype initialization scope initializationscope system err println scope initialization scope initializationscope initialization type initializationtype initialization resolve type resolvetype initialization scope initializationscope field type fieldtype initialization type initializationtype call compute conversion computeconversion type mismatch error typemismatcherror initialization scope initializationscope compilation unit scope compilationunitscope record type conversion recordtypeconversion field type fieldtype initialization type initializationtype initialization is constant value of type assignable to type isconstantvalueoftypeassignabletotype initialization type initializationtype field type fieldtype field type fieldtype is base type isbasetype base type binding basetypebinding is widening iswidening field type fieldtype initialization type initializationtype initialization type initializationtype is compatible with iscompatiblewith field type fieldtype initialization compute conversion computeconversion initialization scope initializationscope field type fieldtype initialization type initializationtype initialization type initializationtype needs unchecked conversion needsuncheckedconversion field type fieldtype initialization scope initializationscope problem reporter problemreporter unsafe type conversion unsafetypeconversion initialization initialization type initializationtype field type fieldtype initialization scope initializationscope is boxing compatible with isboxingcompatiblewith initialization type initializationtype field type fieldtype initialization type initializationtype is base type isbasetype narrowing boxing initialization scope initializationscope compiler options compileroptions source level sourcelevel jdk autoboxing field type fieldtype is base type isbasetype initialization is constant value of type assignable to type isconstantvalueoftypeassignabletotype initialization type initializationtype initialization scope initializationscope environment compute boxing type computeboxingtype field type fieldtype initialization compute conversion computeconversion initialization scope initializationscope field type fieldtype initialization type initializationtype initialization scope initializationscope problem reporter problemreporter type mismatch error typemismatcherror initialization type initializationtype field type fieldtype binding is final isfinal constant actual type variable type binding set constant setconstant initialization constant cast to castto binding return type returntype initialization constant type id typeid binding set constant setconstant not aconstant notaconstant eclipse type munger eclipsetypemunger build class scope classscope class scope classscope eclipse factory eclipsefactory eclipse factory eclipsefactory from scope lookup environment fromscopelookupenvironment class scope classscope resolve on type resolveontype class scope classscope ignore further investigation ignorefurtherinvestigation binding class scope classscope reference context referencecontext binding resolve types for resolvetypesfor binding ignore further investigation ignorefurtherinvestigation error message output is target annotation istargetannotation is target annotation istargetannotation class scope classscope field error message output is target enum istargetenum is target enum istargetenum class scope classscope field modifier is static isstatic declared modifiers declaredmodifiers binding parameters type binding typebinding on type binding ontypebinding arguments argument ast util astutil make final argument makefinalargument ajc to char array tochararray on type binding ontypebinding system err println type binding return type returntype return type returntype resolved type resolvedtype declaring type declaringtype from binding frombinding on type binding ontypebinding resolve get world getworld declaring type declaringtype is raw type israwtype declaring type declaringtype is parameterized type isparameterizedtype declaring type declaringtype declaring type declaringtype get generic type getgenerictype encountered problem building scope don error reported inter type scope intertypescope build correct resolvedmember make resolved member makeresolvedmember understands tvars build fully correct sig resolved member resolvedmember sigtemp make resolved member for itd makeresolvedmemberforitd binding on type binding ontypebinding inter type scope intertypescope get recovery aliases getrecoveryaliases resolved member resolvedmember sig resolved member impl resolvedmemberimpl member field declaring type declaringtype declared modifiers declaredmodifiers sigtemp get return type getreturntype string declared selector declaredselector unresolved type unresolvedtype sig set type variables settypevariables sigtemp get type variables gettypevariables new field type munger newfieldtypemunger my munger mymunger new field type munger newfieldtypemunger sig type variable aliases typevariablealiases set munger setmunger my munger mymunger resolved type resolvedtype aspect type aspecttype from eclipse fromeclipse class scope classscope reference context referencecontext binding resolved member resolvedmember my munger mymunger get init method getinitmethod aspect type aspecttype selector binding selector get name getname to char array tochararray real field type realfieldtype binding return type returntype binding return type returntype type binding typebinding void binding voidbinding eclipse type munger eclipsetypemunger my munger mymunger aspect type aspecttype aj attribute ajattribute make attribute makeattribute aj attribute ajattribute type munger typemunger munger generate code generatecode class scope classscope class scope classscope class file classfile class file classfile ignore further investigation ignorefurtherinvestigation class file classfile extra attributes extraattributes add eclipse attribute adapter eclipseattributeadapter make attribute makeattribute generate code generatecode class scope classscope class file classfile generate dispatch methods generatedispatchmethods class scope classscope class file classfile inter binding interbinding reader generate method generatemethod class scope classscope class file classfile inter binding interbinding writer generate method generatemethod class scope classscope class file classfile generate dispatch methods generatedispatchmethods class scope classscope class scope classscope class file classfile class file classfile eclipse factory eclipsefactory eclipse factory eclipsefactory from scope lookup environment fromscopelookupenvironment class scope classscope resolved member resolvedmember sig munger get signature getsignature unresolved type unresolvedtype aspect type aspecttype from binding frombinding class scope classscope reference context referencecontext binding generate dispatch method generatedispatchmethod sig aspect type aspecttype class scope classscope class file classfile generate dispatch method generatedispatchmethod sig aspect type aspecttype class scope classscope class file classfile generate dispatch method generatedispatchmethod eclipse factory eclipsefactory resolved member resolvedmember sig unresolved type unresolvedtype aspect type aspecttype class scope classscope class scope classscope class file classfile class file classfile is getter isgetter method binding methodbinding binding is getter isgetter binding make method binding makemethodbinding ajc member maker ajcmembermaker inter field get dispatcher interfieldgetdispatcher sig aspect type aspecttype munger get type variable aliases gettypevariablealiases munger get signature getsignature get declaring type getdeclaringtype binding make method binding makemethodbinding ajc member maker ajcmembermaker inter field set dispatcher interfieldsetdispatcher sig aspect type aspecttype munger get type variable aliases gettypevariablealiases munger get signature getsignature get declaring type getdeclaringtype class file classfile generate method info header generatemethodinfoheader binding method attribute offset methodattributeoffset class file classfile contents offset contentsoffset attribute number attributenumber class file classfile generate method info attribute generatemethodinfoattribute binding make effective signature attribute makeeffectivesignatureattribute sig is getter isgetter shadow field get fieldget shadow field set fieldset code attribute offset codeattributeoffset class file classfile contents offset contentsoffset class file classfile generate code attribute header generatecodeattributeheader code stream codestream code stream codestream class file classfile code stream codestream code stream codestream reset class file classfile field binding fieldbinding class field classfield make field binding makefieldbinding ajc member maker ajcmembermaker inter field class field interfieldclassfield sig aspect type aspecttype munger get type variable aliases gettypevariablealiases code stream codestream initialize max locals initializemaxlocals binding is getter isgetter on type binding ontypebinding is interface isinterface unresolved type unresolvedtype declaring tx declaringtx sig get declaring type getdeclaringtype resolved type resolvedtype declaring rtx declaringrtx get world getworld resolve declaring tx declaringtx munger get source location getsourcelocation method binding methodbinding read method readmethod make method binding makemethodbinding ajc member maker ajcmembermaker inter field interface getter interfieldinterfacegetter sig declaring rtx declaringrtx aspect type aspecttype munger get type variable aliases gettypevariablealiases generate interface read body generateinterfacereadbody binding read method readmethod code stream codestream generate class read body generateclassreadbody binding class field classfield code stream codestream on type binding ontypebinding is interface isinterface method binding methodbinding write method writemethod make method binding makemethodbinding ajc member maker ajcmembermaker inter field interface setter interfieldinterfacesetter sig get world getworld resolve sig get declaring type getdeclaringtype munger get source location getsourcelocation aspect type aspecttype munger get type variable aliases gettypevariablealiases generate interface write body generateinterfacewritebody binding write method writemethod code stream codestream generate class write body generateclasswritebody binding class field classfield code stream codestream ast util astutil generate return generatereturn binding return type returntype code stream codestream class file classfile complete code attribute completecodeattribute code attribute offset codeattributeoffset attribute number attributenumber class file classfile complete method info completemethodinfo method attribute offset methodattributeoffset attribute number attributenumber generate interface read body generateinterfacereadbody method binding methodbinding binding method binding methodbinding read method readmethod code stream codestream code stream codestream code stream codestream aload code stream codestream invokeinterface read method readmethod generate interface write body generateinterfacewritebody method binding methodbinding binding method binding methodbinding write method writemethod code stream codestream code stream codestream code stream codestream aload code stream codestream load write method writemethod parameters code stream codestream invokeinterface write method writemethod generate class read body generateclassreadbody method binding methodbinding binding field binding fieldbinding field code stream codestream code stream codestream field is static isstatic code stream codestream getstatic field code stream codestream aload code stream codestream getfield field generate class write body generateclasswritebody method binding methodbinding binding field binding fieldbinding field code stream codestream code stream codestream field is static isstatic code stream codestream load field type code stream codestream putstatic field code stream codestream aload code stream codestream load field type code stream codestream putfield field shadow kind get shadow kind for body getshadowkindforbody