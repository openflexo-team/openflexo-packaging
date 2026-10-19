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

## connie (2.1.0)

- Fixed: the search of a class by name failed on Java 9 and later, breaking the fix proposal for an unresolved type (CONNIE-D-2)

## gina (2.4)

- Fixed: the type field of the inspector failed on Java 9 and later, throwing an exception when an object was selected (GINA-D-1)
  - the class search now also finds classes that are not loaded yet

## openflexo-buildplugin

- Openflexo 3.0 uses pamela 2.0

## diana, openflexo-server, pamela-editor

- Moved to the pamela 2.0 API (`PamelaProxies`)

## openflexo-jdbc

- Declares the javassist version Hibernate needs on Java 17 (pamela no longer brings it)

## formod

- The test suites run again (dependency on the generated SableCC parser declared)

## website

- The component versions page lists pamela 2.0 for Openflexo 3.0

## Build infrastructure

- New Jenkins job `pamela-2.0` builds pamela for the 3.0 line; `pamela-1.6.1` is disabled
