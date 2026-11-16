# com.guicedee.modules.services:commercetools

Grouped Commercetools Java SDK, GraphQL models and HTTP client JPMS module.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `commercetools.sdk.java.api`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/commercetools/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.junit.jupiter:junit-jupiter`; scope `test`.
- `com.commercetools.sdk:commercetools-sdk-java-api`; scope `compile`; optional.
- `com.commercetools.sdk:commercetools-http-client`; scope `compile`; optional.
- `com.guicedee.modules.services:javassist`; scope `compile`.
- `org.bouncycastle:bcprov-jdk18on`; scope `compile`.
- `org.bouncycastle:bcpkix-jdk18on`; scope `compile`.
- `org.bouncycastle:bctls-jdk18on`; scope `compile`.
- `com.guicedee.modules.services:zstd-jni`; scope `compile`.
- `tools.jackson.core:jackson-core`; scope `compile`.
- `dev.failsafe:failsafe`; scope `compile`.
- `javax.money:money-api`; scope `compile`.
- `org.javamoney.moneta:moneta-core`; scope `compile`.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `com.fasterxml.jackson.core:jackson-annotations`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `org.slf4j:slf4j-api`; scope `compile`.
- `io.netty:netty-transport-native-epoll`; scope `compile`.
- `io.netty:netty-transport-classes-epoll`; scope `compile`.
- `io.netty:netty-transport-native-kqueue`; scope `compile`.
- `io.netty:netty-transport-classes-kqueue`; scope `compile`.
- `commons-io:commons-io`; scope `compile`.
- `io.netty:netty-codec`; scope `compile`.
- `io.netty:netty-common`; scope `compile`.
- `io.netty:netty-buffer`; scope `compile`.
- `io.netty:netty-transport`; scope `compile`.
- `io.netty:netty-codec-base`; scope `compile`.
- `io.netty:netty-codec-compression`; scope `compile`.
- `io.netty:netty-codec-http`; scope `compile`.
- `io.netty:netty-codec-socks`; scope `compile`.
- `io.netty:netty-handler-proxy`; scope `compile`.
- `io.netty:netty-handler`; scope `compile`.
- `io.netty:netty-resolver`; scope `compile`.
- `io.netty:netty-transport-native-unix-common`; scope `compile`.
- `com.guicedee.modules.services:reactive-streams`; scope `compile`.
- `com.squareup.okhttp3:okhttp-jvm`; scope `compile`.
- `com.guicedee.modules.services:okio`; scope `compile`.

## JPMS descriptor

```java
open module commercetools.sdk.java.api {
    uses tools.jackson.databind.module.SimpleModule;

    requires javassist;
    requires transitive com.fasterxml.jackson.annotation;
    requires transitive dev.failsafe.core;
    requires transitive io.netty.buffer;
    requires transitive io.netty.codec;
    requires transitive io.netty.codec.compression;
    requires transitive io.netty.codec.http;
    requires transitive io.netty.codec.socks;
    requires transitive io.netty.codec.unused;
    requires transitive io.netty.common;
    requires transitive io.netty.handler;
    requires transitive io.netty.handler.proxy;
    requires transitive io.netty.resolver;
    requires transitive io.netty.transport;
    requires transitive io.netty.transport.classes.epoll;
    requires transitive io.netty.transport.classes.kqueue;
    // Native platform resources have runtime scope; they expose no SDK API.
    requires static io.netty.transport.epoll.linux.x86_64;
    requires static io.netty.transport.kqueue.osx.x86_64;
    requires transitive io.netty.transport.unix.common;
    requires transitive jakarta.annotation;
    requires transitive java.money;
    requires transitive org.apache.commons.io;
    requires transitive org.apache.commons.lang3;
    requires transitive org.javamoney.moneta;
    requires transitive org.reactivestreams;
    requires transitive org.slf4j;
    requires transitive tools.jackson.core;
    requires transitive tools.jackson.databind;

