copyright contributors rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors mik kersten initial implementation andy clement copied changed inpath testing org aspectj ajde java java util java util jar jar input stream jarinputstream java util zip org aspectj util file util fileutil author websterm inpath testcase inpathtestcase ajde test case ajdetestcase string project dir inpath test inpathtest string bin dir bindir bin string indir indir string indir indir string injar name injarname injar jar string outjar name outjarname bin output jar constructor jar resource copy test case jarresourcecopytestcase param arg inpath testcase inpathtestcase string arg arg ensure output directpry clean set up setup exception set up setup project dir file util fileutil delete contents deletecontents open file openfile bin dir bindir inputs compiler inpath indir source src output jar file expected result output jar file contents indir file source src test inpath to outjar testinpathtooutjar inpath hash set hashset file indir open file openfile indir inpath add indir ide manager idemanager get project properties getprojectproperties set inpath setinpath inpath file outjar open file openfile outjar name outjarname ide manager idemanager get project properties getprojectproperties set out jar setoutjar outjar get absolute path getabsolutepath assert true asserttrue build failed do synchronous build dosynchronousbuild build lst assert true asserttrue build warnings ide manager idemanager get compilation source line tasks getcompilationsourcelinetasks is empty isempty expected output jar contents expectedoutputjarcontents hash set hashset indir don copy resources files won expected output jar contents expectedoutputjarcontents add meta inf manifest expected output jar contents expectedoutputjarcontents add meta inf test xml expected output jar contents expectedoutputjarcontents add test test props expected output jar contents expectedoutputjarcontents add test test properties testproperties src expected output jar contents expectedoutputjarcontents add main compare jars comparejars indir src outjar expected output jar contents expectedoutputjarcontents tidy file util fileutil delete contents deletecontents open file openfile bin dir bindir open file openfile bin dir bindir delete assert false assertfalse open file openfile bin dir bindir exists test outputs directory jar test inpath to bin testinpathtobin inpath hash set hashset file indir open file openfile indir inpath add indir ide manager idemanager get project properties getprojectproperties set inpath setinpath inpath assert true asserttrue build failed do synchronous build dosynchronousbuild build lst assert true asserttrue build warnings ide manager idemanager get compilation source line tasks getcompilationsourcelinetasks is empty isempty expected bindir contents expectedbindircontents hash set hashset indir don copy resources files won expected bindir contents expectedbindircontents add meta inf manifest expected bindir contents expectedbindircontents add meta inf test xml expected bindir contents expectedbindircontents add test test props expected bindir contents expectedbindircontents add test test properties testproperties src expected bindir contents expectedbindircontents add main compare indir to bin compareindirtobin indir src bin expected bindir contents expectedbindircontents tidy file util fileutil delete contents deletecontents open file openfile bin dir bindir open file openfile bin dir bindir delete assert false assertfalse open file openfile bin dir bindir exists inputs compiler inpath indir helloworld source file file source src aspect java weaves advice hello world helloworld code indir expected result hello world helloworld copied output jar weaved compiled version aspect java output jar hello world helloworld java source file copied output jar extra check test verify hello world helloworld changed size weaving test inpath to outjar testinpathtooutjar inpath hash set hashset file indir open file openfile indir inpath add indir ide manager idemanager get project properties getprojectproperties set inpath setinpath inpath file outjar open file openfile outjar name outjarname ide manager idemanager get project properties getprojectproperties set out jar setoutjar outjar get absolute path getabsolutepath assert true asserttrue build failed do synchronous build dosynchronousbuild build lst assert true asserttrue build warnings ide manager idemanager get compilation source line tasks getcompilationsourcelinetasks is empty isempty expected output jar contents expectedoutputjarcontents hash set hashset indir expected output jar contents expectedoutputjarcontents add hello world helloworld don copy resources file won expected output jar contents expectedoutputjarcontents add hello world helloworld java src expected output jar contents expectedoutputjarcontents add aspect compare jars comparejars indir src outjar expected output jar contents expectedoutputjarcontents extra test hello world helloworld input directory woven aspect verify size hello world helloworld output directory size input version outputsize fetch from jar fetchfromjar outjar hello world helloworld file input stream fileinputstream fis file input stream fileinputstream open file openfile indir hello world helloworld filedata file util fileutil read as byte array readasbytearray fis inputsize filedata length assert true asserttrue weaving aspect occurred input output size hello world helloworld inputsize outputsize exception print stack trace printstacktrace fail file util fileutil delete contents deletecontents open file openfile bin dir bindir open file openfile bin dir bindir delete assert false assertfalse open file openfile bin dir bindir exists complex inpath jar directory inputs inpath injar jar indir source src aspect java expected result result directory contents injar jar indir aspect file test inpath and injar to bin testinpathandinjartobin inpath hash set hashset file indir open file openfile indir inpath add indir inpath add open file openfile injar name injarname ide manager idemanager get project properties getprojectproperties set inpath setinpath inpath assert true asserttrue build failed do synchronous build dosynchronousbuild build lst assert true asserttrue build warnings ide manager idemanager get compilation source line tasks getcompilationsourcelinetasks is empty isempty expected bindir contents expectedbindircontents hash set hashset indir expected bindir contents expectedbindircontents add hello world helloworld don copy resources file won expected bindir contents expectedbindircontents add hello world helloworld java injar jar expected bindir contents expectedbindircontents add props resources properties src expected bindir contents expectedbindircontents add aspect compare indir to bin compareindirtobin indir src bin expected bindir contents expectedbindircontents check input output versions hello world helloworld sizes file input stream fileinputstream fis file input stream fileinputstream open file openfile indir hello world helloworld filedata file util fileutil read as byte array readasbytearray fis inputsize filedata length file input stream fileinputstream fis file input stream fileinputstream open file openfile bin hello world helloworld filedata file util fileutil read as byte array readasbytearray fis outputsize filedata length assert true asserttrue weaving aspect occurred input output size hello world helloworld outputsize inputsize fis close fis close exception print stack trace printstacktrace fail file util fileutil delete contents deletecontents open file openfile bin dir bindir open file openfile bin dir bindir delete assert false assertfalse open file openfile bin dir bindir exists size entry output jar file fetch from jar fetchfromjar file outjar file outjarfile string filename ret jar input stream jarinputstream outjar outjar jar input stream jarinputstream java file input stream fileinputstream outjar file outjarfile zip entry zipentry entry entry zip entry zipentry outjar get next entry getnextentry string zipentryname entry get name getname zipentryname equals filename filedata file util fileutil read as byte array readasbytearray outjar ret filedata length outjar close entry closeentry outjar close entry closeentry outjar close file not found exception filenotfoundexception print stack trace printstacktrace ioexception print stack trace printstacktrace ret ensure outjar java resouces injars compare jars comparejars file dir file dirfile string source dir sourcedir file outjar file outjarfile expected output jar contents expectedoutputjarcontents assert true asserttrue outjar older injar outjar file outjarfile last modified lastmodified dir file dirfile last modified lastmodified output jar file element remove expected output jar contents expectedoutputjarcontents finish expected output jar contents expectedoutputjarcontents empty jar input stream jarinputstream outjar jar input stream jarinputstream java file input stream fileinputstream outjar file outjarfile zip entry zipentry entry entry outjar get next entry getnextentry string file name filename entry get name getname file name filename file name filename replace file name filename index of indexof cvs expected output jar contents expectedoutputjarcontents remove file name filename assert true asserttrue unexpectedly file name filename outjar outjar close entry closeentry outjar close assert true asserttrue didnt output jar expected output jar contents expectedoutputjarcontents to string tostring expected output jar contents expectedoutputjarcontents is empty isempty ioexception fail to string tostring compare source to outjar comparesourcetooutjar string indir name indirname file outjar file outjarfile hash set hashset resources hash set hashset list source resources listsourceresources indir name indirname resources jar input stream jarinputstream outjar jar input stream jarinputstream java file input stream fileinputstream outjar file outjarfile zip entry zipentry entry entry outjar get next entry getnextentry string file name filename entry get name getname file name filename ends with endswith resources remove file name filename assert true asserttrue file name filename outjar close entry closeentry outjar close assert true asserttrue missing resources resources to string tostring resources is empty isempty ioexception fail to string tostring compare indir to bin compareindirtobin file indir file indirfile string source dir sourcedir string outdir name outdirname expected outdir contents expectedoutdircontents file bin base binbase open file openfile outdir name outdirname string to resources toresources file util fileutil list files listfiles bin base binbase to resources toresources length string file name filename to resources toresources file name filename index of indexof cvs expected outdir contents expectedoutdircontents remove file name filename assert true asserttrue extraneous resources file name filename assert true asserttrue missing resources expected outdir contents expectedoutdircontents to string tostring expected outdir contents expectedoutdircontents is empty isempty list source resources listsourceresources string indir name indirname resources file src base srcbase open file openfile indir name indirname file from resources fromresources file util fileutil list files listfiles src base srcbase aspectj resource file filter aspectjresourcefilefilter from resources fromresources length string file util fileutil normalized path normalizedpath from resources fromresources src base srcbase starts with startswith cvs index of indexof cvs ends with endswith cvs resources add file filter filefilter aspectj resource file filter aspectjresourcefilefilter file filter filefilter accept file pathname string pathname get name getname to lower case tolowercase ends with endswith ends with endswith java ends with endswith compare dirs comparedirs string indir name indirname string outdir name outdirname file bin base binbase open file openfile outdir name outdirname file to resources toresources file util fileutil list files listfiles bin base binbase aspectj resource file filter aspectjresourcefilefilter hash set hashset resources hash set hashset list source resources listsourceresources indir name indirname resources to resources toresources length string file name filename file util fileutil normalized path normalizedpath to resources toresources bin base binbase resources remove file name filename assert true asserttrue extraneous resources file name filename assert true asserttrue missing resources resources to string tostring resources is empty isempty