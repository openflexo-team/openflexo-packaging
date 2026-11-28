# Openflexo 3.0 — Changelog

What changed since 2.99, one line per change, by repository. Identifiers in parentheses point to the
`BACKLOG.md` or `KNOWN_DEFECTS.md` of the repository, where the details are.

## pamela (2.0)

- PAMELA proxies are generated with Byte Buddy; javassist is removed (PAMELA-F-1)
  - creating objects is faster (about 25%) and uses less memory (about 30%)
  - `PamelaProxies` replaces the javassist `ProxyObject` API for testing a proxy and reaching its handler
- Fixed: an implementation class set at run time in another package was reported as a generated class (PAMELA-D-3)
- Fixed: an implementation class loaded by a child class loader was replaced by another copy of it (PAMELA-D-4)
- Fixed: undoing a deletion restores the objects as not deleted, at their place in their lists (PAMELA-D-8)
- Fixed: an object moved to another parent can again be removed from it, detached or deleted (PAMELA-D-9)
- A property can be declared transient (`@Getter(isTransient = true)`): not persistent, not compared, not cloned; a property is persistent when it is neither derived nor transient, independently of XML (PAMELA-F-3)
  - the models of openflexo-core, GINA, DIANA, documents, Excel, JDBC and XML mark their transient properties
- Entities without XML mapping may share their simple name in a model (PAMELA-F-3)
- `@Getter(ignoreForEquality)` is removed: equality compares every property that is neither derived nor transient (PAMELA-F-4)
- Fixed: a model entity that failed to import broke every later import (PAMELA-D-5)
- Fixed: `ModelExecutionException` lost the exception it wrapped and printed it (PAMELA-D-6)
- Fixed: a concept with a `void` behaviour could not be cloned or copied (PAMELA-D-7)
- Version 2.0, requires Java 17 or later; documentation updated

## openflexo-utils (2.0)

- Becomes the bottom layer of the infrastructure: it now contains the resource locator, the logging, the toolbox and the test utilities that were in `connie-utils` and `testutils` (the `org.openflexo` packages are unchanged)
- `p2pp-core` moves out into its own repository, `p2pp` (1.0), because it needs connie
- Fixed: the localization dictionaries saved by the application are sorted again on Java 9 and later, so a new entry changes one line and not the whole file (UTILS-D-1)
- Fixed: removing a metadata value of a file removes that file's entry, instead of leaving it or removing the directory's one (UTILS-D-2)

## p2pp (1.0)

- New repository holding the parser / pretty-printer framework (`p2pp-core`), formerly in `openflexo-utils`
- New `p2pp-test` module: own tests of p2pp, with a small fixture language (Mini), and a test harness (scenarios, generated edits) that other pretty-printers can reuse
- Fixed: the pretty-print keeps the final line separator of a source, and the node of a whole source covers it (P2PP-D-5); an empty source is accepted (P2PP-D-2)
- Fixed: removing a conditional part, or the first or the last item of a list, no longer leaves a stray space or separator (P2PP-D-7, P2PP-D-11)
- Fixed: a value that is `null` is no longer written as "null", and a child added where there was none is written at its place (P2PP-D-12, P2PP-D-13)
- Fixed: a child added before the first one of a list is written first, reordering the children of a node is taken into account, and children added to or removed from a nested node keep the indentation of the source (P2PP-D-8, P2PP-D-9, P2PP-D-10)
- Fixed: a source laid out differently from the canonical layout is no longer reformatted by the preserving pretty-print when nothing changed (P2PP-D-6)
- Fixed: adding or removing the last child of a list no longer corrupts a source whose closing delimiter is on the same row, nor reads the final line separator of the file as a separator of the child (P2PP-D-14)
- Fixed: an annotation or another child written before an indented declaration no longer pushes the declaration to column 0 (P2PP-D-1)
- Fixed: an element added by the model right after one written without its usual space is no longer glued to it; a role declared without `with` was saved as `nameXwith ConceptInstance()`, which could not be read again (CORE-D-60)
- New: re-indentation of a parsed text, or of some of its rows, from the structure of the language: comments follow the block they are in, a statement written over several rows is indented one level more on its following rows, or aligned on the first argument of a parenthesis left open, nothing but the indentation changes (P2PP-F-1)
- The scenarios of the test harness can have several steps on the same document
- New: a printed value can keep the text it was parsed from as long as it does not change, instead of being written again in its normalized form (P2PP-F-2)
- The method in which a node declares how it is printed is renamed `definePrettyPrint` (it was `preparePrettyPrint`)

## connie (2.1.0)

- `connie-utils` and `testutils` are removed: connie depends on `openflexo-utils` 2.0 instead
- connie-core no longer brings Guava: the modules that use it declare it themselves (CONNIE-F-2)

- Fixed: the search of a class by name failed on Java 9 and later, breaking the fix proposal for an unresolved type (CONNIE-D-2)
- `ClassScope` no longer uses a `SecurityManager` (replaced by `StackWalker`, which Java 24+ requires) (CONNIE-F-1)
- Fixed: the text of a binding no longer keeps the former name of a renamed element (CONNIE-D-3)
- New: a binding can be written back as it was written (`getPrettyPrint()`), its normalized text staying in `toString()` (CONNIE-F-3)
- New: a strict evaluation mode, asked by the evaluation context, where a binding that cannot be evaluated fails instead of giving null; exceptions keep their message and their cause (CONNIE-F-4)
- A failure of Java code called from an expression is no longer printed by Connie before it reaches the caller (CONNIE-F-4)
- Fixed: a property initialised with a diamond (`new HashMap<>()`) no longer fails with an `ArrayIndexOutOfBoundsException` (CORE-D-87)

