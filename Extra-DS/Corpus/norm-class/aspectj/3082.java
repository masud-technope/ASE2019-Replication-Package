copyright xerox corporation palo alto center incorporated parc rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors xerox parc initial implementation org aspectj internal tools build java buffered reader bufferedreader java byte array output stream bytearrayoutputstream java file java file input stream fileinputstream java file reader filereader java ioexception java print stream printstream java util array list arraylist java util iterator java util list java util list iterator listiterator java util properties java util string tokenizer stringtokenizer org aspectj internal tools build result kind represents eclipse build module unit builder compile classes assemble zip file classes optionally antecedants implementation infers attributes files module directory eclipse project code classpath code file required libraries modules collectively antecedants file code module name modulename txt code manifest jar file produced filtering builder modules get module getmodule string module string atts string exported kind path sourcepath get attsindex getattsindex string key atts length atts equals key file read modified time out of date outofdate time file file file file can read canread file last modified lastmodified time source files src dir srcdir iterator source files sourcefiles file src dir srcdir array list arraylist result array list arraylist source files sourcefiles src dir srcdir result result iterator source files sourcefiles file src dir srcdir list result src dir srcdir src dir srcdir can read canread src dir srcdir is directory isdirectory file files src dir srcdir list files listfiles files length files is directory isdirectory source files sourcefiles files result is source file issourcefile files result add files add if new addifnew list source list sink iterator iter source iterator iter has next hasnext object item iter sink item sink add item recursively find antecedant jars find known jar antecedants findknownjarantecedants do find jar requirements dofindjarrequirements result result list util iax if null iaxifnull result result util iax if null iaxifnull add if new addifnew result get lib jars getlibjars add if new addifnew result get exported lib jars getexportedlibjars result reqs result get required getrequired reqs length result required result requiredresult reqs file required jar requiredjar required result requiredresult get output file getoutputfile required jar requiredjar add required jar requiredjar do find jar requirements dofindjarrequirements required result requiredresult source file is source file issourcefile file file string path file get path getpath xxxfile literal xxxfileliteral path ends with endswith java path ends with endswith list file module library jar suffix array list arraylist find jars by suffix findjarsbysuffix string suffix kind kind list lib jars libjars list required array list arraylist result array list arraylist suffix library jars iterator iter lib jars libjars iterator iter has next hasnext file file file iter file get path getpath ends with endswith suffix result add file module jars iterator iter required iterator iter has next hasnext module module module iter result module result moduleresult module get result getresult kind file file module result moduleresult get output file getoutputfile file get path getpath ends with endswith suffix result add file result valid file module dir moduledir string reference collection creating required modules modules modules result release result test result test all testall result release all releaseall path output jar exist file module jar modulejar path fully assembed jar exist file assembled jar assembledjar file list library jars list lib jars libjars string list classpath variables list classpath variables classpathvariables file list library jars exported clients duplicates lib jars libjars entries list exported lib jars exportedlibjars file list source directories list src dirs srcdirs properties modules properties file properties properties module list required modules list required modules requiredmodules list file newer module jar modulejar requested list newer files newerfiles out of date outofdate calculated out of date set outofdateset logger messager messager file jar dir jardir module file module dir moduledir file jar dir jardir string modules modules messager messager util iax if not can read dir iaxifnotcanreaddir module dir moduledir module dir moduledir util iax if not can read dir iaxifnotcanreaddir jar dir jardir jar dir jardir util iax if null iaxifnull util iax if null iaxifnull modules modules module dir moduledir module dir moduledir jar dir jardir jar dir jardir lib jars libjars array list arraylist exported lib jars exportedlibjars array list arraylist required modules requiredmodules array list arraylist src dirs srcdirs array list arraylist classpath variables classpathvariables array list arraylist properties properties modules modules messager messager module jar modulejar file jar dir jardir jar assembled jar assembledjar file jar dir jardir jar release result result release jar dir jardir release all releaseall result result release jar dir jardir test result result test jar dir jardir test all testall result result test jar dir jardir valid init modules registry modules including modules get modules getmodules modules param kind kind result recalculate param recalculate force recalculation target jar module older source files source directory required modules libraries libraries required modules missing out of date outofdate result result file output file outputfile result get output file getoutputfile output file outputfile exists output file outputfile can read canread time output file outputfile last modified lastmodified file file iterator iter result get src dirs getsrcdirs iterator iter has next hasnext file src dir srcdir file iter iterator src files srcfiles source files sourcefiles src dir srcdir src files srcfiles has next hasnext file file src files srcfiles out of date outofdate time file required modules result reqs result get required getrequired reqs length result required result requiredresult reqs file required result requiredresult get output file getoutputfile out of date outofdate time file libraries iterator iter result get lib jars getlibjars iterator iter has next hasnext file file iter out of date outofdate time file string to string tostring string to long string tolongstring module src dirs srcdirs src dirs srcdirs required required modules requiredmodules module jar modulejar module jar modulejar lib jars libjars lib jars libjars result get result getresult kind kind kind assemble kind normal release all releaseall test all testall kind normal release test list src dirs srcdirs result result my result myresult result src dirs srcdirs list lib jars libjars result result my result myresult result lib jars libjars list classpath variables classpathvariables result result my result myresult result classpath variables classpathvariables list exported lib jars exportedlibjars result result my result myresult result exported lib jars exportedlibjars list required modules requiredmodules result result my result myresult result required modules requiredmodules my result myresult result result result result get module getmodule illegal argument exception illegalargumentexception result result init init classpath initclasspath init properties initproperties review init reviewinit init results initresults read eclipse classpath file xxx oriented hack init classpath initclasspath meaning testsrc directory junit library xxxfile literal xxxfileliteral file file file module dir moduledir classpath file reader filereader fin fin file reader filereader file buffered reader bufferedreader reader buffered reader bufferedreader fin string xmlitem item xmlitem classpathentry icb reader read line readline trim dumb handle comment lines starts with startswith xml starts with startswith item accept line acceptline src dirs srcdirs size lib jars libjars size ioexception messager log exception logexception ioexception reading file fin fin close ioexception ignore update string to string tostring string attributes string kind attributes get attsindex getattsindex kind string path attributes get attsindex getattsindex path string exp attributes get attsindex getattsindex exported exported equals exp update kind path to string tostring exported update string kind string path string to string tostring exported string lib path libpath src equals kind path starts with startswith module string module name modulename path substring module req modules get module getmodule module name modulename req required modules requiredmodules add req messager error update unable create required module module name modulename src dir string full path fullpath get full path getfullpath path file src dir srcdir file full path fullpath src dir srcdir can read canread src dir srcdir is directory isdirectory src dirs srcdirs add src dir srcdir messager error src dir src dir srcdir lib equals kind lib path libpath path equals kind string java java path starts with startswith java path path substring java length string system get property getproperty java lib path libpath util path path file file lib path libpath exists ends with endswith jre file get parent file getparentfile lib path libpath util path get path getpath path lib path libpath warn variable warnvariable path to string tostring classpath variables classpathvariables add path con equals kind path index of indexof jre warn jre containers messager log handle con to string tostring equals kind output equals kind ignore output entries messager log unrecognized kind kind to string tostring lib path libpath file lib jar libjar file lib path libpath lib jar libjar exists lib jar libjar file get full path getfullpath lib path libpath lib jar libjar can read canread lib jar libjar is file isfile lib jars libjars add lib jar libjar exported exported lib jars exportedlibjars add lib jar libjar messager error library jar lib jar libjar to string tostring warn variable warnvariable string path string to string tostring string jre lib aspectjrt lib jre lib length equals path messager log module handle to string tostring properties read correctly init properties initproperties xxxfile literal xxxfileliteral file file file module dir moduledir properties util can read file canreadfile file properties read file input stream fileinputstream fin fin file input stream fileinputstream file properties load fin ioexception messager log exception logexception ioexception reading file fin fin close ioexception ignore post process initialization implementation trims java source dirs running java initialization post processing worked review init reviewinit list iterator listiterator iter src dirs srcdirs list iterator listiterator iter has next hasnext file src dir srcdir file iter string lcname src dir srcdir get name getname to lower case tolowercase util java util constants java src equals lcname util constants java testsrc equals lcname assume optional pre builds iter remove unsupported operation exception unsupportedoperationexception review init reviewinit setup kinds init results initresults initialized lazily resolve path absolutely assuming base modules dir string get full path getfullpath string path string full path fullpath path starts with startswith full path fullpath modules base dir basedir get absolute path getabsolutepath path full path fullpath module dir moduledir get absolute path getabsolutepath path check absolute paths untested modules file test file testfile file full path fullpath test file testfile get absolute path getabsolutepath test file testfile exists test file testfile file path test file testfile exists test file testfile is absolute isabsolute full path fullpath path full path fullpath icb xmlitem icallback properties attributes string kind attributes get property getproperty kind string path attributes get property getproperty path string exp attributes get property getproperty exported exported equals exp byte array output stream bytearrayoutputstream bout byte array output stream bytearrayoutputstream attributes list print stream printstream bout update kind path bout to string tostring exported xmlitem icallback properties attributes string start classpathentry string att started started icallback callback string buffer stringbuffer input string buffer stringbuffer string attributes string atts length string target entity targetentity string entity name entityname string attribute name attributename xmlitem string target entity targetentity icallback callback callback callback target entity targetentity target entity targetentity reset reset input set length setlength attributes length attributes entity name entityname attribute name attributename string tokenize string string delim string tokenizer stringtokenizer string tokenizer stringtokenizer delim array list arraylist result array list arraylist string buffer stringbuffer quote string buffer stringbuffer in quote inquote has more tokens hasmoretokens string next token nexttoken length delim index of indexof equals quote escaped in quote inquote in quote inquote quote append result add quote to string tostring quote set length setlength quote append in quote inquote result add delimiter in quote inquote quote append result add string result to array toarray string accept line acceptline string string tokens tokenize tokens length tokens properties attributes to properties attributestoproperties properties result properties attributes length string attributes result set property setproperty atts result error if not null errorifnotnull string string error expect error if null errorifnull string string error expected active entity activeentity target entity targetentity equals entity name entityname assumes comments xml style lines removed string length input append trim length equals error if not null errorifnotnull entity name entityname entity name entityname error if not null errorifnotnull attribute name attributename attribute name attributename equals error if null errorifnull entity name entityname entity name entityname equals attribute name attributename attribute name attributename error if not null errorifnotnull attribute name attributename attribute name attributename active entity activeentity callback attributes to properties attributestoproperties entity name entityname equals error if null errorifnull entity name entityname entity name entityname error if null errorifnull attribute name attributename attribute name attributename starts with startswith error if null errorifnull entity name entityname entity name entityname error if null errorifnull attribute name attributename attribute name attributename write attribute writeattribute attribute name attributename attribute name attributename entity name entityname reset entity name entityname attribute name attributename attribute name attributename system println unknown attribute entity read attribute readattribute string atts length equals atts attributes att started write attribute writeattribute string string atts length equals atts starts with startswith ends with endswith error bad attribute substring length attributes error string error input input