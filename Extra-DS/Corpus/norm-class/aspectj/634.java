copyright contributors rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors adrian colyer initial implementation org aspectj weaver reflect java util hash set hashset java util junit framework test case testcase org aspectj bridge imessage handler imessagehandler org aspectj weaver reference type referencetype org aspectj weaver resolved member resolvedmember org aspectj weaver resolved type resolvedtype org aspectj weaver unresolved type unresolvedtype org aspectj weaver bcel bcel world bcelworld reflection based reference type delegate test reflectionbasedreferencetypedelegatetest test case testcase reflection world reflectionworld resolved type resolvedtype object type objecttype resolved type resolvedtype class type classtype test is aspect testisaspect assert false assertfalse object type objecttype is aspect isaspect test is annotation style aspect testisannotationstyleaspect assert false assertfalse object type objecttype is annotation style aspect isannotationstyleaspect test is interface testisinterface assert false assertfalse object type objecttype is interface isinterface assert true asserttrue resolve java serializable is interface isinterface test is enum testisenum assert false assertfalse object type objecttype is enum isenum test is annotation testisannotation assert false assertfalse object type objecttype is annotation isannotation test is annotation with runtime retention testisannotationwithruntimeretention assert false assertfalse object type objecttype is annotation with runtime retention isannotationwithruntimeretention test is class testisclass assert true asserttrue object type objecttype is class isclass assert false assertfalse resolve java serializable is class isclass test is generic testisgeneric assert false assertfalse object type objecttype is generic type isgenerictype test is exposed to weaver testisexposedtoweaver assert false assertfalse object type objecttype is exposed to weaver isexposedtoweaver test has annotation testhasannotation assert false assertfalse object type objecttype has annotation hasannotation unresolved type unresolvedtype for name forname foo test get annotations testgetannotations assert equals assertequals entries object type objecttype get annotations getannotations length test get annotation types testgetannotationtypes assert equals assertequals entries object type objecttype get annotation types getannotationtypes length test get type variables testgettypevariables assert equals assertequals entries object type objecttype get type variables gettypevariables length test get per clause testgetperclause assert null assertnull object type objecttype get per clause getperclause test get modifiers testgetmodifiers assert equals assertequals object get modifiers getmodifiers object type objecttype get modifiers getmodifiers test get superclass testgetsuperclass assert true asserttrue superclass object object type objecttype get superclass getsuperclass object type objecttype get superclass getsuperclass assert equals assertequals object type objecttype resolve java lang get superclass getsuperclass resolved type resolvedtype resolve reflect tests assert equals assertequals resolve reflect tests get superclass getsuperclass find method findmethod string resolved member resolvedmember methods methods length equals methods get name getname find method findmethod string num args numargs resolved member resolvedmember methods methods length equals methods get name getname methods get parameter types getparametertypes length num args numargs test get declared methods testgetdeclaredmethods resolved member resolvedmember methods object type objecttype get declared methods getdeclaredmethods assert equals assertequals object get declared methods getdeclaredmethods length object get declared constructors getdeclaredconstructors length methods length resolved type resolvedtype resolve reflect tests methods get declared methods getdeclaredmethods assert equals assertequals methods length idx find method findmethod foo methods assert true asserttrue idx assert equals assertequals resolve java lang string methods idx get return type getreturntype assert equals assertequals methods idx get parameter types getparametertypes length assert equals assertequals object type objecttype methods idx get parameter types getparametertypes assert equals assertequals methods idx get exceptions getexceptions length assert equals assertequals resolve java lang exception methods idx get exceptions getexceptions baridx find method findmethod bar methods initidx find method findmethod init methods assert true asserttrue baridx assert true asserttrue initidx assert true asserttrue baridx initidx baridx idx idx initidx baridx resolved type resolvedtype resolve reflect tests methods get declared methods getdeclaredmethods assert equals assertequals methods length class type classtype resolve java lang methods class type classtype get declared methods getdeclaredmethods assert equals assertequals get declared methods getdeclaredmethods length get declared constructors getdeclaredconstructors length methods length test get declared fields testgetdeclaredfields resolved member resolvedmember fields object type objecttype get declared fields getdeclaredfields assert equals assertequals fields length resolved type resolvedtype resolve reflect tests fields get declared fields getdeclaredfields assert equals assertequals fields length assert equals assertequals fields get name getname assert equals assertequals fields get name getname assert equals assertequals resolved type resolvedtype fields get return type getreturntype assert equals assertequals resolve java lang string fields get return type getreturntype test get declared interfaces testgetdeclaredinterfaces resolved type resolvedtype interfaces object type objecttype get declared interfaces getdeclaredinterfaces assert equals assertequals interfaces length resolved type resolvedtype resolve reflect tests interfaces get declared interfaces getdeclaredinterfaces assert equals assertequals interfaces length assert equals assertequals resolve java serializable interfaces test get declared pointcuts testgetdeclaredpointcuts resolved member resolvedmember pointcuts object type objecttype get declared pointcuts getdeclaredpointcuts assert equals assertequals pointcuts length test serializable superclass testserializablesuperclass resolved type resolvedtype serializable type serializabletype resolve java serializable resolved type resolvedtype super type supertype serializable type serializabletype get superclass getsuperclass assert true asserttrue superclass serializable object super type supertype super type supertype equals unresolved type unresolvedtype object bcel world bcelworld bcelworld bcel world bcelworld bcelworld set behave in java setbehaveinjava resolved type resolvedtype bcel supertype bcelsupertype bcelworld resolve unresolved type unresolvedtype serializable get superclass getsuperclass assert true asserttrue bcel supertype bcelsupertype bcel supertype bcelsupertype equals unresolved type unresolvedtype object test subinterface superclass testsubinterfacesuperclass resolved type resolvedtype iface type ifacetype resolve java security key resolved type resolvedtype super type supertype iface type ifacetype get superclass getsuperclass assert true asserttrue superclass object super type supertype super type supertype equals unresolved type unresolvedtype object bcel world bcelworld bcelworld bcel world bcelworld bcelworld set behave in java setbehaveinjava resolved type resolvedtype bcel supertype bcelsupertype bcelworld resolve java security key get superclass getsuperclass assert true asserttrue bcel supertype bcelsupertype bcel supertype bcelsupertype equals unresolved type unresolvedtype object test void superclass testvoidsuperclass resolved type resolvedtype void type voidtype resolve type resolved type resolvedtype super type supertype void type voidtype get superclass getsuperclass assert null assertnull super type supertype bcel world bcelworld bcelworld bcel world bcelworld bcelworld set behave in java setbehaveinjava resolved type resolvedtype bcel supertype bcelsupertype bcelworld resolve get superclass getsuperclass assert true asserttrue bcel supertype bcelsupertype bcel supertype bcelsupertype test int superclass testintsuperclass resolved type resolvedtype void type voidtype resolve integer type resolved type resolvedtype super type supertype void type voidtype get superclass getsuperclass assert null assertnull super type supertype bcel world bcelworld bcelworld bcel world bcelworld bcelworld set behave in java setbehaveinjava resolved type resolvedtype bcel supertype bcelsupertype bcelworld resolve get superclass getsuperclass assert true asserttrue bcel supertype bcelsupertype bcel supertype bcelsupertype test generic interface superclass testgenericinterfacesuperclass bcel world resolution bcelworldresolution bcel world bcelworld bcelworld bcel world bcelworld bcelworld set behave in java setbehaveinjava unresolved type unresolvedtype java util map javautilmap unresolved type unresolvedtype for name forname java util map reference type referencetype raw type rawtype reference type referencetype bcelworld resolve java util map javautilmap assert true asserttrue raw type raw type rawtype get typekind gettypekind raw type rawtype is raw type israwtype reference type referencetype generic type generictype reference type referencetype raw type rawtype get generic type getgenerictype assert true asserttrue type generic type generictype get typekind gettypekind generic type generictype is generic type isgenerictype resolved type resolvedtype raw type rawtype get superclass getsuperclass assert true asserttrue superclass map raw type object equals unresolved type unresolvedtype object resolved type resolvedtype generic type generictype get superclass getsuperclass assert true asserttrue superclass map type object equals unresolved type unresolvedtype object fixme asc reflection list methods returned doesn include clinit initializer problem test compare subclass delegates testcomparesubclassdelegates barf if clinit missing barfifclinitmissing set behave in java setbehaveinjava bcel world bcelworld bcel world bcelworld bcel world bcelworld get class getclass get class loader getclassloader imessage handler imessagehandler bcel world bcelworld set behave in java setbehaveinjava unresolved type unresolvedtype java util hash map javautilhashmap unresolved type unresolvedtype for name forname java util hash map hashmap reference type referencetype raw type rawtype reference type referencetype bcel world bcelworld resolve java util hash map javautilhashmap reference type referencetype raw reflect type rawreflecttype reference type referencetype resolve java util hash map javautilhashmap resolved member resolvedmember rms raw type rawtype get delegate getdelegate get declared methods getdeclaredmethods resolved member resolvedmember rms raw reflect type rawreflecttype get delegate getdelegate get declared methods getdeclaredmethods string buffer stringbuffer errors string buffer stringbuffer hash set hashset rms length add rms to string tostring hash set hashset rms length add rms to string tostring rms length rms to string tostring errors append couldn find rms to string tostring bcel rms length rms to string tostring barf if clinit missing barfifclinitmissing rms get name getname equals clinit errors append couldn find rms to string tostring reflection assert true asserttrue errors errors to string tostring errors length barf if clinit missing barfifclinitmissing numbers exact assert equals assertequals rms length rms length numbers favour bcel assert true asserttrue extra clinit bcel bcel rms length reflect rms length rms length rms length todo array set up setup exception reflection world reflectionworld get class getclass get class loader getclassloader object type objecttype resolve java lang object