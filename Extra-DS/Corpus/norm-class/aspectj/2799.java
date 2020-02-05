copyright ibm corporation rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors ibm corporation initial api implementation org aspectj org eclipse jdt core dom java util iterator java util list internal ast visitor serializing ast quick dirty fashion reasons string legal java code legal java code string corresponds ast useless purposes fine generating debug print strings usage code pre naive astflattener naiveastflattener naive astflattener naiveastflattener node accept string result get result getresult pre code call code reset code method clear previous result reusing existing instance aj naive astflattener ajnaiveastflattener aj astvisitor ajastvisitor string buffer serialized representation ast written string buffer stringbuffer buffer indent creates ast printer aj naive astflattener ajnaiveastflattener buffer string buffer stringbuffer returns string accumulated visit serialized string get result getresult buffer to string tostring resets printer reset buffer set length setlength print indent printindent indent nls buffer append appends text representation modifier flags single space modifiers annotations param ext list modifier annotation nodes element type code iextended modifiers iextendedmodifiers code print modifiers printmodifiers list ext iterator ext iterator has next hasnext astnode astnode accept nls buffer append appends text representation modifier flags single space jls modifiers param modifiers modifier flags print modifiers printmodifiers modifiers modifier is public ispublic modifiers nls buffer append modifier is protected isprotected modifiers nls buffer append modifier is private isprivate modifiers nls buffer append modifier is static isstatic modifiers nls buffer append modifier is abstract isabstract modifiers nls buffer append modifier is final isfinal modifiers nls buffer append modifier is synchronized issynchronized modifiers nls buffer append modifier is volatile isvolatile modifiers nls buffer append modifier is native isnative modifiers nls buffer append modifier is strictfp isstrictfp modifiers nls buffer append modifier is transient istransient modifiers nls buffer append astvisitor visit annotation type declaration annotationtypedeclaration visit annotation type declaration annotationtypedeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept print indent printindent print modifiers printmodifiers node modifiers nls buffer append node get name getname accept nls buffer append iterator node body declarations bodydeclarations iterator has next hasnext body declaration bodydeclaration body declaration bodydeclaration accept nls buffer append astvisitor visit annotation type member declaration annotationtypememberdeclaration visit annotation type member declaration annotationtypememberdeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept print indent printindent print modifiers printmodifiers node modifiers node get type gettype accept nls buffer append node get name getname accept nls buffer append node get default getdefault nls buffer append node get default getdefault accept nls buffer append astvisitor visit anonymous class declaration anonymousclassdeclaration visit anonymous class declaration anonymousclassdeclaration node nls buffer append indent iterator node body declarations bodydeclarations iterator has next hasnext body declaration bodydeclaration body declaration bodydeclaration accept indent print indent printindent nls buffer append astvisitor visit array access arrayaccess visit array access arrayaccess node node get array getarray accept nls buffer append node get index getindex accept nls buffer append astvisitor visit array creation arraycreation visit array creation arraycreation node nls buffer append array type arraytype node get type gettype dims get dimensions getdimensions type element type elementtype get element type getelementtype element type elementtype accept iterator node dimensions iterator has next hasnext nls buffer append expression expression accept nls buffer append dims add empty extra array dimension dims nls buffer append node get initializer getinitializer node get initializer getinitializer accept astvisitor visit array initializer arrayinitializer visit array initializer arrayinitializer node nls buffer append iterator node expressions iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append astvisitor visit array type arraytype visit array type arraytype node node get component type getcomponenttype accept nls buffer append astvisitor visit assert statement assertstatement visit assert statement assertstatement node print indent printindent nls buffer append node get expression getexpression accept node get message getmessage nls buffer append node get message getmessage accept nls buffer append astvisitor visit assignment visit assignment node node get left hand side getlefthandside accept buffer append node get operator getoperator to string tostring node get right hand side getrighthandside accept astvisitor visit block visit block node nls buffer append indent iterator node statements iterator has next hasnext statement statement accept indent print indent printindent nls buffer append astvisitor visit block comment blockcomment visit block comment blockcomment node print indent printindent nls buffer append astvisitor visit boolean literal booleanliteral visit boolean literal booleanliteral node node boolean value booleanvalue nls buffer append nls buffer append astvisitor visit break statement breakstatement visit break statement breakstatement node print indent printindent nls buffer append node get label getlabel nls buffer append node get label getlabel accept nls buffer append astvisitor visit cast expression castexpression visit cast expression castexpression node nls buffer append node get type gettype accept nls buffer append node get expression getexpression accept astvisitor visit catch clause catchclause visit catch clause catchclause node nls buffer append node get exception getexception accept nls buffer append node get body getbody accept astvisitor visit character literal characterliteral visit character literal characterliteral node buffer append node get escaped value getescapedvalue astvisitor visit class instance creation classinstancecreation visit class instance creation classinstancecreation node node get expression getexpression node get expression getexpression accept nls buffer append nls buffer append node get ast getast api level apilevel ast jls internal node internal get name internalgetname accept node get ast getast api level apilevel ast jls node type arguments typearguments is empty isempty nls buffer append nls iterator node type arguments typearguments iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls node get type gettype accept nls buffer append iterator node arguments iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append node get anonymous class declaration getanonymousclassdeclaration node get anonymous class declaration getanonymousclassdeclaration accept astvisitor visit compilation unit compilationunit visit compilation unit compilationunit node node get package getpackage node get package getpackage accept iterator node imports iterator has next hasnext import declaration importdeclaration import declaration importdeclaration accept iterator node types iterator has next hasnext abstract type declaration abstracttypedeclaration abstract type declaration abstracttypedeclaration accept astvisitor visit conditional expression conditionalexpression visit conditional expression conditionalexpression node node get expression getexpression accept nls buffer append node get then expression getthenexpression accept nls buffer append node get else expression getelseexpression accept astvisitor visit constructor invocation constructorinvocation visit constructor invocation constructorinvocation node print indent printindent node get ast getast api level apilevel ast jls node type arguments typearguments is empty isempty nls buffer append nls iterator node type arguments typearguments iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls nls buffer append iterator node arguments iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append astvisitor visit continue statement continuestatement visit continue statement continuestatement node print indent printindent nls buffer append node get label getlabel nls buffer append node get label getlabel accept nls buffer append astvisitor visit do statement dostatement visit do statement dostatement node print indent printindent nls buffer append node get body getbody accept nls buffer append node get expression getexpression accept nls buffer append astvisitor visit empty statement emptystatement visit empty statement emptystatement node print indent printindent nls buffer append astvisitor visit enhanced for statement enhancedforstatement visit enhanced for statement enhancedforstatement node print indent printindent nls buffer append node get parameter getparameter accept nls buffer append node get expression getexpression accept nls buffer append node get body getbody accept visit pointcut declaration pointcutdeclaration node print indent printindent buffer append pointcut node get name getname accept buffer append list parameters node parameters iterator iter parameters iterator iter has next hasnext single variable declaration singlevariabledeclaration element single variable declaration singlevariabledeclaration iter buffer append element get type gettype to string tostring element get name getname iter has next hasnext buffer append buffer append buffer append default pointcut defaultpointcut node get designator getdesignator get detail getdetail buffer append astvisitor visit enum constant declaration enumconstantdeclaration visit enum constant declaration enumconstantdeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept print indent printindent print modifiers printmodifiers node modifiers node get name getname accept node arguments is empty isempty nls buffer append iterator node arguments iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append node get anonymous class declaration getanonymousclassdeclaration node get anonymous class declaration getanonymousclassdeclaration accept astvisitor visit enum declaration enumdeclaration visit enum declaration enumdeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept print indent printindent print modifiers printmodifiers node modifiers nls buffer append enum node get name getname accept nls buffer append node super interface types superinterfacetypes is empty isempty nls buffer append iterator node super interface types superinterfacetypes iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls buffer append iterator node enum constants enumconstants iterator has next hasnext enum constant declaration enumconstantdeclaration enum constant declaration enumconstantdeclaration accept enum constant declarations include punctuation has next hasnext enum constant declarations separated commas nls buffer append nls node body declarations bodydeclarations is empty isempty nls buffer append iterator node body declarations bodydeclarations iterator has next hasnext body declaration bodydeclaration body declaration bodydeclaration accept body declarations include trailing punctuation nls buffer append astvisitor visit expression statement expressionstatement visit expression statement expressionstatement node print indent printindent node get expression getexpression accept nls buffer append astvisitor visit field access fieldaccess visit field access fieldaccess node node get expression getexpression accept nls buffer append node get name getname accept astvisitor visit field declaration fielddeclaration visit field declaration fielddeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept print indent printindent node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers node get type gettype accept nls buffer append iterator node fragments iterator has next hasnext variable declaration fragment variabledeclarationfragment variable declaration fragment variabledeclarationfragment accept has next hasnext nls buffer append nls nls buffer append astvisitor visit for statement forstatement visit for statement forstatement node print indent printindent nls buffer append iterator node initializers iterator has next hasnext expression expression accept nls has next hasnext buffer append nls buffer append node get expression getexpression node get expression getexpression accept nls buffer append iterator node updaters iterator has next hasnext expression expression accept nls has next hasnext buffer append nls buffer append node get body getbody accept astvisitor visit if statement ifstatement visit if statement ifstatement node print indent printindent nls buffer append node get expression getexpression accept nls buffer append node get then statement getthenstatement accept node get else statement getelsestatement nls buffer append node get else statement getelsestatement accept astvisitor visit import declaration importdeclaration visit import declaration importdeclaration node print indent printindent nls buffer append node get ast getast api level apilevel ast jls node is static isstatic nls buffer append nls node get name getname accept node is on demand isondemand nls buffer append nls buffer append astvisitor visit infix expression infixexpression visit infix expression infixexpression node node get left operand getleftoperand accept cases buffer append buffer append node get operator getoperator to string tostring buffer append node get right operand getrightoperand accept list extended operands extendedoperands node extended operands extendedoperands extended operands extendedoperands size buffer append iterator extended operands extendedoperands iterator has next hasnext buffer append node get operator getoperator to string tostring append expression expression accept astvisitor visit instanceof expression instanceofexpression visit instanceof expression instanceofexpression node node get left operand getleftoperand accept nls buffer append node get right operand getrightoperand accept astvisitor visit initializer visit initializer node node get javadoc getjavadoc node get javadoc getjavadoc accept node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers node get body getbody accept astvisitor visit javadoc visit javadoc node print indent printindent nls buffer append iterator node tags iterator has next hasnext astnode astnode accept nls buffer append astvisitor visit labeled statement labeledstatement visit labeled statement labeledstatement node print indent printindent node get label getlabel accept nls buffer append node get body getbody accept astvisitor visit line comment linecomment visit line comment linecomment node nls buffer append astvisitor visit marker annotation markerannotation visit marker annotation markerannotation node nls buffer append node get type name gettypename accept astvisitor visit member ref memberref visit member ref memberref node node get qualifier getqualifier node get qualifier getqualifier accept nls buffer append node get name getname accept astvisitor visit member value pair membervaluepair visit member value pair membervaluepair node node get name getname accept nls buffer append node get value getvalue accept astvisitor visit method ref methodref visit method ref methodref node node get qualifier getqualifier node get qualifier getqualifier accept nls buffer append node get name getname accept nls buffer append iterator node parameters iterator has next hasnext method ref parameter methodrefparameter method ref parameter methodrefparameter accept has next hasnext nls buffer append nls nls buffer append astvisitor visit method ref parameter methodrefparameter visit method ref parameter methodrefparameter node node get type gettype accept node get ast getast api level apilevel ast jls node is varargs isvarargs nls buffer append nls node get name getname nls buffer append node get name getname accept astvisitor visit method declaration methoddeclaration visit method declaration methoddeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept print indent printindent node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers node type parameters typeparameters is empty isempty nls buffer append nls iterator node type parameters typeparameters iterator has next hasnext type parameter typeparameter type parameter typeparameter accept has next hasnext nls buffer append nls nls buffer append nls node is constructor isconstructor node get ast getast api level apilevel ast jls internal node internal get return type internalgetreturntype accept node get return type getreturntype node get return type getreturntype accept methods type nls buffer append nls nls buffer append node get name getname accept nls buffer append iterator node parameters iterator has next hasnext single variable declaration singlevariabledeclaration single variable declaration singlevariabledeclaration accept has next hasnext nls buffer append nls nls buffer append node get extra dimensions getextradimensions nls buffer append node thrown exceptions thrownexceptions is empty isempty nls buffer append iterator node thrown exceptions thrownexceptions iterator has next hasnext accept has next hasnext nls buffer append nls nls buffer append node get body getbody nls buffer append node get body getbody accept astvisitor visit method invocation methodinvocation visit method invocation methodinvocation node node get expression getexpression node get expression getexpression accept nls buffer append node get ast getast api level apilevel ast jls node type arguments typearguments is empty isempty nls buffer append nls iterator node type arguments typearguments iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls node get name getname accept nls buffer append iterator node arguments iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append astvisitor visit modifier visit modifier node buffer append node get keyword getkeyword to string tostring astvisitor visit normal annotation normalannotation visit normal annotation normalannotation node nls buffer append node get type name gettypename accept nls buffer append iterator node values iterator has next hasnext member value pair membervaluepair member value pair membervaluepair accept has next hasnext nls buffer append nls nls buffer append astvisitor visit null literal nullliteral visit null literal nullliteral node nls buffer append astvisitor visit number literal numberliteral visit number literal numberliteral node buffer append node get token gettoken astvisitor visit package declaration packagedeclaration visit package declaration packagedeclaration node node get ast getast api level apilevel ast jls node get javadoc getjavadoc node get javadoc getjavadoc accept iterator node annotations iterator has next hasnext annotation annotation accept nls buffer append nls print indent printindent nls buffer append node get name getname accept nls buffer append astvisitor visit parameterized type parameterizedtype visit parameterized type parameterizedtype node node get type gettype accept nls buffer append iterator node type arguments typearguments iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append astvisitor visit parenthesized expression parenthesizedexpression visit parenthesized expression parenthesizedexpression node nls buffer append node get expression getexpression accept nls buffer append astvisitor visit postfix expression postfixexpression visit postfix expression postfixexpression node node get operand getoperand accept buffer append node get operator getoperator to string tostring astvisitor visit prefix expression prefixexpression visit prefix expression prefixexpression node buffer append node get operator getoperator to string tostring node get operand getoperand accept astvisitor visit primitive type primitivetype visit primitive type primitivetype node buffer append node get primitive type code getprimitivetypecode to string tostring astvisitor visit qualified name qualifiedname visit qualified name qualifiedname node node get qualifier getqualifier accept nls buffer append node get name getname accept astvisitor visit qualified type qualifiedtype visit qualified type qualifiedtype node node get qualifier getqualifier accept nls buffer append node get name getname accept astvisitor visit return statement returnstatement visit return statement returnstatement node print indent printindent nls buffer append node get expression getexpression nls buffer append node get expression getexpression accept nls buffer append astvisitor visit simple name simplename visit simple name simplename node buffer append node get identifier getidentifier astvisitor visit simple type simpletype visit simple type simpletype node astvisitor visit single member annotation singlememberannotation visit single member annotation singlememberannotation node nls buffer append node get type name gettypename accept nls buffer append node get value getvalue accept nls buffer append astvisitor visit single variable declaration singlevariabledeclaration visit single variable declaration singlevariabledeclaration node print indent printindent node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers node get type gettype accept node get ast getast api level apilevel ast jls node is varargs isvarargs nls buffer append nls nls buffer append node get name getname accept node get extra dimensions getextradimensions nls buffer append node get initializer getinitializer nls buffer append node get initializer getinitializer accept astvisitor visit string literal stringliteral visit string literal stringliteral node buffer append node get escaped value getescapedvalue astvisitor visit super constructor invocation superconstructorinvocation visit super constructor invocation superconstructorinvocation node print indent printindent node get expression getexpression node get expression getexpression accept nls buffer append node get ast getast api level apilevel ast jls node type arguments typearguments is empty isempty nls buffer append nls iterator node type arguments typearguments iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls nls buffer append iterator node arguments iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append astvisitor visit super field access superfieldaccess visit super field access superfieldaccess node node get qualifier getqualifier node get qualifier getqualifier accept nls buffer append nls buffer append node get name getname accept astvisitor visit super method invocation supermethodinvocation visit super method invocation supermethodinvocation node node get qualifier getqualifier node get qualifier getqualifier accept nls buffer append nls buffer append node get ast getast api level apilevel ast jls node type arguments typearguments is empty isempty nls buffer append nls iterator node type arguments typearguments iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls node get name getname accept nls buffer append iterator node arguments iterator has next hasnext expression expression accept has next hasnext nls buffer append nls nls buffer append astvisitor visit switch case switchcase visit switch case switchcase node node is default isdefault nls buffer append nls buffer append node get expression getexpression accept nls buffer append decremented visit switch statement switchstatement indent astvisitor visit switch statement switchstatement visit switch statement switchstatement node nls buffer append node get expression getexpression accept nls buffer append nls buffer append indent iterator node statements iterator has next hasnext statement statement accept incremented visit switch case switchcase indent indent print indent printindent nls buffer append astvisitor visit synchronized statement synchronizedstatement visit synchronized statement synchronizedstatement node nls buffer append node get expression getexpression accept nls buffer append node get body getbody accept astvisitor visit tag element tagelement visit tag element tagelement node node is nested isnested nested tags enclosed braces nls buffer append top level tags nls buffer append previous requires white space previousrequireswhitespace node get tag name gettagname buffer append node get tag name gettagname previous requires white space previousrequireswhitespace previous requires new line previousrequiresnewline iterator node fragments iterator has next hasnext astnode astnode assume text elements include leading trailing whitespace member ref memberref method ref methodref nested tag element tagelement include white space current includes white space currentincludeswhitespace text element textelement previous requires new line previousrequiresnewline current includes white space currentincludeswhitespace nls buffer append nls previous requires new line previousrequiresnewline current includes white space currentincludeswhitespace add space required separate previous requires white space previousrequireswhitespace current includes white space currentincludeswhitespace nls buffer append nls accept previous requires white space previousrequireswhitespace current includes white space currentincludeswhitespace tag element tagelement node is nested isnested nls buffer append astvisitor visit text element textelement visit text element textelement node buffer append node get text gettext astvisitor visit this expression thisexpression visit this expression thisexpression node node get qualifier getqualifier node get qualifier getqualifier accept nls buffer append nls buffer append astvisitor visit throw statement throwstatement visit throw statement throwstatement node print indent printindent nls buffer append node get expression getexpression accept nls buffer append astvisitor visit try statement trystatement visit try statement trystatement node print indent printindent nls buffer append node get body getbody accept nls buffer append iterator node catch clauses catchclauses iterator has next hasnext catch clause catchclause catch clause catchclause accept node get finally getfinally nls buffer append node get finally getfinally accept astvisitor visit type declaration typedeclaration visit type declaration typedeclaration node node get javadoc getjavadoc node get javadoc getjavadoc accept node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers buffer append node is interface isinterface nls nls node is interface isinterface nls buffer append aj type declaration ajtypedeclaration node is aspect isaspect nls buffer append aspect nls buffer append node get name getname accept node get ast getast api level apilevel ast jls node type parameters typeparameters is empty isempty nls buffer append nls iterator node type parameters typeparameters iterator has next hasnext type parameter typeparameter type parameter typeparameter accept has next hasnext nls buffer append nls nls buffer append nls nls buffer append node get ast getast api level apilevel ast jls internal node internal get superclass internalgetsuperclass nls buffer append nls node internal get superclass internalgetsuperclass accept nls buffer append nls node internal super interfaces internalsuperinterfaces is empty isempty buffer append node is interface isinterface nls nls node is interface isinterface nls buffer append nls nls buffer append nls iterator node internal super interfaces internalsuperinterfaces iterator has next hasnext accept has next hasnext nls buffer append nls nls buffer append nls node get ast getast api level apilevel ast jls node get superclass type getsuperclasstype nls buffer append nls node get superclass type getsuperclasstype accept nls buffer append nls node super interface types superinterfacetypes is empty isempty buffer append node is interface isinterface nls nls node is interface isinterface nls buffer append nls nls buffer append nls iterator node super interface types superinterfacetypes iterator has next hasnext type type accept has next hasnext nls buffer append nls nls buffer append nls nls buffer append indent body declaration bodydeclaration prev iterator node body declarations bodydeclarations iterator has next hasnext body declaration bodydeclaration body declaration bodydeclaration prev enum constant declaration enumconstantdeclaration enum constant declarations include punctuation enum constant declaration enumconstantdeclaration enum constant declarations separated commas nls buffer append nls semicolon separates enum constant declaration body declarations nls buffer append nls accept indent print indent printindent nls buffer append astvisitor visit type declaration statement typedeclarationstatement visit type declaration statement typedeclarationstatement node node get ast getast api level apilevel ast jls internal node internal get type declaration internalgettypedeclaration accept node get ast getast api level apilevel ast jls node get declaration getdeclaration accept astvisitor visit type literal typeliteral visit type literal typeliteral node node get type gettype accept nls buffer append astvisitor visit type parameter typeparameter visit type parameter typeparameter node node get name getname accept node type bounds typebounds is empty isempty nls buffer append iterator node type bounds typebounds iterator has next hasnext type type accept has next hasnext nls buffer append nls astvisitor visit variable declaration expression variabledeclarationexpression visit variable declaration expression variabledeclarationexpression node node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers node get type gettype accept nls buffer append iterator node fragments iterator has next hasnext variable declaration fragment variabledeclarationfragment variable declaration fragment variabledeclarationfragment accept has next hasnext nls buffer append nls astvisitor visit variable declaration fragment variabledeclarationfragment visit variable declaration fragment variabledeclarationfragment node node get name getname accept node get extra dimensions getextradimensions nls buffer append node get initializer getinitializer nls buffer append node get initializer getinitializer accept astvisitor visit variable declaration statement variabledeclarationstatement visit variable declaration statement variabledeclarationstatement node print indent printindent node get ast getast api level apilevel ast jls internal print modifiers printmodifiers node get modifiers getmodifiers node get ast getast api level apilevel ast jls print modifiers printmodifiers node modifiers node get type gettype accept nls buffer append iterator node fragments iterator has next hasnext variable declaration fragment variabledeclarationfragment variable declaration fragment variabledeclarationfragment accept has next hasnext nls buffer append nls nls buffer append astvisitor visit wildcard type wildcardtype visit wildcard type wildcardtype node nls buffer append type bound node get bound getbound bound node is upper bound isupperbound nls buffer append nls nls buffer append nls bound accept astvisitor visit while statement whilestatement visit while statement whilestatement node print indent printindent nls buffer append node get expression getexpression accept nls buffer append node get body getbody accept