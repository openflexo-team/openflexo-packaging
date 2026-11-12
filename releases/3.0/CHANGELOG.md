# Openflexo 3.0 — Changelog

What changed since 2.99, one line per change, by repository. Identifiers in parentheses point to the
`BACKLOG.md` or `KNOWN_DEFECTS.md` of the repository, where the details are.

## pamela (2.0)

- PAMELA proxies are generated with Byte Buddy; javassist is removed (PAMELA-F-1)
  - creating objects is faster (about 25%) and uses less memory (about 30%)
  - `PamelaProxies` replaces the javassist `ProxyObject` API for testing a proxy and reaching its handler
- Fixed: an implementation class set at run time in another package was reported as a generated class (PAMELA-D-3)
- Fixed: an implementation class loaded by a child class loader was replaced by another copy of it (PAMELA-D-4)
- A property can be declared transient (`@Getter(isTransient = true)`): not persistent, not compared, not cloned; a property is persistent when it is neither derived nor transient, independently of XML (PAMELA-F-3)
  - the models of openflexo-core, GINA, DIANA, documents, Excel, JDBC and XML mark their transient properties
- Entities without XML mapping may share their simple name in a model (PAMELA-F-3)
- `@Getter(ignoreForEquality)` is removed: equality compares every property that is neither derived nor transient (PAMELA-F-4)
- Fixed: a model entity that failed to import broke every later import (PAMELA-D-5)
- Version 2.0, requires Java 17 or later; documentation updated

## openflexo-utils (2.0)

- Becomes the bottom layer of the infrastructure: it now contains the resource locator, the logging, the toolbox and the test utilities that were in `connie-utils` and `testutils` (the `org.openflexo` packages are unchanged)
- `p2pp-core` moves out into its own repository, `p2pp` (1.0), because it needs connie
- Fixed: the localization dictionaries saved by the application are sorted again on Java 9 and later, so a new entry changes one line and not the whole file (UTILS-D-1)

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

## connie (2.1.0)

- `connie-utils` and `testutils` are removed: connie depends on `openflexo-utils` 2.0 instead
- connie-core no longer brings Guava: the modules that use it declare it themselves (CONNIE-F-2)

- Fixed: the search of a class by name failed on Java 9 and later, breaking the fix proposal for an unresolved type (CONNIE-D-2)
- `ClassScope` no longer uses a `SecurityManager` (replaced by `StackWalker`, which Java 24+ requires) (CONNIE-F-1)

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

## diana, openflexo-server, pamela-editor

- Moved to the pamela 2.0 API (`PamelaProxies`)
- GeomEdit: the contextual menu, and the save questions of the three diana editors, are translated
- http-server: the REST routes `add-to-list` and `remove-from-list` of behaviours are removed (CORE-F-8)

## openflexo-core

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

## openflexo-diagram

- The model of the FML class diagram (Modeller) is migrated to textual FML and runs again (CORE-F-8)
- Drop, link and draw-rectangle schemes keep their targets only as types (`targetType`, `fromType`, `toType`, `childrenType`): the former URI strings of the XML format are removed (CORE-F-8)
- Fixed: a draw-rectangle scheme declaring a `targetType` was still taken as drawing at top level (CORE-F-8)
- Fixed: loading the example diagrams and palettes of a diagram specification no longer marks them modified (CORE-D-45)

## openflexo-technology-adapters

- The template VirtualModel of the XX adapter is written in textual FML, with a script that runs it (CORE-F-8)

## openflexo-jdbc

- Declares the javassist version Hibernate needs on Java 17 (pamela no longer brings it)

## formod

- The installers are built with `jpackage` by a GitHub Actions workflow (Linux, macOS, Windows) and published to the Formose download directory
- The "missing model" labels of the B mapping view are translated

- The test suites run again (dependency on the generated SableCC parser declared)
- New test of the B pretty-print on every B component of the test resource centers: the preserving pretty-print of an untouched source is the source, and both pretty-prints read back to the same model
- New generated edits of the B components (add, remove, move, rename sets, constants and variables) checked against the pretty-print

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

## Localization (several repositories)

- Fixed: about 230 messages, dialog titles and labels that were never in a dictionary now have an entry (core, diagram, OWL, PDF, PowerPoint, Kafka, XML, JDBC, free modelling editor, enterprise architecture editor, formod, GINA)
- Fixed: the Basic and Advanced tabs of the inspectors are translated in French, from the shared dictionary instead of a copy in each component
- Evolution: the common labels Name, Description, Cancel and URI of the inspectors and panels now come from the shared dictionary, no longer duplicated in each component
- Removed: stray entries (temporary paths, ontology URIs) from the OWL dictionary
- Removed: about 190 entries copied from the shared dictionary into technology adapter, module and FML dictionaries
- Fixed: about 35 common labels (Property, Value, Delete, Behaviour...) are now translated in the shared dictionary, so every module shows them in French

## website

- The component versions page lists pamela 2.0 for Openflexo 3.0
- The user guide gains the FML specification, generated from the one maintained with the core
- The architecture, repository map, component versions and setup pages describe the new bottom layer (`openflexo-utils` 2.0) and the `p2pp` repository
- The Downloads page lists the 3.0 snapshot installers and explains how to run them; the roadmap, FAQ and installation guide say that 2.99 is released and that 3.0 snapshots embed their own Java

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

## openflexo-ui

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
- Faster module loading: the inspectors and actions of a technology are no longer initialized twice (UI-D-5)
- Faster loading of modules using diagrams: the floating Diana inspectors are built when first opened (UI-F-2)
- Faster project opening: virtual models and instances are validated in the background, their error reports appearing a moment later (UI-F-2)
- FML editor: "Re-indent" (context menu, Cmd/Ctrl+I) re-indents the selected rows, or the whole text, from the structure of the FML (P2PP-F-1)
