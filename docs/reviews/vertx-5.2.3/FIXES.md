# Vert.x 5.2.3 configuration fixes

Implemented locally on 2026-11-15/16. The original review and failing probe log remain
historical evidence. Current documentation is in [the generated configuration contract](../../configuration/README.md).

| Review finding | Resolution | Verification |
| --- | --- | --- |
| Redis TLS annotation ignored | Absent override preserves the annotation; explicit false wins | Option regression test |
| Time/unit mismatches | Warning, DNS refresh and named worker duration/unit pairs are applied together | Option tests and both discovery modes |
| gRPC message/protocol settings ignored | Apply maximum message size and remove disabled WEB/WEB_TEXT/TRANSCODING protocols | Live local HTTP/2 echo, oversized message yields RESOURCE_EXHAUSTED, disabled protocols yield HTTP 415 |
| Daemon flag ignored | Apply `setUseDaemonThread` | Option and discovery probes |
| Package and environment-only activation | Shared class/package discovery, duplicate rejection, environment-only core groups and default deployments | SPI/scanner class/package probes and duplicate cases |
| Default drift | Align core duration/flags/pools and filesystem defaults; retain explicit GuicedEE orchestration defaults | Comparison with native 5.2.3 options; generated defaults |
| Inconsistent placeholders | Auth, Redis and gRPC use the canonical resolver | Placeholder and request-option tests |
| Bare annotation property names | Normal option key first, then the annotation text as a key in the same source order; missing names remain literal. String-array entries resolve individually | Source-order, literal, empty-value, placeholder, array, connection, metrics and tracing regression checks |
| Per-event dot-env bypass | Per-address then global lookup through the canonical source resolver | Dot-env, scope and source-precedence tests |
| Inert settings | Redis database applied through supported URI selection; owned Prometheus listener; OAuth request defaults; provider leeway inheritance; reserved event metadata labeled | URI tests, live metrics endpoint/isolation/shutdown, OAuth forwarding tests |
| OAuth timeout precision | Milliseconds retained through idle-timeout unit | 250 ms option regression test |

Two additional precedence defects were fixed: resolved defaults no longer become JVM
properties, and empty nearest dot-env files no longer fall through to parent files.
Concurrent loading publishes both file maps together. Independent defaults, reloads,
explicit empty values, source order and parent-file search have regression coverage.

The website now covers **100% of the inventoried 223 runtime keys/templates** and all
242 annotation members. The six-class upstream inventory classifies all 163 declared
setters as annotation-backed or programmatic. It does not count inherited methods or
claim every Vert.x integration API has a scalar environment equivalent.

## Validation

- Focused Maven reactor: **83 tests passed**, covering client resolution/discovery,
  Vert.x options and real gRPC traffic, injection lifecycle, managed HTTP listeners,
  metrics and telemetry. No external services required.
- Six isolated discovery cases: SPI and scanner, each with class, package and duplicate
  declarations. Package worker name, enum values, capability prefix, duration/unit pairs,
  auth, metrics and telemetry were checked. Bare references were verified for HA groups,
  DNS arrays, filesystem paths, worker pools, metrics registries and telemetry service
  names. Metric names/tags, monitored routes, span names and attribute names were
  checked at the consuming APIs; runtime argument values remain literal.
- Existing benchmark module paths with rebuilt framework jars: SPI, scanner, CDI scanner,
  CDI SPI, explicit HTTP/metrics overrides, and a dedicated Prometheus port. HTTP readiness,
  scrape responses, route isolation and shutdown were checked. Persistence smoke checks
  annotation discovery and connection placeholder defaults only; no database workload.
- Website Maven compilation and generated-reference drift check.

Reproducible checks from the workspace root:

```powershell
python benchmarks/tools/configuration-contract/verify.py
python benchmarks/tools/configuration-smoke/verify.py
python GuicedEE/website/tools/vertx_configuration.py --check
mvn -B -ntp -f GuicedEE/website/pom.xml '-DskipTests=true' compile
```

The Java regressions live in `client/.../BareAnnotationValueTest.java`,
`client/.../DotEnvPrecedenceTest.java` and
`vertx/.../ConfigurationContractTest.java`, alongside the existing application, discovery
and lifecycle tests. Smoke scripts require the local framework jars and benchmark
application classes/dependencies to have been built.

## Build and publication boundary

The six framework modules package successfully together. A full benchmark reactor rebuild
is currently blocked by pre-existing GuicedEE benchmark dependencies on the unavailable
`comparison-quarkus-standard:1.0-SNAPSHOT` artifact. Those POM changes were preserved.
The smoke checks use existing benchmark application classes and full module paths with
the rebuilt framework jars; they do not establish a successful full benchmark rebuild.
No native image, jlink image, database workload, public website deployment or release
publication was performed for these configuration fixes.
