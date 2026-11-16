# com.guicedee.modules.services:oauth2-oidc-sdk

A JPMS modularized shade of oauth2-oidc-sdk (com.nimbusds:oauth2-oidc-sdk) exposing the oauth2.oidc.sdk module for GuicedEE and jlink. Includes content-type, lang-tag, JSON Smart and Accessors Smart; reuses the native Nimbus JOSE/JWT and ASM modules.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `oauth2.oidc.sdk`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/oauth2-oidc-sdk/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.nimbusds:oauth2-oidc-sdk`; scope `compile`; optional.
- `com.nimbusds:nimbus-jose-jwt`; scope `compile`.
- `org.ow2.asm:asm`; scope `compile`.

## JPMS descriptor

```java
/** Nimbus OAuth2/OIDC and its non-modular runtime dependencies. */
module oauth2.oidc.sdk {
    requires transitive com.nimbusds.jose.jwt;
    requires transitive org.objectweb.asm;
    requires transitive java.xml;

    exports com.nimbusds.common.contenttype;
    exports com.nimbusds.langtag;
    exports com.nimbusds.oauth2.sdk;
    exports com.nimbusds.oauth2.sdk.as;
    exports com.nimbusds.oauth2.sdk.assertions;
    exports com.nimbusds.oauth2.sdk.assertions.jwt;
    exports com.nimbusds.oauth2.sdk.assertions.saml2;
    exports com.nimbusds.oauth2.sdk.auth;
    exports com.nimbusds.oauth2.sdk.auth.verifier;
    exports com.nimbusds.oauth2.sdk.ciba;
    exports com.nimbusds.oauth2.sdk.client;
    exports com.nimbusds.oauth2.sdk.cnf;
    exports com.nimbusds.oauth2.sdk.device;
    exports com.nimbusds.oauth2.sdk.dpop;
    exports com.nimbusds.oauth2.sdk.dpop.verifiers;
    exports com.nimbusds.oauth2.sdk.http;
    exports com.nimbusds.oauth2.sdk.id;
    exports com.nimbusds.oauth2.sdk.jarm;
    exports com.nimbusds.oauth2.sdk.jose;
    exports com.nimbusds.oauth2.sdk.pkce;
    exports com.nimbusds.oauth2.sdk.rar;
    exports com.nimbusds.oauth2.sdk.token;
    exports com.nimbusds.oauth2.sdk.tokenexchange;
    exports com.nimbusds.oauth2.sdk.util;
    exports com.nimbusds.oauth2.sdk.util.date;
    exports com.nimbusds.oauth2.sdk.util.singleuse;
    exports com.nimbusds.oauth2.sdk.util.tls;
    exports com.nimbusds.openid.connect.sdk;
    exports com.nimbusds.openid.connect.sdk.assurance;
    exports com.nimbusds.openid.connect.sdk.assurance.claims;
    exports com.nimbusds.openid.connect.sdk.assurance.evidences;
    exports com.nimbusds.openid.connect.sdk.assurance.evidences.attachment;
    exports com.nimbusds.openid.connect.sdk.assurance.request;
    exports com.nimbusds.openid.connect.sdk.claims;
    exports com.nimbusds.openid.connect.sdk.federation;
    exports com.nimbusds.openid.connect.sdk.federation.api;
    exports com.nimbusds.openid.connect.sdk.federation.config;
    exports com.nimbusds.openid.connect.sdk.federation.entities;
    exports com.nimbusds.openid.connect.sdk.federation.policy;
    exports com.nimbusds.openid.connect.sdk.federation.policy.language;
    exports com.nimbusds.openid.connect.sdk.federation.policy.operations;
    exports com.nimbusds.openid.connect.sdk.federation.registration;
    exports com.nimbusds.openid.connect.sdk.federation.trust;
    exports com.nimbusds.openid.connect.sdk.federation.trust.constraints;
    exports com.nimbusds.openid.connect.sdk.federation.trust.marks;
    exports com.nimbusds.openid.connect.sdk.federation.utils;
    exports com.nimbusds.openid.connect.sdk.id;
    exports com.nimbusds.openid.connect.sdk.nativesso;
    exports com.nimbusds.openid.connect.sdk.op;
    exports com.nimbusds.openid.connect.sdk.rp;
    exports com.nimbusds.openid.connect.sdk.rp.statement;
    exports com.nimbusds.openid.connect.sdk.token;
    exports com.nimbusds.openid.connect.sdk.validators;
    exports com.nimbusds.secevent.sdk.claims;
    exports net.minidev.asm;
    exports net.minidev.asm.ex;
    exports net.minidev.json;
    exports net.minidev.json.annotate;
    exports net.minidev.json.parser;
    exports net.minidev.json.reader;
    exports net.minidev.json.writer;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/oauth2-oidc-sdk/README.md). Historical version examples retain their original context.

