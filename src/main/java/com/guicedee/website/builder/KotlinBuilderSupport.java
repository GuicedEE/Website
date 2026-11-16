package com.guicedee.website.builder;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

/** Kotlin source and mixed-language JPMS build support for the downloadable starter. */
public final class KotlinBuilderSupport {
    private KotlinBuilderSupport() {}

    public static List<String> fields() {
        return List.of("language = 'java';",
                "mavenWrapperUnix = atob('" + encoded("mvnw.txt") + "');",
                "mavenWrapperWindows = atob('" + encoded("mvnw.cmd.txt") + "');",
                "mavenWrapperProperties = atob('" + encoded("maven-wrapper.properties") + "');",
                "kotlinMavenPlugins = atob('" + encoded("maven-plugins.xml.txt") + "');",
                "kotlinGradleTasks = atob('" + encoded("gradle-tasks.txt") + "');");
    }

    private static String encoded(String name) {
        try (var input = KotlinBuilderSupport.class.getResourceAsStream("/kotlin-starter/" + name)) {
            if (input == null) throw new IllegalStateException("Missing Kotlin starter: " + name);
            return Base64.getEncoder().encodeToString(input.readAllBytes());
        } catch (IOException e) { throw new IllegalStateException(e); }
    }

    public static String methods() {
        return """
                isKotlin(): boolean { return this.language === 'kotlin'; }

                getKotlinPomDeps(): string {
                    return this.isKotlin() ? `
                        <dependency><groupId>com.guicedee</groupId><artifactId>kotlin</artifactId><version>2.6.0</version></dependency>
                        <dependency><groupId>org.mockito</groupId><artifactId>mockito-core</artifactId><version>5.24.0</version><scope>test</scope></dependency>
                        <dependency><groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><version>6.0.3</version><scope>test</scope></dependency>` : '';
                }

                getKotlinGradleDeps(): string {
                    return this.isKotlin() ? `
                    implementation("com.guicedee:kotlin:2.6.0")
                    testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
                    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.3")` : '';
                }

                getKotlinBoot(): string {
                    return `package ${this.packageName}

                import com.guicedee.client.IGuiceContext
                import com.guicedee.guicedinjection.GuiceApplication

                class Boot {
                    companion object {
                        @JvmStatic
                        fun main(args: Array<String>) {
                            ${this.isNative() ? 'System.setProperty("GUICEDEE_DISCOVERY_MODE", "spi")' : ''}
                            GuiceApplication.run {
                                IGuiceContext.registerModule("${this.artifactId.replace(/-/g, '.')}")
                                ${this.isNative() ? 'IGuiceContext.getContext().getConfig().setServiceLoadWithClassPath(false)' : ''}
                            }
                        }
                    }
                }
                `;
                }

                getKotlinAppModule(): string {
                    return `package ${this.packageName}

                import com.google.inject.AbstractModule
                import com.guicedee.client.services.lifecycle.IGuiceModule

                open class AppModule : AbstractModule(), IGuiceModule<AppModule> {
                    override fun configure() {
                        // Bind your services here.
                    }
                }
                `;
                }

                addKotlinFiles(zip: any, base: string, pkgPath: string) {
                    if (!this.isKotlin()) return;
                    if (!this.app.useGradle()) {
                        zip.file(base + '/mvnw', this.mavenWrapperUnix, {unixPermissions: 0o755});
                        zip.file(base + '/mvnw.cmd', this.mavenWrapperWindows);
                        zip.file(base + '/.mvn/wrapper/maven-wrapper.properties', this.mavenWrapperProperties);
                    }
                    zip.file(base + '/src/test/java/module-info.java', `module ${this.artifactId.replace(/-/g, '.')}.test {
                        requires ${this.artifactId.replace(/-/g, '.')};
                        requires kotlin.stdlib;
                        requires org.junit.jupiter.api;
                        opens ${this.packageName}.test to org.junit.platform.commons;
                    }`);
                    zip.file(base + '/src/test/kotlin/' + pkgPath + '/test/AppModuleTest.kt', `package ${this.packageName}.test

                import ${this.packageName}.AppModule
                import com.google.inject.Guice
                import org.junit.jupiter.api.Assertions.assertNotNull
                import org.junit.jupiter.api.Test

                class AppModuleTest {
                    @Test
                    fun createsInjector() {
                        assertNotNull(Guice.createInjector(AppModule()))
                    }
                }
                `);
                    zip.file(base + '/KOTLIN.md', `# Kotlin starter

                Build with JDK 25 and Maven 4 (mvn verify) or Gradle 9.4.1 (gradle build).
                Kotlin sources live in src/main/kotlin; module-info.java and package annotations remain in src/main/java.
                Both source sets compile into one named module. Tests live in a separate .test package.
                Inject GuicedCoroutines and use coroutines.future { ... } to call suspend services from ordinary
                REST or event-bus methods returning Vert.x Future. Do not mark REST entry methods suspend.
                Keep classes and methods open when they use Guice method interception.
                Native-image integrations can require application-specific reachability metadata; see NATIVE.md when selected.
                `);
                }
                """;
    }
}
