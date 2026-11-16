# com.guicedee.modules.services:keycloak-api

Keycloak compile-only JPMS API

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.keycloak.api`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.keycloak:keycloak-services`; scope `compile`; optional.
- `org.keycloak:keycloak-core`; scope `compile`; optional.
- `org.keycloak:keycloak-common`; scope `compile`; optional.
- `org.keycloak:keycloak-server-spi`; scope `compile`; optional.
- `org.keycloak:keycloak-server-spi-private`; scope `compile`; optional.
- `jakarta.ws.rs:jakarta.ws.rs-api`; scope `provided`.
- `tools.jackson.core:jackson-core`; scope `provided`.
- `tools.jackson.core:jackson-databind`; scope `provided`.

## JPMS descriptor

```java
/* Compile-only boundary. Never deploy this API bundle into Keycloak. */
module org.keycloak.api {
    requires static transitive jakarta.ws.rs;
    /* Jackson 3 - the Jackson 2 references in the upstream Keycloak binaries are relocated
       onto tools.jackson.* by the shade step. Annotations stay on Jackson 2.x per JSTEP-1. */
    requires static transitive com.fasterxml.jackson.annotation;
    requires static transitive tools.jackson.core;
    requires static transitive tools.jackson.databind;

    exports org.keycloak;
    exports org.keycloak.common;
    exports org.keycloak.models;
    exports org.keycloak.protocol.oidc;
    exports org.keycloak.protocol.oidc.grants;
    exports org.keycloak.provider;
    exports org.keycloak.representations;
    exports org.keycloak.representations.dpop;
    exports org.keycloak.sessions;
    exports org.keycloak.services.util;
    exports org.keycloak.services;
    exports org.keycloak.jose.jws;
    exports org.keycloak.events;
    exports org.keycloak.http;
    exports org.keycloak.services.cors;
    exports org.keycloak.util;

    /*
       Qualified opens for every package that carries Jackson annotations or a
       ValueSerializer/ValueDeserializer implementation, so databind can introspect the
       representations and instantiate the @JsonSerialize/@JsonDeserialize handlers without
       this bundle having to export them to the world.

       To regenerate: scan the shaded jar for classes referencing
       com/fasterxml/jackson/annotation/, tools/jackson/databind/annotation/ or
       tools/jackson/databind/Value(Ser|Deser)ializer, and emit the distinct packages.
    */
    opens org.keycloak to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.execactions to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.idpverifyemail to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.inviteorg to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.updateemail to tools.jackson.databind;
    opens org.keycloak.authentication.actiontoken.verifyemail to tools.jackson.databind;
    opens org.keycloak.authentication.authenticators.broker.util to tools.jackson.databind;
    opens org.keycloak.authentication.forms to tools.jackson.databind;
    opens org.keycloak.authorization.config to tools.jackson.databind;
    opens org.keycloak.authorization.protection.introspect to tools.jackson.databind;
    opens org.keycloak.authorization.protection.resource to tools.jackson.databind;
    opens org.keycloak.broker.oidc to tools.jackson.databind;
    opens org.keycloak.broker.spiffe to tools.jackson.databind;
    opens org.keycloak.credential to tools.jackson.databind;
    opens org.keycloak.forms.login.freemarker to tools.jackson.databind;
    opens org.keycloak.jose.jwe to tools.jackson.databind;
    opens org.keycloak.jose.jwk to tools.jackson.databind;
    opens org.keycloak.jose.jws to tools.jackson.databind;
    opens org.keycloak.json to tools.jackson.databind;
    opens org.keycloak.models to tools.jackson.databind;
    opens org.keycloak.models.credential.dto to tools.jackson.databind;
    opens org.keycloak.models.light to tools.jackson.databind;
    opens org.keycloak.partialimport to tools.jackson.databind;
    opens org.keycloak.protocol to tools.jackson.databind;
    opens org.keycloak.protocol.oauth2.cimd.clientpolicy.condition to tools.jackson.databind;
    opens org.keycloak.protocol.oauth2.cimd.clientpolicy.executor to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.issuance.credentialoffer to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.issuance.requiredactions to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.model to tools.jackson.databind;
    opens org.keycloak.protocol.oid4vc.model.vcdm to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.encode to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.grants.ciba.channel to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.grants.ciba.clientpolicy.executor to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.grants.ciba.endpoints to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.par to tools.jackson.databind;
    opens org.keycloak.protocol.oidc.representations to tools.jackson.databind;
    opens org.keycloak.representations to tools.jackson.databind;
    opens org.keycloak.representations.account to tools.jackson.databind;
    opens org.keycloak.representations.adapters.action to tools.jackson.databind;
    opens org.keycloak.representations.adapters.config to tools.jackson.databind;
    opens org.keycloak.representations.docker to tools.jackson.databind;
    opens org.keycloak.representations.dpop to tools.jackson.databind;
    opens org.keycloak.representations.idm to tools.jackson.databind;
    opens org.keycloak.representations.idm.authorization to tools.jackson.databind;
    opens org.keycloak.representations.oidc to tools.jackson.databind;
    opens org.keycloak.representations.provider to tools.jackson.databind;
    opens org.keycloak.representations.userprofile.config to tools.jackson.databind;
    opens org.keycloak.representations.workflows to tools.jackson.databind;
    opens org.keycloak.sdjwt.consumer to tools.jackson.databind;
    opens org.keycloak.services.clientpolicy.condition to tools.jackson.databind;
    opens org.keycloak.services.clientpolicy.executor to tools.jackson.databind;
    opens org.keycloak.services.clientregistration to tools.jackson.databind;
    opens org.keycloak.services.managers to tools.jackson.databind;
    opens org.keycloak.services.resources.account to tools.jackson.databind;
    opens org.keycloak.services.resources.admin to tools.jackson.databind;
    opens org.keycloak.services.util to tools.jackson.databind;
    opens org.keycloak.util to tools.jackson.databind;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## StringListMapDeserializer

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/json/StringListMapDeserializer.java)

### StringListMapDeserializer

```java
@Override public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext);
```

## StringOrArrayDeserializer

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/json/StringOrArrayDeserializer.java)

### StringOrArrayDeserializer

```java
@Override public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext);
```

## StringOrArraySerializer

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/json/StringOrArraySerializer.java)

### StringOrArraySerializer

```java
@Override public void serialize(Object o, JsonGenerator jsonGenerator, SerializationContext serializationContext);
```

## CredentialOfferGrantsDeserializer

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/protocol/oid4vc/model/CredentialOfferGrantsDeserializer.java)

### CredentialOfferGrantsDeserializer

```java
@Override public Map<String, CredentialOfferGrant> deserialize(JsonParser p, DeserializationContext ctx);
```

## MultivaluedHashMapValueDeserializer

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/representations/workflows/MultivaluedHashMapValueDeserializer.java)

### MultivaluedHashMapValueDeserializer

```java
@Override public Object deserialize(JsonParser p, DeserializationContext ctxt);
```

## MultivaluedHashMapValueSerializer

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/representations/workflows/MultivaluedHashMapValueSerializer.java)

### MultivaluedHashMapValueSerializer

```java
@Override public void serialize(MultivaluedHashMap<String, String> map, JsonGenerator gen, SerializationContext ctxt);
@Override public boolean isEmpty(SerializationContext ctxt, MultivaluedHashMap<String, String> value);
```

## ObjectMapperResolver

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/services/util/ObjectMapperResolver.java)

### ObjectMapperResolver

```java
public ObjectMapperResolver();
public static ObjectMapper createStreamSerializer();
@Override public ObjectMapper getContext(Class<?> type);
```

## JsonSerialization

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/keycloak/src/main/java/org/keycloak/util/JsonSerialization.java)

### JsonSerialization

```java
public static final ObjectMapper mapper;
public static final ObjectMapper prettyMapper;
public static ObjectMapper createObjectMapperWithDefaults();
public static ObjectMapper createPrettyObjectMapperWithDefaults();
public static String valueAsString(Object obj);
public static String valueAsPrettyString(Object obj);
public static <T> T valueFromString(String string, Class<T> type);
public static void writeValueToStream(OutputStream os, Object obj) throws IOException;
public static void writeValuePrettyToStream(OutputStream os, Object obj) throws IOException;
public static String writeValueAsPrettyString(Object obj) throws IOException;
public static String writeValueAsString(Object obj) throws IOException;
public static byte[] writeValueAsBytes(Object obj) throws IOException;
public static JsonNode writeValueAsNode(Object obj);
public static <T> T readValue(byte[] bytes, Class<T> type) throws IOException;
public static <T> T readValue(String bytes, Class<T> type) throws IOException;
public static <T> T readValue(InputStream bytes, Class<T> type) throws IOException;
public static <T> T readValue(byte[] bytes, TypeReference<T> type) throws IOException;
public static <T> T readValue(String string, TypeReference<T> type) throws IOException;
public static <T> T readValue(InputStream bytes, TypeReference<T> type) throws IOException;
public static ObjectNode createObjectNode(Object pojo) throws IOException;
public static ObjectNode createObjectNode();
```
