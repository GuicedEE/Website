# com.guicedee.modules.representations:json-representation

JSON Representation

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.jsonrepresentation`.
- [Source POM](https://github.com/GuicedEE/Representations/blob/master/json/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `jakarta.inject:jakarta.inject-api`; scope `compile`; optional.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `org.projectlombok:lombok`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `junit:junit`; scope `test`.
- `org.junit.jupiter:junit-jupiter`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.client.services.lifecycle.IGuiceModule;

/**
 * Provides JSON representation utilities, Jackson configuration, and Guice bindings
 * for consistent serialization and deserialization across the application.
 */
module com.guicedee.jsonrepresentation {
    exports com.guicedee.modules.services.jsonrepresentation;
    exports com.guicedee.modules.services.jsonrepresentation.json;
    exports com.guicedee.vertx.spi.json;

    requires transitive tools.jackson.databind;
    requires transitive tools.jackson.core;

    requires com.guicedee.client;
    requires org.apache.commons.lang3;

    requires static lombok;
    requires java.logging;
    requires com.fasterxml.jackson.annotation;

    requires static jakarta.inject;

    provides IGuiceModule with com.guicedee.modules.services.jsonrepresentation.implementations.ObjectMapperBinder;
    provides com.guicedee.client.services.IJsonCodec with com.guicedee.modules.services.jsonrepresentation.implementations.JacksonJsonCodec;
    provides io.vertx.core.spi.JsonFactory with com.guicedee.vertx.spi.json.GuicedVertxJsonFactory;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Representations/blob/master/json/README.md). Historical version examples retain their original context.

# GuicedEE JSON Representation

[![Maven Central](https://img.shields.io/maven-central/v/com.guicedee.modules.representations/json-representation)](https://central.sonatype.com/artifact/com.guicedee.modules.representations/json-representation)
[![Maven Snapshot](https://img.shields.io/nexus/s/com.guicedee.modules.representations/json-representation?server=https%3A%2F%2Foss.sonatype.org&label=Maven%20Snapshot)](https://oss.sonatype.org/content/repositories/snapshots/com/guicedee/modules/representations/json-representation/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Guice 7](https://img.shields.io/badge/Guice-7%2B-green)
![Jackson](https://img.shields.io/badge/Jackson-3.x-green)

Pre-configured [Jackson](https://github.com/FasterXML/jackson) ObjectMapper setup, Guice bindings, and a mixin interface for painless JSON serialization/deserialization across the [GuicedEE](https://github.com/GuicedEE) ecosystem.

Provides two ready-to-use `ObjectMapper` instances (standard JSON and JavaScript-friendly), a `LaxJsonModule` with lenient type handling for dates, booleans, and integers, and the `IJsonRepresentation<J>` interface that gives any class one-liner `toJson()` / `fromJson()` methods.

Built on [Jackson Databind](https://github.com/FasterXML/jackson-databind) · JPMS module `com.guicedee.jsonrepresentation` · Java 25+

## 📦 Installation

This library is opt-in. GuicedEE core does not depend on Jackson. Adding the library activates its client `IJsonCodec`, Guice mapper bindings and Vert.x `JsonFactory` providers through SPI on both the module path and classpath. No scanning or enable flag is needed. See [activation, alternative providers and migration](https://github.com/GuicedEE/Representations/blob/client/JSON_OPT_IN.md).

```xml
<dependency>
  <groupId>com.guicedee.modules.representations</groupId>
  <artifactId>json-representation</artifactId>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.guicedee.modules.representations:json-representation:2.5.1")
```
</details>

## 🚀 Quick Start

### Mixin interface — zero boilerplate

Implement `IJsonRepresentation` on any class and you get `toJson()` and `fromJson()` for free:

```java
public class Order implements IJsonRepresentation<Order> {
    private String id;
    private LocalDate placed;
    private boolean paid;
}

