copyright palo alto center incorporated parc rights reserved program accompanying materials terms common license accompanies distribution http eclipse org legal cpl html contributors parc initial implementation org aspectj weaver patterns java data output stream dataoutputstream java ioexception java util map org aspectj util fuzzy boolean fuzzyboolean org aspectj util type safe enum typesafeenum org aspectj weaver advice org aspectj weaver advice kind advicekind org aspectj weaver bcexception org aspectj weaver checker org aspectj weaver isource context isourcecontext org aspectj weaver int map intmap org aspectj weaver resolved type resolvedtype org aspectj weaver shadow org aspectj weaver shadow munger shadowmunger org aspectj weaver versioned data input stream versioneddatainputstream org aspectj weaver ast literal org aspectj weaver ast test lifecycle pointcuts modeled pointcut creation symbolic resolve iscope resolved concretize concrete author erik hilsdale author jim hugunin day life pointcut amc pointcuts created pattern parser patternparser called ajdt parse pointcut pseudo tokens pseudotokens ast node turn pointcut designator pointcutdesignator ast node pointcuts resolved ajdt advice declaration advicedeclaration pointcut declaration pointcutdeclaration statements resolved complete type bindings completetypebindings aj lookup environment ajlookupenvironment called diet parse phase compiler named pointcuts references named pointcuts instances reference pointcut referencepointcut compilation process pointcuts serialized write method attributes file weaver loads files unpacks attributes deserializes pointcuts read aspects calling add or replace aspect addorreplaceaspect crosscutting members aspects replaced crosscutting members aspect extracted shadow mungers shadowmungers holding pointcut shadow mungers shadowmungers concretized concretizes pointcuts stage reference pointcuts referencepointcuts replaced declared content weaving weaver processes type type culls matching shadow mungers shadowmungers calling fast match fastmatch method pointcuts match phase phase shadows type created passed pointcut matching match actual munging matched pointcuts asked residue find residue findresidue runtime test negation find residue findresidue called pointcuts match shadow pointcut pattern node patternnode type safe enum typesafeenum string key key ataj formal don warning unbound implicitly bound join point joinpoint advices string ignore unbound binding for names ignoreunboundbindingfornames string symbolic symbolic resolved resolved concrete concrete pointcut kind pointcutkind last matched shadow id lastmatchedshadowid fuzzy boolean fuzzyboolean last matched shadow result lastmatchedshadowresult string type variables in scope typevariablesinscope string has been parameterized hasbeenparameterized constructor pattern pointcut symbolic match shadows code defined type fuzzy boolean fuzzyboolean fast match fastmatch fast match info fastmatchinfo info shadow kinds shadowkinds pointcut match bits kinds shadow java could match kinds couldmatchkinds string get type variables in scope gettypevariablesinscope type variables in scope typevariablesinscope set type variables in scope settypevariablesinscope string type vars typevars type variables in scope typevariablesinscope type vars typevars match shadow xxx implementors handle fuzzy boolean fuzzyboolean match shadow shadow shadow shadow id shadowid last matched shadow id lastmatchedshadowid last matched shadow result lastmatchedshadowresult fuzzy boolean fuzzyboolean ret test prevent lot needed matching shadow get kind getkind is set isset could match kinds couldmatchkinds ret match internal matchinternal shadow ret fuzzy boolean fuzzyboolean last matched shadow id lastmatchedshadowid shadow shadow id shadowid last matched shadow result lastmatchedshadowresult ret ret fuzzy boolean fuzzyboolean match internal matchinternal shadow shadow kinded target args reference cflow withincode handler annotation atwithin atwithincode atthis target change reorder sequence aspectj atargs user extension get pointcut kind getpointcutkind pointcut kind pointcutkind internal called resolve resolve bindings resolvebindings iscope scope bindings bindings returns pointcut mutated pointcut resolve iscope scope assert state assertstate symbolic bindings binding table bindingtable bindings scope get formal count getformalcount iscope binding resolution scope bindingresolutionscope scope type variables in scope typevariablesinscope length binding resolution scope bindingresolutionscope scope with type variables scopewithtypevariables type variables in scope typevariablesinscope scope resolve bindings resolvebindings binding resolution scope bindingresolutionscope binding table bindingtable binding table bindingtable check all bound checkallbound binding resolution scope bindingresolutionscope resolved returns pointcut test cases pointcut concretize resolved type resolvedtype in aspect inaspect resolved type resolvedtype declaring type declaringtype arity pointcut ret concretize in aspect inaspect declaring type declaringtype int map intmap id map idmap arity copy unbound ignore list ret ignore unbound binding for names ignoreunboundbindingfornames ignore unbound binding for names ignoreunboundbindingfornames ret xxx signature moving pointcut concretize resolved type resolvedtype in aspect inaspect resolved type resolvedtype declaring type declaringtype arity shadow munger shadowmunger advice concrete int map intmap map int map intmap id map idmap arity map set enclosing advice setenclosingadvice advice map set concrete aspect setconcreteaspect in aspect inaspect concretize in aspect inaspect declaring type declaringtype map is declare isdeclare shadow munger shadowmunger munger error munger method munger munger checker advice munger get kind getkind equals advice kind advicekind softener pointcut concretize resolved type resolvedtype in aspect inaspect resolved type resolvedtype declaring type declaringtype int map intmap bindings add test assert state assertstate resolved pointcut ret concretize in aspect inaspect declaring type declaringtype bindings should copy location for concretize shouldcopylocationforconcretize ret copy location from copylocationfrom ret concrete copy unbound ignore list ret ignore unbound binding for names ignoreunboundbindingfornames ignore unbound binding for names ignoreunboundbindingfornames ret should copy location for concretize shouldcopylocationforconcretize resolves removes reference pointcuts referencepointcuts replacing basic param in aspect inaspect aspect resolve relative param bindings map formal current lexical context formal concrete advice pointcut object concretized pointcut identical resolved behavior assumed places xxx implementors handle pointcut concretize resolved type resolvedtype in aspect inaspect resolved type resolvedtype declaring type declaringtype int map intmap bindings xxx implementors handle called not pointcut notpointcut pointcuts don match shadow test find residue findresidue shadow shadow exposed state exposedstate shadow shadow id shadowid last matched shadow id lastmatchedshadowid last matched shadow residue lastmatchedshadowresidue test ret find residue internal findresidueinternal shadow last matched shadow residue lastmatchedshadowresidue ret last matched shadow id lastmatchedshadowid shadow shadow id shadowid ret test find residue internal findresidueinternal shadow shadow exposed state exposedstate xxx needed xxx unused keeping stub post read postread resolved type resolvedtype enclosing type enclosingtype pointcut read versioned data input stream versioneddatainputstream isource context isourcecontext context ioexception kind read byte readbyte pointcut ret kind kinded ret kinded pointcut kindedpointcut read context ret within pointcut withinpointcut read context target ret this or target pointcut thisortargetpointcut read context args ret args pointcut argspointcut read context ret and pointcut andpointcut read context ret or pointcut orpointcut read context ret not pointcut notpointcut read context reference ret reference pointcut referencepointcut read context ret if pointcut ifpointcut read context cflow ret cflow pointcut cflowpointcut read context withincode ret withincode pointcut withincodepointcut read context handler ret handler pointcut handlerpointcut read context ret if pointcut ifpointcut make if true pointcut makeiftruepointcut resolved ret if pointcut ifpointcut make if false pointcut makeiffalsepointcut resolved annotation ret annotation pointcut annotationpointcut read context atwithin ret within annotation pointcut withinannotationpointcut read context atwithincode ret within code annotation pointcut withincodeannotationpointcut read context atthis target ret this or target annotation pointcut thisortargetannotationpointcut read context atargs ret args annotation pointcut argsannotationpointcut read context ret make matches nothing makematchesnothing resolved bcexception unknown kind kind ret resolved ret pointcut kind pointcutkind kind ret prepare shadow shadow test method pointcut from string fromstring string str pattern parser patternparser parser pattern parser patternparser str parser parse pointcut parsepointcut matches nothing pointcut matchesnothingpointcut pointcut test find residue internal findresidueinternal shadow shadow exposed state exposedstate earlier error occurred literal could match kinds couldmatchkinds shadow shadow kinds bits fuzzy boolean fuzzyboolean fast match fastmatch fast match info fastmatchinfo type fuzzy boolean fuzzyboolean fuzzy boolean fuzzyboolean match internal matchinternal shadow shadow fuzzy boolean fuzzyboolean resolve bindings resolvebindings iscope scope bindings bindings post read postread resolved type resolvedtype enclosing type enclosingtype pointcut concretize resolved type resolvedtype in aspect inaspect resolved type resolvedtype declaring type declaringtype int map intmap bindings make matches nothing makematchesnothing write data output stream dataoutputstream ioexception write byte writebyte string to string tostring object accept pattern node visitor patternnodevisitor visitor object data visitor visit data pointcut parameterize with parameterizewith map type variable map typevariablemap pointcut matches nothing matchesnothing matches nothing pointcut matchesnothingpointcut good optimizations point pointcut make matches nothing makematchesnothing pointcut ret matches nothing pointcut matchesnothingpointcut ret ret assert state assertstate bcexception expected pointcut parameterize with parameterizewith map type variable map typevariablemap