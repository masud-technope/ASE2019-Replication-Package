copyright ibm corporation rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors andy clement initial implementation org aspectj ajdt internal compiler batch java file java util array list arraylist java util iterator java util list org aspectj apache bcel repository org aspectj apache bcel classfile java class javaclass org aspectj apache bcel verifier verification result verificationresult org aspectj apache bcel verifier verifier org aspectj apache bcel verifier verifier factory verifierfactory org aspectj bridge imessage org aspectj tools ajc ajc test case ajctestcase org aspectj tools ajc compilation result compilationresult tests verify behavior binary implementation declare parents basically attempt source compile classes aspects errors warnings weaving messages compile source files aspects separately binary weave weaving messages execute classes binary weave check passes verifier executes notes implementation testcases marked missing inherited methods overridden reduce visibility cope subclass decp target type hey watch guys kids working classes don provide method implementations children field inheritance worry covariance method overrides supported untested java compiler write easy tests declare parents declareparents ajc test case ajctestcase verbose string project dir binary parents binaryparents file base dir basedir check order doesn difference order test verify order of processing irrelevant testverifyorderofprocessingirrelevant file test base testbase file base dir basedir test a testa run source and binary testcase runsourceandbinarytestcase test base testbase string java java string aspect ab aspectab run class runclass check order doesn difference order test verify order of processing irrelevant testverifyorderofprocessingirrelevant file test base testbase file base dir basedir test a testa run source and binary testcase runsourceandbinarytestcase test base testbase string java java string aspect ab aspectab run class runclass classes top middle bottom bottom top middle top aspect x aspectx declares bottom middle result fits hierarchy problem test simple declare parents testsimpledeclareparents file test base testbase file base dir basedir test a testa run source and binary testcase runsourceandbinarytestcase test base testbase string top java middle java bottom java string aspect x aspectx java run class runclass bottom classes top middle bottom bottom top middle top bottom includes call ctor aspect x aspectx declares bottom middle result fits hierarchy problem implementation call modified top init call middle init call test super ctor call superctorcall file test base testbase file base dir basedir test a testa run source and binary testcase runsourceandbinarytestcase test base testbase string top java middle java bottom java string aspect x aspectx java run class runclass bottom classes top middle bottom bottom top middle top bottom includes call method instance method aspect x aspectx declares bottom middle result implementation don modify call top bottom don jvm ensure chosen runtime nearest bottom hierarchy changed middle version works leaves subtle difference code generated decp application source time decp application weave time source time call bottom middle code gen weave time top practical difference easily morph top call middle call impact peformance crawling bytecodes change test super method call supermethodcall file test base testbase file base dir basedir test a testa run source and binary testcase runsourceandbinarytestcase test base testbase string top java middle java bottom java string aspect x aspectx java run class runclass bottom classes top middle bottom bottom top middle top aspect x aspectx declares bottom middle result fail middle doesn include ctor takes string called bottom test missing ctor in introduced class missingctorinintroducedclass file test base testbase file base dir basedir test a testa run source and binary testcase runsourceandbinarytestcase test base testbase string top java middle java bottom java string aspect x aspectx java overriding instance method overriding method instance method note error messages locations binary weaving source counterparts test cant make inherited instance methods static cantmakeinheritedinstancemethodsstatic file test base testbase file base dir basedir test c testc compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test c testc string java java string java extend xxxtest cant extend final class cantextendfinalclass xxx removed test discuss andy repair file test base testbase file base dir basedir test c testc compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test c testc string java java string java object subject declare parents tested aspect compiled couldn occur binary weaving decp inherit methods override reduce visibility test cant reduce visibility of overridden methods cantreducevisibilityofoverriddenmethods file test base testbase file base dir basedir test b testb compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test b testb string top java middle java string aspect java inherit methods override reduce visibility test checks methods superclass named parent test cant reduce visibility of overridden methods cantreducevisibilityofoverriddenmethods file test base testbase file base dir basedir test b testb compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test b testb string top top toptop java top java middle java string aspect java inherit methods incompatible types java messier test overridden methods cant have incompatible return types overriddenmethodscanthaveincompatiblereturntypes file test base testbase file base dir basedir test b testb compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test b testb string top java middle java java java string aspect java testing inherit methods provide implementation test simple test inherited abstract methods must be implemented inheritedabstractmethodsmustbeimplemented file test base testbase file base dir basedir test b testb compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test b testb string top java middle java java java string aspect java testing decp implement provide implementation test interface methods implemented interfacemethodsimplemented file test base testbase file base dir basedir test d testd compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase test base testbase string simple class simpleclass java simple intf simpleintf java string simple aspect simpleaspect java testing inherit methods provide implementation test includes methods hierarchy implemented test inherited abstract methods must be implemented inheritedabstractmethodsmustbeimplemented file test base testbase file base dir basedir test b testb compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test b testb string top top toptop java top java middle java string aspect java testing inherit methods provide implementation test includes methods hierarchy implemented dependencies satisfied itds aspect test inherited abstract methods must be implemented inheritedabstractmethodsmustbeimplemented file test base testbase file base dir basedir test d testd compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase file base dir basedir test d testd string simple class simpleclass java string simple aspect simpleaspect java adding type hierarchy missing ctor itdc test missing ctor added via itd missingctoraddedviaitd file test base testbase file base dir basedir test e teste compilation result compilationresult result run source and binary testcase runsourceandbinarytestcase test base testbase string java java java string java run source and binary testcase runsourceandbinarytestcase file test base testbase string classes string aspects expect errors expecterrors run source and binary testcase runsourceandbinarytestcase test base testbase classes aspects expect errors expecterrors run source and binary testcase runsourceandbinarytestcase file test base testbase string classes string aspects expect errors expecterrors compare errors compareerrors compile source compilation result compilationresult result execute ajc classes aspects show weave info showweaveinfo string source compile command line sourcecompilecommandline string classes length aspects length system arraycopy classes source compile command line sourcecompilecommandline classes length system arraycopy aspects source compile command line sourcecompilecommandline classes length aspects length string extra option extraoption string show weave info showweaveinfo system arraycopy extra option extraoption source compile command line sourcecompilecommandline classes length aspects length result ajc test base testbase source compile command line sourcecompilecommandline expect errors expecterrors assert true asserttrue errors result get error messages geterrormessages result has error messages haserrormessages list source weave messages sourceweavemessages get weave messages getweavemessages result source weave messages count sourceweavemessagescount source weave messages sourceweavemessages size list source error messages sourceerrormessages result get error messages geterrormessages source error messages count sourceerrormessagescount source error messages sourceerrormessages size verbose system err println source compilation error count source error messages count sourceerrormessagescount source error messages sourceerrormessages system err println source compilation weaving count source weave messages count sourceweavemessagescount source weave messages sourceweavemessages separate compiles classes aspects binary weave execute ajc classes classes result ajc test base testbase merge options mergeoptions classes string classes set should empty sandbox setshouldemptysandbox execute ajc aspects outjar aspects jar classpath classes proceed on error proceedonerror result ajc test base testbase merge options mergeoptions aspects string outjar aspects jar classpath classes proceed on error proceedonerror result get error messages geterrormessages size system err println expecting errors jar building result get error messages geterrormessages assert true asserttrue errors compile result get error messages geterrormessages size result get error messages geterrormessages size execute ajc inpath classes show weave info showweaveinfo classes aspectpath aspects jar result ajc test base testbase string inpath classes show weave info showweaveinfo classes aspectpath aspects jar expect errors expecterrors assert true asserttrue unexpected errors result get error messages geterrormessages result has error messages haserrormessages list binary weave messages binaryweavemessages get weave messages getweavemessages result binary weave messages count binaryweavemessagescount binary weave messages binaryweavemessages size list binary error messages binaryerrormessages result get error messages geterrormessages binary error messages count binaryerrormessagescount binary error messages binaryerrormessages size verbose system err println binary compilation error count binary error messages count binaryerrormessagescount binary error messages binaryerrormessages system err println binary compilation weaving count binary weave messages count binaryweavemessagescount binary weave messages binaryweavemessages system err println standard error standarderror binary compile stage result get standard error getstandarderror assert true asserttrue number errors source error messages count sourceerrormessagescount binary error messages count binaryerrormessagescount source error messages count sourceerrormessagescount binary error messages count binaryerrormessagescount check error messages comparable differing orderings compare errors compareerrors iterator iter binary error messages binaryerrormessages iterator iter has next hasnext imessage binary message binarymessage imessage iter imessage correct source message correctsourcemessage iterator iterator source error messages sourceerrormessages iterator iterator has next hasnext correct source message correctsourcemessage imessage source message sourcemessage imessage iterator source message sourcemessage get message getmessage equals binary message binarymessage get message getmessage correct source message correctsourcemessage source message sourcemessage correct source message correctsourcemessage fail error binary weaving binary message binarymessage equivalent list messages source compilation source error messages sourceerrormessages remove correct source message correctsourcemessage source error messages sourceerrormessages size iterator iter source error messages sourceerrormessages iterator iter has next hasnext imessage src msg srcmsg imessage iter system err println error message source compilation src msg srcmsg didn occur binary weaving fail source error messages sourceerrormessages size extra error messages source compilation check weaving messages comparable source weave messages count sourceweavemessagescount binary weave messages count binaryweavemessagescount fail didn number weave info messages source weaving binary weaving source weave messages count sourceweavemessagescount binary weave messages count binaryweavemessagescount check weaving messages comparable source weave messages sourceweavemessages size imessage imessage source weave messages sourceweavemessages imessage imessage binary weave messages binaryweavemessages string get details getdetails string get details getdetails equals system err println source weave messages source weave messages sourceweavemessages size source weave messages sourceweavemessages system err println binary weave messages binary weave messages binaryweavemessages size binary weave messages binaryweavemessages fail weaving messages source message sourcemessage binary message binarymessage get source location getsourcelocation get source location getsourcelocation get source location getsourcelocation equals get source location getsourcelocation fail source locations weaving messages get source location getsourcelocation get source location getsourcelocation check result binary weaving class path classpath class path classpath ajc get sandbox directory getsandboxdirectory file separator classes file path separator pathseparator system get property getproperty sun boot path system err println synthetic repository syntheticrepository synthetic repository syntheticrepository get instance getinstance repository set repository setrepository classes length string classes substring classes last index of lastindexof list verification problems verificationproblems verify assert true asserttrue expect verification problems verification problems verificationproblems verification problems verificationproblems size string merge options mergeoptions string input string extras string ret string input length extras length system arraycopy input ret input length system arraycopy extras ret input length extras length ret list get weave messages getweavemessages compilation result compilationresult result list info messages infomessages result get info messages getinfomessages list weave messages weavemessages array list arraylist iterator iter info messages infomessages iterator iter has next hasnext imessage element imessage iter element get kind getkind imessage weaveinfo weave messages weavemessages add element weave messages weavemessages set up setup exception set up setup base dir basedir file org aspectj ajdt core testdata project dir list verify string list verify problems verifyproblems array list arraylist system println verifying verifier verifier factory verifierfactory get verifier getverifier verification result verificationresult do pass dopass verification result verificationresult verify problems verifyproblems add pass get message getmessage do pass dopass verification result verificationresult verify problems verifyproblems add pass get message getmessage verification result verificationresult java class javaclass repository lookup class lookupclass get methods getmethods length do pass dopass verification result verificationresult verify problems verifyproblems add pass get methods getmethods get message getmessage do pass dopass verification result verificationresult verify problems verifyproblems add pass get methods getmethods get message getmessage system println warnings string warnings get messages getmessages warnings length system println warnings length system println warnings system println avoid swapping flush repository clear cache clearcache verify problems verifyproblems run class runclass string run result runresult string ajc get sandbox directory getsandboxdirectory file separator classes verify error verifyerror print stack trace printstacktrace fail unexpected verify error verifyerror type declared parents assert true asserttrue didn expect errors to string tostring