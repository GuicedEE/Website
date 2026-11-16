# com.guicedee.modules.services:uadetector-core

The UADetector core library includes the API and implementation to read the detection information and the functions to identify User-Agents, but not the database (also known as UAS data). The UAS data can be found in the resources module.

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `net.sf.uadetector.core`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `com.google.guava:guava`; scope `compile`.
- `jakarta.validation:jakarta.validation-api`; scope `compile`.
- `jakarta.activation:jakarta.activation-api`; scope `compile`.

## JPMS descriptor

```java
module net.sf.uadetector.core {
    exports net.sf.uadetector;

    exports net.sf.uadetector.datastore to net.sf.uadetector.resources;
    exports net.sf.uadetector.datareader to net.sf.uadetector.resources;
    exports net.sf.uadetector.parser to net.sf.uadetector.resources;

    requires transitive jakarta.activation;

    requires jakarta.validation;
    requires java.xml;

    requires transitive java.logging;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/README.md). Historical version examples retain their original context.

UADetector
==========


What is UADetector?
-----

UADetector is a library to identify over 190 different desktop and mobile
browsers and 130 other User-Agents like feed readers, email clients and
multimedia players. In addition, even more than 400 robots like BingBot,
Googlebot or Yahoo Bot can be identified.

This library is a free, portable Java library to analyze User-Agent strings.
The goal of this library is to detect the type and the associated operating
system of a client like `Mobile Firefox 9.0` on `Android` or `Mobile
Safari 5.1` on `iOS`.

UADetector is divided into two modules. The core module includes
the API and implementation to read the detection information and the functions
to identify User-Agents. The resources module contains the database with the
necessary identification information and a service factory class to get simply
preconfigured UserAgentStringParser singletons. This library will be published
monthly, is integration-tested against the core module and is guaranteed to run
against the defined core.


Device categorization
-----

Since version `0.9.10` we support device categorization which means that for
instance an *iPhone* or *Nexus 4* will be classified as "Smartphone" and an
*iPad*, *Kindle* or *Surface RT* as "Tablet". Please take a look into our
[API documentation](http://uadetector.sourceforge.net/modules/uadetector-core/apidocs/net/sf/uadetector/ReadableUserAgent.html)
to get an idea what you can get when parsing an user agent string.


Features
--------

### Detects over 190 different browsers

This library detects over 190 different desktop and mobile browsers and 130
other User-Agents like feed readers, multimedia players and email clients.

### Identifies over 400 robots

In the Internet many robots are on their way to examine sites. A large number
of robots can be detected with this library.

### Monthly updated

Each month a new version of the resources module will be published so you can
always detect the latest User-Agents.

### Extremely tested

All classes in this library have been especially tested. The unit tests have
over 90% branch coverage and 98% line coverage. In addition, many integration
tests performed regularly.


How can You help?
-----------------

UADetector is an open source tool and welcomes contributions.

* Report bugs, feature requests and other issues in the
  [issue tracking](https://github.com/before/uadetector/issues) application, but look
  on our [known issues](http://uadetector.sourceforge.net/known-issues.html)
  page first before posting!
* Help with the documentation by pointing out areas that are lacking or
  unclear, and if you are so inclined, submitting patches to correct it. You
  can quickly contribute rough thoughts by forking this project on
  [GitHub](https://github.com/before/uadetector) and
  [SourceForge](http://sourceforge.net/p/uadetector/code/?branch=ref%2Fmaster),
  or you can volunteer to help collate and organize information that is already
  there.

Your participation in this project is much appreciated!


License
-------

Please visit the UADetector web site for more information:

  * [http://uadetector.sourceforge.net/](http://uadetector.sourceforge.net/)

Copyright 2012 André Rouél

André Rouél licenses this product to you under the Apache License, version 2.0
(the "License"); you may not use this product except in compliance with the
License. You may obtain a copy of the License at:

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied.  See the License for the
specific language governing permissions and limitations under the License.

Also, please refer to each LICENSE.*component*.txt file, which is located in
the same directory as this file, for the license terms of the components that
this product depends on.

-------------------------------------------------------------------------------
This product contains a modified version of Dave Koelle's Alphanum Algorithm,
which can be obtained at:

  * HOMEPAGE:
    * [http://www.davekoelle.com/alphanum.html](http://www.davekoelle.com/alphanum.html)
  * LICENSE:
    * LICENSE.alphanum.txt (GNU LGPL 2.1 or above)

This product uses a version of Jaroslav Mallat's UAS Data, which can be
obtained at:

  * HOMEPAGE:
    * [http://user-agent-string.info/](http://user-agent-string.info/)
  * LICENSE:
    * LICENSE.uas.txt (CC BY 3.0)

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## DataReader

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datareader/DataReader.java)

### DataReader

```java
Data read(URL url, Charset charset);
Data read(String data);
```

## XmlDataReader

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datareader/XmlDataReader.java)

### XmlDataReader

```java
@Override public Data read(@jakarta.validation.constraints.NotNull
URL url, @jakarta.validation.constraints.NotNull
Charset charset);
@Override public Data read(@jakarta.validation.constraints.NotNull
String data);
protected static Data readXml(@jakarta.validation.constraints.NotNull
InputStream inputStream, @jakarta.validation.constraints.NotNull
Charset charset);
```

## AbstractDataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/AbstractDataStore.java)

### AbstractDataStore

```java
protected AbstractDataStore(DataReader reader, String dataUrl, String versionUrl, Charset charset);
protected AbstractDataStore(DataReader reader, URL dataUrl, URL versionUrl, Charset charset);
protected AbstractDataStore(@jakarta.validation.constraints.NotNull
Data data, @jakarta.validation.constraints.NotNull
DataReader reader, @jakarta.validation.constraints.NotNull
URL dataUrl, @jakarta.validation.constraints.NotNull
URL versionUrl, @jakarta.validation.constraints.NotNull
Charset charset);
protected static final Data readData(@jakarta.validation.constraints.NotNull
DataReader reader, @jakarta.validation.constraints.NotNull
URL url, @jakarta.validation.constraints.NotNull
Charset charset);
@Override public Charset getCharset();
@Override public Data getData();
@Override public DataReader getDataReader();
@Override public URL getDataUrl();
@Override public URL getVersionUrl();
```

## AbstractRefreshableDataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/AbstractRefreshableDataStore.java)

### AbstractRefreshableDataStore

```java
protected AbstractRefreshableDataStore(@jakarta.validation.constraints.NotNull
DataReader reader, @jakarta.validation.constraints.NotNull
String dataUrl, @jakarta.validation.constraints.NotNull
String versionUrl, @jakarta.validation.constraints.NotNull
Charset charset, @jakarta.validation.constraints.NotNull
DataStore fallback);
protected AbstractRefreshableDataStore(@jakarta.validation.constraints.NotNull
DataReader reader, @jakarta.validation.constraints.NotNull
URL dataUrl, @jakarta.validation.constraints.NotNull
URL versionUrl, @jakarta.validation.constraints.NotNull
Charset charset, DataStore fallback);
@Override public Charset getCharset();
@Override public Data getData();
@Override public DataReader getDataReader();
@Override public URL getDataUrl();
@Override public URL getVersionUrl();
protected void setData(@jakarta.validation.constraints.NotNull
Data data);
@Override public DataStore getFallback();
@Override public UpdateOperation getUpdateOperation();
@Override public void refresh();
protected void setUpdateOperation(@jakarta.validation.constraints.NotNull
UpdateOperation updateOperation);
```

## AbstractUpdateOperation

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/AbstractUpdateOperation.java)

### AbstractUpdateOperation

```java
public AbstractUpdateOperation(@jakarta.validation.constraints.NotNull
RefreshableDataStore dataStore);
@Override public long getLastUpdateCheck();
@Override public void shutdown();
protected boolean isUpdateAvailable();
@Override public void run();
```

## CachingXmlDataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/CachingXmlDataStore.java)

### CachingXmlDataStore

```java
@jakarta.validation.constraints.NotNull public static CachingXmlDataStore createCachingXmlDataStore(@jakarta.validation.constraints.NotNull
URL dataUrl, @jakarta.validation.constraints.NotNull
URL versionURL, @jakarta.validation.constraints.NotNull
DataStore fallback);
@jakarta.validation.constraints.NotNull public static CachingXmlDataStore createCachingXmlDataStore(@jakarta.validation.constraints.NotNull
File cacheFile, @jakarta.validation.constraints.NotNull
URL dataUrl, @jakarta.validation.constraints.NotNull
URL versionUrl, @jakarta.validation.constraints.NotNull
Charset charset, @jakarta.validation.constraints.NotNull
DataStore fallback);
@jakarta.validation.constraints.NotNull public static File findOrCreateCacheFile();
@Deprecated public static CachingXmlDataStore createCachingXmlDataStore(@jakarta.validation.constraints.NotNull
DataStore fallback);
@jakarta.validation.constraints.NotNull @Deprecated public static CachingXmlDataStore createCachingXmlDataStore(@jakarta.validation.constraints.NotNull
File cacheFile, @jakarta.validation.constraints.NotNull
DataStore fallback);
@jakarta.validation.constraints.NotNull public static CachingXmlDataStore createCachingXmlDataStore(@jakarta.validation.constraints.NotNull
URL dataUrl, @jakarta.validation.constraints.NotNull
URL versionUrl, @jakarta.validation.constraints.NotNull
Charset charset, @jakarta.validation.constraints.NotNull
DataStore fallback);
```

## DataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/DataStore.java)

### DataStore

```java
Charset DEFAULT_CHARSET;
@Deprecated String DEFAULT_DATA_URL;
@Deprecated String DEFAULT_VERSION_URL;
@jakarta.validation.constraints.NotNull Charset getCharset();
@jakarta.validation.constraints.NotNull Data getData();
@jakarta.validation.constraints.NotNull DataReader getDataReader();
@jakarta.validation.constraints.NotNull URL getDataUrl();
@jakarta.validation.constraints.NotNull URL getVersionUrl();
```

## OnlineXmlDataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/OnlineXmlDataStore.java)

### OnlineXmlDataStore

```java
@Deprecated public OnlineXmlDataStore(DataStore fallback);
public OnlineXmlDataStore(URL dataurl, URL versionUrl, DataStore fallback);
```

## RefreshableDataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/RefreshableDataStore.java)

### RefreshableDataStore

```java
String MSG_FAULTY_CONTENT;
String MSG_URL_NOT_READABLE;
@jakarta.validation.constraints.NotNull DataStore getFallback();
@jakarta.validation.constraints.NotNull UpdateOperation getUpdateOperation();
void refresh();
```

## SimpleXmlDataStore

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/SimpleXmlDataStore.java)

### SimpleXmlDataStore

```java
public SimpleXmlDataStore(URL dataUrl, URL versionUrl);
```

## UpdateOperation

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/datastore/UpdateOperation.java)

### UpdateOperation

```java
long DEFAULT_UPDATE_INTERVAL;
void call();
long getLastUpdateCheck();
void shutdown();
```

## DeviceCategory

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/DeviceCategory.java)

### DeviceCategory

```java
public static final DeviceCategory EMPTY;
public DeviceCategory(@jakarta.validation.constraints.NotNull
Category category, @jakarta.validation.constraints.NotNull
String icon, @jakarta.validation.constraints.NotNull
String infoUrl, @jakarta.validation.constraints.NotNull
String name);
@Override @jakarta.validation.constraints.NotNull public Category getCategory();
@Override @jakarta.validation.constraints.NotNull public String getIcon();
@Override @jakarta.validation.constraints.NotNull public String getInfoUrl();
@Override @jakarta.validation.constraints.NotNull public String getName();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
public Builder();
public Builder(@jakarta.validation.constraints.NotNull
DeviceCategory deviceCategory);
@jakarta.validation.constraints.NotNull public DeviceCategory build();
@jakarta.validation.constraints.NotNull public Builder setCategory(@jakarta.validation.constraints.NotNull
Category category);
@jakarta.validation.constraints.NotNull public Builder setIcon(@jakarta.validation.constraints.NotNull
String icon);
@jakarta.validation.constraints.NotNull public Builder setInfoUrl(@jakarta.validation.constraints.NotNull
String infoUrl);
@jakarta.validation.constraints.NotNull public Builder setName(@jakarta.validation.constraints.NotNull
String name);
```

## CannotCloseException

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/exception/CannotCloseException.java)

### CannotCloseException

```java
public CannotCloseException();
public CannotCloseException(String info);
public CannotCloseException(String info, Throwable cause);
public CannotCloseException(Throwable cause);
```

## CanNotOpenStreamException

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/exception/CanNotOpenStreamException.java)

### CanNotOpenStreamException

```java
public CanNotOpenStreamException();
public CanNotOpenStreamException(String url);
public CanNotOpenStreamException(String url, Throwable cause);
public CanNotOpenStreamException(Throwable cause);
```

## IllegalEmptyArgumentException

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/exception/IllegalEmptyArgumentException.java)

### IllegalEmptyArgumentException

```java
public IllegalEmptyArgumentException();
public IllegalEmptyArgumentException(String message);
public IllegalEmptyArgumentException(String message, Throwable cause);
public IllegalEmptyArgumentException(Throwable cause);
public IllegalEmptyArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## IllegalNegativeArgumentException

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/exception/IllegalNegativeArgumentException.java)

