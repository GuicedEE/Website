# Vert.x 5.2.3 configuration review

**Historical baseline:** the findings below describe the pre-fix implementation. See
[fixes and validation](FIXES.md) and the [current configuration contract](../../configuration/README.md)
for the implemented behavior. The original inventories and failing probe log are preserved.

Reviewed 2026-11-15 against the current workspace, including the new benchmark configuration annotations.

## Summary

The annotation/environment/website contract is inconsistent. Correct runtime behavior before describing the missing settings as supported website features.

| Area | Annotation members | Environment keys/templates | Explicit website rows | Row coverage |
| --- | ---: | ---: | ---: | ---: |
| Vert.x runtime, event bus, deployment, DNS, filesystem, auth, Redis, gRPC | 171 | 165 | 82 | 49.7% |
| HTTP/HTTPS web server | 20 | 23 | 22 | 95.7% |
| Metrics | 23 | 12 | 12 | 100.0% |
| Telemetry | 24 | 17 | 12 | 70.6% |
| **Total** | **238** | **217** | **128** | **59.0%** |

There are 35 annotation types, including nested annotations and repeatable containers. **89 environment keys/templates lack explicit rows** in the website's Environment Variables page. Presence is not correctness: the metrics page lists a port that does not configure a listener.

Ten isolated runtime probes reproduced configuration mismatches. These inspect built option objects and resolution behavior; they do not establish live Redis, gRPC, OAuth, cluster, native-image or jlink behavior.

Deliverables:

- [Annotation member inventory](annotation-members.csv): declarations, types, defaults and source anchors.
- [Environment-to-website comparison](environment-website.csv): executable lookup keys/templates, source anchors and current website defaults.
- [Upstream option surface](upstream-option-surface.csv): 163 declared setters across six directly wrapped core option classes; this is a surface inventory, not an implementation pass count.
- [Probe output](runtime-probes.log) and [probe source](probes/review/Probe.java).
- [Inventory generator](inventory.py) and [machine-readable totals](inventory-summary.json).

Scope is the four modules above and their configuration sections in the website source. Other integrations such as Kafka, RabbitMQ, SQL, REST Client and Hazelcast need their own module-specific review. The website source was reviewed; no claim is made that its current checkout is deployed publicly.

## Findings requiring runtime corrections

### 1. High: Redis annotation-only TLS is discarded

`RedisPreStartup.buildFromAnnotation` checks whether the result of `Environment.getSystemPropertyOrEnvironment("REDIS_TLS_ENABLED", null)` is null. The resolver returns an empty string when absent. Consequently, an absent override is parsed as false instead of using `@RedisOptions(tlsEnabled = true)`.

Reproduced: **annotation=true, connection-info=false**. Explicit `REDIS_TLS_ENABLED=true` works around the mapping error. This finding concerns the TLS flag; a `rediss://` URI may independently enable TLS in the underlying client.