## gina (2.4)

- Fixed: the type field of the inspector failed on Java 9 and later, throwing an exception when an object was selected (GINA-D-1)
  - the class search now also finds classes that are not loaded yet
- A colour selector usable in the cells of a table (`FIBColorSelector`, as a custom column) (GINA-F-1)
- `ClassScope` and `LoadedClassesInfo` are no longer duplicated: gina uses the ones of connie, which now live in `openflexo-utils` (`org.openflexo.toolbox`) (CONNIE-F-1)
- The class selector no longer fails on a class whose optional dependencies are missing: such a class is ignored (CONNIE-F-1)
- A UI test of the list widget no longer fails when the event queue is slow (GINA-D-2)
- Fixed: a browser node sometimes showed no children although its object had some, depending on the threads updating the browser (GINA-D-3)
- Fixed: a list could throw an error while painted, when its contents were updated from another thread (GINA-D-4)
- Fixed: a browser could show an object with the element of one of its super types (no children, wrong label), depending on the run (GINA-D-5)
- Fixed: a browser could miss children added while it was being refreshed, e.g. resources found while a resource center is scanned (GINA-D-6)
- Fixed: a browser with a hidden root could fail to refresh a node expanded automatically, e.g. in the free-modelling perspective (GINA-D-7)
- Fixed: inserting a panel at the top of a group of collapsible panels failed on an empty group, and otherwise left a panel out of the group (GINA-D-8)
- No more error on quit when a view with an iteration is closed (GINA-D-9)
- Swing components follow the language and the edition of a translation without a restart: the menus, labels, buttons, tooltips and titles of the editors and of the print preview, and every FIB view (GINA-F-3)
- The Metaphase HTML editor (toolbar, link, image, table, find/replace and about dialogs) is translated (GINA-F-3)
- A localizer can rely on several parent dictionaries, and a new entry can be created in the dictionary of a given context; an installation no longer creates entries for unknown keys, it shows the key read as English words (GINA-F-2)
- A failure of model code invoked by a widget (a button, setting a value, click and value-changed actions, table and browser actions) is handed to the controller (`FIBController.handleException`) instead of being printed (CORE-F-11)
- A disabled browser element greys its label and shows its icon lightened, in its own colours (not greyed)
- Fixed: a browser allowing external drag and drop no longer loses the drag and drop between its own elements (e.g. moving a concept into another in the FML editor, in containment mode); the external listener can be unregistered

## openflexo-buildplugin

- Openflexo 3.0 uses pamela 2.0
- New `jpackage` task for any application project: builds a native installer with an embedded Java runtime (replaces the Maven + Ant chain)
- The Designer, FlexoMaintainer, FreeModelling and Headless packages declare their installer; a GitHub Actions workflow builds them on Linux (.deb, .rpm), macOS (.dmg) and Windows (.exe), unsigned
- Each application is also available as a portable archive (jars and a start script), which needs a Java on the machine
- The Server is delivered as a portable archive and, on Linux, as an application image with its own Java runtime
- The Server has a Docker image (Java 17), built and checked by the workflow and published to ghcr.io on request
- A script publishes the installers built by GitHub to the download server; the installers are also built every week
- The macOS installers are built on macOS 15 (GitHub retired its macOS 14 runner), for openflexo-packaging and formod
- The Maven + Ant packaging chain (poms, assemblies, launch4j and appbundler targets) is removed from openflexo-packaging and formod
- One version per shared third-party library (Jackson 2.22, Guava 33.7, Gson 2.14, HttpClient 4.5.14, commons 2.22/3.21, JGit 7.8, HSQLDB 2.7, Batik 1.19, slf4j 2.0, JUNG 2.1), declared in the plugin instead of in each module
- New dependency helper `p2ppTest()` for the test harness of p2pp
- New dependency helper `xsom()` (XSOM, read by the XML adapter)
- Version policy of the build plugins: build plugin 0.4, SableCC plugin 1.4.0, wrappers on Gradle 9 (BUILD-F-6)

## diana, openflexo-server, pamela-editor

- Moved to the pamela 2.0 API (`PamelaProxies`)
- diana: richer shape palettes (rectangles, basic shapes, arrows, flowchart, stars and banners, equations, 3D shapes, emojis), shown as collapsible panels in the DIANA, FML diagram and Free Modelling editors; the Basic palette gains a rounded rectangle, a square, a circle and labelled rectangles (DIANA-F-1)
- GeomEdit: the contextual menu, and the save questions of the three diana editors, are translated
- http-server: the REST routes `add-to-list` and `remove-from-list` of behaviours are removed (CORE-F-8)
- http-server: a failing FML action (creating an instance, running a behaviour, a terminal command) answers a JSON error (kind, key, message, FML location and stack) with status 400 for an FML error, 500 for an internal error and 409 for a behaviour that is not enabled, and saves nothing (CORE-F-11)
- http-server: a failed assertion of the terminal answers the kind "Assertion failed" (CORE-F-11)
- http-server: an assignation of a behaviour accepts the name of an argument and `this.name` for a property (CORE-F-14)
- gina-http, the HTTP bridge of the GINA technology adapter, is removed with it

## openflexo-core

- FML names are a single kind of identifier: their case no longer matters, so `UML` or `A` can name a concept (CORE-F-5)
  - `Foo.bar()` is a static call when `Foo` names a type and no variable `Foo` is visible; a variable hides a type of the same name, and validation warns about it
  - a cast to a generic type is written `x as List<String>`; `(T) x` is kept for a plain name, and `(Foo) -x` is now a subtraction
  - call arguments can no longer contain an assignment (`f(a = 1)`); `where (...)` still can
