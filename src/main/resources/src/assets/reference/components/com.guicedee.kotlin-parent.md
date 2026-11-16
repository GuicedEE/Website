# com.guicedee:kotlin-parent

Kotlin integration over Vert.x coroutines using GuicedEE Future boundaries.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `pom`.
- JPMS module: not declared in this source POM/directory; inspect the build configuration.
- [Source POM](https://github.com/GuicedEE/Kotlin/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Module guide

Source: [README.md](https://github.com/GuicedEE/Kotlin/blob/master/README.md). Historical version examples retain their original context.

# GuicedEE Kotlin

Thin Kotlin/JVM integration over Vert.x's `vertx-lang-kotlin-coroutines`. Guice owns injection,
GuicedEE owns startup and shutdown, and annotated HTTP/event-bus methods return the existing
Vert.x `Future<T>` type. Suspending services can run inside those boundaries.

This is initial development against **GuicedEE 2.6.0**. These new artifacts have not been
published to Maven Central. The current platform baseline must be installed locally until
its release is available; cloning this repository alone does not supply that baseline.

## Using it

```xml
<dependency>
    <groupId>com.guicedee</groupId>
    <artifactId>kotlin</artifactId>
    <version>2.6.0</version>
</dependency>
```

For HTTP endpoints also add `com.guicedee:rest`. Continue using the normal GuicedEE bootstrap.
The Kotlin module registers its Guice bindings and shutdown hook through JPMS and
`META-INF/services`. It adds Jackson 3's Kotlin module to the shared JSON mappers.

```kotlin
import com.google.inject.Inject
import com.guicedee.kotlin.GuicedCoroutines
import io.vertx.core.Future
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import kotlinx.coroutines.delay

data class Greeting(val message: String)

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
class HelloResource @Inject constructor(private val coroutines: GuicedCoroutines) {
    @GET
    @Path("/{name}")
    fun hello(@PathParam("name") name: String): Future<Greeting> =
        coroutines.future {
            delay(10)
            Greeting("Hello, $name")
        }
}
```

Use upstream `io.vertx.kotlin.coroutines.coAwait` to await a Vert.x Future. The returned
Future preserves the normal GuicedEE response, failure, and event-bus reply paths.
The [complete example](https://github.com/GuicedEE/Kotlin/blob/master/examples/basic) includes HTTP and event-bus consumers.

## Execution and ownership

`GuicedCoroutines` is an injected singleton using the application's injected `Vertx`.
It uses the upstream **context dispatcher**, Kotlin structured concurrency, and a small
Future completion bridge. It owns jobs so failures, cancellation before execution, and
shutdown always resolve the corresponding Future.

* `future { ... }` starts work immediately and returns `Future<T>`.
* `start { ... }` returns `CoroutineCall<T>` with `future` and `cancel()`.
* `shutdown()` rejects new calls, cancels owned jobs, and returns a Future that completes
  after their call-scope cleanup. It leaves the injected Vert.x runtime open.
* The GuicedEE shutdown hook awaits cleanup before persistence pools and Vert.x teardown. The default limit is
  10 seconds, configured with `GUICEDEE_KOTLIN_SHUTDOWN_TIMEOUT_SECONDS` through `Environment`.
  `close()` is for non-event-loop lifecycle callers; use `shutdown()` asynchronously on an event loop.

An active caller's `CallScope` is joined synchronously and retained until work and its
structured children finish. Work without an active scope gets its own duplicated Vert.x
context and scope. Nested calls in the same active scope intentionally share its objects.
The caller remains responsible for establishing isolated incoming request contexts; this
module does not replace the HTTP or event-bus dispatchers.

Scoped work must remain on the owning Vert.x context. `Dispatchers.IO` and `Dispatchers.Default`
do not carry GuicedEE scope. Use upstream `awaitBlocking` for isolated blocking operations;
do not use a context-bound Hibernate Reactive session concurrently or move it to another dispatcher.
Coroutine cancellation is cooperative and does not guarantee cancellation of an underlying I/O operation.

A Vert.x Future has no cancellation method. Returning one from a REST endpoint does **not**
automatically cancel its coroutine when a client disconnects. Callers needing cancellation
must retain the `CoroutineCall`, cancel it explicitly, or use a coroutine timeout. Application
shutdown cancels all remaining owned work.

## Optional Mutiny support

Use `com.guicedee:kotlin-mutiny:2.6.0` in place of the core `kotlin` dependency and
`requires com.guicedee.kotlin.mutiny;` in your application descriptor. This opt-in module
combines the managed runtime with the upstream Mutiny coroutine adapters:

```kotlin
import io.smallrye.mutiny.coroutines.awaitSuspending

fun customer(): Future<Customer> = coroutines.future {
    customerClient.send().awaitSuspending()
}
```

`awaitSuspending()` subscribes to the lazy `Uni`, suspends without blocking, propagates
failure, and cancels the subscription when its coroutine is cancelled. A `Uni` can emit
null, so use nullable Kotlin result types where appropriate. The upstream `asFlow`,
`asMulti`, `asUni`, and Multi-await adapters are also available. Pass an owned coroutine
scope to upstream coroutine builders; their default global scope is not managed by GuicedEE.

The upstream `mutiny-kotlin` JAR shares `io.smallrye.mutiny` with Mutiny core. To avoid a
JPMS split package, `com.guicedee.modules.services:mutiny-kotlin` includes its coroutine
adapters without relocation and keeps the automatic module name `mutiny.kotlin` as an
explicit module. Root-package convenience functions (`uni`, `multi`, tuple/failure helpers,
`replaceWithUnit`) and the dependent `coroutines.multiSuspend` builder are excluded.
The other coroutine classes and Kotlin metadata retain their original bytes. Do not add
the original `io.smallrye.reactive:mutiny-kotlin` alongside this wrapper.

Hibernate transaction cancellation needs an additional ownership rule: cancelling a Uni
subscription alone does not prove its asynchronous rollback/session cleanup is finished.
The [persistence example](https://github.com/GuicedEE/Examples/tree/master/Kotlin/Persistence)
runs transaction work as a child of the caller and awaits the enclosing Hibernate operation
in `NonCancellable`, allowing cancellation to fail the work while awaiting rollback and close.
No session is shared between parallel operations. The
[REST-client example](https://github.com/GuicedEE/Examples/tree/master/Kotlin/RestClient)
demonstrates ordinary `@Endpoint` injection and Future-returning REST methods.

## JPMS

```java
module my.application {
    requires com.guicedee.kotlin;
    requires com.guicedee.rest;
    opens my.application to com.google.guice, com.guicedee.rest, com.guicedee.vertx;
    opens my.application.dto to tools.jackson.databind, tools.jackson.module.kotlin, kotlin.reflect;
}
```

Kotlin DTO constructors and default arguments need access for **both Jackson and Kotlin reflection**.
The upstream coroutine JAR is an automatic module. The accompanying
`com.guicedee.modules.services:vertx-lang-kotlin-coroutines` wrapper adds an explicit descriptor,
retaining its existing module name `vertx.lang.kotlin.coroutines`, classes and Kotlin metadata.
Do not add the upstream JAR alongside the wrapper: they contain the same packages.

## Build and verify

Prerequisites: JDK 25+, Python 3 for the runtime check, and the GuicedEE 2.6.0 platform artifacts.
The wrapper pins Maven 4.0.0-rc-7. Kotlin compiler/stdlib/reflection are aligned at 2.4.21;
coroutines use 1.11.0. Vert.x follows the GuicedEE BOM (5.2.3 in the initial baseline).
Existing official Maven plugin versions are inherited.

```shell
./mvnw -B verify
python scripts/verify-runtime.py --jlink
```

On Windows use `mvnw.cmd`. The reactor contains:

| Directory | Artifact |
| --- | --- |
| `vertx-lang-kotlin-coroutines` | `com.guicedee.modules.services:vertx-lang-kotlin-coroutines` |
| `runtime` | `com.guicedee:kotlin` |
| `mutiny-kotlin` | `com.guicedee.modules.services:mutiny-kotlin` (upstream coroutine subset) |
| `mutiny` | `com.guicedee:kotlin-mutiny` (optional integration) |
| `examples/basic` | Executable HTTP/event-bus example; excluded from deployment |

Runtime tests cover suspension/context identity, concurrent scope isolation, retained caller
scopes, structured children, failures, cancellation, shutdown and Jackson data classes.
The runtime script boots the actual GuicedEE example, checks concurrent HTTP requests and an
event-bus reply, then shuts it down. With `--jlink`, it repeats the smoke test using a linked
image built from the complete generated application module path. Logs and results are saved
under `target/runtime-verification-*`.

## Initial scope

Annotated entry methods are ordinary Kotlin functions returning `Future<T>`.
Automatic `@GET suspend fun` or `@VertxEventDefinition suspend fun` dispatch, Kotlin-specific
OpenAPI inference, Flow endpoint adaptation, and coroutine-aware interceptors on suspending
service methods are future work. Do not assume `@Trace`, retry, or timeout annotations understand
the completion of a directly intercepted `suspend` method. Existing Future-returning boundaries
retain their normal Java contracts.

Upstream `CoroutineVerticle`, `coroutineRouter`, `coRespond`, and `coConsumer` APIs remain available.
Calling them directly uses their own scope/lifecycle rules; it does not automatically enroll
their jobs in `GuicedCoroutines`.

See the [Vert.x coroutine guide](https://vertx.io/docs/vertx-lang-kotlin-coroutines/kotlin/).

Licensed under Apache-2.0. The wrappers retain upstream Vert.x and SmallRye code under their original licenses.
