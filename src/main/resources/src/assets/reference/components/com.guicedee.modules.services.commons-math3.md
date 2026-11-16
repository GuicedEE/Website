# com.guicedee.modules.services:commons-math3

The Apache Commons Math project is a library of lightweight, self-contained mathematics and statistics components addressing the most common practical problems not immediately available in the Java programming language or commons-lang.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `org.apache.commons.math3`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Apache/Commons/commons-math/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.apache.commons:commons-math3`; scope `compile`; optional.

## JPMS descriptor

```java
module org.apache.commons.math3 {
    requires java.desktop;
    //exports org.apache.commons.math3;

    exports org.apache.commons.math3.analysis;
    exports org.apache.commons.math3.complex;
    exports org.apache.commons.math3.dfp;
    ////exports org.apache.commons.math3.distribution;
    //exports org.apache.commons.math3.distribution.fitting;
    //exports org.apache.commons.math3.exception;
    //exports org.apache.commons.math3.exception.util;
    exports org.apache.commons.math3.filter;
    exports org.apache.commons.math3.fitting;
    exports org.apache.commons.math3.fitting.leastsquares;
    exports org.apache.commons.math3.fraction;
    //exports org.apache.commons.math3.genetics;
    //  exports org.apache.commons.math3.geometry;
    //  exports org.apache.commons.math3.geometry.enclosing;
    //  exports org.apache.commons.math3.geometry.euclidean.oned;
    //  exports org.apache.commons.math3.geometry.euclidean.twod;
    //  exports org.apache.commons.math3.geometry.euclidean.threed;
    //  exports org.apache.commons.math3.geometry.hull;
    //  exports org.apache.commons.math3.geometry.partitioning;
    ////    exports org.apache.commons.math3.geometry.spherical.oned;
    //  exports org.apache.commons.math3.geometry.spherical.twod;
    exports org.apache.commons.math3.linear;
    exports org.apache.commons.math3.ml.clustering;
    exports org.apache.commons.math3.ml.clustering.evaluation;
    exports org.apache.commons.math3.ml.distance;
    exports org.apache.commons.math3.ml.neuralnet;
    exports org.apache.commons.math3.ml.neuralnet.oned;
    exports org.apache.commons.math3.ml.neuralnet.twod;
    exports org.apache.commons.math3.ml.neuralnet.twod.util;
    exports org.apache.commons.math3.ml.neuralnet.sofm;
    exports org.apache.commons.math3.ml.neuralnet.sofm.util;
    exports org.apache.commons.math3.ode;
    exports org.apache.commons.math3.ode.events;
    exports org.apache.commons.math3.ode.nonstiff;
    exports org.apache.commons.math3.ode.sampling;
    exports org.apache.commons.math3.optim;
    exports org.apache.commons.math3.optim.linear;
    exports org.apache.commons.math3.optim.nonlinear.scalar;
    exports org.apache.commons.math3.optim.nonlinear.scalar.gradient;
    exports org.apache.commons.math3.optim.nonlinear.scalar.noderiv;
    //exports org.apache.commons.math3.random;
    exports org.apache.commons.math3.special;
    exports org.apache.commons.math3.stat;
    exports org.apache.commons.math3.stat.correlation;
    exports org.apache.commons.math3.stat.descriptive;
    exports org.apache.commons.math3.stat.descriptive.moment;
    exports org.apache.commons.math3.stat.descriptive.rank;
    exports org.apache.commons.math3.stat.descriptive.summary;
    exports org.apache.commons.math3.stat.inference;
    exports org.apache.commons.math3.stat.interval;
    exports org.apache.commons.math3.stat.ranking;
    exports org.apache.commons.math3.stat.regression;
    //exports org.apache.commons.math3.transform;
    exports org.apache.commons.math3.util;
    exports org.apache.commons.math3.exception;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Apache/Commons/commons-math/README.md). Historical version examples retain their original context.

<!---
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->
<!---
 +======================================================================+
 |****                                                              ****|
 |****      THIS FILE IS GENERATED BY THE COMMONS BUILD PLUGIN      ****|
 |****                    DO NOT EDIT DIRECTLY                      ****|
 |****                                                              ****|
 +======================================================================+
 | TEMPLATE FILE: readme-md-template.md                                 |
 | commons-build-plugin/trunk/src/main/resources/commons-xdoc-templates |
 +======================================================================+
 |                                                                      |
 | 1) Re-generate using: mvn commons:readme-md                          |
 |                                                                      |
 | 2) Set the following properties in the component's pom:              |
 |    - commons.componentid (required, alphabetic, lower case)          |
 |    - commons.release.version (required)                              |
 |                                                                      |
 | 3) Example Properties                                                |
 |                                                                      |
 |  <properties>                                                        |
 |    <commons.componentid>math</commons.componentid>                   |
 |    <commons.release.version>1.2</commons.release.version>            |
 |  </properties>                                                       |
 |                                                                      |
 +======================================================================+
--->
Apache Commons Math
===================

[![Build Status](https://travis-ci.org/apache/commons-math.svg?branch=master)](https://travis-ci.org/apache/commons-math)
[![Coverage Status](https://coveralls.io/repos/apache/commons-math/badge.svg?branch=master&service=github)](https://coveralls.io/github/apache/commons-math?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.commons/commons-math3/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.apache.commons/commons-math3/)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

The Apache Commons Math project is a library of lightweight, self-contained mathematics and statistics components addressing the most common practical problems not immediately available in the Java programming language or commons-lang.

Documentation
-------------

More information can be found on the [homepage](https://commons.apache.org/proper/commons-math).
The [Javadoc](https://commons.apache.org/proper/commons-math/apidocs) can be browsed.
Questions related to the usage of Apache Commons Math should be posted to the [user mailing list][ml].

Where can I get the latest release?
-----------------------------------
You can download source and binaries from our [download page](https://commons.apache.org/proper/commons-math/download_math.cgi).

Alternatively you can pull it from the central Maven repositories:

```xml
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-math3</artifactId>
  <version>3.5</version><type>jar</type>
            </dependency>
```

Contributing
------------

We accept PRs via github. The [developer mailing list][ml] is the main channel of communication for contributors.
There are some guidelines which will make applying PRs easier for us:
+ No tabs! Please use spaces for indentation.
+ Respect the code style.
+ Create minimal diffs - disable on save actions like reformat source code or organize imports. If you feel the source code should be reformatted create a separate PR for this change.
+ Provide JUnit tests for your changes and make sure your changes don't break any existing tests by running ```mvn clean test```.

If you plan to contribute on a regular basis, please consider filing a [contributor license agreement](https://www.apache.org/licenses/#clas).
You can learn more about contributing via GitHub in our [contribution guidelines](https://github.com/GuicedEE/Services/blob/master/Apache/Commons/commons-math/CONTRIBUTING.md).

License
-------
Code is under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0.txt).

Donations
---------
You like Apache Commons Math? Then [donate back to the ASF](https://www.apache.org/foundation/contributing.html) to support the development.

Additional Resources
--------------------

+ [Apache Commons Homepage](https://commons.apache.org/)
+ [Apache Bugtracker (JIRA)](https://issues.apache.org/jira/)
+ [Apache Commons Twitter Account](https://twitter.com/ApacheCommons)
+ #apachecommons IRC channel on freenode.org

[ml]:https://commons.apache.org/mail-lists.html