    requires com.github.luben.zstd_jni;
    requires org.bouncycastle.provider;
    requires org.bouncycastle.pkix;
    requires org.bouncycastle.tls;
    requires java.logging;
    requires java.net.http;
    requires java.management;
    requires java.naming;
    requires java.sql;
    requires java.xml;
    requires java.security.jgss;
    requires jdk.unsupported;
    exports com.commercetools.api;
    exports com.commercetools.api.client;
    exports com.commercetools.api.client.error;
    exports com.commercetools.api.defaultconfig;
    exports com.commercetools.api.json;
    exports com.commercetools.api.models;
    exports com.commercetools.api.models.agent;
    exports com.commercetools.api.models.api_client;
    exports com.commercetools.api.models.approval_flow;
    exports com.commercetools.api.models.approval_rule;
    exports com.commercetools.api.models.associate_role;
    exports com.commercetools.api.models.attribute_group;
    exports com.commercetools.api.models.business_unit;
    exports com.commercetools.api.models.business_unit_search;
    exports com.commercetools.api.models.cart;
    exports com.commercetools.api.models.cart_discount;
    exports com.commercetools.api.models.category;
    exports com.commercetools.api.models.channel;
    exports com.commercetools.api.models.common;
    exports com.commercetools.api.models.custom_object;
    exports com.commercetools.api.models.customer;
    exports com.commercetools.api.models.customer_group;
    exports com.commercetools.api.models.customer_search;
    exports com.commercetools.api.models.discount_code;
    exports com.commercetools.api.models.discount_group;
    exports com.commercetools.api.models.error;
    exports com.commercetools.api.models.event;
    exports com.commercetools.api.models.extension;
    exports com.commercetools.api.models.graph_ql;
    exports com.commercetools.api.models.inventory;
    exports com.commercetools.api.models.mcp_server;
    exports com.commercetools.api.models.me;
    exports com.commercetools.api.models.message;
    exports com.commercetools.api.models.order;
    exports com.commercetools.api.models.order_edit;
    exports com.commercetools.api.models.payment;
    exports com.commercetools.api.models.payment_method;
    exports com.commercetools.api.models.product;
    exports com.commercetools.api.models.product_discount;
    exports com.commercetools.api.models.product_search;
    exports com.commercetools.api.models.product_selection;
    exports com.commercetools.api.models.product_tailoring;
    exports com.commercetools.api.models.product_type;
    exports com.commercetools.api.models.project;
    exports com.commercetools.api.models.quote;
    exports com.commercetools.api.models.quote_request;
    exports com.commercetools.api.models.recurrence_policy;
    exports com.commercetools.api.models.recurring_order;
    exports com.commercetools.api.models.reservation;
    exports com.commercetools.api.models.review;
    exports com.commercetools.api.models.search;
    exports com.commercetools.api.models.shipping_method;
    exports com.commercetools.api.models.shopping_list;
    exports com.commercetools.api.models.staged_quote;
    exports com.commercetools.api.models.standalone_price;
    exports com.commercetools.api.models.state;
    exports com.commercetools.api.models.store;
    exports com.commercetools.api.models.store_country;
    exports com.commercetools.api.models.subscription;
    exports com.commercetools.api.models.tax_category;
    exports com.commercetools.api.models.type;
    exports com.commercetools.api.models.variant;
    exports com.commercetools.api.models.variant_attributes;
    exports com.commercetools.api.models.warning;
    exports com.commercetools.api.models.zone;
    exports com.commercetools.api.predicates;
    exports com.commercetools.api.predicates.expansion;
    exports com.commercetools.api.predicates.expansion.approval_flow;
    exports com.commercetools.api.predicates.expansion.approval_rule;
    exports com.commercetools.api.predicates.expansion.associate_role;
    exports com.commercetools.api.predicates.expansion.attribute_group;
    exports com.commercetools.api.predicates.expansion.business_unit;
    exports com.commercetools.api.predicates.expansion.cart;
    exports com.commercetools.api.predicates.expansion.cart_discount;
    exports com.commercetools.api.predicates.expansion.category;
    exports com.commercetools.api.predicates.expansion.channel;
    exports com.commercetools.api.predicates.expansion.common;
    exports com.commercetools.api.predicates.expansion.custom_object;
    exports com.commercetools.api.predicates.expansion.customer;
    exports com.commercetools.api.predicates.expansion.customer_group;
    exports com.commercetools.api.predicates.expansion.discount_code;
    exports com.commercetools.api.predicates.expansion.discount_group;
    exports com.commercetools.api.predicates.expansion.extension;
    exports com.commercetools.api.predicates.expansion.inventory;
    exports com.commercetools.api.predicates.expansion.mcp_server;
    exports com.commercetools.api.predicates.expansion.me;
    exports com.commercetools.api.predicates.expansion.message;
    exports com.commercetools.api.predicates.expansion.order;
    exports com.commercetools.api.predicates.expansion.order_edit;
    exports com.commercetools.api.predicates.expansion.payment;
    exports com.commercetools.api.predicates.expansion.payment_method;
    exports com.commercetools.api.predicates.expansion.product;
    exports com.commercetools.api.predicates.expansion.product_discount;
    exports com.commercetools.api.predicates.expansion.product_selection;
    exports com.commercetools.api.predicates.expansion.product_tailoring;
    exports com.commercetools.api.predicates.expansion.product_type;
    exports com.commercetools.api.predicates.expansion.quote;
    exports com.commercetools.api.predicates.expansion.quote_request;
    exports com.commercetools.api.predicates.expansion.recurrence_policy;
    exports com.commercetools.api.predicates.expansion.recurring_order;
    exports com.commercetools.api.predicates.expansion.reservation;
    exports com.commercetools.api.predicates.expansion.review;
    exports com.commercetools.api.predicates.expansion.shipping_method;
    exports com.commercetools.api.predicates.expansion.shopping_list;
    exports com.commercetools.api.predicates.expansion.staged_quote;
    exports com.commercetools.api.predicates.expansion.standalone_price;
    exports com.commercetools.api.predicates.expansion.state;
    exports com.commercetools.api.predicates.expansion.store;
    exports com.commercetools.api.predicates.expansion.subscription;
    exports com.commercetools.api.predicates.expansion.tax_category;
    exports com.commercetools.api.predicates.expansion.type;
    exports com.commercetools.api.predicates.expansion.variant;
    exports com.commercetools.api.predicates.expansion.zone;
    exports com.commercetools.api.predicates.query;
    exports com.commercetools.api.predicates.query.agent;
    exports com.commercetools.api.predicates.query.api_client;
    exports com.commercetools.api.predicates.query.approval_flow;
    exports com.commercetools.api.predicates.query.approval_rule;
    exports com.commercetools.api.predicates.query.associate_role;
    exports com.commercetools.api.predicates.query.attribute_group;
    exports com.commercetools.api.predicates.query.business_unit;
    exports com.commercetools.api.predicates.query.business_unit_search;
    exports com.commercetools.api.predicates.query.cart;
    exports com.commercetools.api.predicates.query.cart_discount;
    exports com.commercetools.api.predicates.query.category;
    exports com.commercetools.api.predicates.query.channel;
    exports com.commercetools.api.predicates.query.common;
    exports com.commercetools.api.predicates.query.custom_object;
    exports com.commercetools.api.predicates.query.customer;
    exports com.commercetools.api.predicates.query.customer_group;
    exports com.commercetools.api.predicates.query.customer_search;
    exports com.commercetools.api.predicates.query.discount_code;
    exports com.commercetools.api.predicates.query.discount_group;
    exports com.commercetools.api.predicates.query.error;
    exports com.commercetools.api.predicates.query.event;
    exports com.commercetools.api.predicates.query.extension;
    exports com.commercetools.api.predicates.query.graph_ql;
    exports com.commercetools.api.predicates.query.inventory;
    exports com.commercetools.api.predicates.query.mcp_server;
    exports com.commercetools.api.predicates.query.me;
    exports com.commercetools.api.predicates.query.message;
    exports com.commercetools.api.predicates.query.order;
    exports com.commercetools.api.predicates.query.order_edit;
    exports com.commercetools.api.predicates.query.payment;
    exports com.commercetools.api.predicates.query.payment_method;
    exports com.commercetools.api.predicates.query.product;
    exports com.commercetools.api.predicates.query.product_discount;
    exports com.commercetools.api.predicates.query.product_search;
    exports com.commercetools.api.predicates.query.product_selection;
    exports com.commercetools.api.predicates.query.product_tailoring;
    exports com.commercetools.api.predicates.query.product_type;
    exports com.commercetools.api.predicates.query.project;
    exports com.commercetools.api.predicates.query.quote;
    exports com.commercetools.api.predicates.query.quote_request;
    exports com.commercetools.api.predicates.query.recurrence_policy;
    exports com.commercetools.api.predicates.query.recurring_order;
    exports com.commercetools.api.predicates.query.reservation;
    exports com.commercetools.api.predicates.query.review;
    exports com.commercetools.api.predicates.query.search;
    exports com.commercetools.api.predicates.query.shipping_method;
    exports com.commercetools.api.predicates.query.shopping_list;
    exports com.commercetools.api.predicates.query.staged_quote;
    exports com.commercetools.api.predicates.query.standalone_price;
    exports com.commercetools.api.predicates.query.state;
    exports com.commercetools.api.predicates.query.store;
    exports com.commercetools.api.predicates.query.store_country;
    exports com.commercetools.api.predicates.query.subscription;
    exports com.commercetools.api.predicates.query.tax_category;
    exports com.commercetools.api.predicates.query.type;
    exports com.commercetools.api.predicates.query.variant;
    exports com.commercetools.api.predicates.query.variant_attributes;
    exports com.commercetools.api.predicates.query.warning;
    exports com.commercetools.api.predicates.query.zone;
    exports com.commercetools.api.product;
    exports com.commercetools.api.search.products;
    exports com.commercetools.http.asynchttp;
    exports com.commercetools.money;
    exports com.typesafe.netty;
    exports io.vrap.rmf.base.client;
    exports io.vrap.rmf.base.client.error;
    exports io.vrap.rmf.base.client.http;
    exports io.vrap.rmf.base.client.oauth2;
    exports io.vrap.rmf.base.client.utils;
    exports io.vrap.rmf.base.client.utils.json;
    exports io.vrap.rmf.base.client.utils.json.modules;
    exports io.vrap.rmf.base.client.utils.json.modules.deserializers;
    exports io.vrap.rmf.base.client.utils.json.modules.serializers;
    uses com.commercetools.money.MonetaryProvider;
    provides com.commercetools.money.MonetaryProvider with com.commercetools.money.FastMoneyProvider;
    uses io.vrap.rmf.base.client.ExecutorHttpClientSupplier;
    provides io.vrap.rmf.base.client.ExecutorHttpClientSupplier with com.commercetools.http.asynchttp.AsyncHttpClientSupplier;
    uses io.vrap.rmf.base.client.HttpClientSupplier;
    provides io.vrap.rmf.base.client.HttpClientSupplier with com.commercetools.http.asynchttp.AsyncHttpClientSupplier;
    uses io.vrap.rmf.base.client.SolutionInfo;
    provides io.vrap.rmf.base.client.SolutionInfo with com.commercetools.http.asynchttp.AsyncHttpClientSolutionInfo;
    uses io.vrap.rmf.base.client.utils.json.ModuleSupplier;
    provides io.vrap.rmf.base.client.utils.json.ModuleSupplier with com.commercetools.api.json.ApiModuleSupplier;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/commercetools/README.md). Historical version examples retain their original context.

