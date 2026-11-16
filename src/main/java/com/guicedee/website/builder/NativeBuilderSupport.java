package com.guicedee.website.builder;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

/** Native starter content shared by the preview and ZIP generator. */
public final class NativeBuilderSupport {
    private NativeBuilderSupport() {}

    public static List<String> fields() {
        return List.of("runtimeTarget = 'jvm';",
                "nativeBuildSource = atob('" + encoded("NativeBuild.java.txt") + "');",
                "nativeBootstrapSource = atob('" + encoded("bootstrap-registration.txt") + "');",
                "nativeMetadataSource = atob('" + encoded("reachability-metadata.json") + "');",
                "nativeGuideSource = atob('" + encoded("NATIVE.md") + "');");
    }

    private static String encoded(String name) {
        try (var input = NativeBuilderSupport.class.getResourceAsStream("/native-starter/" + name)) {
            if (input == null) throw new IllegalStateException("Missing native starter " + name);
            return Base64.getEncoder().encodeToString(input.readAllBytes());
        } catch (IOException e) { throw new IllegalStateException(e); }
    }

    public static String methods() {
        return """
                isNative(): boolean { return this.runtimeTarget === 'native'; }

                getNativePomDeps(): string {
                    return this.isNative() ? `
                        <dependency><groupId>com.guicedee</groupId><artifactId>native-image</artifactId><version>2.6.0</version></dependency>
                        <dependency><groupId>org.graalvm.sdk</groupId><artifactId>nativeimage</artifactId><version>25.0.4.1</version><scope>provided</scope></dependency>` : '';
                }

                getNativeGradleDeps(): string {
                    return this.isNative() ? `
                    implementation("com.guicedee:native-image:2.6.0")
                    compileOnly("org.graalvm.sdk:nativeimage:25.0.4.1")` : '';
                }

                getNativeGradleTask(): string {
                    return this.isNative() ? `
                tasks.register<Copy>("copyNativeDependencies") {
                    from(configurations.runtimeClasspath)
                    into(layout.buildDirectory.dir("native-libs"))
                }` : '';
                }

                getNativeModuleInfo(): string {
                    return this.isNative() ? `
                    requires com.guicedee.nativeimage;
                    provides com.guicedee.client.services.IGuiceDiscovery with ${this.packageName}.ApplicationDiscovery;
                    provides com.guicedee.nativeimage.GuiceNativeConfiguration with ${this.packageName}.NativeConfiguration;
                ${this.hasHazelcast() ? '    opens ' + this.packageName + '.cluster to com.google.guice, com.guicedee.vertx;' : ''}` : '';
                }

                getNativeTypes(): string {
                    return this.hasHazelcast() ? this.packageName + '.cluster.DiscoveryPackage.class' : '';
                }

                getNativeDiscovery(): string {
                    return `package ${this.packageName};
                import com.guicedee.client.services.IGuiceDiscovery;
                import java.util.Collection;
                import java.util.List;
                public final class ApplicationDiscovery implements IGuiceDiscovery<ApplicationDiscovery> {
                    @Override public Collection<Class<?>> classes() {
                        return List.of(${this.getNativeTypes()});
                    }
                }
                `;
                }

                getNativeConfiguration(): string {
                    return `package ${this.packageName};
                import com.guicedee.nativeimage.GuiceNativeConfiguration;
                import com.guicedee.nativeimage.GuiceNativeRegistration;
                public final class NativeConfiguration implements GuiceNativeConfiguration {
                    @Override public void configure(GuiceNativeRegistration registration) {
                        ${this.nativeBootstrapSource}
                        registration.reflect(${this.getNativeTypes()});
                        // Add registration.intercept(...) for your application's intercepted methods.
                    }
                }
                `;
                }

                getNativeGuide(): string {
                    return this.nativeGuideSource.replaceAll('@ARTIFACT@', this.artifactId)
                        .replaceAll('@VERSION@', this.version).replaceAll('@MODULE@', this.artifactId.replace(/-/g, '.'))
                        .replaceAll('@MAIN@', this.packageName + '.Boot');
                }

                addNativeFiles(zip: any, base: string, pkgPath: string) {
                    if (!this.isNative()) return;
                    zip.file(base + '/NativeBuild.java', this.nativeBuildSource);
                    zip.file(base + '/NATIVE.md', this.getNativeGuide());
                    zip.file(base + '/src/main/resources/META-INF/native-image/' + this.groupId + '/' + this.artifactId + '/reachability-metadata.json', this.nativeMetadataSource);
                    zip.file(base + '/src/main/java/' + pkgPath + '/ApplicationDiscovery.java', this.getNativeDiscovery());
                    zip.file(base + '/src/main/java/' + pkgPath + '/NativeConfiguration.java', this.getNativeConfiguration());
                    zip.file(base + '/src/main/resources/META-INF/services/com.guicedee.client.services.IGuiceDiscovery', this.packageName + '.ApplicationDiscovery\\n');
                    zip.file(base + '/src/main/resources/META-INF/services/com.guicedee.nativeimage.GuiceNativeConfiguration', this.packageName + '.NativeConfiguration\\n');
                    if (this.hasHazelcast()) zip.file(base + '/src/main/java/' + pkgPath + '/cluster/DiscoveryPackage.java',
                        'package ' + this.packageName + '.cluster;\\npublic final class DiscoveryPackage {}\\n');
                }
                """;
    }
}
