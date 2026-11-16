# com.guicedee.modules.services:aopalliance-core

AOP Alliance

- Version line: `2.6.0` (verify publication separately).
- Packaging: `jar`.
- JPMS module: `aopalliance`.
- [Source POM](https://github.com/GuicedEE/Services/blob/master/Google/aop/pom.xml).
- [Reference guide](https://guicedee.com/reference/overview.md).

## JPMS descriptor

```java
module aopalliance {
    exports org.aopalliance.aop;
    exports org.aopalliance.intercept;
}
```

## Usage

This artifact integrates the dependencies listed above. Use their upstream public APIs and the JPMS descriptor; it does not imply a new GuicedEE facade. Consult the source POM for shading, relocations, service merging and native resources.

## Declared API reference

Java public/protected declarations and public interface methods are parsed with the JDK compiler. This includes implementation types; package exports determine JPMS accessibility. Inherited and generated members are not expanded. Kotlin adapters retain their source and comments.

## Advice

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/aop/Advice.java)

### Advice

```java
```

## AspectException

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/aop/AspectException.java)

### AspectException

```java
public AspectException(String str);
public AspectException(String str, Throwable t);
@Override public Throwable getCause();
@Override public String getMessage();
public String toString();
@Override public void printStackTrace();
@Override public void printStackTrace(PrintStream out);
@Override public void printStackTrace(PrintWriter out);
```

## ConstructorInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/ConstructorInterceptor.java)

### ConstructorInterceptor

```java
Object construct(ConstructorInvocation invocation) throws Throwable;
```

## ConstructorInvocation

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/ConstructorInvocation.java)

### ConstructorInvocation

```java
Constructor getConstructor();
```

## FieldAccess

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/FieldAccess.java)

### FieldAccess

```java
int READ;
int WRITE;
Field getField();
Object getValueToSet();
int getAccessType();
```

## FieldInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/FieldInterceptor.java)

### FieldInterceptor

```java
Object get(FieldAccess fieldRead) throws Throwable;
Object set(FieldAccess fieldWrite) throws Throwable;
```

## Interceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/Interceptor.java)

### Interceptor

```java
```

## Invocation

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/Invocation.java)

### Invocation

```java
Object[] getArguments();
```

## Joinpoint

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/Joinpoint.java)

### Joinpoint

```java
Object proceed() throws Throwable;
Object getThis();
AccessibleObject getStaticPart();
```

## MethodInterceptor

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/MethodInterceptor.java)

### MethodInterceptor

```java
Object invoke(MethodInvocation invocation) throws Throwable;
```

## MethodInvocation

[Java source](https://github.com/GuicedEE/Services/blob/master/Google/aop/src/main/java/org/aopalliance/intercept/MethodInvocation.java)

### MethodInvocation

```java
Method getMethod();
```
