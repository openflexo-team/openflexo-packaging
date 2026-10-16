# Openflexo 2.99 — Release notes

Release date: 2026-10-16. Previous stable release: 2.0.1 (March 2023).

Openflexo is a model-federation platform. Its user-facing language, **FML** (Flexo Modeling
Language), connects heterogeneous models (diagrams, XML, OWL ontologies, spreadsheets, documents,
databases, REST services, ...). This release ships the whole 2.99 distribution: every component is
published as a final release (no `-SNAPSHOT`), and installers are provided for Linux, macOS and
Windows.

## Downloads

Everything is under `https://downloads.openflexo.org/openflexo/2.99/2026-10-16/`, in one directory per
platform (`Linux`, `Mac`, `Windows`).

| Package | Linux (`.tar.gz`) | macOS (`.dmg`) | Windows (`.zip`) |
|---|---|---|---|
| Openflexo Designer | yes | yes | yes |
| Openflexo FlexoMaintainer | yes | yes | yes |
| Openflexo FreeModelling | yes | yes | yes |
| Openflexo Headless | yes | yes | yes |
| Openflexo Server | yes | — | — |

File names follow the pattern `Openflexo <Package> 2.99.<extension>`.

## Which package?

| Package | For whom | Content |
|---|---|---|
| **Designer** | Most users | The Openflexo Modeller, the Free Modelling Editor and the Enterprise Architecture module, with the most common technology adapters. |
| **FlexoMaintainer** | Developers of the infrastructure | Everything in Designer, plus the REST, JSON, OSLC, Rhapsody, XX (example) and DSL technology adapters. |
| **FreeModelling** | Users who only draw free models | The Free Modelling Editor alone, with the Diagram and PowerPoint technology adapters it relies on. |
| **Headless** | Automation, scripts, servers without a display | The FML command-line interpreter, with no graphical interface. |
| **Server** | Hosting FML models behind HTTP | An HTTP server exposing the infrastructure. **Linux archive only.** |

### Technology adapters per package

| Technology adapter | Designer | FlexoMaintainer | FreeModelling | Headless | Server |
|---|---|---|---|---|---|
| Diagram | yes | yes | yes | yes | yes |
| Microsoft PowerPoint | yes | yes | yes | yes | yes |
| EMF | yes | yes | — | yes | — |
| XML / XSD | yes | yes | — | yes | — |
| OWL ontologies | yes | yes | — | yes | — |
| Microsoft Excel | yes | yes | — | yes | yes |
| Microsoft Word | yes | yes | — | yes | yes |
| OpenDocument text (ODT) | yes | yes | — | yes | — |
| PDF | yes | yes | — | yes | yes |
| GINA (user-interface components) | yes | yes | — | yes | yes |
| JDBC | yes | yes | — | yes | yes |
| Kafka | yes | yes | — | yes | yes |
| Java | yes | yes | — | — | — |
| OPC-UA | yes | yes | — | — | — |
| REST | — | yes | — | — | — |
| JSON | — | yes | — | — | — |
| OSLC | — | yes | — | — | — |
| Rhapsody | — | yes | — | — | — |
| XX (example adapter) | — | yes | — | — | — |
| DSL | — | yes | — | — | — |

The graphical packages (Designer, FlexoMaintainer) also contain the user-interface part of each
adapter. Headless and Server contain the adapters only.

## Component versions

All components of the distribution are published in the `openflexo-release` repository.

| Component family | Version |
|---|---|
| Connie (expression language and utilities) | 2.0.0 |
| Pamela (modeling framework), Flexo utilities, Diana (diagrams) | 1.6 |
| GINA (user-interface framework) | 2.3 |
| Openflexo core, user interface, technology adapters, modelers, editors, server, packaging | 2.99 |

## Requirements

- **Java 8 is required.** Openflexo 2.99 does not run on more recent Java versions: on Java 11, 17 or 21
  the application freezes at startup. No package bundles a Java runtime.
- Windows, macOS (Intel, or Apple silicon through Rosetta 2) or a Linux distribution, each with a Java 8.

## Installing and running

### Windows

1. Install a Java 8 runtime **with an installer** (not a zip archive), for instance the Eclipse Temurin
   8 JRE. The launcher finds Java through the Windows registry: a Java that was merely unzipped is not
   detected. With Temurin, enable the installer option that sets the JavaSoft (Oracle) registry keys.
2. Download the `.zip` package and extract it.
3. Run `Openflexo <Package> 2.99.exe`. The variant ending with `- Console.exe` also opens a console
   window showing the logs, which is useful when reporting a problem.

The launcher only accepts Java 8. When none is found, it says so and offers to open the Java 8 download
page.

### macOS

Two ways:

- **The application (`.dmg`).** It needs **Oracle's Java 8 runtime for macOS**: the application looks for
  Java exclusively where Oracle's Java 8 installer puts it
  (`/Library/Internet Plug-Ins/JavaAppletPlugin.plugin`). Other Java 8 distributions are not detected.
  Double-click the `.dmg`; it mounts silently (no window opens) and a volume named `<Package>-2.99`
  appears in the Finder sidebar. Drag the application to your Applications folder.
  The application is **not signed**: on the first launch, control-click it, choose *Open*, then confirm.
  Alternatively, remove the quarantine attribute from a terminal:
  `xattr -dr com.apple.quarantine "/Applications/<application name>.app"`.
  On Apple silicon the application runs under Rosetta 2.
- **The Linux archive**, which works with any Java 8 (for instance an Eclipse Temurin 8 JDK installed
  with its `.pkg` installer):

  ```
  mkdir -p ~/Openflexo && cd ~/Openflexo
  tar -xzf ~/Downloads/"Openflexo Designer 2.99.tar.gz"
  export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
  export PATH="$JAVA_HOME/bin:$PATH"
  ./start.sh
  ```

### Linux

1. Install a Java 8 (OpenJDK 8 or Eclipse Temurin 8) and make sure it is the first `java` on your
   `PATH`: `java -version` must print `1.8`.
2. Extract the `.tar.gz` into a directory of its own (the archive has no top-level directory), preferably
   in a path without spaces.
3. Run `./start.sh`.

### Server

The Server package is a Linux archive. It is configured entirely through the arguments of `start.sh`
(`--port`, `--host`, `--center`, `--project`, `--preload`).

## Known limitations

- Java 8 only (see Requirements). No Java runtime is bundled; bundling one is a goal of the next packaging
  generation.
- The macOS application is not signed and not notarized, and only finds Oracle's Java 8. Signed,
  notarized macOS packages are targeted for the 3.0 packaging.
- The macOS application uses an Intel launcher; it runs under Rosetta 2 on Apple silicon.
- The Server is delivered for Linux only.