Source: [RedisPreStartup.java](../../../../vertx/src/main/java/com/guicedee/vertx/redis/RedisPreStartup.java#L103).

Use the same absent/blank handling as the adjacent port/pool mappings, and test annotation-only true, explicit true and explicit false before documenting the setting.

### 2. High: time values and units are not applied as pairs

Three separate defects were reproduced:

| Setting | Requested/documented | Actual built option |
| --- | --- | --- |
| `@VertX.warningExceptionTime` + unit | 5000 milliseconds | 5000 nanoseconds |
| `@AddressResolverOptions.hostsRefreshPeriod` | 30000 milliseconds | 30000 nanoseconds |
| Named `@Verticle.maxWorkerExecuteTime` | 3 minutes | 3 nanoseconds |

`VertXPreStartup.applyVertxAnnotation` never invokes `setWarningExceptionTimeUnit`. DNS configuration never sets `hostsRefreshPeriodUnit`, which defaults to nanoseconds in 5.2.3. `VerticleBuilder.toDeploymentOptions` skips the time-unit setter when the annotation says MINUTES, even though the fresh Vert.x options default to NANOSECONDS.

Sources: [VertXPreStartup.java](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VertXPreStartup.java#L186), [VerticleBuilder.java](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VerticleBuilder.java#L529), [DNS annotation](../../../../vertx/src/main/java/com/guicedee/vertx/spi/AddressResolverOptions.java#L45).

Always apply the resolved value and unit together. The upstream [DeploymentOptions API](https://vertx.io/docs/5.2.3/apidocs/io/vertx/core/DeploymentOptions.html) defines the value in the configured unit and limits the deployment worker-timeout option to named worker pools.

### 3. High: gRPC limits and protocol controls stop at the configuration holder

`GrpcPreStartup` populates `maxMessageSize` and `transcodingEnabled`. `GrpcModule.configureConnection` never applies either to `GrpcServerOptions`. An annotation's apparent 4 MiB default therefore does not replace the upstream 256 KiB default; setting transcoding false does not remove TRANSCODING from enabled protocols.

Source: [GrpcModule.java](../../../../vertx/src/main/java/com/guicedee/vertx/grpc/GrpcModule.java#L86). Upstream [GrpcServerOptions 5.2.3](https://vertx.io/docs/5.2.3/apidocs/io/vertx/grpc/server/GrpcServerOptions.html) supplies `setMaxMessageSize(long)` and `removeEnabledProtocol(...)`.

Wire both controls and verify actual message limits and accepted protocols. Also make it explicit that annotation discovery produces connection definitions: the current abstract `GrpcModule` still needs a registered concrete implementation to create clients/listeners.

### 4. Medium: daemon-thread configuration is never applied

Both `@VertX.useDaemonThreads` and `VERTX_USE_DAEMON_THREADS` are resolved by the wrapper, but `applyVertxAnnotation` never calls `setUseDaemonThread`. Reproduced: **annotation=true, actual=false**.

Source: [VertXPreStartup.java](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VertXPreStartup.java#L167). The website currently presents the environment variable as an effective control.

### 5. Medium: package annotations and environment-only settings have inconsistent activation

The core filesystem, event-bus, DNS and basic Vert.x metrics processors use `Discovery.annotatedClasses(...)` and proceed only when exactly one class is found. Their annotations explicitly support PACKAGE. In SPI mode, a registered annotated package is visible to `Discovery.annotations`, but not to `annotatedClasses`.

Reproduced:

- Package filesystem annotation count: 1; annotated class count: 0; requested caching=false, actual caching=true.
- `VERTX_EVENTBUS_PORT=19000` without an event-bus annotation leaves the actual option at 0.

Sources: [VertXPreStartup.java](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VertXPreStartup.java#L204), [Discovery.java](../../../../client/src/main/java/com/guicedee/client/utils/Discovery.java#L93).

Use one class/package discovery contract, explicit duplicate handling and environment resolution independent of annotation presence. Test SPI and scanner modes separately. Telemetry and auth have their own discovery paths and should join that consistency test matrix rather than being assumed equivalent.

There is also a scanner reconstruction mismatch: [VerticleBuilder.value()](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VerticleBuilder.java#L383) reads the metadata field `workerPoolName`, while the annotation declares `value`. A package-level `@Verticle("pool")` reconstructed through this path loses its pool name. Its enum reconstruction and legacy capability package names also need scanner-specific regression coverage.

### 6. Medium: annotation defaults materially change an otherwise unchanged runtime

| Option | Vert.x 5.2.3 default without `@VertX` | GuicedEE annotation default |
| --- | --- | --- |
| Event loops | 2 × available processors | 10 |
| Maximum worker execution | 60 seconds | 10000 minutes |
| HA quorum | 1 | 5 |
| Prefer native transport | false | true |
| Disable TCCL | false | true |
| Daemon threads | false | true, currently unapplied |

Sources: [VertX.java](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VertX.java), upstream source artifact below.

The benchmarks explicitly preserve their former values, but ordinary applications still experience these differences when adding an annotation for one attribute. Decide which are intentional GuicedEE defaults and which need an unset/inherit representation. The website must distinguish upstream defaults, GuicedEE annotation defaults and effective values without an annotation.

### 7. Medium: placeholders are not resolved consistently

Auth, Redis and gRPC have local placeholder parsers instead of consistently using `Environment.resolvePlaceholders`.

Reproduced:

- Redis `${REVIEW_UNSET_629:-fallback}` becomes `-fallback`.
- OAuth2 `${REVIEW_UNSET_629:fallback}` becomes an empty string.

Sources: [RedisPreStartup.java](../../../../vertx/src/main/java/com/guicedee/vertx/redis/RedisPreStartup.java#L161), [OAuth2AuthenticationProvider.java](../../../../vertx/src/main/java/com/guicedee/vertx/auth/oauth2/OAuth2AuthenticationProvider.java#L326), [GrpcPreStartup.java](../../../../vertx/src/main/java/com/guicedee/vertx/grpc/GrpcPreStartup.java#L125).

Use the canonical resolver and test both default syntaxes, nested values and absent variables. Recheck string annotation defaults that contain placeholders against environment overrides.

### 8. Medium: per-address event overrides bypass dot-env files

Event-address replacement and per-address event options use direct `System.getProperty/getenv` calls. Global event options use `Environment`. Consequently, the two scopes do not share override precedence.

Reproduced: `.env` contains `VERTX_EVENT_WORKER_REVIEW_EVENT=true`, but the resolved worker option remains false.

Source: [VertxEventRegistry.java](../../../../vertx/src/main/java/com/guicedee/vertx/spi/VertxEventRegistry.java#L98).

Preserve the intended per-address-before-global ordering while including dot-env sources and annotation defaults. Document address normalization: uppercase, dots/hyphens converted to underscores.

### 9. Medium: additional advertised attributes have no effective consumer

| Surface | Current behavior | Needed decision |
| --- | --- | --- |
| `RedisOptions.database` / `REDIS_DATABASE` | Stored in connection info; not used by `RedisModule` | Apply database selection, or explicitly require the database in the connection URI |
| `MetricsOptions.PrometheusOptions.port` / `METRICS_PROMETHEUS_PORT` | Parsed; router configurator exposes the endpoint on the existing HTTP listener | Remove/deprecate the ineffective port or implement an owned listener |
| `OAuth2Options.scopes`, `redirectUri` | Declared; no consumers in the Vert.x module | Wire into the appropriate authorization-request flow or remove unsupported claims |
| `AuthOptions.leeway` | Wrapped but not used to configure provider JWT options | Define inheritance versus provider-specific leeway |
| Event ordering/batch/resume fields | Explicitly advisory/informational in annotation Javadoc | Website must mark them as reserved/informational |

Sources: [RedisModule.java](../../../../vertx/src/main/java/com/guicedee/vertx/redis/RedisModule.java#L85), [PrometheusMetricsConfigurator.java](../../../../metrics/src/main/java/com/guicedee/metrics/implementations/PrometheusMetricsConfigurator.java#L20), [OAuth2Options.java](../../../../vertx/src/main/java/com/guicedee/vertx/auth/oauth2/OAuth2Options.java#L147), [VertxEventOptions.java](../../../../vertx/src/main/java/com/guicedee/vertx/VertxEventOptions.java#L52).

### 10. Medium: OAuth timeout resolution loses precision

`readTimeout` is documented in milliseconds but applied through `setIdleTimeout(readTimeout / 1000)`. Positive values below 1000 become zero, disabling the timeout. This configures connection idle time, not a complete OAuth request deadline.

Source: [OAuth2AuthenticationProvider.java](../../../../vertx/src/main/java/com/guicedee/vertx/auth/oauth2/OAuth2AuthenticationProvider.java#L220). Preserve milliseconds with an explicit idle-timeout unit and document which boundary is controlled.

## Website reconciliation

The [environment comparison CSV](environment-website.csv) records every extracted key/template and its current row. The missing groups include all 11 gRPC keys, all 13 Redis keys, seven deployment keys, numerous auth and per-address event keys, `HTTP2_ENABLED`, and five trace/log exporter keys.

The following website rows also require correction:

- `VERTX_PUBLISH_THROTTLE_MS` and `VERTX_PUBLISH_QUEUE_WARN`, including their per-address variants: no executable readers found in the GuicedEE source search. These cannot be presented as working overrides.
- `VERTX_AUTH_OAUTH2_DISCOVERY_URL`: no reader found; the provider currently uses site and well-known-provider configuration.
- `METRICS_PROMETHEUS_PORT`: no dedicated listener is configured.
- Event batching/resume rows: distinguish reserved metadata from implemented behavior.
- DNS refresh period: the page says milliseconds, while the built options currently use nanoseconds.
- `@VertX` rows: distinguish annotation defaults from defaults with no annotation instead of using a single “auto”/default column.
- `GUICEDEE_STARTUP_TIMEOUT_SECONDS`: the page's 60-second value describes the lower-level pre-startup wait; executable `GuiceApplication.run` supplies a 300-second default. Document the entry-point context.

`VERTX_CLUSTER_ENABLED` is not a stale row: its reader belongs to Hazelcast, outside the four-module inventory.

For the newly added `@HttpServerOptions`, document that `http2Enabled/HTTP2_ENABLED` currently selects HTTPS ALPN versions. It does not disable cleartext H2C. Vert.x 5.2.3 has a separate `http2ClearTextEnabled` option. The broader [HttpServerOptions API](https://vertx.io/docs/5.2.3/apidocs/io/vertx/core/http/HttpServerOptions.html) also exposes HTTP/1 and HTTP/2 configuration objects, compression configuration, HTTP/2 flood controls, TLS/network options and WebSocket controls that the annotation does not cover.

## Upstream surface and intentional extension points

| Direct upstream class | Declared setters inventoried | Corresponding annotation members |
| --- | ---: | ---: |
| VertxOptions | 22 | 16 |
| EventBusOptions | 38 | 12 |
| AddressResolverOptions | 16 | 14 |
| FileSystemOptions | 4 | 3 |
| DeploymentOptions | 9 | 7 |
| HttpServerOptions | 74 | 12 |

These are declared methods, not a count of inherited methods or nested configuration fields. HTTP enablement, HTTPS ports and body/upload settings are GuicedEE orchestration controls, so not all 20 HTTP annotation fields correspond to setters on the upstream HTTP options class.

Missing scalar options worth considering include DNS refresh unit and transport enablement, filesystem exact-cache-path selection, HA group, network/reconnect/TLS options for the event bus, and selected HTTP/2/WebSocket limits. Complex objects such as classloaders, factories, arbitrary JSON and runtime callbacks should retain documented SPI/programmatic configuration. Do not turn every upstream method into a scalar environment variable automatically.

## Recommended implementation sequence

1. Fix the reproduced TLS, timeout/unit, ignored-setting, discovery and precedence defects. Add focused tests at the option object and actual resource boundaries.
2. Give each configuration member a recorded contract: annotation/attribute, environment key or template, type, unit, annotation default, no-annotation default, activation requirements, supported discovery modes, runtime setter/consumer and support status.
3. Choose explicit behavior for currently inert fields; preserve compatibility with deprecation/aliases where necessary.
4. Generate website tables and example environment files from that contract. Include annotation links and distinguish implemented, conditional, SPI-only and reserved settings.
5. Add checks for catalog members without consumers, runtime keys without documentation, documentation keys without readers, class/package and SPI/scanner parity, and value/unit round trips.
6. Validate real listeners/protocols after configuration tests, then application-linked/native modes where applicable. Keep published benchmark timings separate from this corrective configuration work.

## Evidence and limits

- Local BOM: [Versioner pom.xml](../../../../bom/Versioner/pom.xml#L430) pins Vert.x **5.2.3**.
- Upstream source: [Maven Central vertx-core 5.2.3 sources](https://repo.maven.apache.org/maven2/io/vertx/vertx-core/5.2.3/vertx-core-5.2.3-sources.jar). Cached source SHA-1 `be428fda2ce4de8875883fd02504c7c8b29bfdfe` matched the official repository checksum during this review.
- The ten probe observations are preserved verbatim. They use local compiled framework classes and the 5.2.3 runtime; no production systems are contacted.
- The inventory generator recognizes current literal lookups, provider-prefix helpers, indexed PEM keys, per-address event templates and per-signal telemetry resolution. It is a source inventory, not a general Java semantic analyzer. Website percentages measure explicit table-row presence.
- No production Java source, dependency version, website page implementation or public deployment was changed during this review. Review artifacts are the only additions.