- Fixed: a concept declared at the root that extends a nested concept stays at the root when the unit is saved, instead of moving into the container of its parent (CORE-D-91)
- Fixed: a concept extending a concept nested in another one must be declared in the same container (or a subtype of it); validation now reports an error otherwise (CORE-D-95)
- Fixed: a concept extending a root concept must be declared at the root of that concept's model, or of a model extending it; validation now reports an error otherwise, also for a concept that only inherits the constraint through an ancestor (CORE-D-96)
- Fixed: an event or an exception nested in a concept has the instance hosting it as `container`, like any concept: its container's properties and `new` work from it, and one with no creation scheme is no longer stored as a plain instance (CORE-D-96)
- A resource whose VirtualModel, required resource or technology adapter cannot be found is marked as in error at the resource layer, without being loaded (red cross in the browsers, message in the tooltip, listed by Validate project), instead of opening a modal dialog (CORE-F-13)
- Fixed: saving an FML unit no longer rewrites the expressions and types nobody edited: spacing, parentheses, qualified names, `log("x")`, argument and `where` lists and annotation values stay as written (CORE-D-33, CORE-D-65)
- An expression typed in an inspector is written in the FML text as it was typed (`1+2`, not `1 + 2`) (CORE-D-33, CONNIE-F-3)
- Fixed: a renamed concept, constructor or property is written with its new name in the expressions that use it, in the FML text and in the text of the bindings, also after several renames (CORE-D-67)
- Fixed: a call of a behaviour renamed twice in a row no longer keeps the intermediate name (CORE-D-71)
- Fixed: a concept-typed role declared without `with` (`ConceptB b;`) is no longer written back with `with ConceptInstance()`, and a role written `with FlexoConceptInstanceRole` keeps its text (CORE-D-33)
- Fixed: the parameter of a setter keeps its name (`set(int aValue)` was written `set(int value)`) (CORE-D-68)
- Fixed: `use` of a model slot class that cannot be found keeps the class written instead of the generic `ModelSlot` (CORE-D-63)
- Fixed: `new X() with (...)` on a concept that does not resolve keeps its clause, and its arguments no longer double at each save (CORE-D-64, CORE-D-66)
- Fixed: `extends` of a concept that does not resolve (a missing import) is no longer dropped; the other parents are still attached (CORE-D-62)
- Fixed: a local variable declared `Object` with a value is no longer written back with the type of its value (`Object o = 1;` became `long o = 1;`) (CORE-D-61)
- Fixed: a statement referring to a concept that does not resolve (a missing import) is no longer dropped from the model; it is kept with the name written and reported (CORE-D-58)
- Fixed: `end match … unmatched: someBehaviour(args)` runs that behaviour, no longer the first action scheme of the concept (which could delete); the `unmatched:` clause is optional, without it nothing runs (CORE-D-2)
- Fixed: the FML written from the model (a new object, a unit created from scratch) keeps the `unmatched:` clause of an `end match` and the `;` of a statement in a loop, and `use …ModelSlot` no longer breaks the parser (CORE-D-57)
- Fixed: creating a concept in the Free Modelling Editor no longer freezes the application (CORE-D-59)
- Added: a complete specification of the FML language (syntax, types, declarations, expressions, actions, FML-script, execution model and known deviations)
- The task thread factory no longer relies on the `SecurityManager`
- A project nature can bring into the project model the entities of extensions loaded at runtime
- Removal of the deprecated XML serialization of VirtualModels started: the test models and migration tests that only existed for it are removed (CORE-F-8)
- VirtualModels serialized in the deprecated XML format (`.fml.xml`) are no longer read nor converted: a warning names them (CORE-F-8)
- The deprecated edition actions that only the XML format could hold are removed (`AddFlexoConceptInstance`, `AddToListAction`, `IncrementalIterationAction`, …): use `new`, `list.add(…)`, `for` (CORE-F-8)
- FML editor: "Add to list" writes `list.add(value)` in the textual FML (CORE-F-8)
- The deprecated URI-string accessors of the FML model (`_getFlexoConceptTypeURI`, `_getParentFlexoConceptsList`, …) are removed: references are typed properties (CORE-F-8)
- Fixed: the FML editor follows a reference retargeted in the text (type of a role, `extends`, `match`, criteria, listened event) (CORE-D-43)
- The behaviour options that only the XML format held are removed: long-running action, popup size, `conditional` (use `@Enable`) (CORE-F-8)
- The old inspector model of a concept (inspector entries) is removed: a concept's inspector is its `.inspector` component; the "Define inspector" step of the concept-creation wizards is gone for now (CORE-F-8, CORE-F-9)
- Other dead deprecated API is removed; the use declarations are read from the compilation unit only (CORE-F-8)
- FML model classes renamed: `FlexoBehaviourParameter` → `FlexoBehaviourArgument`, `JavaRole` → `FMLVariable`, `JavaActorReference` → `FMLVariableActorReference` (older instances still load) (CORE-F-8)
- The FML model classes, those of every technology adapter included, no longer carry XML annotations: VirtualModels are only saved as textual FML (CORE-F-8)
- Fixed: a `match` may create its missing instances with `container.new`, or with `new` on a concept without constructor, without a validation error (CORE-D-41)
- Fixed: the FML technology adapter can be used from textual FML (model slot type, `CreateFlexoConcept` attributes) (CORE-D-42)
- Fixed: validating or printing a VirtualModel nobody edited no longer marks it modified, so the application no longer offers to save it (CORE-D-45)
- FML specification: the workaround given for the ignored `where` of `begin match` was wrong (it let `end match … delete()` delete the instances meant to be kept) and is replaced (CORE-D-12)
- Fixed: FML statements keep their meaning when a behaviour is built — unbraced statements nested in an `if/else`, the empty statement, `do … while`, compound assignments (`n += 2`), `n++` as a statement, `for` without condition or update; a statement that cannot be built is reported instead of dropped (CORE-D-46, CORE-D-6, CORE-D-7, CORE-D-9, CORE-D-18)
- New test of the FML pretty-print on the FML examples of the test resources: the preserving pretty-print of an untouched source must be that source, and both pretty-prints must read back to the same model (all of them pass)
- The FML pretty-print tests (concepts, behaviours, imports, properties, invariants and the update from text) are also available as p2pp scenarios, with a small language of edits of FML
- New generated edits of FML units (rename, abstract, visibility, body, properties, failure clauses) checked against the pretty-print
- A test of the rename of a VirtualModel no longer destroys a shared test fixture when run under Eclipse (CORE-D-72)
- Fixed: an FML execution failure reaches the caller — a failing creation scheme fails the `new` and leaves no partly built instance, a failing listener fails the `fire`, a failing deletion scheme fails the `delete` (the instance is kept), and so does the `end match`; an invalid `if` / `while` / `for` condition is an error instead of being taken as true (CORE-D-47, CORE-D-8)
- FML: a bare `new` of a concept that is not nested in the concept of the behaviour is reported as an invalid binding (it silently gave `null`); use `container.new`
- Fixed: after its block, the name of a local variable is the property of the same name again; redeclaring a visible local (in an inner block, the same block, or as a loop variable) is now reported by validation, as in Java (CORE-D-48)
- Fixed: `new Child()` through a creation scheme inherited from the parent creates a `Child` (concepts and virtual models); default values of inherited properties are set (CORE-D-15, CORE-D-50)
- Fixed: property declarations keep their initializer and cardinality; an edition action can initialize a property (`Person p = select unique Person from container;`), run when the instance is created; the printed text parses again (CORE-D-51, CORE-D-52)
- FML execution failures carry their FML stack (behaviour, statement, line) and are reported by the action that started the execution, in the FML console or the log; a defect of the platform is told apart from an FML error; a bare `return` in a creation or a deletion scheme is no longer an error, a valued one in a creation scheme is; a loop variable no longer outlives a failing loop (CORE-F-11, CORE-D-76)
  - For developers: `FMLExecutionException` moved to the package `org.openflexo.foundation.fml.rt.error`, with the typed FML errors (CORE-F-11)
