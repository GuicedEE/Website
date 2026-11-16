# com.guicedee.modules.services:openpdf

OpenPDF

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.github.librepdf.openpdf`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/openpdf/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.github.librepdf:openpdf`; scope `compile`; optional.
- `org.bouncycastle:bcprov-jdk18on`; scope `compile`.
- `org.bouncycastle:bcpkix-jdk18on`; scope `compile`.
- `com.github.albfernandez:juniversalchardet`; scope `compile`; optional.
- `jakarta.annotation:jakarta.annotation-api`; scope `compile`.

## JPMS descriptor

```java
module com.github.librepdf.openpdf {
    requires static java.desktop;
    requires jakarta.annotation;

    requires static org.bouncycastle.pkix;
    //requires static org.bouncycastle.provider;
    requires static com.github.spotbugs.spotbugs;
    requires static imageio.tiff;
    requires static org.apache.fop;

    // OpenPDF renamed its packages from com.lowagie.text.* to org.openpdf.text.* (the jar
    // now ships org.openpdf.*, nothing under com.lowagie). Exporting the old names made
    // every export stale at once:
    //   InvalidModuleDescriptorException: Package com.lowagie.text not found in module
    // The list below mirrors the previous API surface 1:1 under the new names. Note the jar
    // also contains org.openpdf.text.utils / .xml / .xml.simpleparser / .xml.xmp,
    // org.openpdf.bouncycastle and org.mozilla.universalchardet.* which stay unexported,
    // as they were before the rename.
    exports org.openpdf.text;
    exports org.openpdf.text.alignment;
    exports org.openpdf.text.error_messages;
    exports org.openpdf.text.exceptions;
    exports org.openpdf.text.factories;
    exports org.openpdf.text.html;
    exports org.openpdf.text.html.simpleparser;
    exports org.openpdf.text.pdf;
    exports org.openpdf.text.pdf.codec;
    exports org.openpdf.text.pdf.codec.wmf;
    exports org.openpdf.text.pdf.collection;
    exports org.openpdf.text.pdf.crypto;
    exports org.openpdf.text.pdf.draw;
    exports org.openpdf.text.pdf.events;
    exports org.openpdf.text.pdf.fonts;
    exports org.openpdf.text.pdf.fonts.cmaps;
    exports org.openpdf.text.pdf.hyphenation;
    exports org.openpdf.text.pdf.interfaces;
    exports org.openpdf.text.pdf.internal;
    exports org.openpdf.text.pdf.parser;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