// Serialize
Order order = new Order();
String json = order.toJson();       // pretty-printed
String tiny = order.toJson(true);   // compact

// Deserialize — updates in place
order.fromJson(json);

// Deserialize a list
List<Order> orders = order.fromJsonArray(jsonArray);
```

### Static helpers — read from any source

```java
// From a String
Order o = IJsonRepresentation.From(jsonString, Order.class);

// From a File, InputStream, Reader, or URL
Order o = IJsonRepresentation.From(file, Order.class);
Order o = IJsonRepresentation.From(inputStream, Order.class);
Order o = IJsonRepresentation.From(url, Order.class);

// Arrays → List
List<Order> list = IJsonRepresentation.fromToList(inputStream, Order.class);
List<Order> list = IJsonRepresentation.fromToList(jsonString, Order.class);
```

### Direct ObjectMapper access

```java
ObjectMapper mapper = IJsonRepresentation.getObjectMapper();
ObjectReader reader = IJsonRepresentation.getJsonObjectReader();
```

## ⚙️ ObjectMapper Configuration

`IJsonRepresentation.configureObjectMapper(mapper)` applies the module's standard settings to **any** ObjectMapper — useful for configuring Vert.x `DatabindCodec` or third-party mappers:

| Setting | Value |
|---|---|
| `WRITE_DATES_AS_TIMESTAMPS` | `false` — ISO-8601 strings |
| `FAIL_ON_UNKNOWN_PROPERTIES` | `false` — lenient deserialization |
| `FAIL_ON_MISSING_CREATOR_PROPERTIES` | `false` |
| `READ_UNKNOWN_ENUM_VALUES_AS_NULL` | `true` |
| `ESCAPE_NON_ASCII` | `true` |
| `ALLOW_UNQUOTED_FIELD_NAMES` | `true` (reading) |
| `ALLOW_SINGLE_QUOTES` | `true` (reading) |
| `ALLOW_UNQUOTED_CONTROL_CHARS` | `true` |
| Field visibility | `ANY` — fields serialized directly |
| Getter/setter visibility | `NONE` — no getter/setter interference |

### Max string length

Large payloads are supported out of the box. The maximum JSON string length defaults to **250 MB** and can be tuned via:

- System property: `-DJSON_MAX_STRING_LENGTH=52428800`
- Environment variable: `JSON_MAX_STRING_LENGTH=52428800`

## 🔗 Guice Bindings — `ObjectMapperBinder`

`ObjectMapperBinder` is an `IGuiceModule` discovered automatically via ServiceLoader / JPMS.
It binds the following keys into the Guice injector (all from `ObjectBinderKeys`):

Import `com.guicedee.modules.services.jsonrepresentation.ObjectBinderKeys`; the keys no longer live in client because their API contains Jackson types.

| Guice Key | Type | Description |
|---|---|---|
| `DefaultObjectMapper` | `ObjectMapper` | Standard mapper (singleton by default) |
| `JSONObjectWriter` | `ObjectWriter` | Pretty-printed, quoted field names |
| `JSONObjectWriterTiny` | `ObjectWriter` | Compact output, quoted field names |
| `JSONObjectReader` | `ObjectReader` | Lenient reader (accepts arrays, ignores unknowns) |
| `JavascriptObjectMapper` | `ObjectMapper` | JavaScript-friendly (unquoted keys, non-null) |
| `JavaScriptObjectWriter` | `ObjectWriter` | Pretty-printed, unquoted field names |
| `JavaScriptObjectWriterTiny` | `ObjectWriter` | Compact, unquoted field names |
| `JavaScriptObjectReader` | `ObjectReader` | Lenient reader for JS-style JSON |

Inject them anywhere:

```java
@Inject @Named("JSON")
private ObjectWriter jsonWriter;

