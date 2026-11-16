# 🌐 GuicedEE Website

[![JDK](https://img.shields.io/badge/JDK-25%2B-0A7?logo=java)](https://openjdk.org/projects/jdk/25/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

This repository powers the **public GuicedEE website** hosted at `https://guicedee.com`, presenting the entire GuicedEE ecosystem with the documentation, samples, and builder tools consumers expect.

## GuicedEE 2.5.0 website deployment

Revision `guicedee-website--v250-20261102` is healthy and serves 100% of traffic.
The image is `docker.io/gedmarc/guicedee-website@sha256:0f2aa357fd405c2ba514740686577260c59329b2dc49ec033c320aa585a6f3fe`.
All 94 public HTML/JavaScript/CSS assets match the local production build, and
six SPA routes return the matching index. Release notes, current catalog versions
and application examples target 2.5.0. The separate backend was not redeployed.
Evidence: `target/release-2.5.0-website-public-verification.json` in DevSuite.

## GuicedEE 2.4.2 website content

The version badge, module and service catalogs, dependency examples, and Maven/Gradle application downloads target
GuicedEE 2.4.2. The release page covers the refreshed reactive runtime, official Swagger Core 3 integration (including
its pinned upstream snapshot), and Maven 4 model 4.1.0. Historical release notes and deployment records remain dated
to the versions they describe. Regenerate the Angular site from its Java sources using the commands below before deployment.

## ✨ Site Vision
## Clustering and WebSocket continuity

The capabilities page documents explicit cluster activation, lifecycle ownership, separate member/event-bus endpoints,
owner-local STOMP commands, private replies/storage, broadcast fan-out, bounded socket cleanup and reconnect recovery.
The environment reference includes the activation gates and Hazelcast member settings. The application builder uses
`com.guicedee:hazelcast` / `com.guicedee.guicedhazelcast` and `com.guicedee:websockets` / `com.guicedee.vertx.sockets`.
The release changelog describes the production capabilities shipped with GuicedEE.

The Application Builder offers separate **Hazelcast** (cache-only) and **Vert.x clustering (Hazelcast)**
selections. Either adds the Hazelcast dependency and a `cluster/package-info.java` preview and source file.
Clustering generates explicit TCP membership and advertised event-bus endpoints, plus `cluster-a.env.example`,
`cluster-b.env.example`, `single-instance.env.example`, and `CLUSTERING.md` in both Maven and Gradle downloads.
Load the environment examples explicitly into separate processes; they are not automatically read.
The generated `Boot` uses `GuiceApplication.run(Runnable)` and enables scanning in its preparation callback.
Generated Maven projects pin Compiler Plugin 3.15.0 for Java 25 bytecode support. The GuicedEE BOM manages
the current `com.guicedee:hazelcast` coordinate so clustering selections need no dependency version override.

Regenerate from Java and build the production front end locally, without creating a Docker image:

```powershell
# From GuicedEE/website with the required JDK 25 selected:
mvn.cmd -B -ntp install '-Djwebmp.angular.docker=false' '-Dmaven.javadoc.skip=true'
Push-Location target/webroot/guicedee-website
npm.cmd run build-prod
Pop-Location
```

Check the regenerated production bundle and its new section/aside anchors; Maven success alone does not prove fresh
browser output. Keep generated TypeScript untouched and fix its Java owners. Publishing or deploying is separate.

## ✨ Site Vision
- Surface every GuicedEE module and plugin alongside its capabilities so users can discover, inspect, and compose the platform into new apps.
- Provide a dedicated **Services** directory that highlights each modular service offering and explains how it plugs into the larger GuicedEE stack.
- Offer an **Application Builder** UI that lets visitors choose GuicedEE modules/plugins, configure settings, and download a ready-to-run ZIP containing the generated source package tailored to their selections.
- Treat GuicedVertx and GuicedInject as implicit foundations for every construction flow; they are assumed by default whenever GuicedEE modules are composed.
- Show a single monolithic application entry point using `GuiceApplication.run(Runnable)` from `com.guicedee.guicedinjection`. Register the application's JPMS module in the preparation callback before injection. The runner waits for asynchronous startup; failures trigger cleanup and an unsuccessful process exit.
- Document hosting/security expectations: the site runs on **Azure Container Apps** behind the platform's managed ingress (TLS termination + Azure edge protection) and remains otherwise open to the public.
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
   - A monolithic `Boot` class that calls `GuiceApplication.run(Runnable)` to spin up the composition, using the module name from `module-info.java` in its preparation callback.
   - Service-specific wiring snippets that show how to bootstrap each modularized service.

The generated ZIP mirrors production builds so teams can iterate quickly without configuring every dependency manually.

## 🚀 Hosting & Deployment
The public site is a **static Angular SPA served by nginx** — the `angular-maven-plugin` emits the Angular project to `target/webroot/guicedee-website`, `ng build` writes `dist/jwebmp/browser`, and the generated `Dockerfile` copies that into an `nginx:alpine` image. Dynamic data comes from the separate `website-backend` deliverable, not from this container.

**Runtime topology**

| Setting | Value |
| --- | --- |
| Platform | Azure Container Apps |
| Container app | `guicedee-website` |
| Resource group / region | `DevSites` / UK West |
| Image | `docker.io/gedmarc/guicedee-website:<version>` (public Docker Hub, anonymous pull) |
| Ingress | External, target port `80`, transport `Auto` |
| Custom domain | `guicedee.com` via Azure **managed certificate** (SNI) |
| Revisions | `Single` mode, traffic pinned to `latestRevision` at 100% |
| Scale | min `0` / max `1`, HTTP scaler at 10 concurrent requests |
| Resources | 0.25 vCPU / 0.5 GiB, `Consumption` workload profile |
| Probes | TCP `:80` liveness, readiness, and startup |

> Because `minReplicas` is `0`, the app scales to zero when idle — the first request after a quiet period pays a cold start.

**Deploying a new version**

Deployment verified on 16 October 2026: revision `guicedee-website--v241-20261016`
serves 100% of traffic from `docker.io/gedmarc/guicedee-website@sha256:5a59bea733666fa5472a9fe3b33c514cb8a2d4b6ec3fd849ce3f106f11dcf316`
(tag `2.4.1-20261016`). All 94 HTML/JavaScript/CSS assets and six public routes
match the local production build. The catalog, examples, application downloads and
release notes target GuicedEE 2.4.1. The website backend was also upgraded to revision
`website-backend--v241-20261016`; readiness, APIs, CORS and protected internal routes passed.

Previous deployment, verified on 15 October 2026: revision `guicedee-website--v240-apm-20261015`
serves 100% of traffic from image
`docker.io/gedmarc/guicedee-website@sha256:ac058aecc740e83bfe2969bdc83987ecdcb2bbc81ffa08dc9ad83f825652b8df`
(tag `2.4.0-20261015-apm`). The homepage and main JavaScript
bundle match the local production output. The deployment includes the IntelliJ
plugin section, APM marketplace/installer instructions directly below it and simplified three-layer
architecture diagram. The backend is unchanged.

1. Build and push the image (see `build-guicedee-website.ps1 -Push`, or `mvn install` then `docker build`/`docker push` from `target/webroot/guicedee-website`). Keep `<dockerImageName>` in `pom.xml` in step with the release.
Use subscription `aee3c089-49a8-472c-8d40-5bf568a1ff55` (Pay-As-You-Go), tenant `58d3b009-4fc6-44c1-bc98-197165d3fd04`, explicitly. Do not rely on the Azure CLI default subscription.

2. Point the container app at the immutable image digest. Because revisions run in `Single` mode with `latestRevision: true`, the new revision automatically takes 100% of traffic — no manual traffic split:

   ```powershell
   az containerapp update `
     --subscription aee3c089-49a8-472c-8d40-5bf568a1ff55 `
     --name guicedee-website `
     --resource-group DevSites `
     --image docker.io/gedmarc/guicedee-website@sha256:5a59bea733666fa5472a9fe3b33c514cb8a2d4b6ec3fd849ce3f106f11dcf316 `
     --revision-suffix v241-20261016
   ```

   Without the `containerapp` CLI extension, an ARM `PATCH` against `properties.template.containers[0].image` achieves the same result. If you hand-roll the payload, re-send the probes, resources, and scale rule as well — a `template` patch replaces the containers array. Note that the read API returns `scale.cooldownPeriod` and `scale.pollingInterval`, but api-version `2024-03-01` **rejects** those two on write; omit them and the existing values are retained.
3. Verify: `provisioningState` is `Succeeded`, `latestReadyRevisionName` matches the new suffix, and `https://guicedee.com/` returns the freshly built `index.html` (compare `content-length` / `last-modified` against `dist/jwebmp/browser/index.html`). Confirm the SPA fallback still serves deep links such as `/releases` and `/modules`.

**Security**
- TLS terminates at the Azure Container Apps managed ingress using the Azure managed certificate; no application-level firewalling is applied today.
- CI harness uses GuicedEE/Workflows shared GitHub Actions templates with secrets such as `USERNAME`, `USER_TOKEN`, `SONA_USERNAME`, `SONA_PASSWORD`.

## 🧭 Development & Reference
- Run `mvn -B -U verify` to build the website; `docs/architecture/` and `docs/PROMPT_REFERENCE.md` link all stage-gated artifacts.
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

## 2.6.0 content

The Releases and Capabilities pages preview the modular Firebase/Commercetools SDKs, external modular dependencies, Jackson 3 policy for Commercetools, SDK Javadocs and full application jlink validation. See the [release notes](../RELEASE-2.6.0.md). Website examples and version badges target 2.6.0. Publication and deployment remain pending.

## Homepage benchmark carousel

The App Builder offers independent Firebase Admin and Commercetools SDK selections with BOM-managed wrapper coordinates and their original JPMS names. Services lists both SDKs and the Zstd/Okio support modules. The runtime selector defaults to a JVM JAR; Native image starter adds explicit SPI discovery, reflection registration, dual service registration, `NativeBuild.java` and `NATIVE.md` to Maven and Gradle ZIPs. Build previews use the same content as downloads. GraalVM compilation and workload-specific metadata remain application validation steps; selecting an SDK does not assert native compatibility for all its products.

The generated Maven starter with both SDKs selected passed a Linux native build and startup check after adding baseline Guice reflection metadata to both generators. See [the native starter validation report](docs/native-starter-validation.md) for the exact scope, environment and evidence. A separate copy with the benchmark workload and its native compatibility setup also passed actual Commercetools OAuth/REST/GraphQL and Firebase Firestore write/read calls on JVM and native, using the benchmark's local fixtures. See [the SDK operation validation report](docs/native-sdk-validation.md) for assertions, fresh metadata, reproduction commands and results.

Quarkus is the primary comparison because both frameworks build on Vert.x, as described in [Quarkus's reactive architecture](https://quarkus.io/guides/quarkus-reactive-architecture/). The shared reactive foundation and stack example make the comparison useful; injection, configuration and application models still differ. The Performance slide explains this choice, with implementation details in the CDI slide.

The homepage uses a manual Web Awesome carousel and Pro `wa-chart` bar charts. The long platform tour is retained in an expandable section. Numeric summaries accompany each chart for accessibility; axes start at zero and animation is disabled.

`BenchmarkShowcase` fetches these public files once per page visit (8-second timeout, independent failure handling):

- `https://raw.githubusercontent.com/GuicedEE/benchmarks/master/docs/current-performance.json`: `native-standard` startup and peak container memory, with measurement date and sample counts.
- `https://raw.githubusercontent.com/GuicedEE/benchmarks/master/docs/build-performance.csv`: clean JVM compilation for `guicedee` and `quarkus-standard`.
- `https://raw.githubusercontent.com/GuicedEE/benchmarks/master/docs/persistence-performance.json`: default-profile transaction-cycle p50 when published.

Startup, memory and compile charts retain a labelled 15 November 2026 public snapshot if fetching or validation fails. Persistence has no invented fallback: it stays pending until valid public measurements arrive. Finite positive numeric values are required. Source text is Angular-interpolated, never injected as HTML. The full report links retain methodology and raw evidence. These are separate benchmark cohorts, not universal framework performance claims.

The comparison cards use compact `wa-data-grid` rows beneath each chart. Developer builds default to clean compile versus clean compile, with separate JVM packaging and native-build views. Each chart shows its matching pair: GuicedEE SPI and Quarkus fast-JAR for compile/JVM package; GuicedEE native and Quarkus native for native build. Methodology stays in the linked benchmark report. Native runtime panels compare GuicedEE native with Quarkus native. Application jlink remains covered by the detailed benchmark report, outside the homepage comparisons.

The native build comparison validates the published CSV baseline (`quarkus-standard-native`) before accepting refreshed data. All homepage comparisons retain the public-data and dated-fallback policy above.

The developer-build panel also retains the complete seven-artifact build grid from `RESULTS.md`: compile/package medians, both min–max ranges, explicit baseline and package difference. It spans the panel below the chart and stays visible across chart modes. All seven rows refresh atomically from the published CSV; incomplete rows, invalid ranges or unexpected baselines retain the dated snapshot.

The startup panel also includes a seven-row runtime grid: stack-example startup median/range, starts, warm p50, peak charged container memory, matching baseline and startup percentage difference. Default/C1 switches the five JVM rows, while the separately measured native pair remains unchanged. The table uses the same public summary JSON as the native chart, with validated complete cohorts and dated snapshots.

The Kotlin carousel slide presents the planned launch support across GuicedEE modules, with constructor injection, coroutine work and Kotlin data classes. Its example follows the current `GuicedCoroutines.future` boundary and links to the Kotlin examples. The module-wide Kotlin work is still in progress; this website copy is staged for its completion before launch.

Search sits above Home in the left menu, aligned with the homepage carousel selectors, and also appears in the mobile drawer. A theme-colored magnifying glass prefixes the field; results float in an anchored WebAwesome popup without moving the navigation. Its local index is generated from the page component trees, including module/service descriptions, static examples and environment-variable tables. Results show their page, section and an excerpt around the match. Opening a result navigates to a stable heading fragment, expands enclosing details or selects its carousel slide, then scrolls to and highlights the heading. Result URLs also work on reload and browser back/forward, with the matching aside preserved. Remote README dialogs and builder options that require changing selections are excluded. Keyboard users can press Enter for the first result, use arrow keys through results, or Escape to dismiss them; clicking outside also closes the popup.

After building, run `node tools/verify-site-search.cjs` with Playwright available (or set `PLAYWRIGHT_MODULE` to its installed module path). It checks cross-page results, carousel/disclosure reveal, fragments, snippets, keyboard controls, no-match/clear states and the mobile drawer. Search does not send queries to an external service.

The CDI panel records the local `benchmarks/docs/CDI-CONFIG-VALIDATION.md` evidence from 15 November 2026 (five CDI variants, 16/16 checks each). It explicitly distinguishes capability validation from performance rankings; public performance JSON currently contains no CDI rows. Expandable excerpts show the actual Jakarta annotations, packaged application.properties, environment overrides, bootstrap and JPMS setup, including the different Quarkus configuration/initialization pattern. Native is SPI-only.

Artifact cells in the build/runtime grids use decorative `wa-progress-bar` backgrounds via the [Web Awesome cell formatter API](https://webawesome.com/docs/components/data-grid/). Bar length is the absolute package/startup percentage difference, capped at 100%; the signed value remains in its own column. Baselines use a neutral background. The progress component is preloaded for the grid shadow DOM, and artifact labels are inserted as text rather than HTML.
