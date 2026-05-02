# 🌐 GuicedEE Website

[![JDK](https://img.shields.io/badge/JDK-25%2B-0A7?logo=java)](https://openjdk.org/projects/jdk/25/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

This repository powers the **public GuicedEE website** hosted at `https://guicedee.com`, presenting the entire GuicedEE ecosystem with the documentation, samples, and builder tools consumers expect.

## ✨ Site Vision
- Surface every GuicedEE module and plugin alongside its capabilities so users can discover, inspect, and compose the platform into new apps.
- Provide a dedicated **Services** directory that highlights each modular service offering and explains how it plugs into the larger GuicedEE stack.
- Offer an **Application Builder** UI that lets visitors choose GuicedEE modules/plugins, configure settings, and download a ready-to-run ZIP containing the generated source package tailored to their selections.
- Treat GuicedVertx and GuicedInject as implicit foundations for every construction flow; they are assumed by default whenever GuicedEE modules are composed.
- Host a single monolithic boot implementation that invokes `IGuiceContext.inject()` so the site remains focused and simple for visitors (no multi-module boot wiring is required on the site itself).
- Document hosting/security expectations: the site is fronted by **GCP Cloud Armor** for DDoS protection and remains otherwise open to the public.
- Curate extensive **media-rich write-ups and imagery** for each module, service, and plugin, enabling the marketing/evangelism story to come alive alongside the data and application builder.
- Generate every page with the JWebMP/WebAwesome stack so that the WebAwesome components (`WaButton`, `WaInput`, `WaCluster`, `WaStack`, etc.) drive the layout, styling, and interactive media galleries.
- Define the primary navigation and page structure using WebAwesome’s `WaPage`/`WaMenu` paradigms: a `Home` page that narrates the platform story, a `Capabilities` page for features breakdown, a `Services` catalog page, the App Builder page, and supporting destinations for releases, media, and onboarding resources.

## 🧩 Module Catalog (everything under `GuicedEE/`)
- `cdi` *(migration and compatibility — not a foundation module)*
- `cerial`
- `client`
- `config`
- `inject`
- `openapi`
- `persistence`
- `rabbitmq`
- `representations`
- `rest`
- `swagger-ui`
- `telemetry`
- `vertx`
- `web`
- `webservices`
- `websockets`
- (catalog is a curated static list defined in `ModuleCatalog`; it intentionally excludes non-modular directories like `bom`, `parent`, `services`, and the `website` itself)

Each catalog entry links to its documentation, release notes, and live demos wherever available.

## ⚙️ Plugin & Stack Spotlight
Note: This section describes the technology used to build and run this website itself (not the output of the Application Builder).
- Website UI: TypeScript front end using JWebMP foundations, WebAwesome, and WebAwesome Pro with component names `WaButton`, `WaInput`, `WaCluster`, and `WaStack` (see `GUIDES.md`).
- Java 25 + Maven backend powered through the `GuicedEE/parent` BOM chain and the `guice-inject-client` dependency; logging via Log4j2 with Lombok `@Log4j2` helpers.
- MapStruct for DTO-to-model transformations, Mermaid for diagrams (served from `docs/architecture/` via Mermaid MCP), and CRTP-based fluent APIs across shared modules.

## 🌀 Modular Services Section
This site’s Services section lists a complete, statically-defined set of modularized services (no filesystem scanning at runtime), currently covering:
- **Apache Commons**: `commons-beanutils`, `commons-collections`, `commons-csv`, `commons-fileupload`, `commons-math`
- **Apache CXF (Web Services)**: `apache-cxf`, `apache-cxf-rt-security`, `apache-cxf-rt-transports-http`
- **Apache POI**: `apache-poi`, `apache-poi-ooxml`
- **Database Drivers**: `msal4j`, `mssql-jdbc`, `postgresql`
- **Google**: `aop`, `guice-core`, `guice-assistedinject`, `guice-grapher`, `guice-jmx`, `guice-jndi`
- **Hibernate**: `hibernate-core`, `hibernate-reactive`, `hibernate-validator`
- **JCache**: `cache-annotations-ri-common`, `cache-annotations-ri-guice`, `cache-api`, `ehcache`, `hazelcast`, `hazelcast-hibernate`
- **JNI**: `jna-platform`, `nrjavaserial`
- **Jakarta**: `jakarta-security-jacc`
- **Libraries**: `bcrypt`, `cloudevents`, `ibm-mq`, `jandex`, `javassist`, `json`, `kafka-client`, `mapstruct`, `openpdf`, `rabbitmq-client`, `scram`, `swagger`, `testcontainers`, `uadetector-core`, `uadetector-resources`
- **MicroProfile**: `config-core`, `metrics-core`
- **Vert.x**: `vertx-cassandra`, `vertx-mutiny`, `vertx-kafka`, `vertx-pg-client`, `vertx-rabbitmq`
- **Misc**: `untitled`

Each service card describes configuration patterns, integration requirements, and the modular contracts they expose so teams can adopt them independently.

## 🧱 Application Builder
Note on scope: The Application Builder is for composing GuicedEE modules and services. JWebMP/WebAwesome are the technologies used to power this website and the optional UI starter in the generated ZIP, but they are not selectable modules in the builder and the builder is not a JWebMP project generator.

Visitors can craft a custom GuicedEE application by:
1. Selecting desired GuicedEE modules and plugins from the Module Catalog.
2. Picking the services they wish to include from the Services Section.
3. Configuring optional settings (e.g., logging level, MapStruct mapping mode, service configuration presets).
4. Clicking **Build App** to trigger the builder backend which packages the chosen artifacts into a ZIP archive containing:
   - Maven parent/child POM structure wired for Java 25/GuicedVertx/Inject.
   - Optional minimal UI starter scaffold (if explicitly selected); by default, no Angular or JWebMP code is generated by the builder.
   - A monolithic `Boot` class that calls `IGuiceContext.inject()` to spin up the composition.
   - Service-specific wiring snippets that show how to bootstrap each modularized service.

The generated ZIP mirrors production builds so teams can iterate quickly without configuring every dependency manually.

## 🚀 Hosting & Security
- Deployed as a static + server-rendered hybrid site on `https://guicedee.com`.
- GCP Cloud Armor handles edge protection; no additional application-level firewalling is applied today.
- CI harness uses GuicedEE/Workflows shared GitHub Actions templates with secrets such as `USERNAME`, `USER_TOKEN`, `SONA_USERNAME`, `SONA_PASSWORD`.

## 🧭 Development & Reference
- Run `mvn -B -U clean verify` to build the website; `docs/architecture/` and `docs/PROMPT_REFERENCE.md` link all stage-gated artifacts.
- Stage 1–3 docs (PACT/RULES/GLOSSARY/GUIDES/IMPLEMENTATION) are required before any Stage 4 scaffolding touches actual Angular or Java sources.
- See `.env.example` for environment variables aligned with `rules/generative/platform/secrets-config/env-variables.md`.
- Architecture diagrams (C4, sequences, ERD) live under `docs/architecture/`; Mermaid sources are served via Mermaid MCP (`https://mcp.mermaidchart.com/mcp`).

## 📚 Docs & Rules
- AI Skills: `SKILLS.md`
- Prompt reference: `docs/PROMPT_REFERENCE.md`
- Architecture index: `docs/architecture/README.md`

## 🤝 Contributing
- Keep the documentation loop closed: PACT ↔ RULES ↔ GUIDES ↔ IMPLEMENTATION ↔ GLOSSARY.
- Apply forward-only changes and honor the Document Modularity Policy outlined in `rules/RULES.md`.

## 📝 License
- Apache 2.0 — see `LICENSE`.
