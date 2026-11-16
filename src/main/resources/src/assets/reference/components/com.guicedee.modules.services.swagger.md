# com.guicedee.modules.services:swagger

swagger

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.modules.services.openapi`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/swagger/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.slf4j:slf4j-api`; scope `compile`.
- `io.github.classgraph:classgraph`; scope `compile`.
- `jakarta.ws.rs:jakarta.ws.rs-api`; scope `compile`.
- `tools.jackson.jakarta.rs:jackson-jakarta-rs-base`; scope `compile`.
- `tools.jackson.jakarta.rs:jackson-jakarta-rs-yaml-provider`; scope `compile`; optional.
- `io.swagger.core.v3:swagger-rest`; scope `compile`; optional.
- `org.snakeyaml:snakeyaml-engine`; scope `compile`; optional.
- `com.guicedee.modules.services:javassist`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`; optional.

## JPMS descriptor

```java
module com.guicedee.modules.services.openapi {

    requires org.slf4j;

    requires transitive tools.jackson.databind;
    requires io.github.classgraph;

    requires jakarta.ws.rs;
    requires jakarta.xml.bind;

    // Optional bean-validation integration: swagger-core's ModelResolver reflectively reads
    // jakarta.validation constraint annotations (e.g. @NotNull) off scanned resource DTOs while
    // building the OpenAPI model. The dependency is optional at compile/run time, but because
    // jakarta.validation is present in the resolved module graph of any app that uses it, this
    // static requires still yields the runtime readability edge ModelResolver needs.
    requires static jakarta.validation;

    requires org.apache.commons.lang3;

    requires static tools.jackson.jakarta.rs.json;
    //requires tools.jackson.dataformat.yaml;
    requires static tools.jackson.module.jakarta.xmlbind;

    requires tools.jackson.jakarta.rs.base;
    exports tools.jackson.jakarta.rs.yaml;
    opens tools.jackson.jakarta.rs.yaml;

    exports io.swagger.v3.rest.integration;
    exports io.swagger.v3.oas.integration;
    exports io.swagger.v3.oas.models;
    exports io.swagger.v3.rest.integration.resources;

    exports io.swagger.v3.oas.annotations;
    exports io.swagger.v3.oas.annotations.media;
    exports io.swagger.v3.oas.annotations.responses;
    exports io.swagger.v3.oas.annotations.info;
    exports io.swagger.v3.oas.annotations.tags;
    exports io.swagger.v3.oas.annotations.extensions;
    exports io.swagger.v3.oas.annotations.headers;
    exports io.swagger.v3.oas.annotations.links;
    exports io.swagger.v3.oas.annotations.servers;
    exports io.swagger.v3.oas.annotations.security;
    exports io.swagger.v3.oas.models.tags;

    exports io.swagger.v3.rest.ext;
    exports io.swagger.v3.core.converter;
    exports io.swagger.v3.oas.integration.api;

    opens io.swagger.v3.oas.models.examples to tools.jackson.databind;

    exports io.swagger.v3.oas.annotations.parameters;

    exports io.swagger.v3.oas.models.info;
    exports io.swagger.v3.oas.models.servers;
    exports io.swagger.v3.oas.annotations.enums;
    exports io.swagger.v3.oas.models.security;

    exports io.swagger.v3.oas.models.parameters;
    exports io.swagger.v3.rest;
    exports io.swagger.v3.core.util;
    exports io.swagger.v3.core.jackson;
    opens io.swagger.v3.rest.integration.resources to com.google.guice, org.apache.cxf;
    opens io.swagger.v3.rest to com.google.guice, org.apache.cxf;

    opens io.swagger.v3.oas.integration to tools.jackson.databind;
    opens io.swagger.v3.core.jackson to tools.jackson.databind;
    opens io.swagger.v3.core.jackson.mixin to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.media to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.responses to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.info to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.tags to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.extensions to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.headers to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.links to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.servers to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.security to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.parameters to tools.jackson.databind;
    opens io.swagger.v3.oas.annotations.enums to tools.jackson.databind;

    opens io.swagger.v3.oas.models to tools.jackson.databind;
    opens io.swagger.v3.oas.models.parameters to tools.jackson.databind;
    opens io.swagger.v3.oas.models.media to tools.jackson.databind;
    opens io.swagger.v3.oas.models.responses to tools.jackson.databind;
    opens io.swagger.v3.oas.models.tags to tools.jackson.databind;
    opens io.swagger.v3.oas.models.info to tools.jackson.databind;
    opens io.swagger.v3.oas.models.headers to tools.jackson.databind;
    opens io.swagger.v3.oas.models.callbacks to tools.jackson.databind;
    opens io.swagger.v3.oas.models.links to tools.jackson.databind;
    opens io.swagger.v3.oas.models.security to tools.jackson.databind;
    opens io.swagger.v3.oas.models.servers to tools.jackson.databind;

    uses io.swagger.v3.rest.ext.OpenAPIExtension;
    uses io.swagger.v3.core.converter.ModelConverter;
    uses io.swagger.v3.oas.integration.api.OpenAPIConfigBuilder;

    provides jakarta.ws.rs.ext.MessageBodyReader with
            tools.jackson.jakarta.rs.yaml.JacksonYAMLProvider;
    provides jakarta.ws.rs.ext.MessageBodyWriter with
            tools.jackson.jakarta.rs.yaml.JacksonYAMLProvider;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
