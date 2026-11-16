# GuicedEE configuration for Vert.x 5.2.3

The website's **Annotations & Environment Variables** page is generated from the
framework declarations and executable environment lookups. The current contract covers
**242 annotation members, 223 environment keys/templates, and 163 declared setters across
six upstream core option classes**. Annotation-only metadata and programmatic options
are recorded explicitly; they are not advertised as environment switches.

- [Machine-readable contract](vertx-5.2.3.json)
- [Commented environment reference](vertx-5.2.3.env.example)
- [Upstream setter coverage and extension points](vertx-5.2.3-upstream.csv)
- [Original review and reproduction evidence](../reviews/vertx-5.2.3/REVIEW.md)
- [Fixes and validation](../reviews/vertx-5.2.3/FIXES.md)
- [Annotation text across all GuicedEE modules](ANNOTATION_TEXT.md)

## Value resolution

For each key, the first configured source wins:

1. JVM system property.
2. OS environment, exact key then uppercase with dots/hyphens replaced by underscores.
3. `.env.local`.
4. `.env`.
5. Application defaults registered by `GuiceApplication.run(Main.class)` or
   `Environment.configureApplication(Main.class)`.
6. The module's option annotation, or its runtime default without a declaration.

Each dot-env file is found independently: working directory, up to five parent
directories, then a file resource at the classpath root. The nearest existing file wins,
including an empty file. Files are loaded once; `Environment.reloadDotEnv()` reloads them.
Lookup never writes resolved values into system properties. Both `${NAME:default}` and
`${NAME:-default}` support nested resolution; missing variables without defaults remain
unresolved. Explicit empty strings remain configured values, although individual options
documented as empty/inherit retain their native setting.

String annotation values can also be bare property names. For example,
`@HttpServerOptions(host = "APP_BIND_HOST")` first checks the normal `HTTP_HOST` key
across its sources. Only if absent does it look up `APP_BIND_HOST` using the same
source order, including application defaults. If the name is absent too, the annotation
text remains literal. This also applies to each string-array entry and to
`@EnvironmentProperty` values. `${APP_BIND_HOST:-127.0.0.1}` remains available when a
literal fallback is needed. Found values can contain explicit placeholders, but are
not followed recursively as bare names. Explicit external overrides and placeholder
results remain values, even if their text happens to match another key.

Application class defaults override application package defaults. Module configuration
declarations are singletons and reject duplicates; Redis and gRPC definitions are repeatable.
In SPI mode, register declaring classes/packages through `IGuiceDiscovery`. Scanner mode
must include the application module/package.

Per-event keys precede global event keys, with the complete source order applied to each
key. Address normalization uses locale-independent uppercase, replacing dots/hyphens with
underscores. Provider JWT leeway overrides the global leeway; annotation `-1` inherits,
whereas explicit `0` disables tolerance. OAuth request-specific scopes/redirect URI take
priority over resolved deployment defaults. OTLP signal-specific endpoint keys take
priority over shared endpoint keys, as recorded in the catalog.

`VertxConfigurator.options` and HTTP server option configurators run after declarative
configuration and can deliberately change it. Complex TLS objects, factories, callbacks,
classloaders and other unexposed API options remain programmatic.

## Compatibility changes

- Empty `@VertX` now uses processor-based event-loop sizing, a 60-second worker limit,
  quorum 1, native transport false, daemon threads false and TCCL disabling false.
  Execution durations retain explicit paired units. Benchmark overrides remain explicit.
- Filesystem annotation defaults now enable classpath resolution and caching, matching
  ordinary upstream defaults. Empty cache paths inherit the upstream path.
- DNS hosts refresh defaults to milliseconds in GuicedEE, matching its documented contract.
  The upstream raw API defaults to nanoseconds. `hostsRefreshPeriodUnit` is independently
  configurable. DNS `ndots = -1` inherits system resolver settings.
- `RedisOptions.database = -1` preserves the URI database. Non-negative values select that
  database on every endpoint. Redis Cluster only supports database 0.
- Prometheus port `-1` keeps the endpoint on the application listener. An explicit port
  `0..65535` creates an isolated lifecycle-owned listener; `0` chooses an available port.
  The old port setting was parsed but did not create a listener.
- OAuth scope/redirect defaults now reach authorization and token requests. Subsecond
  connection idle timeouts retain millisecond precision. JWT provider leeway defaults to
  `-1` to inherit the global setting.
- `HTTP2_ENABLED` controls HTTPS ALPN; `HTTP2_CLEAR_TEXT_ENABLED` independently controls H2C.
- Event ordering/batch/resume attributes remain reserved metadata. Stale publisher-throttle
  and OAuth discovery-URL environment claims were removed. OAuth discovery uses `site` and
  `wellKnownProvider`.

## Keeping the website synchronized

From the workspace root:

```powershell
python GuicedEE/website/tools/vertx_configuration.py
python GuicedEE/website/tools/vertx_configuration.py --check
```

The generator inventories source declarations and readers, applies explicit aliases and
activation rules, and writes the JSON contract, commented environment reference, upstream
coverage CSV and Java website catalog. `--check` fails when generated files drift or a
configuration attribute/key has no recorded classification. Runtime tests verify behavior
beyond source presence; this inventory is not a general Java semantic analyzer.