### IllegalNegativeArgumentException

```java
public IllegalNegativeArgumentException();
public IllegalNegativeArgumentException(String message);
public IllegalNegativeArgumentException(String message, Throwable cause);
public IllegalNegativeArgumentException(Throwable cause);
public IllegalNegativeArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## IllegalNullArgumentException

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/exception/IllegalNullArgumentException.java)

### IllegalNullArgumentException

```java
public IllegalNullArgumentException();
public IllegalNullArgumentException(String message);
public IllegalNullArgumentException(String message, Throwable cause);
public IllegalNullArgumentException(Throwable cause);
public IllegalNullArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## IllegalStateOfArgumentException

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/exception/IllegalStateOfArgumentException.java)

### IllegalStateOfArgumentException

```java
public IllegalStateOfArgumentException();
public IllegalStateOfArgumentException(String message);
public IllegalStateOfArgumentException(String message, Throwable cause);
public IllegalStateOfArgumentException(Throwable cause);
public IllegalStateOfArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace);
```

## Check

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/Check.java)

### Check

```java
public static <T> T notEmpty(T type, String... message);
public static <T> T notNull(T type, String... message);
public static <T extends Number> T notNegative(T type, String... message);
public static <T extends Character> T notNegative(T type, String... message);
public static boolean isDebugEnabled();
public static boolean stateIsTrue(boolean bool, String... message);
```

## BrowserOperatingSystemMappingComparator

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/BrowserOperatingSystemMappingComparator.java)

### BrowserOperatingSystemMappingComparator

```java
public static final BrowserOperatingSystemMappingComparator INSTANCE;
@Override public int compareType(BrowserOperatingSystemMapping o1, BrowserOperatingSystemMapping o2);
```

## Data

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/Data.java)

### Data

```java
public static final Data EMPTY;
public Data(@jakarta.validation.constraints.NotNull
Set<Browser> browsers, @jakarta.validation.constraints.NotNull
Map<Integer, SortedSet<BrowserPattern>> browserPatterns, @jakarta.validation.constraints.NotNull
Map<Integer, BrowserType> browserTypes, @jakarta.validation.constraints.NotNull
SortedMap<BrowserPattern, Browser> patternToBrowserMap, @jakarta.validation.constraints.NotNull
Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings, @jakarta.validation.constraints.NotNull
Set<OperatingSystem> operatingSystems, @jakarta.validation.constraints.NotNull
Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns, @jakarta.validation.constraints.NotNull
SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap, @jakarta.validation.constraints.NotNull
List<Robot> robots, @jakarta.validation.constraints.NotNull
Set<Device> devices, @jakarta.validation.constraints.NotNull
Map<Integer, SortedSet<DevicePattern>> devicePatterns, @jakarta.validation.constraints.NotNull
SortedMap<DevicePattern, Device> patternToDeviceMap, @jakarta.validation.constraints.NotNull
String version);
@jakarta.validation.constraints.NotNull public Map<Integer, SortedSet<BrowserPattern>> getBrowserPatterns();
@jakarta.validation.constraints.NotNull public Set<Browser> getBrowsers();
@jakarta.validation.constraints.NotNull public Set<BrowserOperatingSystemMapping> getBrowserToOperatingSystemMappings();
@jakarta.validation.constraints.NotNull public Map<Integer, BrowserType> getBrowserTypes();
@jakarta.validation.constraints.NotNull public Map<Integer, SortedSet<DevicePattern>> getDevicePatterns();
@jakarta.validation.constraints.NotNull public Set<Device> getDevices();
@jakarta.validation.constraints.NotNull public Map<Integer, SortedSet<OperatingSystemPattern>> getOperatingSystemPatterns();
@jakarta.validation.constraints.NotNull public Set<OperatingSystem> getOperatingSystems();
@jakarta.validation.constraints.NotNull public SortedMap<BrowserPattern, Browser> getPatternToBrowserMap();
@jakarta.validation.constraints.NotNull public SortedMap<DevicePattern, Device> getPatternToDeviceMap();
@jakarta.validation.constraints.NotNull public SortedMap<OperatingSystemPattern, OperatingSystem> getPatternToOperatingSystemMap();
@jakarta.validation.constraints.NotNull public List<Robot> getRobots();
@jakarta.validation.constraints.NotNull public String getVersion();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
@jakarta.validation.constraints.NotNull public String toStats();
```

## DataBuilder

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/DataBuilder.java)

### DataBuilder

```java
public DataBuilder appendBrowser(@jakarta.validation.constraints.NotNull
Browser browser);
@jakarta.validation.constraints.NotNull public DataBuilder appendBrowserBuilder(@jakarta.validation.constraints.NotNull
Browser.Builder browserBuilder);
@jakarta.validation.constraints.NotNull public DataBuilder appendBrowserOperatingSystemMapping(@jakarta.validation.constraints.NotNull
BrowserOperatingSystemMapping browserOsMapping);
@jakarta.validation.constraints.NotNull public DataBuilder appendBrowserPattern(@jakarta.validation.constraints.NotNull
BrowserPattern pattern);
@jakarta.validation.constraints.NotNull public DataBuilder appendBrowserType(@jakarta.validation.constraints.NotNull
BrowserType type);
public DataBuilder appendDevice(@jakarta.validation.constraints.NotNull
Device device);
@jakarta.validation.constraints.NotNull public DataBuilder appendDeviceBuilder(@jakarta.validation.constraints.NotNull
Device.Builder deviceBuilder);
@jakarta.validation.constraints.NotNull public DataBuilder appendDevicePattern(@jakarta.validation.constraints.NotNull
DevicePattern pattern);
@jakarta.validation.constraints.NotNull public DataBuilder appendOperatingSystem(@jakarta.validation.constraints.NotNull
OperatingSystem operatingSystem);
@jakarta.validation.constraints.NotNull public DataBuilder appendOperatingSystemBuilder(@jakarta.validation.constraints.NotNull
OperatingSystem.Builder operatingSystemBuilder);
@jakarta.validation.constraints.NotNull public DataBuilder appendOperatingSystemPattern(@jakarta.validation.constraints.NotNull
OperatingSystemPattern pattern);
@jakarta.validation.constraints.NotNull public DataBuilder appendRobot(@jakarta.validation.constraints.NotNull
Robot robot);
@jakarta.validation.constraints.NotNull public Data build();
@jakarta.validation.constraints.NotNull public DataBuilder setVersion(@jakarta.validation.constraints.NotNull
String version);
```

## Browser

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/Browser.java)

### Browser

```java
public Browser(int id, @NotNull
UserAgentFamily family, @NotNull
String familyName, @NotNull
SortedSet<BrowserPattern> patterns, BrowserType type, OperatingSystem operatingSystem, @NotNull
String icon, @NotNull
String infoUrl, @NotNull
String producer, @NotNull
String producerUrl, @NotNull
String url);
public void copyTo(@NotNull
UserAgent.Builder builder);
@NotNull public UserAgentFamily getFamily();
@NotNull public String getFamilyName();
@NotNull public String getIcon();
@Override public int getId();
@NotNull public String getInfoUrl();
public OperatingSystem getOperatingSystem();
@NotNull public SortedSet<BrowserPattern> getPatterns();
@NotNull public String getProducer();
@NotNull public String getProducerUrl();
@NotNull public BrowserType getType();
@NotNull public String getUrl();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
public Builder();
public Builder(@NotNull
Browser browser);
protected Builder(@NotNull
Builder builder);
public Browser build();
public Builder copy();
public UserAgentFamily getFamily();
public String getFamilyName();
@NotNull public Builder setFamilyName(@NotNull
String familyName);
public String getIcon();
@NotNull public Builder setIcon(@NotNull
String icon);
public int getId();
@NotNull public Builder setId(int id);
@NotNull public Builder setId(@NotNull
String id);
public String getInfoUrl();
@NotNull public Builder setInfoUrl(@NotNull
String infoUrl);
public OperatingSystem getOperatingSystem();
@NotNull public Builder setOperatingSystem(@NotNull
OperatingSystem operatingSystem);
public SortedSet<BrowserPattern> getPatterns();
@NotNull public Builder setPatterns(@NotNull
SortedSet<BrowserPattern> patterns);
public String getProducer();
@NotNull public Builder setProducer(@NotNull
String producer);
public String getProducerUrl();
@NotNull public Builder setProducerUrl(@NotNull
String producerUrl);
public BrowserType getType();
@NotNull public Builder setType(@NotNull
BrowserType type);
@NotNull public Builder setTypeId(int typeId);
public int getTypeId();
@NotNull public Builder setTypeId(@NotNull
String typeId);
public String getUrl();
@NotNull public Builder setUrl(@NotNull
String url);
```

## BrowserOperatingSystemMapping

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/BrowserOperatingSystemMapping.java)

### BrowserOperatingSystemMapping

```java
public BrowserOperatingSystemMapping(int browserId, int operatingSystemId);
public int getBrowserId();
public int getOperatingSystemId();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
@jakarta.validation.constraints.NotNull public BrowserOperatingSystemMapping build();
public int getBrowserId();
@jakarta.validation.constraints.NotNull public Builder setBrowserId(@jakarta.validation.constraints.NotNull
String browserId);
@jakarta.validation.constraints.NotNull public Builder setBrowserId(int browserId);
public int getOperatingSystemId();
@jakarta.validation.constraints.NotNull public Builder setOperatingSystemId(@jakarta.validation.constraints.NotNull
String operatingSystemId);
@jakarta.validation.constraints.NotNull public Builder setOperatingSystemId(int operatingSystemId);
```

## BrowserPattern

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/BrowserPattern.java)

### BrowserPattern

```java
public BrowserPattern(int id, @jakarta.validation.constraints.NotNull
Pattern pattern, int position);
@SuppressWarnings("Duplicates") @Override public int compareTo(BrowserPattern other);
@Override public int getId();
@Override public Pattern getPattern();
@Override public int getPosition();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
@jakarta.validation.constraints.NotNull public BrowserPattern build();
@jakarta.validation.constraints.NotNull public Builder setId(@jakarta.validation.constraints.NotNull
String id);
@jakarta.validation.constraints.NotNull public Builder setId(int id);
@jakarta.validation.constraints.NotNull public Builder setPerlRegularExpression(@jakarta.validation.constraints.NotNull
String regex);
@jakarta.validation.constraints.NotNull public Builder setPattern(@jakarta.validation.constraints.NotNull
Pattern pattern);
@jakarta.validation.constraints.NotNull public Builder setPosition(@jakarta.validation.constraints.NotNull
String position);
@jakarta.validation.constraints.NotNull public Builder setPosition(int position);
```

## BrowserType

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/BrowserType.java)

### BrowserType

```java
public BrowserType(int id, @jakarta.validation.constraints.NotNull
String name);
@Override public int getId();
public String getName();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
@jakarta.validation.constraints.NotNull public BrowserType build();
@jakarta.validation.constraints.NotNull public Builder setId(@jakarta.validation.constraints.NotNull
String id);
@jakarta.validation.constraints.NotNull public Builder setId(int id);
@jakarta.validation.constraints.NotNull public Builder setName(@jakarta.validation.constraints.NotNull
String name);
```

## Device

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/Device.java)

### Device

```java
public Device(@jakarta.validation.constraints.NotNull
String name, int id, @jakarta.validation.constraints.NotNull
Category category, @jakarta.validation.constraints.NotNull
String icon, @jakarta.validation.constraints.NotNull
String infoUrl, @jakarta.validation.constraints.NotNull
SortedSet<DevicePattern> patterns);
@jakarta.validation.constraints.NotNull public Category getCategory();
@jakarta.validation.constraints.NotNull public String getIcon();
@Override public int getId();
@jakarta.validation.constraints.NotNull public String getInfoUrl();
@jakarta.validation.constraints.NotNull public String getName();
@jakarta.validation.constraints.NotNull public SortedSet<DevicePattern> getPatterns();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
public Builder();
public Builder(@jakarta.validation.constraints.NotNull
Device device);
@jakarta.validation.constraints.NotNull public Device build();
@jakarta.validation.constraints.NotNull public Builder copy();
public String getIcon();
@jakarta.validation.constraints.NotNull public Builder setIcon(@jakarta.validation.constraints.NotNull
String icon);
public int getId();
@jakarta.validation.constraints.NotNull public Builder setId(@jakarta.validation.constraints.NotNull
String id);
@jakarta.validation.constraints.NotNull public Builder setId(int id);
public String getInfoUrl();
@jakarta.validation.constraints.NotNull public Builder setInfoUrl(@jakarta.validation.constraints.NotNull
String infoUrl);
public String getName();
@jakarta.validation.constraints.NotNull public Builder setName(@jakarta.validation.constraints.NotNull
String name);
public SortedSet<DevicePattern> getPatterns();
@jakarta.validation.constraints.NotNull public Builder setPatterns(@jakarta.validation.constraints.NotNull
SortedSet<DevicePattern> patterns);
```