# Nimbus OAuth2/OIDC JPMS service

`com.guicedee.modules.services:oauth2-oidc-sdk:2.4.1` supplies the explicit
`oauth2.oidc.sdk` module for jlink. Import `com.guicedee:guicedee-bom:2.4.1`
in Maven dependency management, then use:

```xml
<dependency>
    <groupId>com.guicedee.modules.services</groupId>
    <artifactId>oauth2-oidc-sdk</artifactId>
</dependency>
```

```java
module my.app {
    requires oauth2.oidc.sdk;
    requires com.nimbusds.jose.jwt;
}
```

The JOSE dependency is transitive, so its explicit `requires` is optional. It is
shown for applications that directly use the JOSE/JWT API.

| Artifact | Version | Packaging |
| --- | --- | --- |
| com.nimbusds:oauth2-oidc-sdk | 11.38.2 | Bundled; original module name retained |
| com.nimbusds:content-type | 2.3 | Bundled |
| com.nimbusds:lang-tag | 1.7 | Bundled |
| net.minidev:json-smart | 2.6.0 | Bundled |
| net.minidev:accessors-smart | 2.6.0 | Bundled |
| com.github.stephenc.jcip:jcip-annotations | 1.0-1 | Bundled, relocated to an unexported annotation package |
| com.nimbusds:nimbus-jose-jwt | 10.9.1 | External native JPMS module, com.nimbusds.jose.jwt |
| org.ow2.asm:asm | BOM-managed (9.10.1 at introduction) | External native JPMS module, org.objectweb.asm |

JOSE/JWT already ships a Java 9 module descriptor. Reusing it preserves its
crypto-provider module requirements and avoids maintaining a duplicate shade.
The SDK and bundled libraries export their public packages, including JSON and
language/content types used in Nimbus APIs. JCIP annotations remain private.
No input JAR contains a service-provider registration needing a `provides` clause.

Replace the upstream SDK dependency; do not put its original JAR or any of the
bundled dependency JARs alongside this service. Exclude those artifacts if another
dependency brings them in. Published service dependencies contain only JOSE/JWT,
ASM, and the parent's provided Lombok dependency; bundled artifacts do not leak
into consumers' module paths.

## Security integration

Core OAuth2/OIDC and JDK-backed JOSE/DPoP operations are supported. The shade does
not install a GuicedEE authentication provider or a request handler. The application
must validate token signatures, trusted issuer, audience and lifetime before
authorizing requests. Nimbus's DPoP verifier checks the proof, HTTP method/URI,
access-token hash and key binding. Supply replay protection shared across instances
and reject requests if trust configuration or replay storage is unavailable.

See the [upstream DPoP guide](https://connect2id.com/products/nimbus-oauth-openid-connect-sdk/examples/oauth/dpop).
Optional upstream SAML/OpenSAML, servlet, Tink, Bouncy Castle and other optional
integrations are not bundled or enabled by this service. Using SDK adapters for
those integrations requires their dependencies and corresponding JPMS read edges.

## Build and validate

Use Maven 4 and JDK 25+. Install the changed Versioner, StandaloneBOM and GuicedEE
BOM first, in that order (`mvn -B -N install` in each module). From this directory:

```powershell
mvn -B install -DskipTests -Dmaven.javadoc.skip=true
./verify-jlink.ps1 -JdkHome $env:JAVA_HOME
```

The smoke consumer imports the GuicedEE BOM and declares only this service. Its
runtime graph must contain exactly the service, JOSE/JWT and ASM. The script links
and runs a fresh runtime image, exercising OIDC metadata, JSON bean serialization
(Accessors Smart/ASM), RSA JWT signing and ES256 DPoP verification. Wrong HTTP
method, token and key binding must be rejected. It does not require Keycloak or a
database and does not test application replay storage.

DevSuite's `services` profile includes this module. A standalone GitHub service
repository and its reusable Projects Builder workflow can be created when this
service is published; this change does not create a remote repository or release.