@Inject @Named("Default")
private ObjectMapper mapper;  // DefaultObjectMapper
```

Toggle singleton mode if needed:

```java
ObjectMapperBinder.singleton = false; // default is true
```

## 🕐 LaxJsonModule — Lenient Type Handling

The `LaxJsonModule` is a Jackson `SimpleModule` registered on both mappers. It provides relaxed serializers and deserializers for common Java types that often arrive in inconsistent formats from external APIs.

### Date/Time types

| Java Type | Serializer | Deserializer | Key Deserializer |
|---|---|---|---|
| `LocalDate` | `LocalDateSerializer` | `LocalDateDeserializer` | `LocalDateDeserializerKey` |
| `LocalTime` | `LocalTimeSerializer` | `LocalTimeDeserializer` | — |
| `LocalDateTime` | `LocalDateTimeSerializer` | `LocalDateTimeDeserializer` | `LocalDateTimeDeserializerKey` |
| `Instant` | `InstantSerializer` | `InstantDeserializer` | — |
| `OffsetDateTime` | `OffsetDateTimeSerializer` | `OffsetDateTimeDeserializer` | `OffsetDateTimeDeserializerKey` |
| `OffsetTime` | `OffsetTimeSerializer` | `OffsetTimeDeserializer` | — |
| `ZonedDateTime` | — | `ZonedDateTimeDeserializer` | — |
| `Duration` | `DurationToString` | `StringToDurationTimeSeconds` | — |

All deserializers accept **multiple formats** — ISO-8601, epoch millis, numeric strings, and common variants — so you never have to worry about date format mismatches from upstream systems.

### Scalar types

| Java Type | Deserializer | Behaviour |
|---|---|---|
| `Boolean` / `boolean` | `StringToBoolean` / `StringToBool` | Accepts `"1"`, `"yes"`, `"on"`, `"true"`, `"y"` → `true`; `"0"`, `"no"`, `"off"`, `"false"`, `"n"` → `false` |
| `Integer` / `int` | `StringToIntegerRelaxed` / `StringToIntRelaxed` | Parses numeric strings leniently, handles blank/null gracefully |
| `Charset` | `StringToCharacterSet` | Parses charset names from strings |

## 📋 Static Constants — `StaticStrings`

`StaticStrings` centralises commonly used string and character constants (content-type headers, path separators, quote characters, etc.) to avoid magic literals scattered across the codebase.

```java
StaticStrings.HTML_HEADER_JSON           // "application/json"
StaticStrings.HTML_HEADER_JAVASCRIPT     // "application/javascript"
StaticStrings.STRING_EMPTY               // ""
StaticStrings.STRING_FORWARD_SLASH       // "/"
```

## ⚠️ Exception Handling

`JsonRenderException` is an unchecked `RuntimeException` thrown by all `IJsonRepresentation` default methods when serialization or deserialization fails. It wraps the underlying Jackson exception so callers get a clean stack trace without checked-exception noise.

## 🗺️ Module Graph

```
com.guicedee.jsonrepresentation
 ├── tools.jackson.databind               (ObjectMapper, serializers, Java Time)
 ├── tools.jackson.core                   (streaming API)
 ├── com.fasterxml.jackson.annotation     (annotation API)
 ├── com.guicedee.client                  (SPI contracts)
 └── org.apache.commons.lang3
```

## 🧩 JPMS

Module name: **`com.guicedee.jsonrepresentation`**

```java
module com.guicedee.jsonrepresentation {
    exports com.guicedee.modules.services.jsonrepresentation;
    exports com.guicedee.modules.services.jsonrepresentation.json;

    requires transitive tools.jackson.databind;
    requires transitive tools.jackson.core;
    requires com.guicedee.client;