## DevicePattern

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/DevicePattern.java)

### DevicePattern

```java
public DevicePattern(int id, @jakarta.validation.constraints.NotNull
Pattern pattern, int position);
@Override public int compareTo(DevicePattern other);
@Override public int getId();
@Override public Pattern getPattern();
@Override public int getPosition();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
@jakarta.validation.constraints.NotNull public DevicePattern build();
@jakarta.validation.constraints.NotNull public Builder setId(@jakarta.validation.constraints.NotNull
String id);
@jakarta.validation.constraints.NotNull public Builder setId(int id);
@jakarta.validation.constraints.NotNull public Builder setPerlRegularExpression(@jakarta.validation.constraints.NotNull
String regex);
@jakarta.validation.constraints.NotNull public Builder setPattern(@jakarta.validation.constraints.NotNull
Pattern pattern);
@jakarta.validation.constraints.NotNull public Builder setPosition(@jakarta.validation.constraints.NotNull
String position);
@jakarta.validation.constraints.NotNull public Builder setPosition(int position);
```

## Identifiable

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/Identifiable.java)

### Identifiable

```java
int getId();
```

## OperatingSystem

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/OperatingSystem.java)

### OperatingSystem

```java
public OperatingSystem(int id, @jakarta.validation.constraints.NotNull
String name, @jakarta.validation.constraints.NotNull
String family, @jakarta.validation.constraints.NotNull
String infoUrl, @jakarta.validation.constraints.NotNull
SortedSet<OperatingSystemPattern> patterns, @jakarta.validation.constraints.NotNull
String producer, @jakarta.validation.constraints.NotNull
String producerUrl, @jakarta.validation.constraints.NotNull
String url, @jakarta.validation.constraints.NotNull
String icon);
public void copyTo(@jakarta.validation.constraints.NotNull
UserAgent.Builder builder);
@jakarta.validation.constraints.NotNull public String getFamily();
@jakarta.validation.constraints.NotNull public String getIcon();
@Override public int getId();
@jakarta.validation.constraints.NotNull public String getInfoUrl();
@jakarta.validation.constraints.NotNull public String getName();
@jakarta.validation.constraints.NotNull public SortedSet<OperatingSystemPattern> getPatterns();
@jakarta.validation.constraints.NotNull public String getProducer();
@jakarta.validation.constraints.NotNull public String getProducerUrl();
@jakarta.validation.constraints.NotNull public String getUrl();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
public Builder();
protected Builder(@jakarta.validation.constraints.NotNull
Builder builder);
public Builder(@jakarta.validation.constraints.NotNull
OperatingSystem operatingSystem);
@jakarta.validation.constraints.NotNull public Builder addPatterns(@jakarta.validation.constraints.NotNull
Set<OperatingSystemPattern> patterns);
@jakarta.validation.constraints.NotNull public OperatingSystem build();
@jakarta.validation.constraints.NotNull public OperatingSystem.Builder copy();
@jakarta.validation.constraints.NotNull public String getFamily();
@jakarta.validation.constraints.NotNull public Builder setFamily(@jakarta.validation.constraints.NotNull
String family);
@jakarta.validation.constraints.NotNull public String getIcon();
@jakarta.validation.constraints.NotNull public Builder setIcon(@jakarta.validation.constraints.NotNull
String icon);
public int getId();
@jakarta.validation.constraints.NotNull public Builder setId(@jakarta.validation.constraints.NotNull
String id);
@jakarta.validation.constraints.NotNull public Builder setId(int id);
@jakarta.validation.constraints.NotNull public String getInfoUrl();
@jakarta.validation.constraints.NotNull public Builder setInfoUrl(@jakarta.validation.constraints.NotNull
String infoUrl);
@jakarta.validation.constraints.NotNull public String getName();
@jakarta.validation.constraints.NotNull public Builder setName(@jakarta.validation.constraints.NotNull
String name);
@jakarta.validation.constraints.NotNull public SortedSet<OperatingSystemPattern> getPatterns();
@jakarta.validation.constraints.NotNull public Builder setPatterns(@jakarta.validation.constraints.NotNull
SortedSet<OperatingSystemPattern> patterns);
@jakarta.validation.constraints.NotNull public String getProducer();
@jakarta.validation.constraints.NotNull public Builder setProducer(@jakarta.validation.constraints.NotNull
String producer);
@jakarta.validation.constraints.NotNull public String getProducerUrl();
@jakarta.validation.constraints.NotNull public Builder setProducerUrl(@jakarta.validation.constraints.NotNull
String producerUrl);
@jakarta.validation.constraints.NotNull public String getUrl();
@jakarta.validation.constraints.NotNull public Builder setUrl(@jakarta.validation.constraints.NotNull
String url);
```

## OperatingSystemPattern

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/OperatingSystemPattern.java)

### OperatingSystemPattern

```java
public OperatingSystemPattern(int id, @jakarta.validation.constraints.NotNull
Pattern pattern, int position);
@Override public int compareTo(OperatingSystemPattern other);
@Override public int getId();
@Override public Pattern getPattern();
@Override public int getPosition();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
public OperatingSystemPattern build();
public Builder setId(@jakarta.validation.constraints.NotNull
String id);
public Builder setId(int id);
public Builder setPerlRegularExpression(@jakarta.validation.constraints.NotNull
String regex);
public Builder setPattern(@jakarta.validation.constraints.NotNull
Pattern pattern);
public Builder setPosition(@jakarta.validation.constraints.NotNull
String position);
public Builder setPosition(int position);
```

## OrderedPattern

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/OrderedPattern.java)

### OrderedPattern

```java
@jakarta.validation.constraints.NotNull Pattern getPattern();
int getPosition();
```

## Robot

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/domain/Robot.java)

### Robot

