# openflexo-packaging — Backlog

Features and evolutions of the packaging. Known bugs go to `KNOWN_DEFECTS.md` (identifiers `PKG-D-<n>`), not here.

Identifiers are stable and never reused: `PKG-F-<n>`. Reference them from commits and from other entries.

Status legend: `TODO` · `IN PROGRESS` · `BLOCKED` · `DONE` · `DEFERRED`.

---

### PKG-F-1 — Native installers with jpackage  ·  `DONE` (2026-10-24), follow-ups open

**Problem.** The installers of 2.99 came from a Maven + Ant chain (launch4j, appbundler, no Java runtime, Java 8 only).

**Done.** `jpackage` blocks in the application packages (see `README.md`), a GitHub Actions workflow building Linux (`.deb`, `.rpm`,
portable archives, Server image), macOS (`.dmg`) and Windows (`.exe`) installers, a script and a Jenkins job
(`openflexo-packaging-3.0--PUBLISH`) copying them to the download server. The Maven chain is deleted from the 3.0 branch. First
publication: `3.0-SNAPSHOT/2026-10-24`.

**Open.**
- **Test the packages.** Only the `.dmg` was run. The `.deb`, `.rpm`, `.exe` and the Server image remain to be tried by a person.
- **Menu entry on Linux** (`--linux-shortcut`): the `.deb` installs under `/opt/<package>/` with a launcher in `bin/` and nothing in the menu.
- **`.msi` for Windows** (silent installation) instead of the default `.exe`.
- **Signing**: macOS (Developer ID, notarization) and Windows (code-signing certificate); today Gatekeeper and SmartScreen warn.
- **`arm64` builds** (Apple silicon, Linux) next to `x86_64`.
- **Push the Server image** to `ghcr.io/openflexo-team/openflexo-server` (workflow input `publish_docker`), then make the package public.
- **Run the workflow without a person**: a trigger on a tag or a schedule, then the PUBLISH job.

### PKG-F-2 — Remove what is left of the Maven world  ·  `TODO`

**Problem.** The `pom.xml` files of the workspace were removed on 3.0, but four remain, and the repository that fed the former chain too.

**Open.**
- `openflexo-modules/xxxmodule/` (`pom.xml`, `xxx-app`, `xxx-rc`): the module template, whose parent is `openflexo-production`.
- `openflexo-integration-tests/packaging-tests/pom.xml`: a test of the former packaging.
- The repository `openflexo-production` (`packaging-tools`, the parent poms, its Jenkins jobs `openflexo-production-0.5` and `-release`):
  delete or archive, once the 2.99 branches no longer need it.
- Jenkins: disable then delete `openflexo-packaging-3.0--MAVEN`, `openflexo-packaging-3.0--GRADLE` and `formod-packaging-3.0`
  (they fail since the poms were removed; the 2.99 ones are already disabled).