# Commercetools grouped JPMS module

Prepared for the upcoming **2.6.0** release. See the [release notes](https://github.com/GuicedEE/Services/blob/RELEASE-2.6.0.md) for validation and publication status.

Coordinates: `com.guicedee.modules.services:commercetools`.
Module: `commercetools.sdk.java.api` (the upstream API module name).

Bundles the Java API, GraphQL request/response models, RMF base and HTTP client.
Versioner manages the upstream SDK version through StandaloneBOM; consumers use
the GuicedEE BOM. This is the SDK GraphQL client, not a GraphQL server engine.

Supporting libraries are relocated to avoid application split packages.
Javassist is excluded from the bundle and supplied by the existing modular
`com.guicedee.modules.services:javassist` dependency (`requires javassist`).
The async HTTP client retains its package name because its configuration
resources use that namespace. ServiceLoader declarations are explicit.

Build Versioner, StandaloneBOM and GuicedEE BOM before installing the shade.
Use `-Dmaven.jar.forceCreation=true`. Publication and a separate repository
have not been provisioned.

## Javadoc

`mvn verify` documents the Commercetools SDK (including GraphQL models and HTTP
client APIs) and RMF against the current shaded binary. Relocated supporting
libraries are excluded from the documentation scope. Generated source archives,
extracted sources and previous API pages are cleared before shading to prevent
stale relocation output. The extracted documentation sources normalize excluded namespace imports to
match the binary, working around shade source-rewriting differences.
Javadoc errors remain fatal.

Bouncy Castle is external: `bcprov-jdk18on`, `bcpkix-jdk18on` and
`bctls-jdk18on` use StandaloneBOM/Versioner management. `bcutil-jdk18on`
is managed at the same version and resolves transitively. The entire
`org.bouncycastle` namespace is excluded from relocation and shading so
Netty adapters load the signed modular provider JARs unchanged.

The wrapper now externalizes Netty (including native transports), SLF4J,
Jackson 3 core/databind and shared annotations, Commons IO/Lang, Failsafe,
Java Money/Moneta and the modular Reactive Streams wrapper. Moneta's JPMS
requirement on OkHttp is supplied explicitly; Okio uses a small modular wrapper.
SLF4J now resolves the application's logging provider. No Jackson 2 core or
databind is part of this wrapper's resolved dependency graph.

## Mapper discovery

The SDK's API mixins and subtype module scan the same package. This wrapper
shares that Reflections index only while a single `JsonUtils.createObjectMapper`
call runs. It retains both default scanners, inherited types, application
extensions, service providers and module options. The index is discarded on
success or failure; separate threads, context class loaders and nested mapper
construction have independent scopes. There is no trained or persistent cache.

To request the upstream independent scans, return `"false"` for
`ModelDiscovery.REUSE_OPTION` (`guicedee.reuseModelDiscovery`) from the SDK's
`ModuleOptions`. Other options pass to providers unchanged.

The three adapted SDK source files retain the 20.4.0 public API. When updating
the managed upstream SDK version, review their upstream changes and run the
discovery parity tests before packaging. The shade filters exclude only those
replaced upstream classes; the JPMS descriptor and service registrations remain.

The packaged-JAR checks cover named modules and application extensions. The
upstream Reflections scanner reports zero URLs in the benchmark's jlink image;
its passing business-workload checks do not prove complete SDK mixin/subtype
discovery in a linked image. Scoped reuse does not fix that separate limitation.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## ApiModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/commercetools/src/main/java/com/commercetools/api/json/ApiModule.java)

### ApiModule

```java
public ApiModule(ModuleOptions options);
```

## JsonUtils

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/commercetools/src/main/java/io/vrap/rmf/base/client/utils/json/JsonUtils.java)