    provides IGuiceModule
        with ObjectMapperBinder;
    provides IJsonCodec
        with JacksonJsonCodec;
    provides io.vertx.core.spi.JsonFactory
        with GuicedVertxJsonFactory;
}
```

## 🤝 Contributing

Issues and pull requests are welcome.

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## IJsonRepresentation

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/IJsonRepresentation.java)

### IJsonRepresentation

```java
static ObjectMapper configureObjectMapper(ObjectMapper mapper);
static void registerModule(tools.jackson.databind.JacksonModule module);
default String toJson();
default String toJson(boolean tiny);
default J fromJson(String json);
@SuppressWarnings({"UnusedReturnValue"}) default List<J> fromJsonArray(String json);
@SuppressWarnings({"UnusedReturnValue"}) default Set<J> fromJsonArrayUnique(String json, @SuppressWarnings("unused")
Class<J> type);
static <T> T From(InputStream file, Class<T> clazz) throws IOException;
static <T> T From(File file, Class<T> clazz) throws IOException;
static <T> T From(Reader file, Class<T> clazz) throws IOException;
static ObjectMapper getObjectMapper();
static ObjectReader getJsonObjectReader();
static <T> T From(String content, Class<T> clazz) throws IOException;
static <T> T From(URL content, Class<T> clazz) throws IOException;
static <T> List<T> fromToList(InputStream file, Class<T> clazz);
static <T> List<T> fromToList(URL content, Class<T> clazz) throws IOException;
static <T> List<T> fromToList(File file, Class<T> clazz) throws IOException;
static <T> List<T> fromToList(Reader file, Class<T> clazz) throws IOException;
static <T> List<T> fromToList(String content, Class<T> clazz) throws IOException;
```

## JacksonJsonCodec

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/implementations/JacksonJsonCodec.java)

### JacksonJsonCodec

```java
public String write(Object value);
public <T> T read(String json, Type type);
public <T> T convert(Object value, Type type);
public void update(Object target, Object source);
public <T> T copy(T value, Type type);
```

## ObjectMapperBinder

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/implementations/ObjectMapperBinder.java)

### ObjectMapperBinder

```java
public static boolean singleton;
public static synchronized void registerModule(tools.jackson.databind.JacksonModule module);
@Override public void configure();
```

## CoreJsonMixins

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/CoreJsonMixins.java)

### CoreJsonMixins

```java
public static void configure(MapperBuilder<?, ?> builder);
```

## DurationToInteger

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/DurationToInteger.java)

### DurationToInteger

```java
@Override public void serialize(Duration value, JsonGenerator gen, SerializationContext serializers);
public Integer convert(Duration value);
```

## DurationToString

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/DurationToString.java)

### DurationToString

```java
@Override public void serialize(Duration value, JsonGenerator gen, SerializationContext serializers);
public String convert(Duration value);
```

## InstantDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/InstantDeserializer.java)

### InstantDeserializer

```java
@Override public Instant deserialize(JsonParser p, DeserializationContext ctxt);
public Instant convert(String value);
```

## InstantSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/InstantSerializer.java)

### InstantSerializer

```java
public InstantSerializer();
@Override public void serialize(Instant value, JsonGenerator generator, SerializationContext provider);
```

## LaxJsonModule

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LaxJsonModule.java)

### LaxJsonModule

```java
public LaxJsonModule();
```

## LocalDateDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LocalDateDeserializer.java)

### LocalDateDeserializer

```java
@Override public LocalDate deserialize(JsonParser p, DeserializationContext ctxt);
public LocalDate convert(String value);
```

## LocalDateSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LocalDateSerializer.java)

### LocalDateSerializer

```java
public static String LocalDateFormat;
public LocalDateSerializer();
@Override public void serialize(LocalDate value, JsonGenerator generator, SerializationContext provider);
public String convert(LocalDate value);
```

## LocalDateTimeDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LocalDateTimeDeserializer.java)

### LocalDateTimeDeserializer

```java
public static final DateTimeFormatter[] formats;
@Override public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt);
public LocalDateTime convert(String value);
```

## LocalDateTimeSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LocalDateTimeSerializer.java)

### LocalDateTimeSerializer

```java
public static String LocalDateTimeFormat;
public LocalDateTimeSerializer();
@Override public void serialize(LocalDateTime value, JsonGenerator generator, SerializationContext provider);
public String convert(LocalDateTime value);
```

## LocalTimeDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LocalTimeDeserializer.java)

### LocalTimeDeserializer

```java
@Override public LocalTime deserialize(JsonParser p, DeserializationContext ctxt);
public LocalTime convert(String value);
```

## LocalTimeSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/LocalTimeSerializer.java)

### LocalTimeSerializer

```java
public static final DateTimeFormatter[] formats;
public LocalTimeSerializer();
@Override public void serialize(LocalTime value, JsonGenerator generator, SerializationContext provider);
public String convert(LocalTime value);
```

## LocalDateDeserializerKey

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/mapkeys/LocalDateDeserializerKey.java)

### LocalDateDeserializerKey

```java
@Override public Object deserializeKey(String key, DeserializationContext ctxt);
```

## LocalDateTimeDeserializerKey

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/mapkeys/LocalDateTimeDeserializerKey.java)

### LocalDateTimeDeserializerKey

```java
@Override public Object deserializeKey(String key, DeserializationContext ctxt);
```

## OffsetDateTimeDeserializerKey

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/mapkeys/OffsetDateTimeDeserializerKey.java)

### OffsetDateTimeDeserializerKey

```java
@Override public Object deserializeKey(String key, DeserializationContext ctxt);
```

## OffsetDateTimeDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/OffsetDateTimeDeserializer.java)

### OffsetDateTimeDeserializer

```java
@Override public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt);
public OffsetDateTime convert(String value);
```

## OffsetDateTimeSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/OffsetDateTimeSerializer.java)

### OffsetDateTimeSerializer

```java
public OffsetDateTimeSerializer();
@Override public void serialize(OffsetDateTime value, JsonGenerator generator, SerializationContext provider);
public String convert(OffsetDateTime value);
```

## OffsetTimeDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/OffsetTimeDeserializer.java)

### OffsetTimeDeserializer

```java
@Override public OffsetTime deserialize(JsonParser p, DeserializationContext ctxt);
public OffsetTime convert(String value);
```

## OffsetTimeSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/OffsetTimeSerializer.java)

### OffsetTimeSerializer

```java
public OffsetTimeSerializer();
@Override public void serialize(OffsetTime value, JsonGenerator generator, SerializationContext provider);
```

## StaticStrings

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StaticStrings.java)

### StaticStrings

```java
public static final String HTML_HEADER_JSON;
public static final String HTML_HEADER_JAVASCRIPT;
public static final String HTML_HEADER_CSS;
public static final String HTML_HEADER_ZIP;
public static final String HTML_HEADER_PDF;
public static final String HTML_HEADER_JPG;
public static final String HTML_HEADER_MOV;
public static final String HTML_HEADER_DEFAULT_CONTENT_TYPE;
public static final String UTF8;
public static final String STRING_EMPTY;
public static final String STRING_SPACE;
public static final String STRING_FORWARD_SLASH;
public static final String STRING_BACK_SLASH;
public static final String STRING_TAB;
public static final String STRING_DOLLAR;
public static final String STRING_DOLLAR_ESCAPED;
public static final String STRING_EQUALS;
public static final String STRING_NEWLINE_TEXT;
public static final String STRING_DOUBLE_COLON;
public static final String STRING_ASTERISK;
public static final String STRING_QUESTIONMARK;
public static final String STRING_SPACE_DOUBLE_COLON_SPACE;
public static final String STRING_SINGLE_QUOTES;
public static final String STRING_DOUBLE_QUOTES;
public static final String STRING_SINGLE_QUOTES_SPACE;
public static final String STRING_DOUBLE_QUOTES_SPACE;
public static final String STRING_EQUALS_SINGLE_QUOTES;
public static final String STRING_EQUALS_DOUBLE_QUOTES;
public static final String STRING_SHARP_BRACE_OPEN;
public static final String STRING_SHARP_BRACE_SLASH_OPEN;
public static final String STRING_SQUARE_BRACE_OPEN;
public static final String STRING_SQUARE_BRACE_CLOSED;
public static final String STRING_SHARP_BRACE_CLOSED;
public static final String STRING_HASH;
public static final String STRING_DASH;
public static final String STRING_SPACE_DASH_SPACE;
public static final String STRING_COMMNA;
public static final String STRING_COMMNA_SPACE;
public static final String STRING_COMMNA_SEMICOLON;
public static final String STRING_DOT;
public static final String STRING_EQUALS_SPACE_EQUALS;
public static final String STRING_SEMICOLON;
public static final String STRING_BRACES_OPEN;
public static final String STRING_BRACES_CLOSE;
public static final String STRING_CLOSING_BRACKET_SEMICOLON;
public static final String STRING_SPACE_OPEN_BRACKET;
public static final String STRING_AMPERSAND;
public static final String FAKE_KEY;
public static final String STRING_INSERT_INTO_SQL;
public static final String STRING_DELETE_FROM_SQL;
public static final String STRING_UPDATE_SQL;
public static final String STRING_UPDATE_SET_SQL;
public static final String STRING_WHERE_SQL;
public static final String STRING_VALUES_SQL_INSERT;
public static final String STRING_HEX_SQL_START;
public static final String STRING_NULL;
public static final String STRING_DOT_ESCAPED;
public static final String STRING_0;
public static final String STRING_1;
public static final String STRING_AUTHORIZATION;
public static final String STRING_AUTHORIZATION_HEADER;
public static final String STRING_AUTHORIZATION_BASIC;
public static final String STRING_AUTHORIZATION_BASIC_SPACE;
public static final String STRING_AUTHORIZATION_BEARER;
public static final String STRING_AUTHORIZATION_BEARER_SPACE;
public static final String STRING_DURATION_TIME;
public static final String STRING_HMAC_256;
public static final String STRING_HMAC_512;
public static final String QUERY_PARAMETERS_REGEX;
public static final String NOT_WEB_SOCKETS;
public static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER_NAME;
public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER_NAME;
public static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER_NAME;
public static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER_NAME;
public static final String DEFAULT_DATE_TIME_PATTERN;
public static final String DEFAULT_XML_DATE_TIME_PATTERN;
public static final String STRING_SELECTED;
public static final String HTML_TIMES;
public static final String HTML_TAB;
public static final String HTML_AMPERSAND;
public static final String ShortLatin;
public static final String MediumLatin;
public static final char CHAR_SPACE;
public static final char CHAR_DOT;
public static final char CHAR_EQUALS;
public static final char CHAR_COMMA;
public static final char CHAR_SEMI_COLON;
public static final char CHAR_DOUBLE_COLON;
public static final char CHAR_UNDERSCORE;
public static final char CHAR_PERCENT;
public static final char CHAR_DOLLAR;
public static final char CHAR_DASH;
public static final char CHAR_QUESTIONMARK;
public static final char CHAR_SLASH;
public static final char CHAR_BACKSLASH;
public static final char CHAR_HASH;
public static final char CHAR_PLUS;
public static final char CHAR_BRACES_OPEN;
public static final char CHAR_BRACES_CLOSE;
public static final char CHAR_AMPERSAND;
public static final String P;
public static final String H;
public static final String M;
public static final String S;
public static final String E;
public static Charset UTF_CHARSET;
public static void setDefaultChartset(Charset charset);
```

## StringToBool

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToBool.java)

### StringToBool

```java
@Override public Object deserialize(JsonParser p, DeserializationContext ctxt);
public boolean convert(String value);
```

## StringToBoolean

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToBoolean.java)

### StringToBoolean

```java
public static boolean nullable;
@Override public Boolean deserialize(JsonParser p, DeserializationContext ctxt);
public Boolean convert(String value);
```

## StringToCharacterSet

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToCharacterSet.java)

### StringToCharacterSet

```java
@Override public Set<Character> deserialize(JsonParser p, DeserializationContext ctxt);
```

## StringToDurationTime

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToDurationTime.java)

### StringToDurationTime

```java
@Override public Duration deserialize(JsonParser p, DeserializationContext ctxt);
public Duration convert(String value);
```

## StringToDurationTimeSeconds

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToDurationTimeSeconds.java)

### StringToDurationTimeSeconds

```java
@Override public Duration deserialize(JsonParser p, DeserializationContext ctxt);
public Duration convert(String value);
```

## StringToIntegerRelaxed

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToIntegerRelaxed.java)

### StringToIntegerRelaxed

```java
@Override public Integer deserialize(JsonParser p, DeserializationContext ctxt);
public Integer convert(String value);
```

## StringToIntRelaxed

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/StringToIntRelaxed.java)

### StringToIntRelaxed

```java
@Override public Object deserialize(JsonParser p, DeserializationContext ctxt);
public int convert(String value);
```

## ZonedDateTimeDeserializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/ZonedDateTimeDeserializer.java)

### ZonedDateTimeDeserializer

```java
@Override public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt);
public ZonedDateTime convert(String value);
```

## ZonedDateTimeSerializer

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/json/ZonedDateTimeSerializer.java)

### ZonedDateTimeSerializer

```java
public ZonedDateTimeSerializer();
@Override public void serialize(LocalDateTime value, JsonGenerator generator, SerializationContext provider);
```

## JsonRenderException

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/JsonRenderException.java)

### JsonRenderException

```java
public JsonRenderException();
public JsonRenderException(String message);
public JsonRenderException(String message, Throwable cause);
public JsonRenderException(Throwable cause);
public JsonRenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## ObjectBinderKeys

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/modules/services/jsonrepresentation/ObjectBinderKeys.java)