```java
public static final String TYPENAME;
public Robot(int id, @jakarta.validation.constraints.NotNull
String name, @jakarta.validation.constraints.NotNull
UserAgentFamily family, @jakarta.validation.constraints.NotNull
String familyName, @jakarta.validation.constraints.NotNull
String infoUrl, @jakarta.validation.constraints.NotNull
String producer, @jakarta.validation.constraints.NotNull
String producerUrl, @jakarta.validation.constraints.NotNull
String userAgentString, @jakarta.validation.constraints.NotNull
String icon);
public void copyTo(@jakarta.validation.constraints.NotNull
UserAgent.Builder builder);
@jakarta.validation.constraints.NotNull public UserAgentFamily getFamily();
@jakarta.validation.constraints.NotNull public String getFamilyName();
@jakarta.validation.constraints.NotNull public String getIcon();
@Override public int getId();
@jakarta.validation.constraints.NotNull public String getInfoUrl();
@jakarta.validation.constraints.NotNull public String getName();
@jakarta.validation.constraints.NotNull public String getProducer();
@jakarta.validation.constraints.NotNull public String getProducerUrl();
@jakarta.validation.constraints.NotNull public String getUserAgentString();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

### Builder

```java
public Builder();
public Builder(@jakarta.validation.constraints.NotNull
Robot robot);
@jakarta.validation.constraints.NotNull public Robot build();
@jakarta.validation.constraints.NotNull public Builder setFamilyName(@jakarta.validation.constraints.NotNull
String familyName);
@jakarta.validation.constraints.NotNull public Builder setIcon(@jakarta.validation.constraints.NotNull
String icon);
@jakarta.validation.constraints.NotNull public Builder setId(int id);
@jakarta.validation.constraints.NotNull public Builder setId(@jakarta.validation.constraints.NotNull
String id);
@jakarta.validation.constraints.NotNull public Builder setInfoUrl(@jakarta.validation.constraints.NotNull
String infoUrl);
@jakarta.validation.constraints.NotNull public Builder setName(@jakarta.validation.constraints.NotNull
String name);
@jakarta.validation.constraints.NotNull public Builder setProducer(@jakarta.validation.constraints.NotNull
String producer);
@jakarta.validation.constraints.NotNull public Builder setProducerUrl(@jakarta.validation.constraints.NotNull
String producerUrl);
@jakarta.validation.constraints.NotNull public Builder setUserAgentString(@jakarta.validation.constraints.NotNull
String userAgentString);
```

## IdentifiableComparator

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/IdentifiableComparator.java)

### IdentifiableComparator

```java
public static final IdentifiableComparator INSTANCE;
@Override public int compareType(Identifiable o1, Identifiable o2);
```

## OrderedPatternComparator

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/OrderedPatternComparator.java)

### OrderedPatternComparator

```java
@Override public int compareType(T o1, T o2);
```

## XmlDataHandler

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/data/XmlDataHandler.java)

### XmlDataHandler

```java
public XmlDataHandler(@jakarta.validation.constraints.NotNull
DataBuilder builder);
public boolean hasError();
public boolean hasWarning();
@Override public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException;
@Override public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException;
@Override public void endElement(String uri, String localName, String tagName) throws SAXException;
@Override public void characters(char[] ch, int start, int length);
@Override public void warning(SAXParseException e) throws SAXException;
@Override public void error(SAXParseException e) throws SAXException;
@Override public void fatalError(SAXParseException e) throws SAXException;
protected static void logParsingIssue(String prefix, SAXParseException e);
```

### Tag

```java
public static final Tag BROWSER;
public static final Tag BROWSER_ID;
public static final Tag BROWSER_INFO_URL;
public static final Tag BROWSER_OS_MAPPING;
public static final Tag BROWSER_PATTERN;
public static final Tag BROWSER_TYPE;
public static final Tag BROWSER_TYPE_ID;
public static final Tag COMPANY;
public static final Tag COMPANY_URL;
public static final Tag DEVICE;
public static final Tag DEVICE_ID;
public static final Tag DEVICE_INFO_URL;
public static final Tag DEVICE_PATTERN;
public static final Tag DEVICES;
public static final Tag DEVICES_PATTERN;
public static final Tag FAMILY;
public static final Tag ICON;
public static final Tag ID;
public static final Tag NAME;
public static final Tag OPERATING_SYSTEM;
public static final Tag OPERATING_SYSTEM_ID;
public static final Tag OPERATING_SYSTEM_INFO_URL;
public static final Tag OPERATING_SYSTEM_PATTERN;
public static final Tag PATTERN_ORDER;
public static final Tag PATTERN_REGEX;
public static final Tag ROBOT;
public static final Tag ROBOT_INFO_URL;
public static final Tag URL;
public static final Tag USERAGENT;
public static final Tag VERSION;
public static Tag evaluate(@jakarta.validation.constraints.NotNull
String tagName);
@jakarta.validation.constraints.NotNull public String getTagName();
public static boolean isBrowserOsMappingTag(String tagName);
public static boolean isBrowserPatternTag(String tagName);
public static boolean isBrowserTag(String tagName);
public static boolean isBrowserTypeTag(String tagName);
public static boolean isDevicePatternTag(String tagName);
public static boolean isDeviceTag(String tagName);
public static boolean isIdTag(String tagName);
public static boolean isOperatingSystemPatternTag(String tagName);
public static boolean isOperatingSystemTag(String tagName);
public static boolean isRobotTag(String tagName);
```

## Closeables

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/Closeables.java)

### Closeables

```java
public static void close(Closeable closeable, boolean swallowIOException) throws IOException;
public static void closeAndConvert(Closeable closeable, boolean swallowIOException);
```

## CompareNullSafe

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/CompareNullSafe.java)

### CompareNullSafe

```java
public static int compareInt(int a, int b);
@Override public int compare(T o1, T o2);
public abstract int compareType(T o1, T o2);
```

## DaemonThreadFactory

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/DaemonThreadFactory.java)

### DaemonThreadFactory

```java
public DaemonThreadFactory(@jakarta.validation.constraints.NotNull
String threadName);
@Override public Thread newThread(@jakarta.validation.constraints.NotNull
Runnable runnable);
```

## ExecutorServices

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/ExecutorServices.java)

### ExecutorServices

```java
public static final long SHUTDOWN_DURATION;
public static ExecutorService createBackgroundExecutor();
public static ScheduledExecutorService createScheduler();
public static void shutdownAll();
public static void shutdown(@jakarta.validation.constraints.NotNull
ExecutorService executorService);
public static void shutdown(@jakarta.validation.constraints.NotNull
ExecutorService executorService, long duration, @jakarta.validation.constraints.NotNull
TimeUnit unit);
```

## FileUtil

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/FileUtil.java)

### FileUtil

```java
public static boolean isEmpty(File file, Charset charset) throws IOException;
```

## NaturalOrderComparator

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/NaturalOrderComparator.java)

### NaturalOrderComparator

```java
@Override public int compare(Object o1, Object o2);
public static final int compareNatural(String s1, String s2);
```

## RegularExpressionConverter

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/RegularExpressionConverter.java)

### RegularExpressionConverter

```java
public static String convertPatternToPerlRegex(@jakarta.validation.constraints.NotNull
Pattern pattern);
public static Pattern convertPerlRegexToPattern(@jakarta.validation.constraints.NotNull
String regex);
public static Pattern convertPerlRegexToPattern(@jakarta.validation.constraints.NotNull
String regex, @jakarta.validation.constraints.NotNull
boolean faultTolerant);
```

### Flag

```java
public static final Flag CANON_EQ;
public static final Flag CASE_INSENSITIVE;
public static final Flag COMMENTS;
public static final Flag DOTALL;
public static final Flag LITERAL;
public static final Flag MULTILINE;
public static final Flag UNICODE_CASE;
public static final Flag UNIX_LINES;
public static int convertToBitmask(@jakarta.validation.constraints.NotNull
Collection<Flag> flags);
public int getNumber();
public static String convertToModifiers(@jakarta.validation.constraints.NotNull
Collection<Flag> flags);
public char getCharacter();
public static Flag evaluateByNumber(int flag);
@jakarta.validation.constraints.NotNull public static Set<Flag> parse(int bitmask);
public static Set<Flag> parse(@jakarta.validation.constraints.NotNull
String modifiers);
public static Flag evaluateByCharacter(char flag);
```

## UrlUtil

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/internal/util/UrlUtil.java)

### UrlUtil

```java
public static URL build(@jakarta.validation.constraints.NotNull
String url);
public static String read(@jakarta.validation.constraints.NotNull
URL url, @jakarta.validation.constraints.NotNull
Charset charset) throws IOException;
public static InputStream open(@jakarta.validation.constraints.NotNull
URL url);
public static URL toUrl(@jakarta.validation.constraints.NotNull
File file);
```

## OperatingSystem

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/OperatingSystem.java)

### OperatingSystem

```java
public static final OperatingSystem EMPTY;
public OperatingSystem(@jakarta.validation.constraints.NotNull
OperatingSystemFamily family, @jakarta.validation.constraints.NotNull
String familyName, @jakarta.validation.constraints.NotNull
String icon, @jakarta.validation.constraints.NotNull
String name, @jakarta.validation.constraints.NotNull
String producer, @jakarta.validation.constraints.NotNull
String producerUrl, @jakarta.validation.constraints.NotNull
String url, @jakarta.validation.constraints.NotNull
VersionNumber versionNumber);
@Override public OperatingSystemFamily getFamily();
@Override public String getFamilyName();
@Override public String getIcon();
@Override public String getName();
@Override public String getProducer();
@Override public String getProducerUrl();
@Override public String getUrl();
@Override public VersionNumber getVersionNumber();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@Override public String toString();
```

## OperatingSystemFamily

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/OperatingSystemFamily.java)

### OperatingSystemFamily

```java
public static final OperatingSystemFamily AIX;
public static final OperatingSystemFamily AROS;
public static final OperatingSystemFamily AMIGA_OS;
public static final OperatingSystemFamily ANDROID;
public static final OperatingSystemFamily BSD;
public static final OperatingSystemFamily BADA;
public static final OperatingSystemFamily BEOS;
public static final OperatingSystemFamily CHROME_OS;
public static final OperatingSystemFamily DANGEROS;
public static final OperatingSystemFamily FIREFOX_OS;
public static final OperatingSystemFamily HPUX;
public static final OperatingSystemFamily HAIKU;
public static final OperatingSystemFamily IRIX;
public static final OperatingSystemFamily INFERNO_OS;
public static final OperatingSystemFamily JVM;
public static final OperatingSystemFamily LINUX;
public static final OperatingSystemFamily MAC_OS;
public static final OperatingSystemFamily MAEMO;
public static final OperatingSystemFamily MEEGO;
public static final OperatingSystemFamily MINIX;
public static final OperatingSystemFamily OPENVMS;
public static final OperatingSystemFamily OS_X;
public static final OperatingSystemFamily MORPHOS;
public static final OperatingSystemFamily NINTENDO;
public static final OperatingSystemFamily OS_2;
public static final OperatingSystemFamily PALM_OS;
public static final OperatingSystemFamily PLAYSTATION_VITA;
public static final OperatingSystemFamily QNX;
public static final OperatingSystemFamily RISC_OS;
public static final OperatingSystemFamily BREW;
public static final OperatingSystemFamily BLACKBERRY_OS;
public static final OperatingSystemFamily SAILFISH_OS;
public static final OperatingSystemFamily SOLARIS;
public static final OperatingSystemFamily SYLLABLE;
public static final OperatingSystemFamily SYMBIAN;
public static final OperatingSystemFamily TIZEN;
public static final OperatingSystemFamily WII_OS;
public static final OperatingSystemFamily WINDOWS;
public static final OperatingSystemFamily XROSSMEDIABAR;
public static final OperatingSystemFamily IOS;
public static final OperatingSystemFamily WEBOS;
public static final OperatingSystemFamily UNKNOWN;
public static OperatingSystemFamily evaluate(@jakarta.validation.constraints.NotNull
String family);
protected static OperatingSystemFamily evaluateByName(@jakarta.validation.constraints.NotNull
String family);
protected static OperatingSystemFamily evaluateByPattern(@jakarta.validation.constraints.NotNull
String family);
@jakarta.validation.constraints.NotNull public String getName();
@jakarta.validation.constraints.NotNull public Pattern getPattern();
```

## AbstractUserAgentStringParser

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/parser/AbstractUserAgentStringParser.java)

### AbstractUserAgentStringParser

```java
@Override public String getDataVersion();
protected abstract DataStore getDataStore();
@Override public UserAgent parse(String userAgent);
@Override public void shutdown();
```

## UpdatingUserAgentStringParserImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/parser/UpdatingUserAgentStringParserImpl.java)

### UpdatingUserAgentStringParserImpl

```java
public static final long DEFAULT_UPDATE_INTERVAL;
public UpdatingUserAgentStringParserImpl(RefreshableDataStore store);
public long getUpdateInterval();
public void setUpdateInterval(long updateInterval);
@Override public void shutdown();
```

## UserAgentStringParserImpl

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/parser/UserAgentStringParserImpl.java)

### UserAgentStringParserImpl

```java
public UserAgentStringParserImpl(@jakarta.validation.constraints.NotNull
T store);
@jakarta.validation.constraints.NotNull @Override protected T getDataStore();
```

## ReadableDeviceCategory

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/ReadableDeviceCategory.java)

### ReadableDeviceCategory

```java
@jakarta.validation.constraints.NotNull Category getCategory();
@jakarta.validation.constraints.NotNull String getIcon();
@jakarta.validation.constraints.NotNull String getInfoUrl();
@jakarta.validation.constraints.NotNull String getName();
```

### Category

```java
public static final Category GAME_CONSOLE;
public static final Category OTHER;
public static final Category PDA;
public static final Category PERSONAL_COMPUTER;
public static final Category SMART_TV;
public static final Category SMARTPHONE;
public static final Category TABLET;
public static final Category UNKNOWN;
public static final Category WEARABLE_COMPUTER;
public static Category evaluate(@jakarta.validation.constraints.NotNull
String categoryName);
@jakarta.validation.constraints.NotNull public String getName();
```

## ReadableOperatingSystem

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/ReadableOperatingSystem.java)

### ReadableOperatingSystem

```java
@jakarta.validation.constraints.NotNull OperatingSystemFamily getFamily();
@jakarta.validation.constraints.NotNull String getFamilyName();
@jakarta.validation.constraints.NotNull String getIcon();
@jakarta.validation.constraints.NotNull String getName();
@jakarta.validation.constraints.NotNull String getProducer();
@jakarta.validation.constraints.NotNull String getProducerUrl();
@jakarta.validation.constraints.NotNull String getUrl();
@jakarta.validation.constraints.NotNull VersionNumber getVersionNumber();
```

## ReadableUserAgent

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/ReadableUserAgent.java)

### ReadableUserAgent

```java
@jakarta.validation.constraints.NotNull ReadableDeviceCategory getDeviceCategory();
@jakarta.validation.constraints.NotNull UserAgentFamily getFamily();
@jakarta.validation.constraints.NotNull String getIcon();
@jakarta.validation.constraints.NotNull String getName();
@jakarta.validation.constraints.NotNull OperatingSystem getOperatingSystem();
@jakarta.validation.constraints.NotNull String getProducer();
@jakarta.validation.constraints.NotNull String getProducerUrl();
@jakarta.validation.constraints.NotNull UserAgentType getType();
@jakarta.validation.constraints.NotNull String getTypeName();
@jakarta.validation.constraints.NotNull String getUrl();
@jakarta.validation.constraints.NotNull VersionNumber getVersionNumber();
```

## ReadableVersionNumber

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/ReadableVersionNumber.java)

### ReadableVersionNumber

```java
@jakarta.validation.constraints.NotNull String getBugfix();
@jakarta.validation.constraints.NotNull String getExtension();
@jakarta.validation.constraints.NotNull List<String> getGroups();
@jakarta.validation.constraints.NotNull String getMajor();
@jakarta.validation.constraints.NotNull String getMinor();
@jakarta.validation.constraints.NotNull String toVersionString();
```

## UserAgent

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/UserAgent.java)

### UserAgent

```java
public static final UserAgent EMPTY;
public UserAgent(@jakarta.validation.constraints.NotNull
DeviceCategory deviceType, @jakarta.validation.constraints.NotNull
UserAgentFamily family, @jakarta.validation.constraints.NotNull
String icon, @jakarta.validation.constraints.NotNull
String name, @jakarta.validation.constraints.NotNull
OperatingSystem operatingSystem, @jakarta.validation.constraints.NotNull
String producer, @jakarta.validation.constraints.NotNull
String producerUrl, @jakarta.validation.constraints.NotNull
UserAgentType type, @jakarta.validation.constraints.NotNull
String typeName, @jakarta.validation.constraints.NotNull
String url, @jakarta.validation.constraints.NotNull
VersionNumber versionNumber);
@Override public int hashCode();
@Override public boolean equals(Object obj);
@jakarta.validation.constraints.NotNull @Override public String toString();
@jakarta.validation.constraints.NotNull @Override public DeviceCategory getDeviceCategory();
@Override public UserAgentFamily getFamily();
@Override public String getIcon();
@Override public String getName();
@Override public OperatingSystem getOperatingSystem();
@Override public String getProducer();
@Override public String getProducerUrl();
@Override public UserAgentType getType();
@Override public String getTypeName();
@Override public String getUrl();
@Override public VersionNumber getVersionNumber();
```

### Builder

```java
public Builder();
public Builder(@jakarta.validation.constraints.NotNull
String userAgentString);
@jakarta.validation.constraints.NotNull public UserAgent build();
public String getUserAgentString();
@jakarta.validation.constraints.NotNull public Builder setUserAgentString(@jakarta.validation.constraints.NotNull
String userAgentString);
@jakarta.validation.constraints.NotNull public Builder setOperatingSystem(@jakarta.validation.constraints.NotNull
OperatingSystem operatingSystem);
@Override public DeviceCategory getDeviceCategory();
@jakarta.validation.constraints.NotNull public Builder setDeviceCategory(@jakarta.validation.constraints.NotNull
DeviceCategory deviceCategory);
@Override public UserAgentFamily getFamily();
@Override public String getIcon();
@Override public String getName();
@Override public OperatingSystem getOperatingSystem();
@Override public String getProducer();
@Override public String getProducerUrl();
@Override public UserAgentType getType();
@Override public String getTypeName();
@Override public String getUrl();
@Override public VersionNumber getVersionNumber();
@jakarta.validation.constraints.NotNull public Builder setFamily(@jakarta.validation.constraints.NotNull
UserAgentFamily family);
@jakarta.validation.constraints.NotNull public Builder setIcon(@jakarta.validation.constraints.NotNull
String icon);
@jakarta.validation.constraints.NotNull public Builder setName(@jakarta.validation.constraints.NotNull
String name);
@jakarta.validation.constraints.NotNull public Builder setOperatingSystem(@jakarta.validation.constraints.NotNull
ReadableOperatingSystem os);
@jakarta.validation.constraints.NotNull public Builder setProducer(@jakarta.validation.constraints.NotNull
String producer);
@jakarta.validation.constraints.NotNull public Builder setProducerUrl(@jakarta.validation.constraints.NotNull
String producerUrl);
@jakarta.validation.constraints.NotNull public Builder setType(@jakarta.validation.constraints.NotNull
UserAgentType type);
@jakarta.validation.constraints.NotNull public Builder setTypeName(@jakarta.validation.constraints.NotNull
String typeName);
@jakarta.validation.constraints.NotNull public Builder setUrl(@jakarta.validation.constraints.NotNull
String url);
@jakarta.validation.constraints.NotNull public Builder setVersionNumber(@jakarta.validation.constraints.NotNull
VersionNumber versionNumber);
```

## UserAgentFamily

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/UserAgentFamily.java)

### UserAgentFamily

```java
public static final UserAgentFamily UNKNOWN;
public static final UserAgentFamily _192_COMAGENT;
public static final UserAgentFamily _2BONE_LINKCHECKER;
public static final UserAgentFamily _50_NU;
public static final UserAgentFamily _80LEGS;
public static final UserAgentFamily A1_SITEMAP_GENERATOR;
public static final UserAgentFamily AB_APACHE_BENCH;
public static final UserAgentFamily ABBY;
public static final UserAgentFamily ABILON;
public static final UserAgentFamily ABOLIMBA;
public static final UserAgentFamily ABOUNDEXBOT;
public static final UserAgentFamily ABOUTUSBOT;
public static final UserAgentFamily ABRAVE_SPIDER;
public static final UserAgentFamily ABROWSE;
public static final UserAgentFamily ACCELOBOT;
public static final UserAgentFamily ACCOONA_AI_AGENT;
public static final UserAgentFamily ACOO_BROWSER;
public static final UserAgentFamily ACOONBOT;
public static final UserAgentFamily ACORN;
public static final UserAgentFamily ACTIVEXPERTS_NETWORK_MONITOR;
public static final UserAgentFamily ADDTHIS_COM;
public static final UserAgentFamily ADOBE_AIR_RUNTIME;
public static final UserAgentFamily ADRESSENDEUTSCHLAND_DE;
public static final UserAgentFamily ADSBOT_GOOGLE;
public static final UserAgentFamily AHREFSBOT;
public static final UserAgentFamily AIHITBOT;
public static final UserAgentFamily AIPPIE;
public static final UserAgentFamily AIRMAIL;
public static final UserAgentFamily AKREGATOR;
public static final UserAgentFamily AKULA;
public static final UserAgentFamily ALIENFORCE;
public static final UserAgentFamily ALMADEN;
public static final UserAgentFamily AMAGIT_COM;
public static final UserAgentFamily AMAYA;
public static final UserAgentFamily AMAZON_SILK;
public static final UserAgentFamily AMFIBIBOT;
public static final UserAgentFamily AMIBOT;
public static final UserAgentFamily AMIGA_AWEB;
public static final UserAgentFamily AMIGA_VOYAGER;
public static final UserAgentFamily ANDROID_BROWSER;
public static final UserAgentFamily ANEMONE;
public static final UserAgentFamily ANONYMOUSE_ORG;
public static final UserAgentFamily ANTBOT;
public static final UserAgentFamily ANW_HTMLCHECKER;
public static final UserAgentFamily ANW_LOADCONTROL;
public static final UserAgentFamily AOL_EXPLORER;
public static final UserAgentFamily APACHE_INTERNAL_DUMMY_CONNECTION;
public static final UserAgentFamily APACHE_SYNAPSE;
public static final UserAgentFamily APERCITE;
public static final UserAgentFamily APORTWORM;
public static final UserAgentFamily APPLE_MAIL;
public static final UserAgentFamily APPLE_PUBSUB;
public static final UserAgentFamily ARACHNODE_NET;
public static final UserAgentFamily ARCHIVE_ORG_BOT;
public static final UserAgentFamily ARORA;
public static final UserAgentFamily ASAHA_SEARCH_ENGINE_TURKEY;
public static final UserAgentFamily ASK_JEEVES_TEOMA;
public static final UserAgentFamily ATOMIC_EMAIL_HUNTER;
public static final UserAgentFamily ATOMIC_WEB_BROWSER;
public static final UserAgentFamily AVANT_BROWSER;
public static final UserAgentFamily AVANTGO;
public static final UserAgentFamily AWASU;
public static final UserAgentFamily AXEL;
public static final UserAgentFamily BABALOOSPIDER;
public static final UserAgentFamily BACKLINKCRAWLER;
public static final UserAgentFamily BAD_NEIGHBORHOOD;
public static final UserAgentFamily BAIDU_BROWSER;
public static final UserAgentFamily BAIDUSPIDER;
public static final UserAgentFamily BANSHEE;
public static final UserAgentFamily BARCA;
public static final UserAgentFamily BAYPUP;
public static final UserAgentFamily BDFETCH;
public static final UserAgentFamily BEAMRISE;
public static final UserAgentFamily BECOMEBOT;
public static final UserAgentFamily BEONEX;
public static final UserAgentFamily BIGSEARCH_CA;
public static final UserAgentFamily BINGBOT;
public static final UserAgentFamily BINGET;
public static final UserAgentFamily BITLYBOT;
public static final UserAgentFamily BIWEC;
public static final UserAgentFamily BIXO;
public static final UserAgentFamily BIXOLABS;
public static final UserAgentFamily BLACKBERRY_BROWSER;
public static final UserAgentFamily BLACKBIRD;
public static final UserAgentFamily BLACKHAWK;
public static final UserAgentFamily BLAIZ_BEE;
public static final UserAgentFamily BLAZER;
public static final UserAgentFamily BLEKKOBOT;
public static final UserAgentFamily BLINKACRAWLER;
public static final UserAgentFamily BLOGBRIDGE;
public static final UserAgentFamily BLOGGSI;
public static final UserAgentFamily BLOGLINES;
public static final UserAgentFamily BLOGPULSE;
public static final UserAgentFamily BNF_FR_BOT;
public static final UserAgentFamily BOITHO_COM_DC;
public static final UserAgentFamily BOLT;
public static final UserAgentFamily BOOKDOG;
public static final UserAgentFamily BOOKMARKTRACKER;
public static final UserAgentFamily BOT_PGE_CHLOOE_COM;
public static final UserAgentFamily BOTMOBI;
public static final UserAgentFamily BOTONPARADE;
public static final UserAgentFamily BOXXE;
public static final UserAgentFamily BROWNRECLUSE;
public static final UserAgentFamily BROWSERSHOTS;
public static final UserAgentFamily BROWSEX;
public static final UserAgentFamily BROWZAR;
public static final UserAgentFamily BTBOT;
public static final UserAgentFamily BUNJALLOO;
public static final UserAgentFamily BUTTERFLY;
public static final UserAgentFamily BUZZRANKINGBOT;
public static final UserAgentFamily CAMINO;
public static final UserAgentFamily CAMONTSPIDER;
public static final UserAgentFamily CAREERBOT;
public static final UserAgentFamily CARET_NAIL;
public static final UserAgentFamily CASTABOT;
public static final UserAgentFamily CATCHBOT;
public static final UserAgentFamily CAZOODLEBOT;
public static final UserAgentFamily CCBOT;
public static final UserAgentFamily CCUBEE;
public static final UserAgentFamily CHANGEDETECTION;
public static final UserAgentFamily CHARLOTTE;
public static final UserAgentFamily CHARON;
public static final UserAgentFamily CHECKBOT;
public static final UserAgentFamily CHESHIRE;
public static final UserAgentFamily CHILKAT_HTTP_NET;
public static final UserAgentFamily CHROME;
public static final UserAgentFamily CHROME_MOBILE;
public static final UserAgentFamily CHROMIUM;
public static final UserAgentFamily CITY4YOU;
public static final UserAgentFamily CITYREVIEW;
public static final UserAgentFamily CJB_NET_PROXY;
public static final UserAgentFamily CLAWS_MAIL_GTKHTML2_PLUGIN;
public static final UserAgentFamily CLIGOOROBOT;
public static final UserAgentFamily COAST;
public static final UserAgentFamily COCCOC;
public static final UserAgentFamily COLUMBUS;
public static final UserAgentFamily COMBINE;
public static final UserAgentFamily COMETBIRD;
public static final UserAgentFamily COMODO_DRAGON;
public static final UserAgentFamily COMPSPYBOT;
public static final UserAgentFamily CONKEROR;
public static final UserAgentFamily CONVERACRAWLER;
public static final UserAgentFamily COOLNOVO;
public static final UserAgentFamily COPYRIGHT_SHERIFF;
public static final UserAgentFamily COREPLAYER;
public static final UserAgentFamily CORPUSCRAWLER;
public static final UserAgentFamily COVARIO_IDS;
public static final UserAgentFamily CPG_DRAGONFLY_RSS_MODULE;
public static final UserAgentFamily CRAWLER4J;
public static final UserAgentFamily CRAZY_BROWSER;
public static final UserAgentFamily CSCI_B659;
public static final UserAgentFamily CSE_HTML_VALIDATOR;
public static final UserAgentFamily CURL;
public static final UserAgentFamily CYBERDUCK;
public static final UserAgentFamily CYNTHIA;
public static final UserAgentFamily D_PLUS;
public static final UserAgentFamily DATAFOUNTAINS;
public static final UserAgentFamily DATAPARKSEARCH;
public static final UserAgentFamily DAUMOA;
public static final UserAgentFamily DBLBOT;
public static final UserAgentFamily DCPBOT;
public static final UserAgentFamily DEALGATES_BOT;
public static final UserAgentFamily DEEPNET_EXPLORER;
public static final UserAgentFamily DEL_ICIO_US_THUMBNAILS;
public static final UserAgentFamily DELL_WEB_MONITOR;
public static final UserAgentFamily DEMETER;
public static final UserAgentFamily DEPSPID;
public static final UserAgentFamily DESKBROWSE;
public static final UserAgentFamily DILLO;
public static final UserAgentFamily DISCOBOT;
public static final UserAgentFamily DKIMREPBOT;
public static final UserAgentFamily DNS_DIGGER_EXPLORER;
public static final UserAgentFamily DOCZILLA;
public static final UserAgentFamily DOLPHIN;
public static final UserAgentFamily DOMAINDB;
public static final UserAgentFamily DOOBLE;
public static final UserAgentFamily DORIS;
public static final UserAgentFamily DOT_TK_SPIDER;
public static final UserAgentFamily DOTBOT;
public static final UserAgentFamily DOTSEMANTIC;
public static final UserAgentFamily DOWNLOADSTUDIO;
public static final UserAgentFamily DRIPFEEDBOT;
public static final UserAgentFamily DUCKDUCKBOT;
public static final UserAgentFamily DUCKDUCKPREVIEW;
public static final UserAgentFamily E_SOCIETYROBOT;
public static final UserAgentFamily EASYBIB_AUTOCITE;
public static final UserAgentFamily ECAIRN_GRABBER;
public static final UserAgentFamily EDBROWSE;
public static final UserAgentFamily EDI;
public static final UserAgentFamily EDISTERBOT;
public static final UserAgentFamily EGOTHOR;
public static final UserAgentFamily EJUPITER_COM;
public static final UserAgentFamily ELEMENT_BROWSER;
public static final UserAgentFamily ELINKS;
public static final UserAgentFamily ENABOT;
public static final UserAgentFamily ENIGMA_BROWSER;
public static final UserAgentFamily ENTERPRISE_SEARCH;
public static final UserAgentFamily ENVOLK;
public static final UserAgentFamily EPIC;
public static final UserAgentFamily EPIPHANY;
public static final UserAgentFamily ESPIAL_TV_BROWSER;
public static final UserAgentFamily EUDORA;
public static final UserAgentFamily EURIPBOT;
public static final UserAgentFamily EUROBOT;
public static final UserAgentFamily EVENTGURUBOT;
public static final UserAgentFamily EVENTMACHINE;
public static final UserAgentFamily EVOLUTION_CAMEL_STREAM;
public static final UserAgentFamily EVRINID;
public static final UserAgentFamily EXABOT;
public static final UserAgentFamily EXACTSEEK;
public static final UserAgentFamily EZOOMS;
public static final UserAgentFamily FACEBOOKEXTERNALHIT;
public static final UserAgentFamily FACTBOT;
public static final UserAgentFamily FAIRSHARE;
public static final UserAgentFamily FALCONSBOT;
public static final UserAgentFamily FAST_ENTERPRISE_CRAWLER;
public static final UserAgentFamily FAST_METAWEB_CRAWLER;
public static final UserAgentFamily FASTLADDER_FEEDFETCHER;
public static final UserAgentFamily FAUBOT;
public static final UserAgentFamily FAVORSTARBOT;
public static final UserAgentFamily FEED_FIND;
public static final UserAgentFamily FEED_VIEWER;
public static final UserAgentFamily FEEDCATBOT;
public static final UserAgentFamily FEEDDEMON;
public static final UserAgentFamily FEEDFETCHER_GOOGLE;
public static final UserAgentFamily FEEDFINDER_BLOGGZ_SE;
public static final UserAgentFamily FEEDPARSER;
public static final UserAgentFamily FEEDVALIDATOR;
public static final UserAgentFamily FINDEXA_CRAWLER;
public static final UserAgentFamily FINDLINKS;
public static final UserAgentFamily FIREBIRD;
public static final UserAgentFamily FIREFOX;
public static final UserAgentFamily FIREFOX_BONECHO;
public static final UserAgentFamily FIREFOX_GRANPARADISO;
public static final UserAgentFamily FIREFOX_LORENTZ;
public static final UserAgentFamily FIREFOX_MINEFIELD;
public static final UserAgentFamily FIREFOX_NAMOROKA;
public static final UserAgentFamily FIREFOX_SHIRETOKO;
public static final UserAgentFamily FIREWEB_NAVIGATOR;
public static final UserAgentFamily FLATLAND_INDUSTRIES_WEB_SPIDER;
public static final UserAgentFamily FLATLANDBOT;
public static final UserAgentFamily FLIGHTDECKREPORTSBOT;
public static final UserAgentFamily FLIPBOARDPROXY;
public static final UserAgentFamily FLOCK;
public static final UserAgentFamily FLOCKE_BOT;
public static final UserAgentFamily FLUID;
public static final UserAgentFamily FLYCAST;
public static final UserAgentFamily FOLLOWSITE_BOT;
public static final UserAgentFamily FOOBAR2000;
public static final UserAgentFamily FOOOOO_WEB_VIDEO_CRAWL;
public static final UserAgentFamily FORSCHUNGSPORTAL;
public static final UserAgentFamily FRANCIS;
public static final UserAgentFamily FUNAMBOL_MOZILLA_SYNC_CLIENT;
public static final UserAgentFamily FUNAMBOL_OUTLOOK_SYNC_CLIENT;
public static final UserAgentFamily FUNNELBACK;
public static final UserAgentFamily FURLBOT;
public static final UserAgentFamily FYBERSPIDER;
public static final UserAgentFamily G2CRAWLER;
public static final UserAgentFamily GAISBOT;
public static final UserAgentFamily GALEON;
public static final UserAgentFamily GALLENT_SEARCH_SPIDER;
public static final UserAgentFamily GARLIKCRAWLER;
public static final UserAgentFamily GCMAIL;
public static final UserAgentFamily GENIEBOT;
public static final UserAgentFamily GEONABOT;
public static final UserAgentFamily GETRIGHT;
public static final UserAgentFamily GIANT;
public static final UserAgentFamily GIGABOT;
public static final UserAgentFamily GINGERCRAWLER;
public static final UserAgentFamily GIRAFABOT;
public static final UserAgentFamily GLOBALMOJO;
public static final UserAgentFamily GMAIL_IMAGE_PROXY;
public static final UserAgentFamily GNOMEVFS;
public static final UserAgentFamily GO_BROWSER;
public static final UserAgentFamily GOFORITBOT;
public static final UserAgentFamily GOLDENPOD;
public static final UserAgentFamily GOM_PLAYER;
public static final UserAgentFamily GONZO;
public static final UserAgentFamily GOOGLE_APP_ENGINE;
public static final UserAgentFamily GOOGLE_EARTH;
public static final UserAgentFamily GOOGLE_FRIEND_CONNECT;
public static final UserAgentFamily GOOGLE_LISTEN;
public static final UserAgentFamily GOOGLE_RICH_SNIPPETS_TESTING_TOOL;
public static final UserAgentFamily GOOGLE_WIRELESS_TRANSCODER;
public static final UserAgentFamily GOOGLEBOT;
public static final UserAgentFamily GOOGLEBOT_MOBILE;
public static final UserAgentFamily GPODDER;
public static final UserAgentFamily GRAPESHOTCRAWLER;
public static final UserAgentFamily GREATNEWS;
public static final UserAgentFamily GREENBROWSER;
public static final UserAgentFamily GREGARIUS;
public static final UserAgentFamily GSITECRAWLER;
public static final UserAgentFamily GSTREAMER;
public static final UserAgentFamily GURUJIBOT;
public static final UserAgentFamily HAILOOBOT;
public static final UserAgentFamily HATENASCREENSHOT;
public static final UserAgentFamily HEARTRAILS_CAPTURE;
public static final UserAgentFamily HERITRIX;
public static final UserAgentFamily HIDDENMARKET;
public static final UserAgentFamily HOLMES;
public static final UserAgentFamily HOLMESBOT;
public static final UserAgentFamily HOMETAGS;
public static final UserAgentFamily HOOWWWER;
public static final UserAgentFamily HOSTTRACKER_COM;
public static final UserAgentFamily HOTJAVA;
public static final UserAgentFamily HT_DIG;
public static final UserAgentFamily HTML2JPG;
public static final UserAgentFamily HTMLAYOUT;
public static final UserAgentFamily HTMLPARSER;
public static final UserAgentFamily HTTP_NAGIOS_PLUGIN;
public static final UserAgentFamily HTTP_REQUEST2;
public static final UserAgentFamily HTTRACK;
public static final UserAgentFamily HUAWEISYMANTECSPIDER;
public static final UserAgentFamily HV3;
public static final UserAgentFamily HYDRA_BROWSER;
public static final UserAgentFamily IA_ARCHIVER;
public static final UserAgentFamily IASKSPIDER;
public static final UserAgentFamily IBROWSE;
public static final UserAgentFamily ICAB;
public static final UserAgentFamily ICATCHER;
public static final UserAgentFamily ICC_CRAWLER;
public static final UserAgentFamily ICE_BROWSER;
public static final UserAgentFamily ICEAPE;
public static final UserAgentFamily ICECAT;
public static final UserAgentFamily ICEDRAGON;
public static final UserAgentFamily ICEWEASEL;
public static final UserAgentFamily ICF_SITE_CRAWLER;
public static final UserAgentFamily ICHIRO;
public static final UserAgentFamily ICJOBS;
public static final UserAgentFamily IE;
public static final UserAgentFamily IE_MOBILE;
public static final UserAgentFamily IE_RSS_READER;
public static final UserAgentFamily IGETTER;
public static final UserAgentFamily IGOOMAP;
public static final UserAgentFamily ILSEBOT;
public static final UserAgentFamily ILTROVATORE;
public static final UserAgentFamily ILTROVATORE_SETACCIO;
public static final UserAgentFamily IMBOT;
public static final UserAgentFamily INDY_LIBRARY;
public static final UserAgentFamily INFLUENCEBOT;
public static final UserAgentFamily INFOCIOUSBOT;
public static final UserAgentFamily INFOHELFER;
public static final UserAgentFamily INTERNETSEER;
public static final UserAgentFamily INTERNETSURFBOARD;
public static final UserAgentFamily IPSELONBOT;
public static final UserAgentFamily IRIDER;
public static final UserAgentFamily IRLBOT;
public static final UserAgentFamily IRON;
public static final UserAgentFamily ISILOX;
public static final UserAgentFamily ISILOXC;
public static final UserAgentFamily ITUNES;
public static final UserAgentFamily IVIDEO;
public static final UserAgentFamily IXR_LIB;
public static final UserAgentFamily JADYNAVE;
public static final UserAgentFamily JADYNAVEBOT;
public static final UserAgentFamily JAKARTA_COMMONS_HTTPCLIENT;
public static final UserAgentFamily JAMBOT;
public static final UserAgentFamily JAMCAST;
public static final UserAgentFamily JASMINE;
public static final UserAgentFamily JAVA;
public static final UserAgentFamily JIKESPIDER;
public static final UserAgentFamily JOB_ROBOTER_SPIDER;
public static final UserAgentFamily JOBO;
public static final UserAgentFamily JS_KIT_ECHO;
public static final UserAgentFamily JUST_CRAWLER;
public static final UserAgentFamily JYXOBOT;
public static final UserAgentFamily K_MELEON;
public static final UserAgentFamily K_NINJA;
public static final UserAgentFamily KAKLE_BOT;
public static final UserAgentFamily KALOOGA;
public static final UserAgentFamily KAPIKO;
public static final UserAgentFamily KARNEVAL_BOT;
public static final UserAgentFamily KAZEHAKASE;
public static final UserAgentFamily KEYWENBOT;
public static final UserAgentFamily KEYWORDDENSITYROBOT;
public static final UserAgentFamily KINDLE_BROWSER;
public static final UserAgentFamily KIRIX_STRATA;
public static final UserAgentFamily KKMAN;
public static final UserAgentFamily KLONDIKE;
public static final UserAgentFamily KONGULO;
public static final UserAgentFamily KONQUEROR;
public static final UserAgentFamily KRETRIEVE;
public static final UserAgentFamily KRUGLE;
public static final UserAgentFamily KSIBOT;
public static final UserAgentFamily KYLO;
public static final UserAgentFamily L_WEBIS;
public static final UserAgentFamily LAPOZZBOT;
public static final UserAgentFamily LARBIN;
public static final UserAgentFamily LBROWSER;
public static final UserAgentFamily LEECHCRAFT;
public static final UserAgentFamily LEMURWEBCRAWLER;
public static final UserAgentFamily LEXXEBOT;
public static final UserAgentFamily LFTP;
public static final UserAgentFamily LG_WEB_BROWSER;
public static final UserAgentFamily LIBSOUP;
public static final UserAgentFamily LIBWWW_PERL;
public static final UserAgentFamily LIFEREA;
public static final UserAgentFamily LIJIT;
public static final UserAgentFamily LINGUABOT;
public static final UserAgentFamily LINGUEE_BOT;
public static final UserAgentFamily LINK_VALET_ONLINE;
public static final UserAgentFamily LINKAIDER;
public static final UserAgentFamily LINKBACKPLUGIN_FOR_LACONICA;
public static final UserAgentFamily LINKCHECKER;
public static final UserAgentFamily LINKDEX_COM;
public static final UserAgentFamily LINKEXAMINER;
public static final UserAgentFamily LINKS;
public static final UserAgentFamily LINKSMANAGER_BOT;
public static final UserAgentFamily LINKWALKER;
public static final UserAgentFamily LIVEDOOR_SCREENSHOT;
public static final UserAgentFamily LMSPIDER;
public static final UserAgentFamily LOBO;
public static final UserAgentFamily LOLIFOX;
public static final UserAgentFamily LOTUS_NOTES;
public static final UserAgentFamily LUNASCAPE;
public static final UserAgentFamily LWP_SIMPLE;
public static final UserAgentFamily LYNX;
public static final UserAgentFamily MADFOX;
public static final UserAgentFamily MAGPIE_CRAWLER;
public static final UserAgentFamily MAGPIERSS;
public static final UserAgentFamily MAHITI_CRAWLER;
public static final UserAgentFamily MAIL_RU;
public static final UserAgentFamily MAPLE_BROWSER;
public static final UserAgentFamily MAXTHON;
public static final UserAgentFamily MECHANIZE;
public static final UserAgentFamily MEGATEXT;
public static final UserAgentFamily METAGENERATORCRAWLER;
public static final UserAgentFamily METAJOBBOT;
public static final UserAgentFamily METAMOJICRAWLER;
public static final UserAgentFamily METASPINNER;
public static final UserAgentFamily METATAGROBOT;
public static final UserAgentFamily METAURI;
public static final UserAgentFamily MIA_BOT;
public static final UserAgentFamily MICROB;
public static final UserAgentFamily MICROSOFT_EDGE;
public static final UserAgentFamily MICROSOFT_EDGE_MOBILE;
public static final UserAgentFamily MICROSOFT_OFFICE_EXISTENCE_DISCOVERY;
public static final UserAgentFamily MICROSOFT_WEBDAV_CLIENT;
public static final UserAgentFamily MIDORI;
public static final UserAgentFamily MINI_BROWSER;
public static final UserAgentFamily MINIMO;
public static final UserAgentFamily MINIRANK;
public static final UserAgentFamily MIRO;
public static final UserAgentFamily MJ12BOT;
public static final UserAgentFamily MLBOT;
public static final UserAgentFamily MNOGOSEARCH;
public static final UserAgentFamily MOATBOT;
public static final UserAgentFamily MOBA_CRAWLER;
public static final UserAgentFamily MOBILE_FIREFOX;
public static final UserAgentFamily MOBILE_SAFARI;
public static final UserAgentFamily MOJEEKBOT;
public static final UserAgentFamily MOTORICERCA_ROBOTS_TXT_CHECKER;
public static final UserAgentFamily MOTOROLA_INTERNET_BROWSER;
public static final UserAgentFamily MOZDEX;
public static final UserAgentFamily MOZILLA;
public static final UserAgentFamily MP3BOT;
public static final UserAgentFamily MPLAYER;
public static final UserAgentFamily MPLAYER2;
public static final UserAgentFamily MQBOT;
public static final UserAgentFamily MSNBOT;
public static final UserAgentFamily MSRBOT;
public static final UserAgentFamily MUCOMMANDER;
public static final UserAgentFamily MULTI_BROWSER_XP;
public static final UserAgentFamily MULTICRAWLER;
public static final UserAgentFamily MULTIPAGE_VALIDATOR;
public static final UserAgentFamily MULTIZILLA;
public static final UserAgentFamily MY_INTERNET_BROWSER;
public static final UserAgentFamily MYFAMILYBOT;
public static final UserAgentFamily NAJDI_SI;
public static final UserAgentFamily NAVERBOT;
public static final UserAgentFamily NAVISSOBOT;
public static final UserAgentFamily NCSA_MOSAIC;
public static final UserAgentFamily NERDBYNATURE_BOT;
public static final UserAgentFamily NESTREADER;
public static final UserAgentFamily NETBOX;
public static final UserAgentFamily NETCAPTOR;
public static final UserAgentFamily NETCRAFTSURVEYAGENT;
public static final UserAgentFamily NETESTATE_CRAWLER;
public static final UserAgentFamily NETFRONT;
public static final UserAgentFamily NETFRONT_MOBILE_CONTENT_VIEWER;
public static final UserAgentFamily NETINTELLIGENCE_LIVEASSESSMENT;
public static final UserAgentFamily NETNEWSWIRE;
public static final UserAgentFamily NETPOSITIVE;
public static final UserAgentFamily NETRESEARCHSERVER;
public static final UserAgentFamily NETSCAPE_NAVIGATOR;
public static final UserAgentFamily NETSEER;
public static final UserAgentFamily NETSURF;
public static final UserAgentFamily NETVIBES_FEED_READER;
public static final UserAgentFamily NETWHATCRAWLER;
public static final UserAgentFamily NEWSBEUTER;
public static final UserAgentFamily NEWSBREAK;
public static final UserAgentFamily NEWSFOX;
public static final UserAgentFamily NEWSGATORONLINE;
public static final UserAgentFamily NEXTGENSEARCHBOT;
public static final UserAgentFamily NEXTTHING_ORG;
public static final UserAgentFamily NFREADER;
public static final UserAgentFamily NG;
public static final UserAgentFamily NG_SEARCH;
public static final UserAgentFamily NIGMA_RU;
public static final UserAgentFamily NIMBLECRAWLER;
public static final UserAgentFamily NINESKY;
public static final UserAgentFamily NINTENDO;
public static final UserAgentFamily NODESTACKBOT;
public static final UserAgentFamily NOKIA_SYNCML_CLIENT;
public static final UserAgentFamily NOKIA_WEB_BROWSER;
public static final UserAgentFamily NOVELL_BORDERMANAGER;
public static final UserAgentFamily NOYONA;
public static final UserAgentFamily NPBOT;
public static final UserAgentFamily NUHK;
public static final UserAgentFamily NUSEARCH_SPIDER;
public static final UserAgentFamily NUTCH;
public static final UserAgentFamily NWORM;
public static final UserAgentFamily NYMESIS;
public static final UserAgentFamily OBIGO;
public static final UserAgentFamily OBOT;
public static final UserAgentFamily OCELLI;
public static final UserAgentFamily OFF_BY_ONE;
public static final UserAgentFamily OFFLINE_EXPLORER;
public static final UserAgentFamily OMEA_READER;
public static final UserAgentFamily OMNIEXPLORER_BOT;
public static final UserAgentFamily OMNIWEB;
public static final UserAgentFamily ONETSZUKAJ;
public static final UserAgentFamily OPENBOT;
public static final UserAgentFamily OPENCALAISSEMANTICPROXY;
public static final UserAgentFamily OPENINDEXSPIDER;
public static final UserAgentFamily OPENWAVE_MOBILE_BROWSER;
public static final UserAgentFamily OPERA;
public static final UserAgentFamily OPERA_MINI;
public static final UserAgentFamily OPERA_MOBILE;
public static final UserAgentFamily ORBITER;
public static final UserAgentFamily ORCA;
public static final UserAgentFamily OREGANO;
public static final UserAgentFamily ORGBYBOT;
public static final UserAgentFamily OSOBOT;
public static final UserAgentFamily OUTLOOK_2007;
public static final UserAgentFamily OUTLOOK_2010;
public static final UserAgentFamily OUTLOOK_2013;
public static final UserAgentFamily OWB;
public static final UserAgentFamily OWSBOT;
public static final UserAgentFamily P3P_VALIDATOR;
public static final UserAgentFamily PAGE_VERIFIER;
public static final UserAgentFamily PAGE2RSS;
public static final UserAgentFamily PAGEBITESHYPERBOT;
public static final UserAgentFamily PAGEPEEKER;
public static final UserAgentFamily PALE_MOON;
public static final UserAgentFamily PALM_PRE_WEB_BROWSER;
public static final UserAgentFamily PANSCIENT_WEB_CRAWLER;
public static final UserAgentFamily PAPARAZZI;
public static final UserAgentFamily PAPERLIBOT;
public static final UserAgentFamily PARCHBOT;
public static final UserAgentFamily PATRIOTT;
public static final UserAgentFamily PATTERN;
public static final UserAgentFamily PEAR_HTTP_REQUEST;
public static final UserAgentFamily PEEW;
public static final UserAgentFamily PERCBOTSPIDER;
public static final UserAgentFamily PHASEOUT;
public static final UserAgentFamily PHOENIX;
public static final UserAgentFamily PHP;
public static final UserAgentFamily PHP_LINK_CHECKER;
public static final UserAgentFamily PHP_OPENID_LIBRARY;
public static final UserAgentFamily PHPCRAWL;
public static final UserAgentFamily PINGDOM_COM_BOT;
public static final UserAgentFamily PIXRAY_SEEKER;
public static final UserAgentFamily PLEX_MEDIA_CENTER;
public static final UserAgentFamily PLUKKIE;
public static final UserAgentFamily POCKET_TUNES;
public static final UserAgentFamily POCOMAIL;
public static final UserAgentFamily PODKICKER;
public static final UserAgentFamily POE_COMPONENT_CLIENT_HTTP;
public static final UserAgentFamily POGODAK_CO_YU;
public static final UserAgentFamily POLARIS;
public static final UserAgentFamily POLIXEA_DE;
public static final UserAgentFamily POMPOS;
public static final UserAgentFamily POSTBOX;
public static final UserAgentFamily POSTERUS;
public static final UserAgentFamily POSTPOST;
public static final UserAgentFamily POWERMARKS;
public static final UserAgentFamily PRISM;
public static final UserAgentFamily PROCOGBOT;
public static final UserAgentFamily PROXIMIC;
public static final UserAgentFamily PRTG_NETWORK_MONITOR;
public static final UserAgentFamily PS_VITA_BROWSER;
public static final UserAgentFamily PSBOT;
public static final UserAgentFamily PTD_CRAWLER;
public static final UserAgentFamily PUBLIC_RADIO_PLAYER;
public static final UserAgentFamily PYCURL;
public static final UserAgentFamily PYTHON_REQUESTS;
public static final UserAgentFamily PYTHON_URLLIB;
public static final UserAgentFamily PYTHON_WEBCHECKER;
public static final UserAgentFamily QIRINA_HURDLER;
public static final UserAgentFamily QQBROWSER;
public static final UserAgentFamily QSEERO;
public static final UserAgentFamily QTWEB;
public static final UserAgentFamily QUALIDATOR_COM_BOT;
public static final UserAgentFamily QUANTCASTBOT;
public static final UserAgentFamily QUICKOBOT;
public static final UserAgentFamily QUICKTIME;
public static final UserAgentFamily QUPZILLA;
public static final UserAgentFamily R6_BOT;
public static final UserAgentFamily RADAR_BOT;
public static final UserAgentFamily RADIO_DOWNLOADER;
public static final UserAgentFamily RANKURBOT;
public static final UserAgentFamily REDBOT;
public static final UserAgentFamily REEDER;
public static final UserAgentFamily REKONQ;
public static final UserAgentFamily REL_LINK_CHECKER_LITE;
public static final UserAgentFamily RETAWQ;
public static final UserAgentFamily ROBO_CRAWLER;
public static final UserAgentFamily ROBOTS_TESTER;
public static final UserAgentFamily ROBOZILLA;
public static final UserAgentFamily ROCKMELT;
public static final UserAgentFamily ROME_LIBRARY;
public static final UserAgentFamily RONZOOBOT;
public static final UserAgentFamily RSS_BANDIT;
public static final UserAgentFamily RSS_MENU;
public static final UserAgentFamily RSS_POPPER;
public static final UserAgentFamily RSS_RADIO;
public static final UserAgentFamily RSSMICRO_COM;
public static final UserAgentFamily RSSOWL;
public static final UserAgentFamily RUKY_ROBOTER;
public static final UserAgentFamily RYOUKO;
public static final UserAgentFamily RYZECRAWLER;
public static final UserAgentFamily SAAYAA_EXPLORER;
public static final UserAgentFamily SAFARI;
public static final UserAgentFamily SAFARI_RSS_READER;
public static final UserAgentFamily SAGE;
public static final UserAgentFamily SAI_CRAWLER;
public static final UserAgentFamily SANSZBOT;
public static final UserAgentFamily SBIDER;
public static final UserAgentFamily SBSEARCH;
public static final UserAgentFamily SCARLETT;
public static final UserAgentFamily SCHIBSTEDSOKBOT;
public static final UserAgentFamily SCOLLSPIDER;
public static final UserAgentFamily SCOOTER;
public static final UserAgentFamily SCOUTJET;
public static final UserAgentFamily SEAMONKEY;
public static final UserAgentFamily SEARCH_ENGINE_WORLD_ROBOTS_TXT_VALIDATOR;
public static final UserAgentFamily SEARCH_KUMKIE_COM;
public static final UserAgentFamily SEARCH17BOT;
public static final UserAgentFamily SEMAGER;
public static final UserAgentFamily SEMC_BROWSER;
public static final UserAgentFamily SEMRUSHBOT;
public static final UserAgentFamily SENSIS_WEB_CRAWLER;
public static final UserAgentFamily SEODAT;
public static final UserAgentFamily SEOENGBOT;
public static final UserAgentFamily SEOKICKS_ROBOT;
public static final UserAgentFamily SETOOZBOT;
public static final UserAgentFamily SEZNAM_RSS_READER;
public static final UserAgentFamily SEZNAM_WAP_PROXY;
public static final UserAgentFamily SEZNAMBOT;
public static final UserAgentFamily SHARPREADER;
public static final UserAgentFamily SHELOB;
public static final UserAgentFamily SHIIRA;
public static final UserAgentFamily SHIM_CRAWLER;
public static final UserAgentFamily SHOPWIKI;
public static final UserAgentFamily SHOWYOUBOT;
public static final UserAgentFamily SHREDDER;
public static final UserAgentFamily SIEGE;
public static final UserAgentFamily SILK;
public static final UserAgentFamily SIMPLEPIE;
public static final UserAgentFamily SIRKETCE_BUSIVERSE;
public static final UserAgentFamily SISTRIX;
public static final UserAgentFamily SITEDOMAIN_BOT;
public static final UserAgentFamily SITEKIOSK;
public static final UserAgentFamily SITESUCKER;
public static final UserAgentFamily SKIPSTONE;
public static final UserAgentFamily SKREEMRBOT;
public static final UserAgentFamily SKYFIRE;
public static final UserAgentFamily SLEIPNIR;
public static final UserAgentFamily SLIMBOAT;
public static final UserAgentFamily SLIMBROWSER;
public static final UserAgentFamily SMART_APNOTI_COM_ROBOT;
public static final UserAgentFamily SNAP_COM;
public static final UserAgentFamily SNAPBOT;
public static final UserAgentFamily SNAPPY;
public static final UserAgentFamily SNIFFRSS;
public static final UserAgentFamily SNOOPY;
public static final UserAgentFamily SOGOU;
public static final UserAgentFamily SOGOU_EXPLORER;
public static final UserAgentFamily SOGOU_SPIDER;
public static final UserAgentFamily SONGBIRD;
public static final UserAgentFamily SOSOSPIDER;
public static final UserAgentFamily SPARROW;
public static final UserAgentFamily SPBOT;
public static final UserAgentFamily SPEEDY;
public static final UserAgentFamily SPICEBIRD;
public static final UserAgentFamily SPIDERLING;
public static final UserAgentFamily SPINN3R;
public static final UserAgentFamily SPOCK_CRAWLER;
public static final UserAgentFamily SPOKESPIDER;
public static final UserAgentFamily SPROOSE;
public static final UserAgentFamily SREVBOT;
public static final UserAgentFamily SSLBOT;
public static final UserAgentFamily STACKRAMBLER;
public static final UserAgentFamily STAINLESS;
public static final UserAgentFamily STATOOLSBOT;
public static final UserAgentFamily STEELER;
public static final UserAgentFamily STROKEBOT;
public static final UserAgentFamily SUBSTREAM;
public static final UserAgentFamily SUGGYBOT;
public static final UserAgentFamily SUMMER;
public static final UserAgentFamily SUNDANCE;
public static final UserAgentFamily SUNDIAL;
public static final UserAgentFamily SUNRISE;
public static final UserAgentFamily SUPERBOT;
public static final UserAgentFamily SURF;
public static final UserAgentFamily SURPHACE_SCOUT;
public static final UserAgentFamily SURVEYBOT;
public static final UserAgentFamily SWEBOT;
public static final UserAgentFamily SWIFTFOX;
public static final UserAgentFamily SWIFTWEASEL;
public static final UserAgentFamily SYGOLBOT;
public static final UserAgentFamily SYNOOBOT;
public static final UserAgentFamily SZUKACZ;
public static final UserAgentFamily SZUKANKOBOT;
public static final UserAgentFamily TAGOOBOT;
public static final UserAgentFamily TAPTUBOT;
public static final UserAgentFamily TEAR;
public static final UserAgentFamily TEASHARK;
public static final UserAgentFamily TECHNORATIBOT;
public static final UserAgentFamily TELEPORT_PRO;
public static final UserAgentFamily TENFOURFOX;
public static final UserAgentFamily TERAGRAMCRAWLER;
public static final UserAgentFamily TEXTRACTOR;
public static final UserAgentFamily THE_BAT;
public static final UserAgentFamily THEOPHRASTUS;
public static final UserAgentFamily THEWORLD_BROWSER;
public static final UserAgentFamily THUMBNAIL_CZ_ROBOT;
public static final UserAgentFamily THUMBSHOTS_BOT;
public static final UserAgentFamily THUMBSHOTS_DE_BOT;
public static final UserAgentFamily THUMBSHOTS_RU;
public static final UserAgentFamily THUNDERBIRD;
public static final UserAgentFamily TINEYE;
public static final UserAgentFamily TIZEN_BROWSER;
public static final UserAgentFamily TJUSIG;
public static final UserAgentFamily TOPICBOT;
public static final UserAgentFamily TOREAD_CRAWLER;
public static final UserAgentFamily TOUCHE;
public static final UserAgentFamily TRENDICTIONBOT;
public static final UserAgentFamily TRILEET_NEWSROOM;
public static final UserAgentFamily TT_EXPLORER;
public static final UserAgentFamily TULIP_CHAIN;
public static final UserAgentFamily TURNITINBOT;
public static final UserAgentFamily TUTORGIGBOT;
public static final UserAgentFamily TWENGABOT;
public static final UserAgentFamily TWICELER;
public static final UserAgentFamily TWIKLE;
public static final UserAgentFamily TYPHOEUS;
public static final UserAgentFamily UASLINKCHECKER;
public static final UserAgentFamily UC_BROWSER;
public static final UserAgentFamily ULTRABROWSER;
public static final UserAgentFamily UNISTERBOT;
public static final UserAgentFamily UNWINDFETCHOR;
public static final UserAgentFamily UPDATED;
public static final UserAgentFamily UPDOWNERBOT;
public static final UserAgentFamily UPTIMEDOG;
public static final UserAgentFamily UPTIMEROBOT;
public static final UserAgentFamily URLFAN_BOT;
public static final UserAgentFamily URLFILEBOT;
public static final UserAgentFamily URLGRABBER;
public static final UserAgentFamily USEJUMP;
public static final UserAgentFamily UZARD_WEB;
public static final UserAgentFamily UZBL;
public static final UserAgentFamily VAGABONDO;
public static final UserAgentFamily VALIDATOR_NU;
public static final UserAgentFamily VERASYS_2K;
public static final UserAgentFamily VERMUT;
public static final UserAgentFamily VESPA_CRAWLER;
public static final UserAgentFamily VIDEOSURF_BOT;
public static final UserAgentFamily VIRUS_DETECTOR;
public static final UserAgentFamily VIVALDI;
public static final UserAgentFamily VISBOT;
public static final UserAgentFamily VLC_MEDIA_PLAYER;
public static final UserAgentFamily VMBOT;
public static final UserAgentFamily VOID_BOT;
public static final UserAgentFamily VOILABOT;
public static final UserAgentFamily VONKEROR;
public static final UserAgentFamily VORTEX;
public static final UserAgentFamily VOYAGER;
public static final UserAgentFamily VUZE;
public static final UserAgentFamily VWBOT;
public static final UserAgentFamily W3C_CHECKLINK;
public static final UserAgentFamily W3C_CSS_VALIDATOR;
public static final UserAgentFamily W3C_MOBILEOK_CHECKER;
public static final UserAgentFamily W3C_VALIDATOR;
public static final UserAgentFamily W3M;
public static final UserAgentFamily WAPTIGER;
public static final UserAgentFamily WASALIVE_BOT;
public static final UserAgentFamily WATCHMOUSE;
public static final UserAgentFamily WBSEARCHBOT;
public static final UserAgentFamily WDG_CSSCHECK;
public static final UserAgentFamily WDG_PAGE_VALET;
public static final UserAgentFamily WDG_VALIDATOR;
public static final UserAgentFamily WEB_SNIFFER;
public static final UserAgentFamily WEBALTA_CRAWLER;
public static final UserAgentFamily WEBAROOBOT;
public static final UserAgentFamily WEBCOLLAGE;
public static final UserAgentFamily WEBCOPIER;
public static final UserAgentFamily WEBFETCH;
public static final UserAgentFamily WEBFS;
public static final UserAgentFamily WEBIAN_SHELL;
public static final UserAgentFamily WEBIMAGES;
public static final UserAgentFamily WEBINATORBOT;
public static final UserAgentFamily WEBMASTERCOFFEE;
public static final UserAgentFamily WEBNL;
public static final UserAgentFamily WEBRANKSPIDER;
public static final UserAgentFamily WEBRENDER;
public static final UserAgentFamily WEBSCOPE_CRAWLER;
public static final UserAgentFamily WEBSTRIPPER;
public static final UserAgentFamily WEBWATCH_ROBOT_TXT_CHECKER;
public static final UserAgentFamily WEBZIP;
public static final UserAgentFamily WECTAR;
public static final UserAgentFamily WELTWEITIMNETZ_BROWSER;
public static final UserAgentFamily WESEE_SEARCH;
public static final UserAgentFamily WGET;
public static final UserAgentFamily WHOISMINDBOT;
public static final UserAgentFamily WIKIOFEEDBOT;
public static final UserAgentFamily WIKIWIX_BOT;
public static final UserAgentFamily WILLOW_INTERNET_CRAWLER;
public static final UserAgentFamily WINAMP_FOR_ANDROID;
public static final UserAgentFamily WINDOWS_LIVE_MAIL;
public static final UserAgentFamily WINDOWS_MEDIA_PLAYER;
public static final UserAgentFamily WINHTTP;
public static final UserAgentFamily WINKBOT;
public static final UserAgentFamily WINPODDER;
public static final UserAgentFamily WINWAP;
public static final UserAgentFamily WINWEBBOT;
public static final UserAgentFamily WIRE;
public static final UserAgentFamily WKIOSK;
public static final UserAgentFamily WMCAI_ROBOT;
public static final UserAgentFamily WOKO;
public static final UserAgentFamily WORDPRESS_PINGBACK;
public static final UserAgentFamily WORIOBOT;
public static final UserAgentFamily WORLDWIDEWEB;
public static final UserAgentFamily WOSBROWSER;
public static final UserAgentFamily WOTBOX;
public static final UserAgentFamily WSANALYZER;
public static final UserAgentFamily WWW_FI_CRAWLER;
public static final UserAgentFamily WWW_MECHANIZE;
public static final UserAgentFamily WWWSTER;
public static final UserAgentFamily WYZO;
public static final UserAgentFamily X_SMILES;
public static final UserAgentFamily XALDON_WEBSPIDER;
public static final UserAgentFamily XBMC;
public static final UserAgentFamily XENU;
public static final UserAgentFamily XINE;
public static final UserAgentFamily XMARKSFETCH;
public static final UserAgentFamily XML_RPC_FOR_PHP;
public static final UserAgentFamily XML_RPC_FOR_RUBY;
public static final UserAgentFamily XML_SITEMAPS_GENERATOR;
public static final UserAgentFamily XMPLAY;
public static final UserAgentFamily YAANB;
public static final UserAgentFamily YACYBOT;
public static final UserAgentFamily YAHOO;
public static final UserAgentFamily YAHOO_LINK_PREVIEW;
public static final UserAgentFamily YAHOO_JAPAN;
public static final UserAgentFamily YAHOOFEEDSEEKER;
public static final UserAgentFamily YANDEX_BROWSER;
public static final UserAgentFamily YANDEXBOT;
public static final UserAgentFamily YANGA;
public static final UserAgentFamily YEAHREADER;
public static final UserAgentFamily YIOOPBOT;
public static final UserAgentFamily YODAOBOT;
public static final UserAgentFamily YOONO_BOT;
public static final UserAgentFamily YOUDAOBOT;
public static final UserAgentFamily YOWEDOBOT;
public static final UserAgentFamily YRSPIDER;
public static final UserAgentFamily ZACATEK_CZ;
public static final UserAgentFamily ZBROWSER;
public static final UserAgentFamily ZEND_HTTP_CLIENT;
public static final UserAgentFamily ZEUSBOT;
public static final UserAgentFamily ZIPZAP;
public static final UserAgentFamily ZOOKABOT;
public static final UserAgentFamily ZOOMSPIDER;
public static final UserAgentFamily ZYBORG;
@jakarta.validation.constraints.NotNull public static UserAgentFamily evaluate(@jakarta.validation.constraints.NotNull
String family);
@jakarta.validation.constraints.NotNull protected static UserAgentFamily evaluateByName(@jakarta.validation.constraints.NotNull
String family);
@jakarta.validation.constraints.NotNull protected static UserAgentFamily evaluateByPattern(@jakarta.validation.constraints.NotNull
String family);
@jakarta.validation.constraints.NotNull public String getName();
@jakarta.validation.constraints.NotNull public Pattern getPattern();
```

## UserAgentStringParser

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/UserAgentStringParser.java)

### UserAgentStringParser

```java
String getDataVersion();
ReadableUserAgent parse(String userAgent);
void shutdown();
```

## UserAgentType

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/UserAgentType.java)

### UserAgentType

```java
public static final UserAgentType BROWSER;
public static final UserAgentType EMAIL_CLIENT;
public static final UserAgentType FEED_READER;
public static final UserAgentType LIBRARY;
public static final UserAgentType MEDIAPLAYER;
public static final UserAgentType MOBILE_BROWSER;
public static final UserAgentType OFFLINE_BROWSER;
public static final UserAgentType OTHER;
public static final UserAgentType ROBOT;
public static final UserAgentType UNKNOWN;
public static final UserAgentType USERAGENT_ANONYMIZER;
public static final UserAgentType VALIDATOR;
public static final UserAgentType WAP_BROWSER;
public static UserAgentType evaluateByTypeName(@jakarta.validation.constraints.NotNull
String typeName);
@jakarta.validation.constraints.NotNull public String getName();
```

## VersionNumber

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/VersionNumber.java)

### VersionNumber

```java
public static final String EMPTY_EXTENSION;
public static final String EMPTY_GROUP;
public static final VersionNumber UNKNOWN;
public VersionNumber(@jakarta.validation.constraints.NotNull
List<String> groups);
public VersionNumber(@jakarta.validation.constraints.NotNull
List<String> groups, @jakarta.validation.constraints.NotNull
String extension);
public static List<String> replaceNullValueWithEmptyGroup(@jakarta.validation.constraints.NotNull
List<String> groups);
public VersionNumber(@jakarta.validation.constraints.NotNull
String major);
public VersionNumber(@jakarta.validation.constraints.NotNull
String major, @jakarta.validation.constraints.NotNull
String minor);
public VersionNumber(@jakarta.validation.constraints.NotNull
String major, @jakarta.validation.constraints.NotNull
String minor, @jakarta.validation.constraints.NotNull
String bugfix);
public VersionNumber(@jakarta.validation.constraints.NotNull
String major, @jakarta.validation.constraints.NotNull
String minor, @jakarta.validation.constraints.NotNull
String bugfix, @jakarta.validation.constraints.NotNull
String extension);
public static VersionNumber parseLastVersionNumber(@jakarta.validation.constraints.NotNull
String text);
public static VersionNumber parseOperatingSystemVersion(@jakarta.validation.constraints.NotNull
OperatingSystemFamily family, @jakarta.validation.constraints.NotNull
String userAgent);
public static VersionNumber parseVersion(@jakarta.validation.constraints.NotNull
String version);
@Override public int compareTo(ReadableVersionNumber other);
@Override public String getBugfix();
@Override public String getExtension();
@Override public List<String> getGroups();
@Override public String getMajor();
@Override public String getMinor();
@jakarta.validation.constraints.NotNull @Override public String toVersionString();
@Override public int hashCode();
@Override public boolean equals(Object obj);
@jakarta.validation.constraints.NotNull @Override public String toString();
```

## IniDataWriter

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/writer/IniDataWriter.java)

### IniDataWriter

```java
public static void write(@jakarta.validation.constraints.NotNull
Data data, @jakarta.validation.constraints.NotNull
OutputStream outputStream) throws IOException;
```

## XmlDataWriter

[Java source](https://github.com/GuicedEE/Services/blob/master/Libraries/uadetector-core/src/main/java/net/sf/uadetector/writer/XmlDataWriter.java)

### XmlDataWriter

```java
public static void write(@jakarta.validation.constraints.NotNull
Data data, @jakarta.validation.constraints.NotNull
OutputStream outputStream) throws ParserConfigurationException, TransformerException;
```
