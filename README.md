openflexo-packaging
===================

Contains some Openflexo-Infrastructure packaging managed by Openflexo team

Installers
----------

The installers are built with `jpackage`, through the `jpackage` task of the build plugin. Each application package
(`openflexo-designer`, `flexomaintainer`, `freemodelling`, `openflexo-headless`, `openflexo-server-package`) declares the modules and
technology adapters it contains as `runtimeOnly` dependencies, and its installer in a `jpackage { }` block of its `build.gradle`.

    ./gradlew :packages:openflexo-designer:jpackage            # native installer of the platform you run on (.dmg, .exe, .deb)
    ./gradlew -Pjpackage.type=rpm :packages:openflexo-designer:jpackage
    ./gradlew :packages:openflexo-designer:distTar             # portable archive: jars in lib/, start script in bin/

`jpackage` builds for the system it runs on. The workflow `.github/workflows/installers.yml` (run it by hand) builds Linux (`.deb`,
`.rpm`, portable archives, the Server image), macOS (`.dmg`) and Windows (`.exe`) installers on three runners. The installers embed
their own Java 17 runtime and are not signed.

`tools/publish-installers.sh` copies the installers of a successful run to the download server; the Jenkins job
`openflexo-packaging-3.0--PUBLISH` runs it.
