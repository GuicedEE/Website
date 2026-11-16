# com.guicedee.modules.services:azure-identity

azure-identity

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.azure.identity`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Database/msal4j/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.azure:azure-identity`; scope `compile`; optional.
- `com.microsoft.azure:msal4j`; scope `compile`; optional.
- `com.microsoft.azure:msal4j-persistence-extension`; scope `compile`; optional.
- `com.microsoft.azure:adal4j`; scope `compile`.
- `com.google.code.gson:gson`; scope `compile`.
- `com.azure:azure-security-keyvault-keys`; scope `compile`.
- `com.google.guava:guava`; scope `compile`.
- `commons-codec:commons-codec`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `jakarta.servlet:jakarta.servlet-api`; scope `compile`.
- `org.bouncycastle:bcprov-jdk18on`; scope `compile`; optional.
- `org.bouncycastle:bcpkix-jdk18on`; scope `compile`; optional.
- `org.slf4j:slf4j-api`; scope `compile`.
- `jakarta.mail:jakarta.mail-api`; scope `compile`.
- `jakarta.activation:jakarta.activation-api`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `com.guicedee.modules.services:jna-platform`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.
- `org.codehaus.woodstox:stax2-api`; scope `compile`.
- `com.fasterxml.woodstox:woodstox-core`; scope `compile`.
- `com.guicedee.modules.services:json`; scope `compile`.

## JPMS descriptor

```java
module com.azure.identity {

    exports com.azure.identity;
    exports com.microsoft.aad.adal4j;

    exports com.nimbusds.openid.connect.sdk;
    exports com.nimbusds.jwt;
    exports com.nimbusds.oauth2.sdk;
    exports com.microsoft.aad.msal4j;

    requires jakarta.mail;
    requires jakarta.servlet;
    requires jakarta.xml.bind;

    requires com.google.gson;

    requires tools.jackson.databind;
    requires tools.jackson.core;
    requires com.fasterxml.jackson.annotation;

    requires org.json;

    requires transitive java.naming;
    requires java.sql;
    requires org.slf4j;
    requires com.sun.jna;
    requires org.apache.commons.lang3;

    uses com.azure.core.http.HttpClientProvider;
    provides com.azure.core.http.HttpClientProvider with com.azure.core.http.netty.NettyAsyncHttpClientProvider;
  //  uses reactor.blockhound.integration.BlockHoundIntegration;
 //   provides reactor.blockhound.integration.BlockHoundIntegration with io.netty.util.internal.Hidden.NettyBlockHoundIntegration;

    opens com.azure.identity.implementation to tools.jackson.databind;

    opens com.microsoft.aad.msal4j to  tools.jackson.databind;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
