# com.guicedee:parent

The parent POM file for Guiced EE Builds

- Version line: `2.6.0` (verify publication separately).
- Packaging: `pom`.
- JPMS module: not declared in this source POM/directory; inspect the build configuration.
- [Source POM](https://github.com/GuicedEE/Parent/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.projectlombok:lombok`; scope `provided`.
- `org.junit.platform:junit-platform-launcher`; scope `test`.

## Module guide

Source: [README.md](https://github.com/GuicedEE/Parent/blob/master/README.md). Historical version examples retain their original context.

# 🧩 GuicedEE Parent POM

[![JDK](https://img.shields.io/badge/JDK-25%2B-0A7?logo=java)](https://openjdk.org/projects/jdk/25/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

The GuicedEE Parent POM centralizes plugin management, encoding, Java toolchain, and shared build configuration across GuicedEE modules.

## 🔧 Usage (Maven)
```
<parent>
  <groupId>com.guicedee</groupId>
  <artifactId>guicedee-parent</artifactId>
  <version>${guicedee.version}</version>
</parent>
```

## 📚 Docs & Rules
- Pact: `PACT.md`
- Rules: `RULES.md`
- Guides: `GUIDES.md`
- Architecture index: `docs/architecture/README.md`

## 📝 License & Contributions
- License: Apache 2.0
- Contributions: Issues/PRs welcome.