### ObjectBinderKeys

```java
public static final Key<ObjectMapper> DefaultObjectMapper;
public static final Key<ObjectWriter> JSONObjectWriter;
public static final Key<ObjectWriter> JSONObjectWriterTiny;
public static final Key<ObjectReader> JSONObjectReader;
public static final Key<ObjectMapper> JavascriptObjectMapper;
public static final Key<ObjectWriter> JavaScriptObjectWriter;
public static final Key<ObjectWriter> JavaScriptObjectWriterTiny;
public static final Key<ObjectReader> JavaScriptObjectReader;
```

## GuicedVertxJsonCodec

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/vertx/spi/json/GuicedVertxJsonCodec.java)

### GuicedVertxJsonCodec

```java
@Override @SuppressWarnings("unchecked") public <T> T fromString(String json, Class<T> clazz) throws DecodeException;
@Override @SuppressWarnings("unchecked") public <T> T fromBuffer(Buffer buf, Class<T> clazz) throws DecodeException;
@Override @SuppressWarnings("unchecked") public <T> T fromValue(Object json, Class<T> clazz);
@Override public String toString(Object object, boolean pretty) throws EncodeException;
@Override public Buffer toBuffer(Object object, boolean pretty) throws EncodeException;
```

## GuicedVertxJsonFactory

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/vertx/spi/json/GuicedVertxJsonFactory.java)

### GuicedVertxJsonFactory

```java
public GuicedVertxJsonFactory();
@Override public int order();
@Override public JsonCodec codec();
```

## GuicedVertxJsonModule

[Java source](https://github.com/GuicedEE/Representations/blob/master/json/src/main/java/com/guicedee/vertx/spi/json/GuicedVertxJsonModule.java)

### GuicedVertxJsonModule

```java
@SuppressWarnings("unchecked") public GuicedVertxJsonModule();
```
