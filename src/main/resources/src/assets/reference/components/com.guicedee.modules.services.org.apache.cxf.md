# com.guicedee.modules.services:org.apache.cxf

Apache CXF is an open-source services framework that aids in the development of services using front-end programming APIs, like JAX-WS and JAX-RS.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.cxf`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.cxf:cxf-core`; scope `compile`; optional.
- `org.apache.cxf:cxf-rt-frontend-jaxws`; scope `compile`; optional.
- `org.apache.cxf:cxf-rt-wsdl`; scope `compile`; optional.
- `org.slf4j:slf4j-api`; scope `compile`.
- `org.apache.commons:commons-lang3`; scope `compile`.
- `jakarta.xml.ws:jakarta.xml.ws-api`; scope `compile`.
- `jakarta.xml.bind:jakarta.xml.bind-api`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.
- `com.fasterxml.woodstox:woodstox-core`; scope `compile`.
- `org.apache.ws.xmlschema:xmlschema-core`; scope `compile`; optional.
- `jakarta.ws.rs:jakarta.ws.rs-api`; scope `compile`.
- `org.xmlresolver:xmlresolver`; scope `compile`; optional.
- `cglib:cglib`; scope `compile`; optional.
- `com.sun.xml.fastinfoset:FastInfoset`; scope `compile`.
- `xml-resolver:xml-resolver`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.cxf {

    requires transitive java.logging;
    requires static jakarta.servlet;

    requires static org.apache.commons.io;
    requires transitive org.apache.commons.lang3;

    requires transitive com.google.common;
    requires transitive jakarta.xml.ws;
    requires transitive jakarta.ws.rs;
    requires transitive jakarta.validation;
    requires transitive jakarta.annotation;


    requires static java.management;
    requires static java.desktop;
    requires org.slf4j;

//  requires static org.bouncycastle.pkix;


//  requires transitive com.guicedee.modules.services.openapi;



    requires org.codehaus.stax2;
    exports org.apache.cxf;
    exports org.apache.cxf.common.classloader;
    exports org.apache.cxf.common.annotation;
    exports org.apache.cxf.common.injection;
    exports org.apache.cxf.common.logging;
    exports org.apache.cxf.common.security;
    exports org.apache.cxf.common.jaxb;
    exports org.apache.cxf.common.spi;
    exports org.apache.cxf.common.util;
    exports org.apache.cxf.common.xmlschema;
    exports org.apache.cxf.common.i18n;

    exports org.apache.cxf.resource;
    exports org.apache.cxf.helpers;
    exports org.apache.cxf.io;
    exports org.apache.cxf.ws.addressing;
    exports org.apache.cxf.ws.addressing.wsdl;
    exports org.apache.cxf.security;
    exports org.apache.cxf.version;

    exports javax.wsdl;
    exports javax.wsdl.extensions;
    exports javax.wsdl.extensions.http;
    exports javax.wsdl.extensions.mime;
    exports javax.wsdl.extensions.schema;
    exports javax.wsdl.extensions.soap;
    exports javax.wsdl.extensions.soap12;
    exports javax.wsdl.factory;
    exports javax.wsdl.xml;
    exports org.apache.cxf.policy;
    exports org.apache.cxf.continuations;

    exports org.apache.cxf.annotations;
    exports org.apache.cxf.attachment;
    exports org.apache.cxf.binding;
    exports org.apache.cxf.binding.soap;
    exports org.apache.cxf.binding.xml;
    exports org.apache.cxf.bindings.xformat;
    exports org.apache.cxf.bus;
    exports org.apache.cxf.bus.extension;
    exports org.apache.cxf.bus.managers;
    exports org.apache.cxf.bus.resource;

    exports org.apache.cxf.buslifecycle;

    exports org.apache.cxf.endpoint;

    exports org.apache.cxf.transport;
    exports org.apache.cxf.transport.common.gzip;

    exports org.apache.cxf.configuration;
    exports org.apache.cxf.configuration.security;
    exports org.apache.cxf.configuration.jsse;



    exports org.apache.cxf.phase;
    exports org.apache.cxf.interceptor;
    exports org.apache.cxf.interceptor.security;
    exports org.apache.cxf.interceptor.security.callback;
    exports org.apache.cxf.interceptor.transform;
    exports org.apache.cxf.feature;
    exports org.apache.cxf.feature.validation;
    exports org.apache.cxf.feature.transform;


    exports org.apache.cxf.message;
    exports org.apache.cxf.jaxws;
    exports org.apache.cxf.jaxws.spi;
    exports org.apache.cxf.jaxws.support;
    exports org.apache.cxf.jaxws.context;
    exports org.apache.cxf.jaxws.interceptors;
    exports org.apache.cxf.service;
    exports org.apache.cxf.service.factory;
    exports org.apache.cxf.service.model;
    exports org.apache.cxf.service.invoker;
    exports org.apache.cxf.wsdl.service.factory;
    exports org.apache.cxf.jaxb;





/*  exports org.apache.cxf.jaxrs.ext;
    exports org.apache.cxf.jaxrs;
    exports org.apache.cxf.jaxrs.utils;*/

    //exports org.apache.cxf.frontend;

    /*exports org.apache.cxf.jaxrs.client;
    exports org.apache.cxf.jaxrs.lifecycle;*/
    /*exports org.apache.cxf.transport.servlet;*/
    /*exports org.apache.cxf.jaxrs.servlet;
    exports org.apache.cxf.jaxrs.impl;*/
    //exports org.apache.cxf.binding.soap;
//  exports org.apache.cxf.headers;
    //exports org.apache.cxf.ws.security.wss4j;
    //exports org.apache.wss4j.dom.handler;
    //exports org.apache.wss4j.common.ext;

    //exports org.apache.cxf.transport.servlet.servicelist;

    /*exports org.apache.cxf.rs.security.jose.common;
    exports org.apache.cxf.rs.security.jose.jwa;
    exports org.apache.cxf.rs.security.jose.jwk;
    exports org.apache.cxf.rs.security.jose.jws;
    exports org.apache.cxf.rs.security.jose.jwt;*/

    /*exports org.apache.cxf.rs.security.saml.sso;
    exports org.apache.cxf.sts.rest;
    exports org.apache.cxf.jaxrs.ext.search;
    exports org.apache.cxf.jaxrs.nio;
    exports org.apache.cxf.jaxrs.provider;
    exports org.apache.cxf.jaxrs.sse;
    exports org.apache.cxf.jaxrs.validation;
    exports org.apache.cxf.rs.security.oauth.filters;
    exports org.apache.cxf.rs.security.oauth.services;
    exports org.apache.cxf.rs.security.oauth2.filters;
    exports org.apache.cxf.rs.security.oauth2.provider;*/

    //exports org.apache.cxf.feature;



/*  opens org.apache.cxf.rs.security.oauth2.services;
    opens org.apache.cxf.rs.security.oauth2.provider to com.google.guice;
    opens org.apache.cxf.rs.security.oauth2.filters to com.google.guice;
    opens org.apache.cxf.rs.security.oauth2.common;
    opens org.apache.cxf.rs.security.oauth2.client;*/

/*  opens org.apache.cxf.rs.security.oauth.services;
    opens org.apache.cxf.rs.security.oauth.filters to com.google.guice;*/


//  exports org.apache.cxf.transport.http_undertow;
//  exports org.glassfish.jersey.internal to java.ws.rs;

    //Filters and Providers
    //opens org.apache.cxf.jaxrs.provider.json to com.google.guice;
    //opens org.apache.cxf.jaxrs.provider.xmlbeans to com.google.guice;
//  opens org.apache.cxf.jaxrs.provider to com.google.guice;
//  opens org.apache.cxf.jaxrs.ext.search to com.google.guice;
//opens org.apache.cxf.jaxrs.validation to com.google.guice;
    //opens org.apache.cxf.rs.security.saml.sso.state to com.google.guice;
//  opens org.apache.cxf.rs.security.saml.sso to com.google.guice;

//  opens org.apache.cxf.jaxrs.provider.aegis to com.google.guice;
//  opens org.apache.cxf.jaxrs.provider.atom to com.google.guice;

    //exports org.apache.cxf.jaxrs.provider.json ;
    //exports org.apache.cxf.jaxrs.provider.xmlbeans ;
//  exports org.apache.cxf.jaxrs.provider ;
//  exports org.apache.cxf.jaxrs.ext.search ;
//  exports org.apache.cxf.jaxrs.validation ;
//  exports org.apache.cxf.rs.security.saml.sso.state ;
// exports org.apache.cxf.rs.security.saml.sso ;

//  exports org.apache.cxf.jaxrs.provider.aegis ;
//  exports org.apache.cxf.jaxrs.provider.atom ;


    uses jakarta.ws.rs.ext.MessageBodyWriter;
    uses jakarta.ws.rs.ext.MessageBodyReader;
    uses jakarta.ws.rs.ext.ExceptionMapper;
    uses jakarta.ws.rs.ext.ContextResolver;
    uses jakarta.ws.rs.ext.ReaderInterceptor;
    uses jakarta.ws.rs.ext.WriterInterceptor;
    uses jakarta.ws.rs.ext.ParamConverterProvider;
    uses jakarta.ws.rs.container.ContainerRequestFilter;
    uses jakarta.ws.rs.container.ContainerResponseFilter;
    uses jakarta.ws.rs.container.DynamicFeature;


//  opens org.glassfish.jersey.server.wadl.internal  to java.ws.rs;

//  opens org.apache.cxf.ws.addressing to jakarta.xml.bind;

//  provides jakarta.xml.ws.spi.Provider with org.apache.cxf.jaxws22.spi.ProviderImpl;

    //provides org.apache.cxf.jaxrs.openapi.JaxRs2Extension with org.apache.cxf.jaxrs.openapi.JaxRs2Extension;
//  provides jakarta.ws.rs.sse.SseEventSource.Builder with org.apache.cxf.jaxrs.sse.client.SseEventSourceBuilderImpl;

//  uses org.apache.cxf.jaxrs.ext.JAXRSServerFactoryCustomizationExtension;
//  provides org.apache.cxf.jaxrs.ext.JAXRSServerFactoryCustomizationExtension with org.apache.cxf.jaxrs.sse.ext.SseTransportCustomizationExtension;

//  provides com.sun.xml.ws.spi.db.BindingContextFactory with com.sun.xml.ws.db.glassfish.JAXBRIContextFactory;
//  provides jakarta.ws.rs.client.ClientBuilder with org.apache.cxf.jaxrs.client.spec.ClientBuilderImpl;

//  provides jakarta.ws.rs.ext.RuntimeDelegate with org.apache.cxf.jaxrs.impl.RuntimeDelegateImpl;

// provides jakarta.xml.soap.MessageFactory with com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl,
//                                                 com.sun.xml.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl;
//  provides jakarta.xml.soap.SAAJMetaFactory with com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl;
//  provides jakarta.xml.soap.SOAPConnectionFactory with com.sun.xml.messaging.saaj.client.p2p.HttpSOAPConnectionFactory;
//  provides jakarta.xml.soap.SOAPFactory with com.sun.xml.messaging.saaj.soap.ver1_1.SOAPFactory1_1Impl,
//                                              com.sun.xml.messaging.saaj.soap.ver1_2.SOAPFactory1_2Impl;

    //uses org.eclipse.jetty.http.HttpFieldPreEncoder;
    //provides org.eclipse.jetty.http.HttpFieldPreEncoder with org.eclipse.jetty.http.Http1FieldPreEncoder;

//  uses org.opensaml.core.config.Initializer;
/*  provides org.opensaml.core.config.Initializer with org.opensaml.saml.config.XMLObjectProviderInitializer,
            org.opensaml.saml.config.SAMLConfigurationInitializer,
            org.opensaml.security.config.impl.ClientTLSValidationConfiguratonInitializer,
            org.opensaml.xmlsec.config.JavaCryptoValidationInitializer,
            org.opensaml.xmlsec.config.XMLObjectProviderInitializer,
            org.opensaml.xmlsec.config.ApacheXMLSecurityInitializer,
            org.opensaml.xmlsec.config.GlobalSecurityConfigurationInitializer,
            org.opensaml.xacml.config.impl.XMLObjectProviderInitializer,
            org.opensaml.xacml.profile.saml.config.impl.XMLObjectProviderInitializer,
            org.opensaml.xmlsec.config.GlobalAlgorithmRegistryInitializer,
            org.opensaml.xmlsec.config.DecryptionParserPoolInitializer,
            org.opensaml.core.xml.config.XMLObjectProviderInitializer,
            org.opensaml.core.xml.config.GlobalParserPoolInitializer,
            org.opensaml.core.metrics.impl.MetricRegistryInitializer;

    uses org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
    provides org.opensaml.xmlsec.algorithm.AlgorithmDescriptor with org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES128CBC,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES128GCM,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES192CBC,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES192GCM,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES256CBC,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionAES256GCM,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.BlockEncryptionDESede,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestMD5,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestRIPEMD160,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestSHA1,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestSHA224,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestSHA256,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestSHA384,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.DigestSHA512,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACMD5,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACRIPEMD160,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACSHA1,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACSHA224,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACSHA256,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACSHA384,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.HMACSHA512,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.KeyTransportRSA15,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.KeyTransportRSAOAEP,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.KeyTransportRSAOAEPMGF1P,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureDSASHA1,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureDSASHA256,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA1,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA224,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA256,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA384,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureECDSASHA512,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSAMD5,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSARIPEMD160,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA1,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA224,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA256,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA384,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SignatureRSASHA512,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapAES128,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapAES192,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapAES256,
                                                                       org.opensaml.xmlsec.algorithm.descriptors.SymmetricKeyWrapDESede;
    uses org.opensaml.xmlsec.signature.support.SignatureValidationProvider;
    provides org.opensaml.xmlsec.signature.support.SignatureValidationProvider with org.opensaml.xmlsec.signature.support.impl.provider.ApacheSantuarioSignatureValidationProviderImpl;

    uses org.opensaml.xmlsec.signature.support.SignerProvider;
    provides org.opensaml.xmlsec.signature.support.SignerProvider with org.opensaml.xmlsec.signature.support.impl.provider.ApacheSantuarioSignerProviderImpl;*/
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf/README.md). Historical version examples retain their original context.