- FML evaluation is strict: an expression that does not resolve, goes through `null` (in conditions too: `if`, loops, `where`, `@Enable`) or makes Java code fail is an error of its kind (null reference, resolution, type, host…) with its FML location, no longer a silent `null` or `false`; `&&` and `||` short-circuit, so a null check can guard a condition (CORE-F-11, CORE-D-75, CORE-D-77, CORE-D-78, CORE-D-79)
- Fixed: a failing action that changed nothing no longer undoes the previous action (a failing behaviour could delete the instance it ran on) (CORE-D-81)
- The FML console records an FML failure with its kind (null reference, type error…, translated) and its FML stack; a failure is reported once, wherever it is received again (CORE-F-11)
- Cancelling a behaviour called by another one (its parameters dialog) cancels the caller too: nothing is reported as an error (CORE-F-11)
- fml-cli: a failing command reports its line (and its script, when run by `execute`), the kind of the error, its message and its FML stack; the interactive shell goes on after any failure; a failed assertion always gives a message (CORE-F-11)
- fml-cli: `return` (with or without a value) ends a script successfully (CORE-F-11)
- fml-cli: run headless with a script (`fml-cli script.fmlscript`), it exits with 0 on success, 1 on an FML error or a failed assertion, 2 on an invalid script, 3 on an internal error (CORE-F-11)
- FML exceptions: `exception` declares an exception (a concept), `throw` raises it, `try … catch … finally` handles it or any FML runtime error by its type (`FMLError`, `FMLNullReferenceError`…); a defect of the platform and a cancellation are never caught; a caught error is not reported (CORE-F-11)
- FML behaviours declare the exceptions they let go (`throws`); as in Java, an exception defined in FML must be caught or declared, which the validation checks (CORE-F-11)
- fml-cli: a failed `assert` is an FML error of kind "Assertion failed" (CORE-F-11)
- `select unique` that finds several instances is an error (cardinality), no longer one of them; a `select` from a null model is an error (null reference), no longer an empty list (CORE-F-11)
- The FML technology adapter (`CreateFlexoConcept`, `CreateFlexoBehaviour`, the role creations…) and resource creations raise typed errors instead of returning null; a creation through a technology action whose creation scheme fails, or is cancelled, leaves no partly built instance (CORE-F-11, CORE-D-73)
- Fixed: `FMLRT::NewInstance` runs the creation scheme once, with its arguments, and no longer leaves an extra instance in the model (CORE-D-84)
- The argument of a behaviour is designated by its name (`label`, not `parameters.label`), like a local variable, and can be assigned; it hides a property of the same name (`this.label` reaches the property). `parameters.label` still works but is reported as deprecated by the validation, with a fix; a local variable named like an argument is an error. A migration tool rewrites the FML of resource centers (`label = parameters.label;` becomes `this.label = label;`), and the generators of FML write the new notation (CORE-F-14)
- Fixed: a `match … where (…)` criterion that is not a property of the matched concept is reported by the validation and by the execution (an FML error instead of a `NullPointerException`), and `selected.x = …` gets an explicit syntax message (CORE-D-88)
- Renamed: the Java base exception `FlexoException` is now `OpenflexoRuntimeException`, and the FML exception concept `FlexoExceptionConcept` is now `FlexoException` (the name no longer clashes)
- A `new` finds its container by climbing the containment tree: from a `ConceptB` nested in a `ConceptA`, `new ConceptA()` creates the instance in the model and `new ConceptB()` a sibling; `c.new X()` climbs from `c`. The same for root concepts, events, exceptions and virtual models nested in another one (CORE-F-18)
- Fixed: a concept of a child virtual model instantiates a concept inherited from the parent virtual model (CORE-D-89)
- A concept now has a single container (the concept it is declared in, or its virtual model); the former, duplicated containment API is removed (CORE-F-19)
- Fixed: closing a project no longer fails on a missing root folder of the VirtualModel instance repository, which left its resources open (CORE-D-92)
- Fixed: opening a project again after closing it no longer shows its resources twice in the browser (CORE-D-93)
- A project cannot be created inside another project
- Fixed: an event or an enum declared inside a concept is no longer dropped from the model (CORE-D-86)
- Removed: the `inside X` clause of model, concept, event and exception declarations; a concept is contained where it is declared, and `inside` is no longer a keyword (CORE-F-19)
- A concept's container is never inherited from its parents: instances of a concept live in instances of the concept it is declared in (CORE-F-19)
- Fixed: loading FML units that import each other no longer logs transient "Unresolved type" and "still invalid" warnings (CORE-D-34)
- FML specification: new section on the lexical context of a behaviour (`this`, `container`, `super`, bare names), with a test that runs its example
- Fixed: the cached information of a FML unit (dependencies, technologies, concepts) is refreshed after the file is edited outside the application; loading a unit no longer parses its file twice (CORE-D-100)

