copyright palo alto center incorporated parc rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors parc initial implementation org aspectj ajdt internal compiler lookup java lang reflect modifier org aspectj bridge isource location isourcelocation org aspectj org eclipse jdt internal compiler ast abstract method declaration abstractmethoddeclaration org aspectj org eclipse jdt internal compiler env iconstants org aspectj org eclipse jdt internal compiler lookup field binding fieldbinding org aspectj org eclipse jdt internal compiler lookup method binding methodbinding org aspectj org eclipse jdt internal compiler lookup source type binding sourcetypebinding org aspectj weaver concrete type munger concretetypemunger org aspectj weaver new constructor type munger newconstructortypemunger org aspectj weaver new field type munger newfieldtypemunger org aspectj weaver new method type munger newmethodtypemunger org aspectj weaver resolved member resolvedmember org aspectj weaver resolved type resolvedtype org aspectj weaver resolved type munger resolvedtypemunger eclipse type munger eclipsetypemunger concrete type munger concretetypemunger resolved type resolvedtype target type x targettypex reference binding referencebinding target binding targetbinding abstract method declaration abstractmethoddeclaration source method sourcemethod eclipse factory eclipsefactory isource location isourcelocation source location sourcelocation eclipse type munger eclipsetypemunger eclipse factory eclipsefactory resolved type munger resolvedtypemunger munger resolved type resolvedtype aspect type aspecttype abstract method declaration abstractmethoddeclaration source method sourcemethod munger aspect type aspecttype source method sourcemethod source method sourcemethod source method sourcemethod source location sourcelocation eclipse source location eclipsesourcelocation source method sourcemethod compilation result compilationresult source method sourcemethod source start sourcestart source method sourcemethod source end sourceend piece magic tells type mungers won persisted resolved type munger resolvedtypemunger persist source location persistsourcelocation munger set source location setsourcelocation source location sourcelocation target type x targettypex munger get signature getsignature get declaring type getdeclaringtype resolve get world getworld amc needed raw distinct sigs target type x targettypex is parameterized type isparameterizedtype target type x targettypex is raw type israwtype target type x targettypex target type x targettypex get generic type getgenerictype target binding targetbinding reference binding referencebinding make type binding maketypebinding target type x targettypex supports kind supportskind resolved type munger resolvedtypemunger kind kind kind resolved type munger resolvedtypemunger field kind resolved type munger resolvedtypemunger method kind resolved type munger resolvedtypemunger constructor string to string tostring eclipse type munger eclipsetypemunger get munger getmunger modifies signatures type binding typebinding class scope classscope adds method field bindings fieldbindings plays inheritance munge source type binding sourcetypebinding source type sourcetype resolved type resolvedtype on type ontype resolved type resolvedtype on type ontype is raw type israwtype is parameterized type isparameterizedtype get generic type getgenerictype is exact target type isexacttargettype equals target type x targettypex is exact target type isexacttargettype topmost implementor care munger get kind getkind resolved type munger resolvedtypemunger method on type ontype is interface isinterface munger needs access to topmost implementor needsaccesstotopmostimplementor access type on type ontype is topmost implementor istopmostimplementor target type x targettypex drive jdt method verifier methodverifier correctly modifier is public ispublic munger get signature getsignature get modifiers getmodifiers target type x targettypex munger get kind getkind resolved type munger resolvedtypemunger field munge new field mungenewfield source type sourcetype new field type munger newfieldtypemunger munger munger get kind getkind resolved type munger resolvedtypemunger method munge new method mungenewmethod source type sourcetype on type ontype new method type munger newmethodtypemunger munger is exact target type isexacttargettype munger get kind getkind resolved type munger resolvedtypemunger constructor munge new constructor mungenewconstructor source type sourcetype new constructor type munger newconstructortypemunger munger runtime exception runtimeexception unimplemented munger get kind getkind munge new method mungenewmethod source type binding sourcetypebinding source type sourcetype resolved type resolvedtype on type ontype new method type munger newmethodtypemunger munger is exact target type isexacttargettype inter type method binding intertypemethodbinding binding inter type method binding intertypemethodbinding munger aspect type aspecttype source method sourcemethod is exact target type isexacttargettype munging topmost implementor resolved member resolvedmember existing member existingmember on type ontype lookup member including itds on interfaces lookupmemberincludingitdsoninterfaces get signature getsignature existing member existingmember implementation don on type ontype existing member existingmember get declaring type getdeclaringtype modifier is final isfinal munger get signature getsignature get modifiers getmodifiers modifier implementation provide implementation method binding methodbinding offending binding offendingbinding source type sourcetype get exact method getexactmethod binding selector binding parameters source type sourcetype scope compilation unit scope compilationunitscope source type sourcetype scope problem reporter problemreporter final method cannot be overridden finalmethodcannotbeoverridden offending binding offendingbinding binding find methods superinterfaces find or create inter type member finder findorcreateintertypememberfinder source type sourcetype retain visibility modifiers putting methods source type sourcetype is interface isinterface is abstract isabstract binding modifiers iconstants acc abstract accabstract binding modifiers binding modifiers iconstants acc public accpublic iconstants acc protected accprotected iconstants acc private accprivate is abstract isabstract binding modifiers iconstants acc abstract accabstract munger get signature getsignature is varargs method isvarargsmethod binding modifiers iconstants acc varargs accvarargs find or create inter type member finder findorcreateintertypememberfinder source type sourcetype add inter type method addintertypemethod binding munge new constructor mungenewconstructor source type binding sourcetypebinding source type sourcetype new constructor type munger newconstructortypemunger munger should treat as public shouldtreataspublic method binding methodbinding binding make method binding makemethodbinding munger get signature getsignature munger get type variable aliases gettypevariablealiases find or create inter type member finder findorcreateintertypememberfinder source type sourcetype add inter type method addintertypemethod binding class scope classscope reference context referencecontext binding add method addmethod binding inter type method binding intertypemethodbinding binding inter type method binding intertypemethodbinding munger aspect type aspecttype source method sourcemethod find or create inter type member finder findorcreateintertypememberfinder source type sourcetype add inter type method addintertypemethod binding munge new field mungenewfield source type binding sourcetypebinding source type sourcetype new field type munger newfieldtypemunger munger should treat as public shouldtreataspublic target type x targettypex is interface isinterface field binding fieldbinding binding make field binding makefieldbinding munger find or create inter type member finder findorcreateintertypememberfinder source type sourcetype add inter type field addintertypefield binding class scope classscope reference context referencecontext binding add field addfield binding inter type field binding intertypefieldbinding binding inter type field binding intertypefieldbinding munger aspect type aspecttype source method sourcemethod find or create inter type member finder findorcreateintertypememberfinder source type sourcetype add inter type field addintertypefield binding should treat as public shouldtreataspublic modifier is public ispublic munger get signature getsignature get modifiers getmodifiers inter type member finder intertypememberfinder find or create inter type member finder findorcreateintertypememberfinder source type binding sourcetypebinding source type sourcetype inter type member finder intertypememberfinder finder inter type member finder intertypememberfinder source type sourcetype member finder memberfinder finder finder inter type member finder intertypememberfinder source type sourcetype member finder memberfinder finder finder source type binding sourcetypebinding source type sourcetype finder isource location isourcelocation get source location getsourcelocation source location sourcelocation set source location setsourcelocation isource location isourcelocation source location sourcelocation source location sourcelocation source location sourcelocation abstract method declaration abstractmethoddeclaration abstract method declaration abstractmethoddeclaration get source method getsourcemethod source method sourcemethod concrete type munger concretetypemunger parameterized for parameterizedfor resolved type resolvedtype target eclipse type munger eclipsetypemunger munger parameterized for parameterizedfor target aspect type aspecttype source method sourcemethod