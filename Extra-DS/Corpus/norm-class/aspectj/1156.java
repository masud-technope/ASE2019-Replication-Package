copyright palo alto center incorporated parc rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors parc initial implementation org aspectj weaver java data input stream datainputstream java ioexception java util array list arraylist java util collections java util hash set hashset java util iterator java util list java util org aspectj asm irelationship org aspectj bridge imessage org aspectj bridge isource location isourcelocation org aspectj bridge message org aspectj bridge message util messageutil org aspectj bridge weave message weavemessage org aspectj lang join point joinpoint org aspectj util partial order partialorder org aspectj util type safe enum typesafeenum org aspectj weaver ast org aspectj weaver bcel bcel advice bceladvice shadow shadow unique doesn matter wraps easier spot next shadow id nextshadowid kind kind member signature member matching signature matchingsignature resolved member resolvedmember resolved signature resolvedsignature shadow enclosing shadow enclosingshadow list mungers collections empty list time build shadow shadow id shadowid next shadow id nextshadowid shadow kind kind member signature shadow enclosing shadow enclosingshadow kind kind signature signature enclosing shadow enclosingshadow enclosing shadow enclosingshadow get iworld getiworld list get mungers getmungers shadow munger shadowmunger mungers pcd match has this hasthis get kind getkind never has this neverhasthis get kind getkind is enclosing kind isenclosingkind get signature getsignature is static isstatic enclosing shadow enclosingshadow enclosing shadow enclosingshadow has this hasthis type object illegal state exception illegalstateexception unresolved type unresolvedtype get this type getthistype has this hasthis illegal state exception illegalstateexception get kind getkind is enclosing kind isenclosingkind get signature getsignature get declaring type getdeclaringtype enclosing shadow enclosingshadow get this type getthistype referencing illegal state exception illegalstateexception target get this var getthisvar target pcd match has target hastarget get kind getkind never has target neverhastarget get kind getkind is target same as this istargetsameasthis has this hasthis get signature getsignature is static isstatic type target object illegal state exception illegalstateexception target unresolved type unresolvedtype get target type gettargettype has target hastarget illegal state exception illegalstateexception target get signature getsignature get declaring type getdeclaringtype referencing target illegal state exception illegalstateexception target get target var gettargetvar unresolved type unresolvedtype get arg types getargtypes get kind getkind field set fieldset unresolved type unresolvedtype get signature getsignature get return type getreturntype get signature getsignature get parameter types getparametertypes is shadow for array construction joinpoint isshadowforarrayconstructionjoinpoint get kind getkind constructor call constructorcall signature get declaring type getdeclaringtype is array isarray length array ints depending dimensions array resolved type resolvedtype get argument types for array construction shadow getargumenttypesforarrayconstructionshadow string signature get declaring type getdeclaringtype get signature getsignature pos index of indexof dims pos length pos pos length dims char at charat pos dims resolved type resolvedtype resolved type resolvedtype resolved type resolvedtype some ints someints resolved type resolvedtype dims dims some ints someints resolved type resolvedtype some ints someints unresolved type unresolvedtype get generic arg types getgenericargtypes is shadow for array construction joinpoint isshadowforarrayconstructionjoinpoint get argument types for array construction shadow getargumenttypesforarrayconstructionshadow get kind getkind field set fieldset unresolved type unresolvedtype get resolved signature getresolvedsignature get generic return type getgenericreturntype get resolved signature getresolvedsignature get generic parameter types getgenericparametertypes unresolved type unresolvedtype get arg type getargtype arg get kind getkind field set fieldset get signature getsignature get return type getreturntype get signature getsignature get parameter types getparametertypes arg get arg count getargcount get kind getkind field set fieldset get signature getsignature get parameter types getparametertypes length unresolved type unresolvedtype get enclosing type getenclosingtype get arg var getargvar get this join point var getthisjoinpointvar get this join point static part var getthisjoinpointstaticpartvar get this enclosing join point static part var getthisenclosingjoinpointstaticpartvar annotation variables get kinded annotation var getkindedannotationvar unresolved type unresolvedtype for annotation type forannotationtype get within annotation var getwithinannotationvar unresolved type unresolvedtype for annotation type forannotationtype get within code annotation var getwithincodeannotationvar unresolved type unresolvedtype for annotation type forannotationtype get this annotation var getthisannotationvar unresolved type unresolvedtype for annotation type forannotationtype get target annotation var gettargetannotationvar unresolved type unresolvedtype for annotation type forannotationtype get arg annotation var getargannotationvar unresolved type unresolvedtype for annotation type forannotationtype member get enclosing code signature getenclosingcodesignature returns kind shadow representing shadow kind get kind getkind kind returns signature shadow member get signature getsignature signature returns signature shadow synthetic arguments removed member get matching signature getmatchingsignature matching signature matchingsignature matching signature matchingsignature signature set matching signature setmatchingsignature member member matching signature matchingsignature member returns resolved signature shadow resolved member resolvedmember get resolved signature getresolvedsignature resolved signature resolvedsignature resolved signature resolvedsignature signature resolve get iworld getiworld resolved signature resolvedsignature unresolved type unresolvedtype get return type getreturntype kind constructor call constructorcall get signature getsignature get declaring type getdeclaringtype kind field set fieldset resolved type resolvedtype get resolved signature getresolvedsignature get generic return type getgenericreturntype names returned this join point thisjoinpoint get kind getkind documented kind method call methodcall kind join point joinpoint method call kind constructor call constructorcall kind join point joinpoint constructor call kind method execution methodexecution kind join point joinpoint method execution kind constructor execution constructorexecution kind join point joinpoint constructor execution kind field get fieldget kind join point joinpoint field kind field set fieldset kind join point joinpoint field kind static initialization staticinitialization kind join point joinpoint staticinitialization kind pre initialization preinitialization kind join point joinpoint preinitialization kind advice execution adviceexecution kind join point joinpoint advice execution kind initialization kind join point joinpoint initialization kind exception handler exceptionhandler kind join point joinpoint exception handler bits kind get key getkey didn start bits start method call bit methodcallbit constructor call bit constructorcallbit method execution bit methodexecutionbit constructor execution bit constructorexecutionbit field get bit fieldgetbit field set bit fieldsetbit static initialization bit staticinitializationbit pre initialization bit preinitializationbit advice execution bit adviceexecutionbit initialization bit initializationbit exception handler bit exceptionhandlerbit max shadow kind kind shadow kinds kind method call methodcall constructor call constructorcall method execution methodexecution constructor execution constructorexecution field get fieldget field set fieldset static initialization staticinitialization pre initialization preinitialization advice execution adviceexecution initialization exception handler exceptionhandler shadow kinds bits shadow kinds bits shadow kinds bits xffe shadow kinds bits count bits supplied parameter how many howmany count shadow kinds length shadow kinds bit count count type safe enum representing kind shadows kind type safe enum typesafeenum args on stack argsonstack xxx unused bit kind string key args on stack argsonstack key bit key args on stack argsonstack args on stack argsonstack string to legal java identifier tolegaljavaidentifier get name getname replace args on stack argsonstack is target same as this istargetsameasthis handlers allows extraction allowsextraction is set isset bit xxx revisit removal priorities has high priority exceptions hashighpriorityexceptions is target same as this istargetsameasthis has return value flag hasreturnvalueflag method call bit methodcallbit constructor call bit constructorcallbit method execution bit methodexecutionbit field get bit fieldgetbit advice execution bit adviceexecutionbit shadow kinds values bound returning dooberry doo advice has return value hasreturnvalue bit has return value flag hasreturnvalueflag is enclosing kind flag isenclosingkindflag method execution bit methodexecutionbit constructor execution bit constructorexecutionbit advice execution bit adviceexecutionbit static initialization bit staticinitializationbit initialization bit initializationbit shadows shadows methods is enclosing kind isenclosingkind bit is enclosing kind flag isenclosingkindflag is target same as this flag istargetsameasthisflag method execution bit methodexecutionbit constructor execution bit constructorexecutionbit static initialization bit staticinitializationbit pre initialization bit preinitializationbit advice execution bit adviceexecutionbit initialization bit initializationbit is target same as this istargetsameasthis bit is target same as this flag istargetsameasthisflag never has target flag neverhastargetflag constructor call bit constructorcallbit exception handler bit exceptionhandlerbit pre initialization bit preinitializationbit static initialization bit staticinitializationbit never has target neverhastarget bit never has target flag neverhastargetflag never has this flag neverhasthisflag pre initialization bit preinitializationbit static initialization bit staticinitializationbit never has this neverhasthis bit never has this flag neverhasthisflag string get simple name getsimplename dash get name getname last index of lastindexof dash get name getname get name getname substring dash kind read data input stream datainputstream ioexception key read byte readbyte key method call methodcall constructor call constructorcall method execution methodexecution constructor execution constructorexecution field get fieldget field set fieldset static initialization staticinitialization pre initialization preinitialization advice execution adviceexecution initialization exception handler exceptionhandler bcexception unknown kind key check munger requires aspects don param munger check munger checkmunger shadow munger shadowmunger munger munger must check exceptions mustcheckexceptions iterator munger get thrown exceptions getthrownexceptions iterator has next hasnext check can throw checkcanthrow munger resolved type resolvedtype check can throw checkcanthrow shadow munger shadowmunger munger resolved type resolvedtype resolved type x resolvedtypex get kind getkind exception handler exceptionhandler xxx lenient rules walk exception handlers is declared exception isdeclaredexception resolved type x resolvedtypex get signature getsignature get iworld getiworld show message showmessage imessage error advice munger weaver messages weavermessages format weaver messages weavermessages checked resolved type x resolvedtypex get source location getsourcelocation munger get source location getsourcelocation is declared exception isdeclaredexception resolved type resolvedtype resolved type x resolvedtypex member member resolved type resolvedtype excs get iworld getiworld resolve member get exceptions getexceptions get iworld getiworld len excs length len excs is assignable from isassignablefrom resolved type x resolvedtypex add munger addmunger shadow munger shadowmunger munger check munger checkmunger munger mungers collections empty list mungers array list arraylist mungers add munger implement sort mungers sortmungers mungers prepare for mungers prepareformungers implement mungers implementmungers sort mungers sortmungers list sorted partial order partialorder sort mungers bunch code work report xlints advice isn ordered joinpoint possibly report unordered advice possiblyreportunorderedadvice sorted sorted circular dependencies iterator mungers iterator has next hasnext shadow munger shadowmunger shadow munger shadowmunger get iworld getiworld get message handler getmessagehandler handle message handlemessage message util messageutil error weaver messages weavermessages format weaver messages weavermessages circular dependency get source location getsourcelocation mungers sorted optimal xlint ignore possibly report unordered advice possiblyreportunorderedadvice list sorted sorted get iworld getiworld get lint getlint unordered advice at shadow unorderedadviceatshadow is enabled isenabled mungers size stores strings form aspect aspect precedence aspects shadow clashing aspects clashingaspects hash set hashset max mungers size compare pair advice mungers max object mungers object mungers type bcel advice bceladvice bcel advice bceladvice bcel advice bceladvice advice a advicea bcel advice bceladvice bcel advice bceladvice advice b adviceb bcel advice bceladvice advice a advicea concrete aspect concreteaspect equals advice b adviceb concrete aspect concreteaspect advice kind advicekind advice kind a advicekinda advice a advicea get kind getkind advice kind advicekind advice kind b advicekindb advice b adviceb get kind getkind create support features language advice kind a advicekinda get key getkey advice kind b advicekindb get key getkey advice kind a advicekinda get precedence getprecedence advice kind b advicekindb get precedence getprecedence precedence integer order get iworld getiworld get precedence if any getprecedenceifany advice a advicea concrete aspect concreteaspect advice b adviceb concrete aspect concreteaspect order order equals integer string key advice a advicea get declaring aspect getdeclaringaspect advice b adviceb get declaring aspect getdeclaringaspect string possible existing key possibleexistingkey advice b adviceb get declaring aspect getdeclaringaspect advice a advicea get declaring aspect getdeclaringaspect clashing aspects clashingaspects possible existing key possibleexistingkey clashing aspects clashingaspects add key iterator iter clashing aspects clashingaspects iterator iter has next hasnext string element string iter string aspect element substring element index of indexof string aspect element substring element index of indexof get iworld getiworld get lint getlint unordered advice at shadow unorderedadviceatshadow signal string to string tostring aspect aspect get source location getsourcelocation prepare shadow implementation shadow position munger simply implemented prepare for mungers prepareformungers runtime exception runtimeexception shadows prepared ensure report nice source location source info missing binary weave string beautify location beautifylocation isource location isourcelocation isl string buffer stringbuffer nice string buffer stringbuffer isl isl get source file getsourcefile isl get source file getsourcefile get name getname index of indexof debug info nice append debug info file get name getname fails linux box encounters path created windows vice versa take from takefrom isl get source file getsourcefile get path getpath last index of lastindexof take from takefrom take from takefrom isl get source file getsourcefile get path getpath last index of lastindexof nice append isl get source file getsourcefile get path getpath substring take from takefrom isl get line getline nice append append isl get line getline nice to string tostring report weaving message reportweavingmessage shadow munger shadowmunger munger advice advice advice munger advice kind advicekind a kind akind advice get kind getkind a kind akind advice get concrete aspect getconcreteaspect a kind akind equals advice kind advicekind a kind akind equals advice kind advicekind a kind akind equals advice kind advicekind after returning afterreturning a kind akind equals advice kind advicekind after throwing afterthrowing a kind akind equals advice kind advicekind a kind akind equals advice kind advicekind softener string description advice get kind getkind to string tostring string advised type advisedtype get enclosing type getenclosingtype get name getname string advising type advisingtype advice get concrete aspect getconcreteaspect get name getname message msg advice get kind getkind equals advice kind advicekind softener msg weave message weavemessage construct weaving message constructweavingmessage weave message weavemessage weavemessage softens string advised type advisedtype beautify location beautifylocation get source location getsourcelocation advising type advisingtype beautify location beautifylocation munger get source location getsourcelocation advised type advisedtype advising type advisingtype runtime test runtimetest bcel advice bceladvice advice has dynamic tests hasdynamictests string join point description joinpointdescription to string tostring msg weave message weavemessage construct weaving message constructweavingmessage weave message weavemessage weavemessage advises string join point description joinpointdescription advised type advisedtype beautify location beautifylocation get source location getsourcelocation description advising type advisingtype beautify location beautifylocation munger get source location getsourcelocation runtime test runtimetest runtime test advised type advisedtype advising type advisingtype get iworld getiworld get message handler getmessagehandler handle message handlemessage msg irelationship kind determine rel kind determinerelkind shadow munger shadowmunger munger advice kind advicekind advice munger get kind getkind get key getkey advice kind advicekind get key getkey irelationship kind advice get key getkey advice kind advicekind get key getkey irelationship kind advice get key getkey advice kind advicekind after throwing afterthrowing get key getkey irelationship kind advice afterthrowing get key getkey advice kind advicekind after returning afterreturning get key getkey irelationship kind advice afterreturning get key getkey advice kind advicekind get key getkey irelationship kind advice get key getkey advice kind advicekind cflow entry cflowentry get key getkey get key getkey advice kind advicekind cflow below entry cflowbelowentry get key getkey get key getkey advice kind advicekind inter initializer interinitializer get key getkey get key getkey advice kind advicekind per cflow entry percflowentry get key getkey get key getkey advice kind advicekind per cflow below entry percflowbelowentry get key getkey get key getkey advice kind advicekind per this entry perthisentry get key getkey get key getkey advice kind advicekind per target entry pertargetentry get key getkey get key getkey advice kind advicekind softener get key getkey get key getkey advice kind advicekind per type within entry pertypewithinentry get key getkey runtime exception runtimeexception shadow determine rel kind determinerelkind hell implement mungers implementmungers get iworld getiworld iterator iter mungers iterator iter has next hasnext shadow munger shadowmunger munger shadow munger shadowmunger iter munger implement on implementon get cross reference handler getcrossreferencehandler get cross reference handler getcrossreferencehandler add cross reference addcrossreference munger get source location getsourcelocation get source location getsourcelocation determine rel kind determinerelkind munger bcel advice bceladvice munger has dynamic tests hasdynamictests get iworld getiworld get message handler getmessagehandler is ignoring isignoring imessage weaveinfo report weaving message reportweavingmessage munger get model getmodel asm relationship provider asmrelationshipprovider get default getdefault advice munger advicemunger get model getmodel munger string make reflective factory string makereflectivefactorystring isource location isourcelocation get source location getsourcelocation string to string tostring get kind getkind get signature getsignature string to resolved string toresolvedstring get kind getkind resolve get signature getsignature to generic string togenericstring to set toset hash set hashset shadow shadow kinds length kind shadow shadow kinds is set isset add