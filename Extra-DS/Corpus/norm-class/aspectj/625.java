copyright ibm corporation rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html org aspectj weaver patterns java util iterator java util java util string tokenizer stringtokenizer org aspectj weaver shadow junit framework test case testcase author colyer todo change template generated type comment window preferences java code style code templates pointcut rewriter test pointcutrewritertest test case testcase pointcut rewriter pointcutrewriter prw test distribute not testdistributenot pointcut plain get pointcut getpointcut foo assert equals assertequals unchanged plain prw rewrite plain pointcut get pointcut getpointcut foo assert equals assertequals unchanged prw rewrite pointcut not not notnot get pointcut getpointcut foo assert equals assertequals foo prw rewrite not not notnot to string tostring pointcut not not not notnotnot get pointcut getpointcut foo assert equals assertequals foo prw rewrite not not not notnotnot to string tostring pointcut get pointcut getpointcut foo goo assert equals assertequals foo goo prw rewrite to string tostring pointcut get pointcut getpointcut foo goo assert equals assertequals foo goo prw rewrite to string tostring pointcut nested not nestednot get pointcut getpointcut foo goo assert equals assertequals foo goo prw rewrite nested not nestednot to string tostring test pull up disjunctions testpullupdisjunctions pointcut a andb aandb get pointcut getpointcut foo goo assert equals assertequals unchanged a andb aandb prw rewrite a andb aandb pointcut a orb aorb get pointcut getpointcut foo moo assert equals assertequals unchanged a orb aorb prw rewrite a orb aorb pointcut left or leftor get pointcut getpointcut foo goo boo assert equals assertequals anyorder foo anyorder boo goo prw rewrite left or leftor assert equals assertequals foo boo goo prw rewrite left or leftor to string tostring pointcut right or rightor get pointcut getpointcut goo boo foo assert equals assertequals foo boo goo prw rewrite right or rightor to string tostring assert equals assertequals anyorder foo anyorder goo boo prw rewrite right or rightor pointcut left and leftand get pointcut getpointcut foo goo boo assert equals assertequals boo foo foo goo prw rewrite left and leftand to string tostring assert equals assertequals anyorder anyorder boo foo anyorder foo goo prw rewrite left and leftand pointcut right and rightand get pointcut getpointcut goo boo foo assert equals assertequals boo foo foo goo prw rewrite right and rightand to string tostring assert equals assertequals anyorder anyorder boo foo anyorder foo goo prw rewrite right and rightand pointcut nested ors nestedors get pointcut getpointcut foo goo boo assert equals assertequals boo foo goo prw rewrite nested ors nestedors to string tostring assert equals assertequals anyorder goo anyorder boo foo prw rewrite nested ors nestedors pointcut nested ands nestedands get pointcut getpointcut foo boo goo moo assert equals assertequals boo foo goo boo foo moo prw rewrite nested ands nestedands to string tostring assert equals assertequals anyorder anyorder anyorder boo foo goo anyorder anyorder boo foo moo prw rewrite nested ands nestedands spec reverse polish notation operators anyorder delimiter whitespace param spec param assert equals assertequals string spec pointcut string tokenizer stringtokenizer str tok strtok string tokenizer stringtokenizer spec string tokens string str tok strtok count tokens counttokens tokens length tokens str tok strtok next token nexttoken token index tokenindex assert true asserttrue spec equals tokens token index tokenindex equals pointcut string tokens tokens token index tokenindex equals token index tokenindex and pointcut andpointcut and pointcut andpointcut apc and pointcut andpointcut pointcut left apc get left getleft pointcut apc get right getright tokens token index tokenindex equals anyorder token index tokenindex restore point restorepoint token index tokenindex left match first leftmatchfirst equals left tokens equals tokens left match first leftmatchfirst token index tokenindex restore point restorepoint right match first rightmatchfirst equals tokens equals left tokens right match first rightmatchfirst equals left tokens equals tokens tokens token index tokenindex equals token index tokenindex or pointcut orpointcut or pointcut orpointcut opc or pointcut orpointcut pointcut left opc get left getleft pointcut opc get right getright tokens token index tokenindex equals anyorder token index tokenindex restore point restorepoint token index tokenindex left match first leftmatchfirst equals left tokens equals tokens left match first leftmatchfirst token index tokenindex restore point restorepoint right match first rightmatchfirst equals tokens equals left tokens right match first rightmatchfirst equals left tokens equals tokens tokens token index tokenindex equals not pointcut notpointcut token index tokenindex not pointcut notpointcut not pointcut notpointcut equals get negated pointcut getnegatedpointcut tokens tokens token index tokenindex equals to string tostring test split out withins testsplitoutwithins pointcut simple execution simpleexecution get pointcut getpointcut execution assert equals assertequals unchanged simple execution simpleexecution prw rewrite simple execution simpleexecution pointcut simple within code simplewithincode get pointcut getpointcut withincode assert equals assertequals unchanged simple within code simplewithincode prw rewrite simple within code simplewithincode pointcut execution get pointcut getpointcut execution foo foo goo org xyz foo boo assert equals assertequals goo org xyz execution foo foo foo boo prw rewrite execution to string tostring pointcut withincode get pointcut getpointcut withincode foo foo goo org xyz foo boo assert equals assertequals goo org xyz withincode foo foo foo boo prw rewrite withincode to string tostring pointcut not execution notexecution get pointcut getpointcut execution foo bank account bankaccount assert equals assertequals bank account bankaccount execution foo prw rewrite not execution notexecution to string tostring pointcut and withincode andwithincode get pointcut getpointcut withincode foo foo assert equals assertequals foo withincode foo prw rewrite and withincode andwithincode to string tostring pointcut or execution orexecution get pointcut getpointcut foo execution goo foo moo baa assert equals assertequals foo execution goo moo baa foo prw rewrite or execution orexecution to string tostring test remove duplicates in and testremoveduplicatesinand pointcut dup and dupand get pointcut getpointcut foo foo assert equals assertequals foo prw rewrite dup and dupand to string tostring pointcut splitdup and splitdupand get pointcut getpointcut foo target boo foo assert equals assertequals target boo foo prw rewrite splitdup and splitdupand to string tostring test not remove nearly duplicates in and testnotremovenearlyduplicatesinand pointcut to andto toandto get pointcut getpointcut object object pointcut rewritten prw rewrite to andto toandto test aand not ain and testaandnotainand pointcut a and nota aandnota get pointcut getpointcut foo foo assert equals assertequals matches prw rewrite a and nota aandnota to string tostring pointcut a and band nota aandbandnota get pointcut getpointcut foo execution foo assert equals assertequals matches prw rewrite a and band nota aandbandnota to string tostring test if false in and testiffalseinand pointcut if false iffalse if pointcut ifpointcut make if false pointcut makeiffalsepointcut pointcut concrete pointcut get pointcut getpointcut assert equals assertequals matches prw rewrite and pointcut andpointcut if false iffalse to string tostring test matches nothingin and testmatchesnothinginand pointcut pointcut make matches nothing makematchesnothing pointcut concrete pointcut get pointcut getpointcut assert equals assertequals matches prw rewrite and pointcut andpointcut to string tostring test mixed kinds in and testmixedkindsinand pointcut mixed kinds mixedkinds get pointcut getpointcut call execution assert equals assertequals matches prw rewrite mixed kinds mixedkinds to string tostring pointcut get pointcut getpointcut call foo assert equals assertequals prw rewrite test determine kind set of and testdeterminekindsetofand pointcut one kind onekind get pointcut getpointcut execution foo boo and pointcut andpointcut rewritten and pointcut andpointcut prw rewrite one kind onekind assert equals assertequals kind shadow how many howmany rewritten could match kinds couldmatchkinds assert true asserttrue shadow method execution methodexecution shadow method execution methodexecution is set isset rewritten could match kinds couldmatchkinds test kind set of execution testkindsetofexecution pointcut get pointcut getpointcut execution foo assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow method execution methodexecution shadow method execution methodexecution is set isset could match kinds couldmatchkinds get pointcut getpointcut execution assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow constructor execution constructorexecution shadow constructor execution constructorexecution is set isset could match kinds couldmatchkinds test kind set of call testkindsetofcall pointcut get pointcut getpointcut call foo assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow method call methodcall shadow method call methodcall is set isset could match kinds couldmatchkinds get pointcut getpointcut call assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow constructor call constructorcall shadow constructor call constructorcall is set isset could match kinds couldmatchkinds test kind set of advice execution testkindsetofadviceexecution pointcut get pointcut getpointcut adviceexecution assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow advice execution adviceexecution shadow advice execution adviceexecution is set isset could match kinds couldmatchkinds test kind set of get testkindsetofget pointcut get pointcut getpointcut assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow field get fieldget shadow field get fieldget is set isset could match kinds couldmatchkinds test kind set of set testkindsetofset pointcut get pointcut getpointcut assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow field set fieldset shadow field set fieldset is set isset could match kinds couldmatchkinds test kind set of handler testkindsetofhandler pointcut get pointcut getpointcut handler assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow exception handler exceptionhandler shadow exception handler exceptionhandler is set isset could match kinds couldmatchkinds test kind set of initialization testkindsetofinitialization pointcut get pointcut getpointcut initialization assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow initialization shadow initialization is set isset could match kinds couldmatchkinds test kind set of pre initialization testkindsetofpreinitialization pointcut get pointcut getpointcut preinitialization assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow pre initialization preinitialization shadow pre initialization preinitialization is set isset could match kinds couldmatchkinds test kind set of static initialization testkindsetofstaticinitialization pointcut get pointcut getpointcut staticinitialization assert equals assertequals kind shadow how many howmany could match kinds couldmatchkinds assert true asserttrue shadow static initialization staticinitialization shadow static initialization staticinitialization is set isset could match kinds couldmatchkinds test kind set of this testkindsetofthis pointcut get pointcut getpointcut foo matches shadow to set toset could match kinds couldmatchkinds iterator iter matches iterator iter has next hasnext shadow kind kind shadow kind iter assert false assertfalse kinds don kind never has this neverhasthis shadow shadow kinds length shadow shadow kinds never has this neverhasthis assert true asserttrue kinds matches shadow shadow kinds get pointcut getpointcut foo matches shadow to set toset could match kinds couldmatchkinds iterator iter matches iterator iter has next hasnext shadow kind kind shadow kind iter assert false assertfalse kinds don kind never has this neverhasthis shadow shadow kinds length shadow shadow kinds never has this neverhasthis assert true asserttrue kinds matches shadow shadow kinds test kind set of target testkindsetoftarget pointcut get pointcut getpointcut target foo matches shadow to set toset could match kinds couldmatchkinds iterator iter matches iterator iter has next hasnext shadow kind kind shadow kind iter assert false assertfalse kinds don target kind never has target neverhastarget shadow shadow kinds length shadow shadow kinds never has target neverhastarget assert true asserttrue kinds target matches shadow shadow kinds get pointcut getpointcut target foo matches shadow to set toset could match kinds couldmatchkinds iterator iter matches iterator iter has next hasnext shadow kind kind shadow kind iter assert false assertfalse kinds don target kind never has target neverhastarget shadow shadow kinds length shadow shadow kinds never has target neverhastarget assert true asserttrue kinds target matches shadow shadow kinds test kind set of args testkindsetofargs pointcut get pointcut getpointcut args assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits get pointcut getpointcut args assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits test kind set of annotation testkindsetofannotation pointcut get pointcut getpointcut annotation foo assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits test kind set of within testkindsetofwithin pointcut get pointcut getpointcut assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits get pointcut getpointcut foo assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits test kind set of within code testkindsetofwithincode pointcut get pointcut getpointcut withincode foo matches shadow to set toset could match kinds couldmatchkinds iterator iter matches iterator iter has next hasnext shadow kind kind shadow kind iter assert false assertfalse kinds enclosing kind is enclosing kind isenclosingkind kind shadow constructor execution constructorexecution kind shadow initialization shadow shadow kinds length shadow shadow kinds is enclosing kind isenclosingkind assert true asserttrue kinds enclosing matches shadow shadow kinds assert true asserttrue cons exe inlined field inits matches shadow constructor execution constructorexecution assert true asserttrue init inlined field inits matches shadow initialization get pointcut getpointcut withincode foo matches shadow to set toset could match kinds couldmatchkinds iterator iter matches iterator iter has next hasnext shadow kind kind shadow kind iter assert false assertfalse kinds enclosing kind is enclosing kind isenclosingkind shadow shadow kinds length shadow shadow kinds is enclosing kind isenclosingkind assert true asserttrue kinds enclosing matches shadow shadow kinds test kind set of if testkindsetofif pointcut if pointcut ifpointcut assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits if pointcut ifpointcut make if true pointcut makeiftruepointcut pointcut concrete assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits if pointcut ifpointcut make if false pointcut makeiffalsepointcut pointcut concrete assert true asserttrue could match kinds couldmatchkinds shadow shadow kinds bits test kind set of cflow testkindsetofcflow pointcut get pointcut getpointcut cflow foo assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits get pointcut getpointcut cflowbelow foo assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits test kind set in negation testkindsetinnegation pointcut get pointcut getpointcut execution assert true asserttrue kinds could match kinds couldmatchkinds shadow shadow kinds bits test kind set of or testkindsetofor pointcut get pointcut getpointcut execution matches shadow to set toset could match kinds couldmatchkinds assert equals assertequals kinds matches size assert true asserttrue constructor execution constructorexecution matches shadow constructor execution constructorexecution assert true asserttrue field get fieldget matches shadow field get fieldget test ordering in and testorderinginand pointcut big long pc biglongpc get pointcut getpointcut cflow foo args args foo target boo moo target boo annotation moo withincode boo withincode foo foo pointcut rewritten prw rewrite big long pc biglongpc assert equals assertequals foo foo withincode withincode boo annotation moo target boo moo target boo foo args args cflow foo rewritten to string tostring test ordering in simple or testorderinginsimpleor or pointcut orpointcut opc or pointcut orpointcut get pointcut getpointcut execution assert equals assertequals reordered execution prw rewrite opc to string tostring test ordering in nested ors testorderinginnestedors or pointcut orpointcut opc or pointcut orpointcut get pointcut getpointcut execution abc assert equals assertequals reordered abc execution prw rewrite opc to string tostring test ordering in ors with nested ands testorderinginorswithnestedands or pointcut orpointcut opc or pointcut orpointcut get pointcut getpointcut execution abc assert equals assertequals reordered abc execution prw rewrite opc to string tostring pointcut get pointcut getpointcut string pattern parser patternparser parse pointcut parsepointcut test case testcase set up setup set up setup exception set up setup prw pointcut rewriter pointcutrewriter