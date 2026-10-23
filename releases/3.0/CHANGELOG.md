# Openflexo 3.0 — Changelog

What changed since 2.99, one line per change, by repository. Identifiers in parentheses point to the
`BACKLOG.md` or `KNOWN_DEFECTS.md` of the repository, where the details are.

## pamela (2.0)

- PAMELA proxies are generated with Byte Buddy; javassist is removed (PAMELA-F-1)
  - creating objects is faster (about 25%) and uses less memory (about 30%)
  - `PamelaProxies` replaces the javassist `ProxyObject` API for testing a proxy and reaching its handler
- Fixed: an implementation class set at run time in another package was reported as a generated class (PAMELA-D-3)
- Fixed: an implementation class loaded by a child class loader was replaced by another copy of it (PAMELA-D-4)
- Version 2.0, requires Java 17 or later; documentation updated

## openflexo-utils (2.0)

- Becomes the bottom layer of the infrastructure: it now contains the resource locator, the logging, the toolbox and the test utilities that were in `connie-utils` and `testutils` (the `org.openflexo` packages are unchanged)
- `p2pp-core` moves out into its own repository, `p2pp` (1.0), because it needs connie

## p2pp (1.0)

- New repository holding the parser / pretty-printer framework (`p2pp-core`), formerly in `openflexo-utils`

## connie (2.1.0)

- `connie-utils` and `testutils` are removed: connie depends on `openflexo-utils` 2.0 instead

- Fixed: the search of a class by name failed on Java 9 and later, breaking the fix proposal for an unresolved type (CONNIE-D-2)
- `ClassScope` no longer uses a `SecurityManager` (replaced by `StackWalker`, which Java 24+ requires) (CONNIE-F-1)

## gina (2.4)

- Fixed: the type field of the inspector failed on Java 9 and later, throwing an exception when an object was selected (GINA-D-1)
  - the class search now also finds classes that are not loaded yet
- A colour selector usable in the cells of a table (`FIBColorSelector`, as a custom column) (GINA-F-1)
- `ClassScope` and `LoadedClassesInfo` are no longer duplicated: gina uses the ones of connie, which now live in `connie-utils` (`org.openflexo.toolbox`) (CONNIE-F-1)
- The class selector no longer fails on a class whose optional dependencies are missing: such a class is ignored (CONNIE-F-1)

## openflexo-buildplugin

- Openflexo 3.0 uses pamela 2.0
- One version per shared third-party library (Jackson 2.22, Guava 33.7, Gson 2.14, HttpClient 4.5.14, commons 2.22/3.21, JGit 7.8, HSQLDB 2.7, Batik 1.19, slf4j 2.0, JUNG 2.1), declared in the plugin instead of in each module

## diana, openflexo-server, pamela-editor

- Moved to the pamela 2.0 API (`PamelaProxies`)

## openflexo-core

- Added: a complete specification of the FML language (syntax, types, declarations, expressions, actions, FML-script, execution model and known deviations)
- The task thread factory no longer relies on the `SecurityManager`

## openflexo-jdbc

- Declares the javassist version Hibernate needs on Java 17 (pamela no longer brings it)

## formod

- The test suites run again (dependency on the generated SableCC parser declared)

## website

- The component versions page lists pamela 2.0 for Openflexo 3.0
- The user guide gains the FML specification, generated from the one maintained with the core

## Build infrastructure

- New Jenkins job `pamela-2.0` builds pamela for the 3.0 line; `pamela-1.6.1` is disabled
- The build plugin compiles for Java 17 (`--release 17`) and can run the whole build on another JDK with `-PjavaVersion=<n>`; the test suites give the same results on Java 17 and 25
- The build plugin and the SableCC plugin no longer use the retired JCenter repository and the deprecated Groovy property syntax
- The JAX-WS libraries and several unused JAXB declarations are removed from the build
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