### JsonUtils

```java
public static ObjectMapper createObjectMapper();
public static ObjectMapper createObjectMapper(final ModuleOptions options);
public static byte[] toJsonByteArray(final Object value) throws JacksonException;
public static String toJsonString(final Object value) throws JacksonException;
public static <T> T fromJsonString(final String content, final Class<T> clazz);
public static <T> T fromJsonString(final String jsonAsString, final TypeReference<T> typeReference);
public static <T> T fromJsonNode(final JsonNode jsonNode, final TypeReference<T> typeReference);
public static JsonNode toJsonNode(final Object value);
public static JsonNode parse(final String jsonAsString);
public static <T> T fromJsonByteArray(final byte[] content, final Class<T> clazz);
public static <T> T fromInputStream(final InputStream content, final Class<T> clazz);
public static <T> T fromInputStream(final InputStream content, final TypeReference<T> typeReference);
public static <T> T readObjectFromResource(final String resourcePath, final TypeReference<T> typeReference);
public static <T> T readObjectFromResource(final String resourcePath, final JavaType javaType);
public static <T> T readObjectFromResource(final String resourcePath, final Class<T> clazz);
public static ObjectMapper getConfiguredObjectMapper();
public static String prettyPrint(final String json);
public static <T> T executing(final SupplierThrowingIOException<T> supplier);
```

### SupplierThrowingIOException

```java
T get() throws IOException;
```

## ModelDiscovery

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/commercetools/src/main/java/io/vrap/rmf/base/client/utils/json/ModelDiscovery.java)

### ModelDiscovery

```java
public static final String REUSE_OPTION;
public static <T> T inScope(boolean reuse, Supplier<T> action);
public static Set<Class<?>> annotatedTypes(String prefix, Class<? extends Annotation> annotation);
```

## SubTypeModule

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/commercetools/src/main/java/io/vrap/rmf/base/client/utils/json/modules/SubTypeModule.java)

### SubTypeModule

```java
public SubTypeModule(final String prefix);
```
