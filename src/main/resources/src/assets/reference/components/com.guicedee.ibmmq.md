# com.guicedee:ibmmq

Provides annotation-driven IBM MQ integration for GuicedEE

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `com.guicedee.ibmmq`.
- [Source POM](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## Direct dependency declarations

Versions may be supplied by the BOM or properties in the linked POM; this is not a flattened dependency lock.

- `org.projectlombok:lombok`; scope `compile`.
- `com.guicedee.modules.services:com.ibm.mq.jakarta.client`; scope `compile`.
- `com.guicedee:client`; scope `compile`.
- `com.guicedee.modules.representations:json-representation`; scope `compile`.
- `jakarta.jms:jakarta.jms-api`; scope `compile`.
- `com.guicedee:inject`; scope `test`.
- `com.guicedee:vertx`; scope `compile`.
- `org.junit.jupiter:junit-jupiter-api`; scope `test`.
- `com.guicedee.modules.services:testcontainers`; scope `test`.

## JPMS descriptor

```java
import com.guicedee.client.services.lifecycle.IGuiceModule;
import com.guicedee.client.services.lifecycle.IGuicePostStartup;
import com.guicedee.client.services.lifecycle.IGuicePreDestroy;
import com.guicedee.client.services.lifecycle.IGuicePreStartup;
import com.guicedee.ibmmq.implementations.*;

module com.guicedee.ibmmq {
    requires com.guicedee.jsonrepresentation;
    exports com.guicedee.ibmmq;
    exports com.guicedee.ibmmq.implementations;

    requires transitive com.ibm.mq.jakarta;
    requires com.guicedee.vertx;
    requires com.guicedee.client;
    requires static lombok;

    requires io.github.classgraph;
    requires org.apache.commons.lang3;

    provides IGuicePostStartup with IBMMQPostStartup;
    provides IGuiceModule with IBMMQModule;
    provides IGuicePreStartup with IBMMQPreStartup;
    provides IGuicePreDestroy with IBMMQPreDestroy;

    opens com.guicedee.ibmmq to com.google.guice, tools.jackson.databind;
    opens com.guicedee.ibmmq.implementations to tools.jackson.databind, com.google.guice;
}
```

## Module guide

Source: [README.md](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/README.md). Historical version examples retain their original context.

# Guiced IBM MQ

Annotation-driven IBM MQ integration for GuicedEE using the IBM MQ JMS client.

## Quick Start

1. Add `com.guicedee:ibmmq` dependency
2. Define a connection on `package-info.java` or any class:
   ```java
   @IBMMQConnectionOptions(
       value = "my-connection",
       host = "localhost",
       port = 1414,
       queueManager = "QM1",
       channel = "DEV.APP.SVRCONN"
   )
   package com.example.messaging;
   ```
3. Create a consumer:
   ```java
   @IBMMQQueueDefinition("DEV.QUEUE.1")
   public class OrderConsumer implements IBMMQConsumer {
       @Override
       public void consume(Message message) {
           // process message
       }
   }
   ```
4. Inject a publisher:
   ```java
   @Inject @Named("DEV.QUEUE.1")
   private IBMMQPublisher publisher;
   ```
5. Configure `module-info.java`:
   ```java
   module my.app {
       requires com.guicedee.ibmmq;
       opens my.app.messaging to com.google.guice, com.guicedee.ibmmq;
   }
   ```

## Environment Variable Overrides

Every annotation attribute can be overridden at runtime:
- `IBMMQ_{NORMALIZED_NAME}_{PROPERTY}` — name-specific override
- `IBMMQ_{PROPERTY}` — global fallback

## License

Apache License 2.0

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## IBMMQConnectionOptions

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/IBMMQConnectionOptions.java)

### IBMMQConnectionOptions

```java
String value() default "default";
String host() default "localhost";
int port() default 1414;
String queueManager() default "";
String channel() default "DEV.APP.SVRCONN";
String user() default "";
String password() default "";
String applicationName() default "";
String transportType() default "client";
boolean sslEnabled() default false;
String sslCipherSuite() default "";
String sslPeerName() default "";
int connectionTimeout() default 0;
int ccsid() default 0;
int reconnectAttempts() default 3;
long reconnectIntervalMs() default 5000;
String clientId() default "";
```

## IBMMQConsumer

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/IBMMQConsumer.java)

### IBMMQConsumer

```java
void consume(Message message);
```

## IBMMQPublisher

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/IBMMQPublisher.java)

### IBMMQPublisher

```java
public IBMMQPublisher(ConnectionFactory connectionFactory, String destinationName, boolean isTopic, IBMMQQueueOptions queueOptions);
public void publish(String body);
public void publish(String body, String correlationId);
public void publish(byte[] body);
public String getDestinationName();
public void close();
```

## IBMMQQueueDefinition

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/IBMMQQueueDefinition.java)

### IBMMQQueueDefinition

```java
String value();
IBMMQQueueOptions options() default @IBMMQQueueOptions;
String connection() default "default";
```

## IBMMQQueueOptions

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/IBMMQQueueOptions.java)

### IBMMQQueueOptions

```java
boolean autoAck() default true;
boolean worker() default false;
int consumerCount() default 1;
boolean transacted() default false;
boolean autobind() default true;
long receiveTimeoutMs() default 1000;
boolean topic() default false;
String durableSubscription() default "";
String messageSelector() default "";
int persistence() default -1;
int priority() default -1;
long timeToLive() default 0;
```

## IBMMQModule

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/implementations/IBMMQModule.java)

### IBMMQModule

```java
@Override protected void configure();
public static ConnectionFactory createConnectionFactory(IBMMQConnectionOptions options) throws JMSException;
```

## IBMMQPostStartup

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/implementations/IBMMQPostStartup.java)

### IBMMQPostStartup

```java
@Override public List<Uni<Boolean>> postLoad();
@Override public Integer sortOrder();
```

## IBMMQPreDestroy

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/implementations/IBMMQPreDestroy.java)

### IBMMQPreDestroy

```java
@Override public void onDestroy();
@Override public Integer sortOrder();
```

## IBMMQPreStartup

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/implementations/IBMMQPreStartup.java)

### IBMMQPreStartup

```java
@Override public List<Future<Boolean>> onStartup();
@Override public Integer sortOrder();
```

## IBMMQQueueOptionsDefault

[Java source](https://github.com/GuicedEE/GuicedIBMMQ/blob/master/src/main/java/com/guicedee/ibmmq/implementations/IBMMQQueueOptionsDefault.java)

### IBMMQQueueOptionsDefault

```java
@Override public Class<? extends Annotation> annotationType();
@Override public boolean autoAck();
@Override public boolean worker();
@Override public int consumerCount();
@Override public boolean transacted();
@Override public boolean autobind();
@Override public long receiveTimeoutMs();
@Override public boolean topic();
@Override public String durableSubscription();
@Override public String messageSelector();
@Override public int persistence();
@Override public int priority();
@Override public long timeToLive();
@Override public boolean equals(Object obj);
@Override public int hashCode();
```
