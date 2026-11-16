# GuicedEE developer reference

> GuicedEE is a Guice-first, JPMS modular platform for reactive Java and Kotlin services on Vert.x. Select the integrations your application needs; the runtime discovers their lifecycle hooks and bindings.

## Scope and versions

This reference describes the 2.6.0 source line. A documentation version is not proof of Maven Central publication. Check the [release page](https://guicedee.com/releases) and the repository before selecting a version. Use one consistent GuicedEE BOM version across an application. The component inventory records actual Maven coordinates and JPMS names; marketing names are not dependency coordinates.

The linked module documents contain source README instructions, dependencies, module descriptors, and declared API signatures. Source READMEs can include historical examples and validation notes: use the inventory for coordinates, and check the particular version's source for behavior. The API index covers source declarations, not inherited or Lombok-generated members. SDK wrappers expose upstream APIs; their POMs identify the upstream artifacts instead of inventing GuicedEE facade functions.

## Start an application

Use JDK 25 for this source line. The [App Builder](https://guicedee.com/builder) and [IntelliJ plugin](https://plugins.jetbrains.com/plugin/31112) generate Java or Kotlin applications with Maven or Gradle, module descriptors, selected integrations, and packaging configuration. Choose JVM, application jlink, or Native-Image according to the deployment target.

Import `com.guicedee:guicedee-bom` in Maven dependency management or as a Gradle platform. Add the modules you directly use, for example `com.guicedee:rest`. Keep the application on the module path. A `module-info.java` declares the required modules and opens application packages to the actual reflection consumers; opening to Guice does not transfer access to Hibernate or other modules.

```java
import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedinjection.GuiceApplication;

public final class Boot {
    public static void main(String[] args) {
        GuiceApplication.run(() ->
            IGuiceContext.registerModule("example.app"));
    }
}
```

The registered name must match the application's JPMS module. `GuiceApplication.run(Runnable)` waits for asynchronous startup and handles unsuccessful startup cleanup. Use `IGuiceContext.get(Class)` or `get(Key)` for explicit lookup; prefer constructor injection in application services.

## Dependency injection and lifecycle

`client` defines context, lifecycle and discovery contracts; `inject` implements bootstrap and Guice integration. Module installers, startup/shutdown hooks, scanners and other services are discoverable via SPI. JPMS `provides ... with ...` and classpath `META-INF/services` declarations have different packaging roles; preserve both where required. SPI discovery can avoid classpath scanning when the application supplies the necessary registrations. Do not assume that turning off scanning automatically registers application resources or entities.

The CDI module bridges supported Jakarta CDI annotations and scopes into Guice. It is an optional compatibility integration. The runtime is not a Servlet container, and applications do not need a CDI XML deployment model. See the CDI module for producers, qualifiers, events, scopes, and BeanManager support and limitations.

## HTTP, REST and event-driven code

`vertx` owns the reactive runtime and event-bus integration. `web` configures HTTP/HTTPS routing and server lifecycle. `rest` binds Jakarta REST resources and parameters. `rest-client` supplies typed outbound clients, endpoint configuration and authentication strategies. `websockets` integrates socket receivers, actions and connection lifecycle. `web-services` provides SOAP through Apache CXF. GraphQL includes schema providers, data loaders, HTTP/WebSocket endpoints and optional registry-based schema composition.

Return the async types supported by the relevant adapter, including Vert.x `Future` and Mutiny APIs. Do not perform blocking SDK or database calls on an event-loop thread. Use the integration's worker or coroutine boundary where needed. See each module's examples for context propagation, cancellation, errors and shutdown.

## Configuration, security and observability

MicroProfile Config integrates SmallRye configuration with Guice. Supported fields can use `@ConfigProperty` without an extra `@Inject`. Environment variables and system properties provide deployment overrides; the [environment reference](https://guicedee.com/environment-variables) documents names and defaults. Consult individual module configuration tables for additional integration-specific settings.

JWT maps Vert.x authentication to MicroProfile JWT and call-scoped claims. Consult the JWT and REST client guides for authentication, authorization and outbound credentials; never put service account keys or client secrets into source examples. The compile-only Keycloak API service is for building provider extensions and must not be deployed inside Keycloak.

Health supplies liveness, readiness and startup checks. Metrics supports instrumented operations and scrape/reporting endpoints. Telemetry supplies tracing and reactive context propagation. OpenAPI and Swagger UI expose API documentation. Fault tolerance adds retries, timeouts, circuit breakers, fallbacks and bulkheads. Configure authentication and endpoint exposure deliberately in deployed applications.

## Persistence and representations

Persistence integrates Hibernate Reactive and Vert.x SQL clients, plus MongoDB, Cassandra and Redis integrations. Supported SQL paths include PostgreSQL, MySQL, SQL Server, Oracle and DB2; availability and configuration depend on the selected driver. Configure a database module and persistence unit, entity scanning/registration, and the reactive session factory. Use explicit transactions for writes. Stateless sessions require explicit insert/update/delete operations; they do not provide stateful dirty checking or automatic cascade behavior.

JSON, XML and Excel representations are separate artifacts. EntityAssist is a related project for entities and a reactive query DSL; it is not a replacement name for the persistence module. See the module docs for exact session, query and serialization APIs.

## Messaging, caching and service discovery

Kafka, RabbitMQ and IBM MQ integrations provide connection/topic/queue declarations and managed consumer/publisher lifecycles. Mail Client provides SMTP sending. Cerial supplies serial-port connections, framing/reconnect behavior and monitoring.

Hazelcast clustering and caching require explicit activation. Merely adding a dependency must not start a cluster. Distinguish a cache-only member from a Vert.x clustered event bus, and configure member and advertised event-bus addresses independently. WebSocket private replies and connection ownership are process-local concerns; cluster-wide fan-out is a separate operation.

Service Registry manages named services, resolution and health. Service Discovery supplies Vert.x resolvers such as DNS SRV and Kubernetes. Consul and its resolver are separate modules. Runtime Autoconfigure detects deployment platforms and supplies runtime defaults. Consult each module for activation, priority and override behavior.

## Kotlin

`com.guicedee:kotlin` provides coroutine integration over the normal GuicedEE lifecycle. `GuicedCoroutines.future { ... }` bridges suspending code to Vert.x `Future`; optional Kotlin/Mutiny adapters are separate artifacts. Java and Kotlin share the same Guice injector, REST annotations, JPMS model and integrations. A `suspend` function is not automatically a registered REST endpoint: use the documented adapter boundary. The Kotlin sources list public extension functions and coroutine helpers.

## Firebase, Commercetools and modular library services

`com.guicedee.modules.services:firebase-admin` exposes `firebase.admin`: Firebase Auth, Messaging and Firestore APIs in a grouped JPMS module. `com.guicedee.modules.services:commercetools` exposes `commercetools.sdk.java.api`: commerce API and GraphQL models, RMF and HTTP integration. Use upstream client APIs with externalized credentials and documented lifecycle cleanup.

The Commercetools path uses Jackson 3 core/databind; do not add Jackson 2 core/databind to fix resolution. Zstd JNI and Okio services provide corrected modular dependencies. Services also cover Guice extensions, Apache Commons/CXF/POI, database drivers, Hibernate, caching, JNI, Jakarta, security, messaging, MicroProfile, and Vert.x adapters. The library-service inventory lists every selected reactor artifact and its upstream dependencies.

Preserve upstream module names where supplied. A wrapper's artifact name is not necessarily its JPMS name. Native resources, service providers and reflection metadata are part of runtime compatibility; compilation alone does not verify them.

## JVM, jlink and Native-Image

Clean compile and normal JVM packaging describe the developer edit/build cycle. Application jlink packages the application and its complete named-module dependency graph with a trimmed Java runtime. It remains a JVM application. Native-Image creates a native executable and needs the relevant reachability metadata, resources and service registrations.

Validate the generated application's entire module path. For jlink, run the linked application and exercise ServiceLoader providers and selected integrations. For Native-Image, execute the compiled application and make representative integration calls; a successful image build alone does not prove network or SDK behavior. Emulator/fixture tests and real cloud-service tests have different coverage and credential requirements.

## Benchmarks

The [public benchmark repository](https://github.com/GuicedEE/benchmarks) contains methodology, results and machine-readable chart inputs. Compare equivalent scenarios and packaging modes, record the commit/date, and retain limitations. Quarkus is a useful comparison because it also uses Vert.x; the website also compares Micronaut. Keep normal JVM compile/package, application jlink, and native build/runtime measurements distinct. Persistence and CDI results measure their documented workload and setup, not every possible application.

The intended message is keeping up with leading frameworks. Avoid universal faster/slower claims, invented figures, or treating a snapshot as a continuously verified production guarantee.

## Finding exact APIs

Start with the module README and its declared API index. The generated inventory links the real repository and source files, includes direct dependency declarations and the JPMS descriptor, and identifies source inputs by SHA-256. Follow upstream artifact coordinates for SDK APIs. For a version-specific answer, verify against that version's source or published source/Javadoc artifact. The full reference is a convenience concatenation of the same individual documents.