Welcome to Apache CXF!
======================
Apache CXF is an open source services framework. CXF helps you build and
develop services using frontend programming APIs, like JAX-WS and JAX-RS.
These services can speak a variety of protocols such as SOAP, XML/HTTP,
RESTful HTTP, or CORBA and work over a variety of transports such as HTTP,
JMS or JBI.

CXF includes a broad feature set, but it is primarily focused on the following
areas:

- Web Services Standards Support: CXF supports a variety of web service
  standards including SOAP, the Basic Profile, WSDL, WS-Addressing,
  WS-Policy, WS-ReliableMessaging, WS-Security, WS-SecurityPolicy,
  WS-SecureConversation, and WS-Trust.
- Frontends: CXF supports a variety of "frontend" programming models. CXF
  implements the JAX-WS APIs. It also includes a "simple frontend" which
  allows creation of clients and endpoints without annotations. CXF supports
  both contract first development with WSDL and code first development
  starting from Java.  There is also a JAX-RS frontend for providing
  REST support.
- Ease of use: CXF is designed to be intuitive and easy to use. There
  are simple APIs to quickly build code-first services, Maven plug-ins to
  make tooling integration easy, JAX-WS API support, Spring 2.x XML support
  to make configuration a snap, and much more.
- Binary and Legacy Protocol Support: CXF has been designed to provide a
  pluggable architecture that supports not only XML but also non-XML type
  bindings, such as JSON and CORBA, in combination with any type of transport.


