copyright ibm corporation rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors matthew webster initial implementation org aspectj ajdt internal compiler batch java file org aspectj bridge imessage org aspectj tools ajc ajc test case ajctestcase org aspectj tools ajc compilation result compilationresult org aspectj weaver dump compiler dump test case compilerdumptestcase ajc test case ajctestcase string project dir dump test case dumptestcase file base dir basedir file dump file dumpfile imessage kind saved dump condition saveddumpcondition set up setup exception set up setup base dir basedir file org aspectj ajdt core testdata project dir dump file dumpfile saved dump condition saveddumpcondition dump get dump on exit getdumponexit tear down teardown exception tear down teardown dump file dumpfile dump file dumpfile exists deleted dump file dumpfile delete assert true asserttrue dump file dump file dumpfile get path getpath deleted deleted dump set dump on exit setdumponexit saved dump condition saveddumpcondition aim dump successful compile ensure command inputs compiler hello world helloworld java pointcuts aspect expected result compile succeeds test dump testdump string args string src hello world helloworld java src pointcuts src aspect compilation result compilationresult result ajc base dir basedir args assert no messages assertnomessages result string file name filename dump dump dump test case dumptestcase test dump testdump dump file dumpfile file file name filename org aspectj weaver dump test case dumptestcase assert contents assertcontents dump file dumpfile command hello world helloworld java aim dump successful compile ensure warning messages inputs compiler hello world helloworld java pointcuts aspect declare warning declarewarning expected result compile succeeds test dump with warnings testdumpwithwarnings string args string src hello world helloworld java src pointcuts src declare warning declarewarning dump preserve on next reset preserveonnextreset compilation result compilationresult result ajc base dir basedir args string file name filename dump dump dump test case dumptestcase test dump with warnings testdumpwithwarnings dump file dumpfile file file name filename org aspectj weaver dump test case dumptestcase assert contents assertcontents dump file dumpfile compiler messages warning aim dump errors inputs compiler hello world helloworld java pointcuts aspect declare error declareerror expected result compile fails dump file created test with errors testwitherrors dump set dump on exit setdumponexit imessage error string previous file name previousfilename dump get last dump file name getlastdumpfilename string args string src hello world helloworld java src pointcuts src declare error declareerror compilation result compilationresult result ajc base dir basedir args string file name filename dump get last dump file name getlastdumpfilename assert true asserttrue dump file created file name filename equals previous file name previousfilename dump file dumpfile file file name filename org aspectj weaver dump test case dumptestcase assert contents assertcontents dump file dumpfile compiler messages error