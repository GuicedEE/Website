# com.guicedee.modules.services:javassist

Javassist (JAVA programming ASSISTant) makes Java bytecode manipulation simple. It is a class library for editing bytecodes in Java.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `javassist`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/javassist/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.javassist:javassist`; scope `compile`; optional.

## JPMS descriptor

```java
module javassist {
    requires java.instrument;
    requires java.management;
    requires static jdk.attach;
    requires static jdk.jdi;
    requires java.desktop;

    exports javassist;
    exports javassist.util;
    exports javassist.util.proxy;
    exports javassist.bytecode;
    exports javassist.bytecode.analysis;
    exports javassist.bytecode.annotation;
    exports javassist.bytecode.stackmap;
    exports javassist.compiler;
    exports javassist.compiler.ast;
    exports javassist.convert;
    exports javassist.expr;
    exports javassist.runtime;
    exports javassist.scopedpool;
    exports javassist.tools;
    // javassist 3.32.0-GA removed javassist.tools.reflect, javassist.tools.rmi and
    // javassist.tools.web (3.31.0-GA still shipped all three). Exporting a package the
    // jar does not contain makes the module unreadable at runtime:
    //   InvalidModuleDescriptorException: Package javassist.tools.reflect not found in module
    // Re-add these only if a future upstream release restores those packages.

}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/javassist/README.md). Historical version examples retain their original context.

Java bytecode engineering toolkit
### [Javassist version 3](http://www.javassist.org)

Copyright (C) 1999-2019 by Shigeru Chiba, All rights reserved.

Javassist (JAVA programming ASSISTant) makes Java bytecode manipulation
simple. It is a class library for editing bytecodes in Java; it enables Java
programs to define a new class at runtime and to modify a class file when the
JVM loads it. Unlike other similar bytecode editors, Javassist provides two
levels of API: source level and bytecode level. If the users use the source-
level API, they can edit a class file without knowledge of the specifications
of the Java bytecode. The whole API is designed with only the vocabulary of
the Java language. You can even specify inserted bytecode in the form of
source text; Javassist compiles it on the fly. On the other hand, the
bytecode-level API allows the users to directly edit a class file as other
editors.

This software is distributed under the Mozilla Public License Version 1.1,
the GNU Lesser General Public License Version 2.1 or later, or
the Apache License Version 2.0.

#### Files

  * [Readme.html](https://github.com/GuicedEE/Services/blob/master/Libraries/javassist/Readme.html)
Readme file (a longer version of this file).

  * [License.html](https://github.com/GuicedEE/Services/blob/master/Libraries/javassist/License.html)
License file

  * [tutorial/tutorial.html](https://github.com/GuicedEE/Services/blob/master/Libraries/javassist/tutorial/tutorial.html)
Tutorial

  * ./javassist.jar
The Javassist jar file (class files)

  * ./src/main
The source files

  * [html/index.html](https://github.com/GuicedEE/Services/blob/master/Libraries/javassist/html/index.html)
The top page of the Javassist API document.