Export Notice
============================
This distribution includes cryptographic software.  The country in
which you currently reside may have restrictions on the import,
possession, use, and/or re-export to another country, of
encryption software.  BEFORE using any encryption software, please
check your country's laws, regulations and policies concerning the
import, possession, or use, and re-export of encryption software, to
see if this is permitted.  See <http://www.wassenaar.org/> for more
information.

The U.S. Government Department of Commerce, Bureau of Industry and
Security (BIS), has classified this software as Export Commodity
Control Number (ECCN) 5D002.C.1, which includes information security
software using or performing cryptographic functions with asymmetric
algorithms.  The form and manner of this Apache Software Foundation
distribution makes it eligible for export under the License Exception
ENC Technology Software Unrestricted (TSU) exception (see the BIS
Export Administration Regulations, Section 740.13) for both object
code and source code.

The following provides more details on the included cryptographic
software:
- http://santuario.apache.org/
- http://www.bouncycastle.org/
- http://ws.apache.org/wss4j/



Getting Started
===============

For an Apache CXF source distribution, please read BUILDING.txt for
instructions on building Apache CXF.

For an Apache CXF binary distribution, please read release_notes.txt
for installation instructions and list of supported and unsupported
features.

Alternatively, you can also find out how to get started here:
http://cxf.apache.org/

If you need more help try talking to us on our mailing lists:
http://cxf.apache.org/mailing-lists.html

If you find any issues with CXF, please submit reports with JIRA here:
https://issues.apache.org/jira/browse/CXF

We welcome contributions, and encourage you to get involved in the CXF
community. If you'd like to learn more about how you can contribute, please
see:
http://cxf.apache.org/getting-involved.html

Thank you for using CXF!

The Apache CXF Team
http://cxf.apache.org/

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## Version

[Java source](https://github.com/GuicedEE/Services/blob/master/Apache/CXF/apache-cxf/src/main/java/org/apache/cxf/version/Version.java)

### Version

```java
public static void main(String[] args);
public static String getCurrentVersion();
public static String getName();
public static String getCompleteVersionString();
```