## openflexo-diagram

- The pretty-print test of the diagram model slot expects the current node positions and the current FML of its resource again
- The model of the FML class diagram (Modeller) is migrated to textual FML and runs again (CORE-F-8)
- Drop, link and draw-rectangle schemes keep their targets only as types (`targetType`, `fromType`, `toType`, `childrenType`): the former URI strings of the XML format are removed (CORE-F-8)
- Fixed: a draw-rectangle scheme declaring a `targetType` was still taken as drawing at top level (CORE-F-8)
- Fixed: loading the example diagrams and palettes of a diagram specification no longer marks them modified (CORE-D-45)
- Deleting diagram elements stops when the deletion scheme of an instance fails: the instance and its shapes are kept, and the error is shown (CORE-D-47)
- A failing drop, link or draw-rectangle scheme is reported as an FML error like any action (no more "not implemented yet" message), and the diagram tools no longer go on as if it had succeeded (CORE-F-11)
- The diagram edition actions raise typed FML errors (no container, no common ancestor to the shapes of a connector, no graphical feature…) instead of returning null after a warning (DIAGRAM-F-1, CORE-F-11)
- The FML generated for a diagram concept uses the name of the argument, and `this.` for the property it assigns (CORE-F-14)
- The FML class diagram gives the browser of concepts its own drag and drop back when the diagram is hidden
- Fixed: the concept list of "Create palette element binding" only offers concepts with a drop scheme (DIAGRAM-D-5)

## openflexo-integration-tests

- New use case mom-challenge: a federation prototype of the MoM Challenge 2026 (satellite configuration), covering change propagation, the communications view and requirement validation over OWL, JSON, CSV and DocX artefacts (ITEST-F-1 to ITEST-F-3 for what remains)
- The Acme metamodel of multi-process-challenge declares its task types in `AcmeProcessType` and `Coding` in `AcmeProcess`, as the containment rule requires (CORE-D-95)
- Multi-process challenge: task types, gateways and tasks no longer extend the root concepts `Type` / `Instance`, which a nested concept cannot extend (CORE-D-96)
- Multi-process challenge: the process type editor creates Acme process types on an Acme model, and the stored Acme instance loads completely (its first task type was lost) (ITEST-D-1)
- Multi-process challenge: two behaviours of the Acme meta-model created their artifact with a bare `new`, which gave nothing; they use `container.new`
- The FML of city-mapping and multi-process-challenge checks for null where a null used to be taken as false (CORE-F-11)

## openflexo-technology-adapters

- The template VirtualModel of the XX adapter is written in textual FML, with a script that runs it (CORE-F-8)
- For developers: the edition actions of the technology adapters declare `FMLExecutionException` where the core helpers now throw it (CORE-F-11)
- For developers: the REST and JDBC concept instances override the strict property accessors, so that FML execution reaches their mapping (CORE-F-11)
- The DSL and XX template edition actions and selects raise typed FML errors; adding a DSL link returns the link (TA-F-1, CORE-F-11)
- For developers: the adapter catalogue states the standards every adapter converges to, with a status per adapter and a conformity grid of the 24 adapters
- For developers: the XX template meets these standards; new adapters copy it with the package `org.openflexo.technologyadapter.xx` (was `org.openflexo.ta.xx`) and the test resource center `http://openflexo.org/xx-test`
  - `AddXXLine` really inserts a line, with its `value`; its arguments were ignored when written in FML
  - inserting a line can be undone; the index of a line follows insertions and removals
  - error-kind scripts, a save round trip, an undo test and inspector validations show how an adapter is tested
- Every adapter has a README (status, technology, FML surface, example, build), a licence file and its records of known defects and planned features; the former shared backlog and lists of known issues are spread into them
- The leftovers of the Maven site are removed from the adapters; the documentation sites of diagram and owl stay

## openflexo-jdbc

