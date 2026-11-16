# com.guicedee.modules.services:cloudevents

A modularized test containers implementation

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `io.cloudevents`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/cloudevents/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `io.cloudevents:cloudevents-core`; scope `compile`; optional.
- `io.cloudevents:cloudevents-api`; scope `compile`; optional.
- `io.cloudevents:cloudevents-json-jackson`; scope `compile`; optional.

## JPMS descriptor

```java
module io.cloudevents {
    exports io.cloudevents;
    //exports io.cloudevents.core;
    exports io.cloudevents.core.builder;
    exports io.cloudevents.core.data;
    //exports io.cloudevents.core.extensions;
    exports io.cloudevents.core.extensions.impl;
    exports io.cloudevents.core.format;
    exports io.cloudevents.core.impl;
    exports io.cloudevents.core.message;
    exports io.cloudevents.core.message.impl;
    //exports io.cloudevents.core.provider;
    exports io.cloudevents.core.v1;
//    exports io.cloudevents.core.v03;
    exports io.cloudevents.core.validator;
    exports io.cloudevents.lang;
    exports io.cloudevents.rw;
    exports io.cloudevents.types;

    uses io.cloudevents.core.format.EventFormat;
    provides io.cloudevents.core.format.EventFormat with io.cloudevents.jackson.JsonFormat;

    opens io.cloudevents to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.jackson to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.builder to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.data to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.extensions to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.extensions.impl to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.format to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.impl to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.message to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.message.impl to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.provider to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.v1 to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.v03 to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.core.validator to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.lang to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.rw to tools.jackson.databind, tools.jackson.core,com.google.guice;
    opens io.cloudevents.types to tools.jackson.databind, tools.jackson.core,com.google.guice;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
