# com.guicedee.modules.services:jna-platform

jna-platform

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.sun.jna`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/JNI/jna-platform/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `net.java.dev.jna:jna-platform`; scope `compile`; optional.
- `tools.jackson.core:jackson-core`; scope `compile`.
- `tools.jackson.core:jackson-databind`; scope `compile`.

## JPMS descriptor

```java
module com.sun.jna {
    exports com.sun.jna;
    exports com.sun.jna.platform;

    exports com.sun.jna.platform.dnd;
    exports com.sun.jna.platform.linux;
    exports com.sun.jna.platform.mac;
    exports com.sun.jna.platform.unix;
    exports com.sun.jna.platform.unix.aix;
    exports com.sun.jna.platform.unix.solaris;

    exports com.sun.jna.platform.wince;
    exports com.sun.jna.platform.win32;
    exports com.sun.jna.platform.win32.COM;
    //exports com.sun.jna.platform.win32.COM.tlb;
    //exports com.sun.jna.platform.win32.COM.tlb.imp;
    //exports com.sun.jna.platform.win32.COM.util;
    //exports com.sun.jna.platform.win32.COM.util.annotation;

    requires static java.desktop;

    requires java.logging;
    requires tools.jackson.core;
    requires com.fasterxml.jackson.annotation;

    exports com.sun.jna.win32 to org.testcontainers;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.