- Declares the javassist version Hibernate needs on Java 17 (pamela no longer brings it)
- The JDBC and Hibernate edition actions and selects raise typed FML errors (null table or connection, unknown column, failing query or transaction…) instead of returning null after a warning; the creation scheme of a Hibernate object creation now runs, and its failure leaves no object (JDBC-F-1, CORE-F-11, CORE-D-73)
- The FML generated for a JDBC mapping uses the name of the argument, and `this.` for the property it assigns (CORE-F-14)

## formod

- Requirement references of the document annotation methodology are declared at the root and point at their element reference, as the containment rule requires; they are still deleted with it (CORE-D-96)
- The installers are built with `jpackage` by a GitHub Actions workflow (Linux, macOS, Windows) and published to the Formose download directory
- The "missing model" labels of the B mapping view are translated

- The test suites run again (dependency on the generated SableCC parser declared)
- New test of the B pretty-print on every B component of the test resource centers: the preserving pretty-print of an untouched source is the source, and both pretty-prints read back to the same model
- New generated edits of the B components (add, remove, move, rename sets, constants and variables) checked against the pretty-print
- The B edition actions raise typed FML errors (missing name, operand or component, unknown operator, a text that is not an integer…) instead of returning null after a warning; a text that is not B still gives no predicate, as the B methodology expects (FORMOD-F-5, CORE-F-11)
- A text that is not B is an error, which the B methodology catches (FORMOD-F-5, CORE-F-11)

## free-modelling-editor

- The FreeModellingEditor is split into a generic core and one extension per technology; diagrams are the first extension, in their own module (FME-F-1)
- The unfinished PowerPoint free model is removed, to be rebuilt as an extension (FME-F-2)
- Fixed: when declaring a diagram element as an instance of an existing concept, the chosen representation strategy is now applied (FME-D-1)
- The free modelling actions (new concept from an element, declare as an instance of an existing concept, disconnect, relationship or property from a link) are shared by all technologies (FME-F-3)
- An element can stand for an instance that already exists, possibly represented elsewhere; disconnecting it deletes the instance only when nothing else represents it (FME-F-3)
- Fixed: declaring a diagram element as an instance of the concept proposed by default failed (FME-D-2)
- Fixed: a concept first declared from a plain shape of a diagram now gets its element in the concepts palette (FME-D-3)
- PowerPoint extension: annotate the shapes of an existing slideshow as instances of new or existing concepts, without modifying it; a slideshow is annotated once per project, with a vocabulary shared by all its slideshows (FME-F-2)
- Fixed: instantiating a free model offered the free models of the other technologies (FME-F-2)
- The project panel offers one button per technology (new diagram, annotate a PowerPoint slideshow); the inactive import buttons are removed (FME-F-1)
- The FML generated for a free model uses the name of the argument, and `this.` for the property it assigns (CORE-F-14)

## openflexo-docx

- The DocX edition actions raise typed FML errors (a generated part not found in the document, a role without actor, a document that cannot be created or loaded…) instead of returning null after a warning (DOCX-F-1, CORE-F-11)

## openflexo-pptx

- Added: eight sample presentations of fictional businesses in the test resource center (company profile, incident post-mortem, project kick-off, marketing plan, public consultation, training module, strategic plan, design review), each with its own visual style and the concepts and instances expected from it
- The elements of a slide can be selected in the slideshow view, the rendering being unchanged (PPTX-F-1)
- The shapes of a group are now available, from FML and in the slideshow view; ESCAPE selects the enclosing group, ENTER its first shape (PPTX-F-1)
- A browser shows the slides and shapes of a presentation, synchronized with the selection in the slides (PPTX-F-1)
- The slides are chosen from a column of thumbnails; each kind of element has its own icon and a read-only inspector (PPTX-F-1)
- The shown slide is zoomed to fit the available space, the thumbnails to the width of their resizable column, and the browser shows the contents of the shown slide (PPTX-F-1)
- The model reflects the tables (rows, cells), the charts (series and their data), the shapes connectors are glued to, the speaker notes and the paragraphs of the texts, each with its browser node, icon, inspector and selection (PPTX-F-2)
- The charts (column, bar, line, pie, doughnut, radar and their combinations) are drawn in the slides and their thumbnails, which POI does not do (PPTX-F-3)
- The pattern fills of the backgrounds and shapes (grids, hatchings) are painted, which POI does not do (PPTX-F-4)
- The paragraphs of a text box can be selected one by one in the slide; a text box with a single paragraph shows its text in the browser (PPTX-F-1)
- Fixed: the shape role was typed as a slide (PPTX-D-4)
- Fixed: an instance pointing at slides or shapes could not be saved (PPTX-D-5)
- The slides and shapes an instance points at are found again after a reload, and after the slides are reordered: they are identified by the ids stored in the file (PPTX-F-5)
- The slideshow view can be embedded in another module: contextual menu on the slides and their elements, and an overlay painting a border, a tint and a badge over chosen elements (PPTX-F-6)
- A shape annotated by an instance is no longer deleted with it (PPTX-F-5)
- The slideshow view lets the module embedding it place the browser of the shown slide (PPTX-F-6)
- Faster opening of a slideshow: the view of a slide is built when the slide is first shown (PPTX-F-7)
- Fixed: deleting an instance pointing at slides or shapes could fail once its model slot was gone (PPTX-D-6)
- The PowerPoint edition actions and selects raise typed FML errors (null slideshow or slide) instead of returning null after a warning (PPTX-F-8, CORE-F-11)

## openflexo-xlsx

- The Excel edition actions and selects raise typed FML errors (null sheet, row or name, refused merge, failing workbook creation…) instead of returning null after a warning; `createReflectedInstance` raises an error for an unknown concept or a missing sheet; a reflected cell whose content does not fit its property is a type error (XLSX-F-2, CORE-F-11)

