copyright contributors rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors andy clement ibm initial implementation org aspectj weaver asm java ioexception java input stream inputstream java util array list arraylist java util collection java util collections java util iterator java util list org aspectj apache bcel classfile generic signature parser genericsignatureparser org aspectj apache bcel classfile signature org aspectj weaver abstract reference type delegate abstractreferencetypedelegate org aspectj weaver aj attribute ajattribute org aspectj weaver annotation aj annotationaj org aspectj weaver annotation target kind annotationtargetkind org aspectj weaver annotation x annotationx org aspectj weaver isource context isourcecontext org aspectj weaver reference type referencetype org aspectj weaver reference type delegate referencetypedelegate org aspectj weaver resolved member resolvedmember org aspectj weaver resolved member impl resolvedmemberimpl org aspectj weaver resolved pointcut definition resolvedpointcutdefinition org aspectj weaver resolved type resolvedtype org aspectj weaver source context impl sourcecontextimpl org aspectj weaver type variable typevariable org aspectj weaver unresolved type unresolvedtype org aspectj weaver weaver state info weaverstateinfo org aspectj weaver org aspectj weaver aj attribute ajattribute declare attribute declareattribute org aspectj weaver aj attribute ajattribute pointcut declaration attribute pointcutdeclarationattribute org aspectj weaver aj attribute ajattribute privileged attribute privilegedattribute org aspectj weaver aj attribute ajattribute source context attribute sourcecontextattribute org aspectj weaver aj attribute ajattribute type munger typemunger org aspectj weaver aj attribute ajattribute weaver state weaverstate org aspectj weaver aj attribute ajattribute weaver version info weaverversioninfo org aspectj weaver bcel bcel generic signature to type xconverter bcelgenericsignaturetotypexconverter org aspectj weaver bcel bcel generic signature to type xconverter bcelgenericsignaturetotypexconverter generic signature format exception genericsignatureformatexception org aspectj weaver patterns per clause perclause org aspectj org objectweb asm attribute org aspectj org objectweb asm class reader classreader org aspectj org objectweb asm opcodes lightweight fast delegate alternative bcel delegate type represented referenced compile weave exposed weaver java lang string unnecessary processed linenumbertable visiting isnt methods annotations implementation type populated asm class visitor classvisitor attributes annotations unpacked lazily author andy clement andyclement asm delegate asmdelegate abstract reference type delegate abstractreferencetypedelegate care about member annotations and attributes careaboutmemberannotationsandattributes class modifiers classmodifiers resolved pointcut definition resolvedpointcutdefinition pointcuts filled signature parsed type variable typevariable type variables typevariables resolved type resolvedtype annotation types annotationtypes annotation x annotationx annotation xs annotationxs list annotations collections empty list resolved member resolvedmember methods resolved member resolvedmember fields list attributes collections empty list collection declare declares collection concrete type munger concretetypemunger type mungers typemungers collection resolved member resolvedmember privileged accesses privilegedaccesses relevant bits bitflag guard lazy initialization pointcuts discovered pointcuts guard lazy initialization declares discovered declares guard lazy initialization type mungers discovered typemungers guard lazy initialization privileged access list discovered privilegedaccesses sourcecontext held supertype discovered sourcecontext discovered weaverstateinfo signature unpacked annotation types correct annotationx correct superset fieldsfixedup methodsfixedup resolved type resolvedtype superclass type superclasstype string superclass name superclassname resolved type resolvedtype interface types interfacetypes string interface names interfacenames fields based attribute annotation values eager visitor type lazy requests eager populated aspect attribute is aspect isaspect per clause perclause per clause perclause eager populated weaver version info weaverversioninfo attribute weaver version info weaverversioninfo weaver version weaverversion aj attribute ajattribute weaver version info weaverversioninfo unknown lazy populated weaver state info weaverstateinfo attribute weaver state info weaverstateinfo weaver state info weaverstateinfo eager populated visit inner class visitinnerclass method type visitor typevisitor is anonymous isanonymous is nested isnested eager populated visit method type visitor typevisitor is generic type isgenerictype string declared signature declaredsignature eager populated retention annotation is runtime retention isruntimeretention string retention policy retentionpolicy eager populated target annotation learn can annotation target type canannotationtargettype annotation target kind annotationtargetkind target kinds targetkinds asm delegate asmdelegate reference type referencetype input stream inputstream input stream inputstream get world getworld class reader classreader input stream inputstream accept type visitor typevisitor asm constants asmconstants aj attributes ajattributes input stream inputstream close remove synthetic class modifiers classmodifiers class modifiers classmodifiers remove deprecated class modifiers classmodifiers class modifiers classmodifiers ioexception ioe ioe print stack trace printstacktrace set source context setsourcecontext source context impl sourcecontextimpl is annotation style aspect isannotationstyleaspect can annotation target type canannotationtargettype can annotation target type canannotationtargettype annotation target kind annotationtargetkind get annotation target kinds getannotationtargetkinds target kinds targetkinds is generic isgeneric is generic type isgenerictype is anonymous isanonymous is anonymous isanonymous is nested isnested is nested isnested has annotation hasannotation unresolved type unresolvedtype of type oftype ensure annotations unpacked ensureannotationsunpacked annotation types annotationtypes length annotation types annotationtypes equals of type oftype annotation x annotationx get annotations getannotations ensure annotation xs unpacked ensureannotationxsunpacked annotation xs annotationxs resolved type resolvedtype get annotation types getannotationtypes ensure annotations unpacked ensureannotationsunpacked annotation types annotationtypes ensure annotation xs unpacked ensureannotationxsunpacked bitflag annotation types correct bitflag annotationx correct ensure annotations unpacked ensureannotationsunpacked annotations size annotation xs annotationxs annotation x annotationx annotation xs annotationxs annotation x annotationx annotations size pos iterator iter annotations iterator iter has next hasnext annotation aj annotationaj element annotation aj annotationaj iter annotation xs annotationxs pos annotation x annotationx element dont annotations bitflag annotationx correct ensure annotations unpacked ensureannotationsunpacked bitflag annotation types correct annotations size annotation types annotationtypes resolved type resolvedtype annotation types annotationtypes resolved type resolvedtype annotations size pos iterator iter annotations iterator iter has next hasnext annotation aj annotationaj element annotation aj annotationaj iter annotation types annotationtypes pos resolve unresolved type unresolvedtype for signature forsignature element get type signature gettypesignature bitflag annotation types correct signature formal type parameter formaltypeparameter get all formals getallformals ensure signature unpacked ensuresignatureunpacked formals for resolution formalsforresolution signature formal type parameter formaltypeparameter formals for resolution formalsforresolution testing attribute doesnt aj attribute ajattribute get attributes getattributes string list array list arraylist iterator iter attributes iterator iter has next hasnext attribute element attribute iter element type equals element aj asmattribute ajasmattribute add aj asmattribute ajasmattribute element unpack size aj attribute ajattribute to array toarray aj attribute ajattribute testing method string get attribute names getattributenames string strs string attributes size iterator iter attributes iterator iter has next hasnext attribute element attribute iter strs element type strs isource context isourcecontext get source context getsourcecontext bitflag discovered sourcecontext bitflag discovered sourcecontext attribute found it foundit iterator iter attributes iterator iter has next hasnext object iter aj asmattribute ajasmattribute aj asmattribute ajasmattribute element aj asmattribute ajasmattribute element type equals aj attribute ajattribute source context attribute sourcecontextattribute attribute name attributename found it foundit element source context attribute sourcecontextattribute sca source context attribute sourcecontextattribute aj asmattribute ajasmattribute element unpack get source context getsourcecontext source context impl sourcecontextimpl unknown source context set source context setsourcecontext source context impl sourcecontextimpl source context impl sourcecontextimpl get source context getsourcecontext configure from attribute configurefromattribute sca get source file name getsourcefilename sca get line breaks getlinebreaks save space found it foundit attributes remove found it foundit get source context getsourcecontext weaver state info weaverstateinfo get weaver state getweaverstate bitflag discovered weaverstateinfo iterator iter attributes iterator iter has next hasnext object iter aj asmattribute ajasmattribute aj asmattribute ajasmattribute element aj asmattribute ajasmattribute element type equals aj attribute ajattribute weaver state weaverstate attribute name attributename weaver state weaverstate ws info wsinfo weaver state weaverstate aj asmattribute ajasmattribute element unpack weaver state info weaverstateinfo ws info wsinfo reify bitflag discovered weaverstateinfo weaver state info weaverstateinfo string get declared generic signature getdeclaredgenericsignature declared signature declaredsignature collection get type mungers gettypemungers bitflag discovered typemungers type mungers typemungers array list arraylist iterator iter attributes iterator iter has next hasnext object iter aj asmattribute ajasmattribute aj asmattribute ajasmattribute element aj asmattribute ajasmattribute element type equals aj attribute ajattribute type munger typemunger attribute name attributename type munger typemunger type munger typemunger type munger typemunger aj asmattribute ajasmattribute element unpack type mungers typemungers add type munger typemunger reify get resolved type x getresolvedtypex bitflag discovered typemungers type mungers typemungers collection get privileged accesses getprivilegedaccesses bitflag discovered privilegedaccesses privileged accesses privilegedaccesses array list arraylist iterator iter attributes iterator iter has next hasnext object iter aj asmattribute ajasmattribute aj asmattribute ajasmattribute element aj asmattribute ajasmattribute element type equals aj attribute ajattribute privileged attribute privilegedattribute attribute name attributename privileged attribute privilegedattribute privileged attribute privilegedattribute privileged attribute privilegedattribute aj asmattribute ajasmattribute element unpack resolved member resolvedmember pas privileged attribute privilegedattribute get accessed members getaccessedmembers pas length privileged accesses privilegedaccesses add pas bitflag discovered privilegedaccesses privileged accesses privilegedaccesses type variable typevariable get type variables gettypevariables ensure signature unpacked ensuresignatureunpacked type variables typevariables signature formal type parameter formaltypeparameter formals for resolution formalsforresolution ensure signature unpacked ensuresignatureunpacked bitflag signature unpacked type variables typevariables type variable typevariable get resolved type x getresolvedtypex get world getworld is in java isinjava mode bitflag signature unpacked declared signature declaredsignature generic signature parser genericsignatureparser parser generic signature parser genericsignatureparser signature class signature classsignature c sig csig parser parse as class signature parseasclasssignature declared signature declaredsignature type variables typevariables type variable typevariable c sig csig formal type parameters formaltypeparameters length type variables typevariables length signature formal type parameter formaltypeparameter ftp c sig csig formal type parameters formaltypeparameters type variables typevariables bcel generic signature to type xconverter bcelgenericsignaturetotypexconverter formal type parameter formaltypeparameter type variable typevariable ftp c sig csig formal type parameters formaltypeparameters get resolved type x getresolvedtypex get world getworld generic signature format exception genericsignatureformatexception illegal state exception illegalstateexception type variables type to string tostring signature c sig csig error condition detected get message getmessage c sig csig formals for resolution formalsforresolution c sig csig formal type parameters formaltypeparameters is nested isnested find type variables type proceeding resolution signature formal type parameter formaltypeparameter extra formals extraformals get formal type parameters from outer class getformaltypeparametersfromouterclass extra formals extraformals length list all formals allformals array list arraylist formals for resolution formalsforresolution length all formals allformals add formals for resolution formalsforresolution extra formals extraformals length all formals allformals add extra formals extraformals formals for resolution formalsforresolution signature formal type parameter formaltypeparameter all formals allformals size all formals allformals to array toarray formals for resolution formalsforresolution signature class type signature classtypesignature super sig supersig c sig csig superclass signature superclasssignature superclass type superclasstype bcel generic signature to type xconverter bcelgenericsignaturetotypexconverter class type signature classtypesignature type x typex super sig supersig formals for resolution formalsforresolution get resolved type x getresolvedtypex get world getworld bitflag superset generic signature format exception genericsignatureformatexception illegal state exception illegalstateexception determing superclass get resolved type x getresolvedtypex signature declared signature declaredsignature error detected get message getmessage interface types interfacetypes resolved type resolvedtype c sig csig super interface signatures superinterfacesignatures length c sig csig super interface signatures superinterfacesignatures length interface types interfacetypes bcel generic signature to type xconverter bcelgenericsignaturetotypexconverter class type signature classtypesignature type x typex c sig csig super interface signatures superinterfacesignatures formals for resolution formalsforresolution get resolved type x getresolvedtypex get world getworld generic signature format exception genericsignatureformatexception illegal state exception illegalstateexception determing superinterfaces get resolved type x getresolvedtypex signature declared signature declaredsignature error detected get message getmessage is generic isgeneric update resolved typex point type raw type reference type referencetype generic type generictype reference type referencetype resolved type x resolvedtypex get generic type getgenerictype generic type generictype set source context setsourcecontext resolved type x resolvedtypex get source context getsourcecontext generic type generictype set start pos setstartpos resolved type x resolvedtypex get start pos getstartpos resolved type x resolvedtypex generic type generictype bitflag signature unpacked reference type referencetype get outer class getouterclass is nested isnested illegal state exception illegalstateexception nested type last dollar lastdollar get resolved type x getresolvedtypex get name getname last index of lastindexof string super class name superclassname get resolved type x getresolvedtypex get name getname substring last dollar lastdollar unresolved type unresolvedtype unresolved type unresolvedtype for name forname super class name superclassname reference type referencetype resolve get resolved type x getresolvedtypex get world getworld signature formal type parameter formaltypeparameter get formal type parameters from outer class getformaltypeparametersfromouterclass list type parameters typeparameters array list arraylist reference type referencetype get outer class getouterclass reference type delegate referencetypedelegate outer delegate outerdelegate get delegate getdelegate outer delegate outerdelegate asm delegate asmdelegate illegal state exception illegalstateexception asm object type asmobjecttype resolving type asm object type asmobjecttype asm delegate asmdelegate outer object type outerobjecttype asm delegate asmdelegate outer delegate outerdelegate outer object type outerobjecttype is nested isnested signature formal type parameter formaltypeparameter parent params parentparams outer object type outerobjecttype get formal type parameters from outer class getformaltypeparametersfromouterclass parent params parentparams length type parameters typeparameters add parent params parentparams generic signature parser genericsignatureparser parser generic signature parser genericsignatureparser string sig outer object type outerobjecttype get declared generic signature getdeclaredgenericsignature sig signature class signature classsignature outer sig outersig parser parse as class signature parseasclasssignature sig outer sig outersig outer sig outersig formal type parameters formaltypeparameters length type parameters typeparameters add outer sig outersig formal type parameters formaltypeparameters signature formal type parameter formaltypeparameter ret signature formal type parameter formaltypeparameter type parameters typeparameters size type parameters typeparameters to array toarray ret ret is interface isinterface class modifiers classmodifiers opcodes acc string get retention policy getretentionpolicy retention policy retentionpolicy is annotation with runtime retention isannotationwithruntimeretention is runtime retention isruntimeretention is annotation isannotation class modifiers classmodifiers opcodes acc annotation is enum isenum class modifiers classmodifiers opcodes acc enum get modifiers getmodifiers class modifiers classmodifiers resolved member resolvedmember get declared fields getdeclaredfields ensure signature unpacked ensuresignatureunpacked bitflag fieldsfixedup fields length resolved member impl resolvedmemberimpl fields set declaring type setdeclaringtype get resolved type x getresolvedtypex bitflag fieldsfixedup fields resolved type resolvedtype get declared interfaces getdeclaredinterfaces interface types interfacetypes interface names interfacenames interface names interfacenames length interface types interfacetypes resolved type resolvedtype interface types interfacetypes resolved type resolvedtype interface names interfacenames length interface names interfacenames length interface types interfacetypes resolve interface names interfacenames replace interface names interfacenames ensure signature unpacked ensuresignatureunpacked interface types interfacetypes resolved member resolvedmember get declared methods getdeclaredmethods ensure signature unpacked ensuresignatureunpacked bitflag methodsfixedup methods length resolved member impl resolvedmemberimpl methods set declaring type setdeclaringtype get resolved type x getresolvedtypex bitflag methodsfixedup methods resolved member resolvedmember get declared pointcuts getdeclaredpointcuts bitflag discovered pointcuts list pcts array list arraylist list for removal forremoval array list arraylist iterator iter attributes iterator iter has next hasnext object iter aj asmattribute ajasmattribute aj asmattribute ajasmattribute element aj asmattribute ajasmattribute element type equals aj attribute ajattribute pointcut declaration attribute pointcutdeclarationattribute attribute name attributename pointcut declaration attribute pointcutdeclarationattribute pointcut pointcut declaration attribute pointcutdeclarationattribute aj asmattribute ajasmattribute element unpack pcts add pointcut reify for removal forremoval add element pointcuts resolved pointcut definition resolvedpointcutdefinition pcts to array toarray resolved pointcut definition resolvedpointcutdefinition attributes remove all removeall for removal forremoval bitflag discovered pointcuts pointcuts collection get declares getdeclares bitflag discovered declares declares array list arraylist list for removal forremoval array list arraylist iterator iter attributes iterator iter has next hasnext object iter aj asmattribute ajasmattribute aj asmattribute ajasmattribute element aj asmattribute ajasmattribute element type equals aj attribute ajattribute declare attribute declareattribute attribute name attributename declare attribute declareattribute declare declare attribute declareattribute aj asmattribute ajasmattribute element unpack declares add declare get declare getdeclare for removal forremoval add element attributes remove all removeall for removal forremoval discovered declares discovereddeclares bitflag discovered declares declares resolved type resolvedtype get superclass getsuperclass bitflag superset superclass name superclassname type jl object jlobject superclass type superclasstype superclass type superclasstype resolve superclass name superclassname replace ensure signature unpacked ensuresignatureunpacked superclass name superclassname bitflag superset superclass type superclasstype per clause perclause get per clause getperclause per clause perclause is aspect isaspect is aspect isaspect get world getworld feb asm delegate asmdelegate types won woven target annotations add annotation addannotation annotation x annotationx annotation x annotationx method left blank purpose ensure delegate consistent ensuredelegateconsistent doesnt methods add annotation addannotation implemented methods modify delegate differs disk contents