## openflexo-rest

- The failures of a REST select (unreachable server, HTTP error, bad credentials, a concept without endpoint…) are typed FML errors naming the call and the access point, instead of untyped errors or a null result; the access point is loaded when a select needs it (REST-F-1, CORE-F-11)

## openflexo-owl

- The OWL edition actions and selects raise typed FML errors (no ontology, duplicate URI, a statement without subject or object…) instead of returning null after a warning (OWL-F-1, CORE-F-11)

## openflexo-csv

- The CSV edition actions and selects raise typed FML errors (null document, row or index, a failing creation…) instead of returning null after a warning (CSV-F-1, CORE-F-11)

## openflexo-emf

- The EMF edition actions and selects raise typed FML errors (no model, a new enumeration value, a failing creation…) instead of returning null after a warning (EMF-F-1, CORE-F-11)

## openflexo-json

- The JSON edition actions and selects raise typed FML errors (null content or document, unsupported content) instead of internal errors or an empty result (JSON-F-1, CORE-F-11)

## openflexo-markdown

- The Markdown edition actions and selects raise typed FML errors (null document, a document that cannot be created) instead of returning null after a warning (MARKDOWN-F-1, CORE-F-11)
- The Markdown test model queries headings and paragraphs with `select ... from`, so the edition-actions script runs again

## openflexo-opc-ua

- Reading an OPC UA value raises typed FML errors (null node, a node that is not a variable, a read refused by the server) instead of returning null after a warning (OPCUA-F-1, CORE-F-11)

## openflexo-capella

- The Capella edition actions and selects raise typed FML errors instead of returning null (CAPELLA-F-1, CORE-F-11)

## openflexo-kafka

- The Kafka edition actions raise typed FML errors (null server or consumer, a record refused) instead of returning null after a warning (KAFKA-F-1, CORE-F-11)

## openflexo-oslc

- The OSLC edition actions evaluate their expressions strictly (OSLC-F-1, CORE-F-11)

## openflexo-gina

- The GINA technology adapter is removed from the platform: the user interface of a VirtualModel is a `.fib` / `.inspector` stored in its `.fml` directory

## openflexo-odt

- The ODT edition actions declare their FML errors (ODT-F-1, CORE-F-11)

## openflexo-xml

- Fixed: deleting an instance that plays an XML role failed once its model slot was gone (XML-D-1)
- The XML edition actions and selects raise typed FML errors (null model, metamodel or root, duplicate or missing URI…) instead of returning null after a warning; the condition of an ontology assertion (XML, EMF, OWL individuals) that fails or is null is an error, no longer taken as true (XML-F-1, CORE-F-11)
- Fixed: saving an XML document silently did nothing; a free XML document is now written back (XML-D-3); a typed one (conform to an XSD) is still not written, and a warning now says so (XML-D-4)
- Fixed: changing a property of a typed XML element or an attribute of a free one did not mark the document modified (XML-D-5)
- The adapter logs its failures instead of printing them, and its classes and packages are documented

## Localization (several repositories)

- Fixed: about 230 messages, dialog titles and labels that were never in a dictionary now have an entry (core, diagram, OWL, PDF, PowerPoint, Kafka, XML, JDBC, free modelling editor, enterprise architecture editor, formod, GINA)
- Fixed: the Basic and Advanced tabs of the inspectors are translated in French, from the shared dictionary instead of a copy in each component
- Evolution: the common labels Name, Description, Cancel and URI of the inspectors and panels now come from the shared dictionary, no longer duplicated in each component
- Removed: stray entries (temporary paths, ontology URIs) from the OWL dictionary
- Removed: about 190 entries copied from the shared dictionary into technology adapter, module and FML dictionaries
- Fixed: about 35 common labels (Property, Value, Delete, Behaviour...) are now translated in the shared dictionary, so every module shows them in French
- Fixed: the "FML-controlled diagram" entry of the New menu and the "project name" message of the project creation wizard are read from their own dictionaries, instead of creating entries in the shared one

## website

- The component versions page lists pamela 2.0 for Openflexo 3.0
- The user guide gains the FML specification, generated from the one maintained with the core
- The architecture, repository map, component versions and setup pages describe the new bottom layer (`openflexo-utils` 2.0) and the `p2pp` repository
- The Downloads page lists the 3.0 snapshot installers and explains how to run them; the roadmap, FAQ and installation guide say that 2.99 is released and that 3.0 snapshots embed their own Java
- The FML specification and the FML pages of the user guide state the strict evaluation of 3.0: an unresolved path or a null in a condition is an error, `&&` and `||` short-circuit (CORE-F-11)
- The FML specification describes how an FML-script reports a failure, `return` in a script, and the exit codes of fml-cli (CORE-F-11)
- The FML specification describes exceptions, `throw`, `try … catch … finally` and `throws` (CORE-F-11)
- The FML specification states the containment rule of inheritance (a concept is declared in the container of each of its ancestors, events and exceptions included) and the container of an event or an exception (CORE-D-96)
- The guide "Write a technology adapter" explains where an adapter sits in FML, its packages, its adapter class, its model slots and its tests

## Build infrastructure

- New Jenkins job `pamela-2.0` builds pamela for the 3.0 line; `pamela-1.6.1` is disabled
- The build plugin compiles for Java 17 (`--release 17`) and can run the whole build on another JDK with `-PjavaVersion=<n>`; the test suites give the same results on Java 17 and 25
- The build plugin and the SableCC plugin no longer use the retired JCenter repository and the deprecated Groovy property syntax
- The JAX-WS libraries and several unused JAXB declarations are removed from the build
- The leftover Maven `pom.xml` files are removed from the repositories: Gradle is the only build
- The build files and both build plugins no longer use the syntaxes that Gradle 10 turns into errors (map dependency notation, `url x` property assignment, `afterSuite(Closure)`) (BUILD-F-1)
- docx: docx4j 17 (was 6.1.2), JAXB on Jakarta
- jdbc: Hibernate 6.6 (was 5.2), Jakarta Persistence
- xlsx, pptx, diagram: Apache POI 5.5 (was 3.17), also for the import of binary PowerPoint slides
- openflexo-headless: the package embeds `flexo-foundation-rm` (the FML resources could not be loaded without it), and has a `migrateLegacyParameters` task that migrates FML to the new notation of the arguments of a behaviour (CORE-F-14)
- Defects and features are now tracked as GitHub issues titled with their identifier (`CORE-D-87`, `PAMELA-F-3`…), the Markdown files being their local copy; one issue per entry, imported for the small repositories first

## openflexo-ui

- The container of a concept can only be chosen, in the inspector, the creation wizard or by drag and drop, where the containment rule of inheritance allows it (UI-F-4)
- Validate project is also available from the contextual menu of the project (CORE-F-13)
- The application starts on Java runtimes without a `jmods` directory (JDK 25, some JREs); the class completion of the FML editor reads the runtime image (UI-D-3)
- All repositories build with Gradle 9.8.2 (wrapper); the dependency helpers of the build plugin are no longer a project convention (`openflexo(name)` is `openflexoModule(name)`), the applications declare `application { mainClass }`
- SableCC plugin 1.4.0: works with Gradle 9, and a parser can be generated several times in a row without error (SABLECC-D-1)
- FML editor: annotations (`@Name`, `@a.b.Name`) are coloured; `String` and the eight primitive types are coloured as types, `create` and `delete` as keywords
- Security updates: PDFBox 2.0.37, Vert.x 4.5, Kafka client 4.4 (needs a Kafka broker 2.1 or newer) Velocity 2.4 for the documentation generator and Jena 5.6 for OWL (BUILD-F-7)
- Build plugin: the UI tests (`uiTest`) run again with Gradle 9 (they were silently skipped)
- FML editor: colouring themes (default, eclipse, dark, and your own), edited in a new "FML editor" page of the preferences and applied live to every editor (UI-F-1)
- Debug `finalize()` methods removed (deprecated for removal in Java); no behaviour change
- Faster startup: the FIB editor is built when first opened, no longer at startup (UI-F-2)
- The document editor dialogs (table, margins, paragraph), the inspector placeholders ("No selection"), the version line of the splash screen and about box, and the search bar of the FML editor are translated
- Loading a module or a project: the progress bar moves steadily with time, based on the previous loading, and never goes back (UI-F-2)
- Opening a project in the Free Modelling Editor no longer freezes the window: its models are loaded with the project; the "Select default object" bar is gone (UI-F-2)
- Opening a project from the File menu, the recent projects or a module welcome panel no longer freezes the window: it is loaded in a task, with its progress bar (UI-F-2)
- The splash screen shows the progress of the startup and its current step (UI-F-2)
- Opening a FML unit is faster, and the window no longer freezes for several seconds once it is loaded: the FML editor does not parse and validate again the text the unit was just loaded from (UI-F-2)
- Opening a FML unit shows what it loads: a task per technology to activate and per unit it imports, each with its progress bar, in the task panel (UI-F-2)
- Opening a FML unit is faster: the technologies it needs are activated while the units it imports load (UI-F-2)
- Faster module loading: the inspectors and actions of a technology are no longer initialized twice (UI-D-5)
- Faster loading of modules using diagrams: the floating Diana inspectors are built when first opened (UI-F-2)
- Faster project opening: virtual models and instances are validated in the background, their error reports appearing a moment later (UI-F-2)
- FML editor: "Re-indent" (context menu, Cmd/Ctrl+I) re-indents the selected rows, or the whole text, from the structure of the FML (P2PP-F-1)
- An FML error during an action is shown in a dialog: its kind, its message and its FML stack, with "Show in console" and "Open in the FML editor" at the line of the selected frame; the edits of the action are undone first; a defect of the platform proposes the bug report; a cancelled action shows nothing (CORE-F-11)
- An FML failure started by the user interface itself (a button or a binding of a component, a listener) goes to the FML console only, once; the FML console opens the FML editor at the line of a failure (CORE-F-11)
- The FML terminal describes a failing command with its kind and its FML stack, and goes on after any failure (CORE-F-11)
- Resource browsers: a modified resource is shown with a "> " before its name (the resource notifies its modified state to the bindings); an unloaded resource has its name greyed and its icon lightened, in colour
- Fixed: a concept pasted into another concept is also listed among the concepts of its virtual model (CORE-F-19)
- A project can be closed, and validated, from the contextual menu of its root folder (Close project), as from the File menu
- When a project is opened, the resource browser expands its first level
- Concept container editing follows the single containment (CORE-F-19): the inspector shows the VirtualModel of a root concept, the container selector lists the VirtualModel and its nested concepts only, and a concept can no longer be moved into another VirtualModel or into one of its own nested concepts

## openflexo-packaging

- FlexoMaintainer includes the CSV technology adapter
- The Designer, Headless and Server distributions include the CSV and Markdown technology adapters (FlexoMaintainer through the Designer)
- The Designer, Headless and Server distributions include the MCP technology adapter

## openflexo-mcp

- The MCP technology adapter joins the 3.0 platform: ported to Java 17 and to the typed FML errors; the scenarios that need live MCP servers run apart (task liveServerTest)
