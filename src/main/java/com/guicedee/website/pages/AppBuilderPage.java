package com.guicedee.website.pages;

import com.guicedee.website.App;
import com.guicedee.website.builder.NativeBuilderSupport;
import com.guicedee.website.builder.KotlinBuilderSupport;
import com.jwebmp.webawesome.components.select.WaSelect;
import com.jwebmp.webawesome.components.select.WaSelectOption;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.annotations.typescript.TsDependency;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.components.NgIf;
import com.jwebmp.plugins.markdown.Markdown;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.details.WaDetails;
import com.jwebmp.webawesome.components.input.WaInput;
import com.jwebmp.webawesome.components.tree.WaTree;
import com.jwebmp.webawesome.components.tree.WaTreeItem;
import com.jwebmp.webawesome.components.tree.TreeSelectionMode;

import java.util.ArrayList;
import java.util.List;

@NgComponent("guicedee-app-builder")
@NgRoutable(path = "builder")
@NgComponentReference(App.class)
@TsDependency(value = "jszip", version = "^3.10.1")
@NgImportReference(value = "JSZip", reference = "jszip", wrapValueInBraces = false)
public class AppBuilderPage extends WebsitePage<AppBuilderPage> implements INgComponent<AppBuilderPage>
{
    public AppBuilderPage()
    {
        renderBuilder();
    }

    @Override
    public List<String> fields()
    {
        var f = new ArrayList<>(super.fields());
        f.add("groupId = 'com.example';");
        f.add("artifactId = 'my-service';");
        f.add("version = '1.0.0-SNAPSHOT';");
        f.add("packageName = 'com.example.myservice';");
        f.add("selectedModules: string[] = [];");
        f.add("private previewAsideCleanup?: () => void;");
        f.add("""
                moduleMap: Record<string, {groupId: string, artifactId: string, moduleName: string}> = {
                    'Firebase Admin': {groupId: 'com.guicedee.modules.services', artifactId: 'firebase-admin', moduleName: 'firebase.admin'},
                    'Commercetools SDK': {groupId: 'com.guicedee.modules.services', artifactId: 'commercetools', moduleName: 'commercetools.sdk.java.api'},
                    'REST Services': {groupId: 'com.guicedee', artifactId: 'rest', moduleName: 'com.guicedee.rest'},
                    'REST Client': {groupId: 'com.guicedee', artifactId: 'rest-client', moduleName: 'com.guicedee.rest.client'},
                    'WebSockets': {groupId: 'com.guicedee', artifactId: 'websockets', moduleName: 'com.guicedee.vertx.sockets'},
                    'OpenAPI + Swagger UI': {groupId: 'com.guicedee', artifactId: 'openapi', moduleName: 'com.guicedee.openapi'},
                    'Web Services (SOAP)': {groupId: 'com.guicedee', artifactId: 'webservices', moduleName: 'com.guicedee.webservices'},
                'HTTP Proxy': {groupId: 'com.guicedee', artifactId: 'vertx', moduleName: 'com.guicedee.vertx'},
                'gRPC': {groupId: 'com.guicedee', artifactId: 'vertx', moduleName: 'com.guicedee.vertx'},
                    'Persistence (Hibernate Reactive)': {groupId: 'com.guicedee', artifactId: 'persistence', moduleName: 'com.guicedee.persistence'},
                    'PostgreSQL': {groupId: 'com.guicedee.services', artifactId: 'postgresql', moduleName: 'com.guicedee.services.postgresql'},
                    'MySQL': {groupId: 'com.guicedee.services', artifactId: 'mysql-connector', moduleName: 'com.guicedee.services.mysql'},
                    'Oracle': {groupId: 'com.guicedee.services', artifactId: 'ojdbc', moduleName: 'com.guicedee.services.ojdbc'},
                    'DB2': {groupId: 'com.guicedee.services', artifactId: 'db2-jcc', moduleName: 'com.guicedee.services.db2'},
                    'SQL Server': {groupId: 'com.guicedee.services', artifactId: 'mssql-jdbc', moduleName: 'com.guicedee.services.mssql'},
                    'JDBC (generic)': {groupId: 'com.guicedee.services', artifactId: 'jdbc', moduleName: 'com.guicedee.services.jdbc'},
                'MongoDB': {groupId: 'com.guicedee', artifactId: 'persistence', moduleName: 'com.guicedee.persistence'},
                'Cassandra': {groupId: 'com.guicedee', artifactId: 'persistence', moduleName: 'com.guicedee.persistence'},
                'Redis': {groupId: 'com.guicedee', artifactId: 'vertx', moduleName: 'com.guicedee.vertx'},
                    'RabbitMQ': {groupId: 'com.guicedee', artifactId: 'rabbitmq', moduleName: 'com.guicedee.rabbitmq'},
                    'Kafka': {groupId: 'com.guicedee', artifactId: 'kafka', moduleName: 'com.guicedee.kafka'},
                    'IBM MQ': {groupId: 'com.guicedee', artifactId: 'ibmmq', moduleName: 'com.guicedee.ibmmq'},
                    'GraphQL': {groupId: 'com.guicedee', artifactId: 'graphql', moduleName: 'com.guicedee.vertx.graphql'},
                    'Hazelcast': {groupId: 'com.guicedee', artifactId: 'hazelcast', moduleName: 'com.guicedee.guicedhazelcast'},
                    'Vert.x clustering (Hazelcast)': {groupId: 'com.guicedee', artifactId: 'hazelcast', moduleName: 'com.guicedee.guicedhazelcast'},
                    'EhCache': {groupId: 'com.guicedee.services', artifactId: 'ehcache', moduleName: 'com.guicedee.services.ehcache'},
                    'OAuth2 / OIDC': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'JWT': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'ABAC': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'OTP / TOTP / HOTP': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'Property File': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'LDAP': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'htpasswd': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'htdigest': {groupId: 'com.guicedee', artifactId: 'auth', moduleName: 'com.guicedee.auth'},
                    'Health': {groupId: 'com.guicedee', artifactId: 'health', moduleName: 'com.guicedee.health'},
                    'Config': {groupId: 'com.guicedee.microprofile', artifactId: 'config', moduleName: 'com.guicedee.microprofile.config'},
                    'Metrics': {groupId: 'com.guicedee', artifactId: 'metrics', moduleName: 'com.guicedee.metrics'},
                    'Telemetry': {groupId: 'com.guicedee', artifactId: 'telemetry', moduleName: 'com.guicedee.telemetry'},
                    'Fault Tolerance': {groupId: 'com.guicedee', artifactId: 'fault-tolerance', moduleName: 'com.guicedee.faulttolerance'},
                    'Mail Client': {groupId: 'com.guicedee', artifactId: 'mail', moduleName: 'com.guicedee.mail'},
                    'Cerial': {groupId: 'com.guicedee', artifactId: 'cerial', moduleName: 'com.guicedee.cerial'},
                    'Service Registry': {groupId: 'com.guicedee', artifactId: 'service-registry', moduleName: 'com.guicedee.service.registry'},
                    'Service Discovery': {groupId: 'com.guicedee', artifactId: 'service-discovery', moduleName: 'com.guicedee.vertx.servicediscovery'},
                    'Consul': {groupId: 'com.guicedee', artifactId: 'consul', moduleName: 'com.guicedee.consul'},
                    'Consul Service Resolver': {groupId: 'com.guicedee', artifactId: 'consul-service-resolver', moduleName: 'com.guicedee.consul.resolver'},
                    'Runtime Autoconfigure': {groupId: 'com.guicedee', artifactId: 'runtime-autoconfigure', moduleName: 'com.guicedee.runtime.autoconfigure'},
                    'TestContainers': {groupId: 'com.guicedee.services', artifactId: 'testcontainers', moduleName: 'com.guicedee.services.testcontainers'}
                };
                
                """);
        f.addAll(NativeBuilderSupport.fields());
        f.addAll(KotlinBuilderSupport.fields());
        return f;
    }

    @Override
    public List<String> methods()
    {
        var m = new ArrayList<>(super.methods());
        m.add(NativeBuilderSupport.methods());
        m.add(KotlinBuilderSupport.methods());
        m.add("""
                onTreeSelectionChange(event: any) {
                    const items = event?.detail?.selection || [];
                    this.selectedModules = items
                        .map((el: any) => el.textContent?.trim())
                        .filter((t: string) => t && this.moduleMap[t]);
                }
                """);
        m.add("""
                getSelectedDeps(): {groupId: string, artifactId: string, moduleName: string}[] {
                    const seen = new Set<string>(['inject']);
                    const deps: {groupId: string, artifactId: string, moduleName: string}[] = [
                        {groupId: 'com.guicedee', artifactId: 'inject', moduleName: 'com.guicedee.guicedinjection'}
                    ];
                    for (const name of this.selectedModules) {
                        const dep = this.moduleMap[name];
                        if (dep && !seen.has(dep.artifactId)) {
                            seen.add(dep.artifactId);
                            deps.push(dep);
                        }
                    }
                    return deps;
                }
                """);
        m.add("""
                getPomPreview(): string {
                    return '```xml\\n' + this.getRawPom() + '\\n```';
                }
                """);
        m.add("""
                getGradlePreview(): string {
                    return '```kotlin\\n' + this.getRawGradle() + '\\n```';
                }
                """);
        m.add("""
                getModuleInfoPreview(): string {
                    return '```java\\n' + this.getRawModuleInfo() + '\\n```';
                }
                """);
        m.add("""
                getBootPreview(): string {
                    return '```' + this.language + '\\n' + this.getRawBoot() + '\\n```';
                }
                """);
        m.add("""
                getRawPom(): string {
                    const deps = this.getSelectedDeps();
                    const depXml = deps.length === 0
                        ? '        <!-- Select modules from the tree -->'
                        : deps.map(d => `        <dependency>\\n            <groupId>${d.groupId}</groupId>\\n            <artifactId>${d.artifactId}</artifactId>\\n        </dependency>`).join('\\n');
                    return `<?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>

                    <groupId>${this.groupId}</groupId>
                    <artifactId>${this.artifactId}</artifactId>
                    <version>${this.version}</version>

                    <properties>
                        <maven.compiler.release>25</maven.compiler.release>
                        <guicedee.version>2.6.0</guicedee.version>
                    </properties>

                    <dependencyManagement>
                        <dependencies>
                            <dependency>
                                <groupId>com.guicedee</groupId>
                                <artifactId>guicedee-bom</artifactId>
                                <version>\\${guicedee.version}</version>
                                <type>pom</type>
                                <scope>import</scope>
                            </dependency>
                        </dependencies>
                    </dependencyManagement>

                    <dependencies>
                ${depXml}${this.getNativePomDeps()}${this.getKotlinPomDeps()}
                    </dependencies>
                    <build>
                        <plugins>
                            ${this.isKotlin() ? this.kotlinMavenPlugins : ''}
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-compiler-plugin</artifactId>
                                <version>3.15.0</version>
                            </plugin>
                        </plugins>
                    </build>
                </project>`;
                }
                """);
        m.add("""
                getRawGradle(): string {
                    const deps = this.getSelectedDeps();
                    const depLines = deps.length === 0
                        ? '    // Select modules from the tree'
                        : deps.map(d => `    implementation("${d.groupId}:${d.artifactId}")`).join('\\n');
                    return `plugins {
                    java
                    ${this.isKotlin() ? 'kotlin("jvm") version "2.4.21"' : ''}
                }

                group = "${this.groupId}"
                version = "${this.version}"

                repositories {
                    mavenCentral()
                }

                java {
                    toolchain {
                        languageVersion.set(JavaLanguageVersion.of(25))
                    }
                }

                dependencies {
                    implementation(platform("com.guicedee:guicedee-bom:2.6.0"))
                ${depLines}${this.getNativeGradleDeps()}${this.getKotlinGradleDeps()}
                }${this.getNativeGradleTask()}
                ${this.isKotlin() ? this.kotlinGradleTasks.replaceAll('@MODULE@', this.artifactId.replace(/-/g, '.')) : ''}`;
                }
                """);
        m.add("""
                getRawModuleInfo(): string {
                    const deps = this.getSelectedDeps();
                    const modName = this.artifactId.replace(/-/g, '.');
                    const requires = deps.length === 0
                        ? '    // requires for each selected module'
                        : deps.map(d => `    requires transitive ${d.moduleName};`).join('\\n');
                    let extras = '';
                    if (this.selectedModules.includes('MongoDB')) {
                        extras += '\\n    requires io.vertx.client.mongo;';
                    }
                    if (this.selectedModules.includes('Cassandra')) {
                        extras += '\\n    requires io.vertx.cassandra.client;';
                    }
                    if (this.selectedModules.includes('HTTP Proxy')) {
                        extras += '\\n    requires io.vertx.httpproxy;';
                    }
                    if (this.selectedModules.includes('Redis')) {
                        extras += '\\n    requires io.vertx.redis.client;';
                    }
                    if (this.selectedModules.includes('gRPC')) {
                        extras += '\\n    requires io.vertx.grpc.server;';
                        extras += '\\n    requires io.vertx.grpc.client;';
                    }
                    return `module ${modName} {
                ${requires}${extras}${this.getNativeModuleInfo()}
                ${this.isKotlin() ? '    requires com.guicedee.kotlin;\\n    exports ' + this.packageName + ';' : ''}

                    opens ${this.packageName} to com.google.guice;

                    provides com.guicedee.client.services.lifecycle.IGuiceModule
                        with ${this.packageName}.AppModule;
                }`;
                }
                """);
        m.add("""
                getRawBoot(): string {
                    if (this.isKotlin()) return this.getKotlinBoot();
                    return `package ${this.packageName};

                import com.guicedee.client.IGuiceContext;
                import com.guicedee.guicedinjection.GuiceApplication;

                public class Boot {
                    public static void main(String[] args) {
                        ${this.isNative() ? 'System.setProperty("GUICEDEE_DISCOVERY_MODE", "spi");' : ''}
                        GuiceApplication.run(() -> {
                            IGuiceContext.registerModule("${this.artifactId.replace(/-/g, '.')}");
                            ${this.isNative() ? 'IGuiceContext.getContext().getConfig().setServiceLoadWithClassPath(false);' : ''}
                        });
                    }
                }`;
                }
                """);
        m.add("""
                getRawAppModule(): string {
                    if (this.isKotlin()) return this.getKotlinAppModule();
                    return `package ${this.packageName};

                import com.google.inject.AbstractModule;
                import com.guicedee.client.services.lifecycle.IGuiceModule;

                public class AppModule extends AbstractModule implements IGuiceModule<AppModule> {
                    @Override
                    protected void configure() {
                        // Bind your services here
                    }
                }`;
                }
                """);
        m.add("""
                hasHazelcast(): boolean {
                    return this.selectedModules.includes('Hazelcast') || this.isClustered();
                }

                isClustered(): boolean {
                    return this.selectedModules.includes('Vert.x clustering (Hazelcast)');
                }

                getRawClusterOptions(): string {
                    if (!this.hasHazelcast()) return '';
                    const options = this.isClustered() ? `@com.guicedee.vertx.spi.EventBusOptions(
                    host = "127.0.0.1", port = 7801,
                    clusterPublicHost = "127.0.0.1", clusterPublicPort = 7801)
                @com.guicedee.guicedhazelcast.HazelcastServerOptions(
                    clusterName = "${this.artifactId}-cluster", clustered = true, startLocal = false,
                    port = 5701, portAutoIncrement = false,
                    interfaces = "127.0.0.1", interfacesEnabled = true,
                    joinType = com.guicedee.guicedhazelcast.HazelcastServerOptions.JoinType.TCP,
                    tcpMembers = "127.0.0.1:5701,127.0.0.1:5702")` : `@com.guicedee.guicedhazelcast.HazelcastServerOptions(
                    clusterName = "${this.artifactId}-cache", clustered = false, startLocal = true,
                    joinType = com.guicedee.guicedhazelcast.HazelcastServerOptions.JoinType.NONE)`;
                    return options + `\\npackage ${this.packageName}.cluster;\\n`;
                }

                getClusterOptionsPreview(): string {
                    return '```java\\n' + this.getRawClusterOptions() + '```';
                }

                getNodeEnvironment(node: number): string {
                    return `VERTX_CLUSTER_ENABLED=true
                HAZELCAST_CLIENT_ENABLED=false
                HAZELCAST_START_LOCAL=false
                HAZELCAST_PORT=${5700 + node}
                HAZELCAST_PUBLIC_ADDRESS=127.0.0.1:${5700 + node}
                VERTX_EVENTBUS_HOST=127.0.0.1
                VERTX_EVENTBUS_PORT=${7800 + node}
                VERTX_EVENTBUS_CLUSTER_PUBLIC_HOST=127.0.0.1
                VERTX_EVENTBUS_CLUSTER_PUBLIC_PORT=${7800 + node}
                HTTP_PORT=${8080 + node}
                `.split('\\n').map(line => line.trim()).join('\\n').trim() + '\\n';
                }

                getSingleInstanceEnvironment(): string {
                    return `VERTX_CLUSTER_ENABLED=false
                HAZELCAST_CLIENT_ENABLED=false
                HAZELCAST_START_LOCAL=false
                HTTP_PORT=8080
                `.split('\\n').map(line => line.trim()).join('\\n').trim() + '\\n';
                }

                getClusterEnvironmentPreview(): string {
                    return '### cluster-a.env.example\\n```properties\\n' + this.getNodeEnvironment(1) +
                        '```\\n### cluster-b.env.example\\n```properties\\n' + this.getNodeEnvironment(2) +
                        '```\\n### single-instance.env.example\\n```properties\\n' + this.getSingleInstanceEnvironment() + '```';
                }

                getClusteringGuide(): string {
                    return `# Clustering

                Load cluster-a.env.example and cluster-b.env.example into separate processes running this application.
                These files are not loaded automatically. Export their values or copy the appropriate file to
                .env.local in each process's working directory. The default annotation configuration matches node A.
                They bind only to loopback; choose reachable membership and event-bus bind/advertised addresses
                for another host or deployment. Membership and event-bus ports are separate endpoints.
                single-instance.env.example disables the event-bus cluster, local member and client.

                The built-in GuicedEE configurator owns the cluster manager; do not create another one.
                Configure only one HazelcastServerOptions annotation per assembled application.
                Selecting Hazelcast alone generates an owned cache member with clustered=false;
                selecting Vert.x clustering intentionally enables TCP membership and the event-bus cluster.

                Public broadcasts can fan out across nodes. Socket commands and private replies/storage remain
                with the owning connection. Reconnect with fresh session-bound capabilities, restore subscriptions
                and refresh authoritative state. Delivery is best effort without durable replay.

                See https://guicedee.com/capabilities#clustering-websocket-continuity
                and https://guicedee.com/environment-variables#hazelcast-clustering.
                `.split('\\n').map(line => line.trim()).join('\\n').trim() + '\\n';
                }
                """);
        m.add("""
                async downloadZip() {
                    const zip = new JSZip();
                    const base = this.artifactId;
                    const pkgPath = this.packageName.replace(/\\./g, '/');

                    const useGradle = this.app.useGradle();

                    if (useGradle) {
                        zip.file(base + '/build.gradle.kts', this.getRawGradle());
                        zip.file(base + '/settings.gradle.kts', `rootProject.name = "${this.artifactId}"\\n`);
                    } else {
                        zip.file(base + '/pom.xml', this.getRawPom());
                    }

                    zip.file(base + '/src/main/java/module-info.java', this.getRawModuleInfo());
                    const sourceRoot = this.isKotlin() ? '/src/main/kotlin/' : '/src/main/java/';
                    const extension = this.isKotlin() ? '.kt' : '.java';
                    zip.file(base + sourceRoot + pkgPath + '/Boot' + extension, this.getRawBoot());
                    zip.file(base + sourceRoot + pkgPath + '/AppModule' + extension, this.getRawAppModule());
                    this.addKotlinFiles(zip, base, pkgPath);
                    zip.file(base + '/src/main/resources/META-INF/services/com.guicedee.client.services.lifecycle.IGuiceModule',
                        this.packageName + '.AppModule\\n');
                    if (this.hasHazelcast()) {
                        zip.file(base + '/src/main/java/' + pkgPath + '/cluster/package-info.java', this.getRawClusterOptions());
                    }
                    if (this.isClustered()) {
                        zip.file(base + '/cluster-a.env.example', this.getNodeEnvironment(1));
                        zip.file(base + '/cluster-b.env.example', this.getNodeEnvironment(2));
                        zip.file(base + '/single-instance.env.example', this.getSingleInstanceEnvironment());
                        zip.file(base + '/CLUSTERING.md', this.getClusteringGuide());
                    }

                    this.addNativeFiles(zip, base, pkgPath);
                    const blob = await zip.generateAsync({type: 'blob', platform: 'UNIX'});
                    const url = URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = base + '.zip';
                    a.click();
                    URL.revokeObjectURL(url);
                }
                """);
        return m;
    }

    private void renderBuilder()
    {
        var layout = new WaGrid<>();
        layout.addClass("builder-page-layout");
        layout.setGap(PageSize.Large);
        getMain().add(layout);

        var configuration = new WaStack<>();
        configuration.addClass("builder-configuration");
        configuration.setGap(PageSize.ExtraLarge);
        configuration.add(buildMetadataSection());
        configuration.add(buildModuleSelectionSection());
        layout.add(configuration);
        var previewHome = new WaStack<>();
        previewHome.addClass("builder-preview-home");
        previewHome.add(buildPreview());
        layout.add(previewHome);
    }

    @Override
    public List<String> afterViewInit()
    {
        var lifecycle = new ArrayList<>(super.afterViewInit());
        lifecycle.add("""
                (() => {
                this.previewAsideCleanup?.();
                const shell = document.querySelector('wa-page');
                const home = document.querySelector('.builder-preview-home');
                const preview = home?.querySelector('.builder-preview');
                const aside = shell?.querySelector(':scope > [slot="aside"]');
                if (shell && home && preview && aside) {
                    const placePreview = () => {
                        const useAside = window.innerWidth >= 1100;
                        (useAside ? aside : home).appendChild(preview);
                    };
                    window.addEventListener('resize', placePreview);
                    placePreview();
                    this.previewAsideCleanup = () => {
                        window.removeEventListener('resize', placePreview);
                        home.appendChild(preview);
                    };
                }
                })();
                """);
        return lifecycle;
    }

    @Override
    public List<String> onDestroy()
    {
        var lifecycle = new ArrayList<>(super.onDestroy());
        lifecycle.add("this.previewAsideCleanup?.();");
        return lifecycle;
    }

    // ── Metadata ─────────────────────────────────────

    private WaStack<?> buildMetadataSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var metadataGrid = new WaGrid<>();
        metadataGrid.setMinColumnSize("14rem");
        metadataGrid.setGap(PageSize.Medium);

        var groupIdInput = new WaInput<>();
        groupIdInput.setLabel("Group ID");
        groupIdInput.setPlaceholder("com.example");
        groupIdInput.bind("groupId");
        metadataGrid.add(groupIdInput);

        var artifactIdInput = new WaInput<>();
        artifactIdInput.setLabel("Artifact ID");
        artifactIdInput.setPlaceholder("my-service");
        artifactIdInput.bind("artifactId");
        metadataGrid.add(artifactIdInput);

        var versionInput = new WaInput<>();
        versionInput.setLabel("Version");
        versionInput.setPlaceholder("1.0.0-SNAPSHOT");
        versionInput.bind("version");
        metadataGrid.add(versionInput);

        var packageInput = new WaInput<>();
        packageInput.setLabel("Package");
        packageInput.setPlaceholder("com.example.myservice");
        packageInput.bind("packageName");
        metadataGrid.add(packageInput);

        var language = new WaSelect<>();
        language.setLabel("Language");
        language.bind("language");
        language.addAttribute("name", "language");
        language.add(new WaSelectOption<>().setValue("java").setText("Java"));
        language.add(new WaSelectOption<>().setValue("kotlin").setText("Kotlin"));
        metadataGrid.add(language);
        var runtime = new WaSelect<>();
        runtime.setLabel("Runtime target");
        runtime.bind("runtimeTarget");
        runtime.addAttribute("name", "runtimeTarget");
        runtime.add(new WaSelectOption<>().setValue("jvm").setText("JVM JAR"));
        runtime.add(new WaSelectOption<>().setValue("native").setText("Native image starter (SPI)"));
        metadataGrid.add(runtime);
        content.add(metadataGrid);
        content.add(bodyText("Native image adds SPI discovery, reflection registration and a GraalVM build guide. " +
                "JVM builds remain available; selected integrations may need additional native metadata.", "s"));

        return buildSection("Configure", "Project metadata",
                "Maven/Gradle coordinates and base package for your generated project.",
                true, content);
    }

    // ── Module selection + Preview ────────────────────

    private WaStack<?> buildModuleSelectionSection()
    {
        var content = new WaStack<>();
        content.addClass("builder-selection");
        content.setGap(PageSize.Medium);

        // ── Left column: Module tree ──
        var treeColumn = new WaStack<>();
        treeColumn.addClass("builder-modules");
        treeColumn.setGap(PageSize.Small);
        treeColumn.add(headingText("h3", "m", "Modules"));

        var tree = new WaTree<>();
        tree.addClass("builder-module-tree");
        tree.addAttribute("aria-label", "Available modules");
        tree.setSelection(TreeSelectionMode.Multiple);

        var webReactive = parentItem("Web Reactive", false);
        webReactive.add(leafItem("REST Services"));
        webReactive.add(leafItem("REST Client"));
        webReactive.add(leafItem("WebSockets"));
        webReactive.add(leafItem("OpenAPI + Swagger UI"));
        webReactive.add(leafItem("Web Services (SOAP)"));
        webReactive.add(leafItem("HTTP Proxy"));
        webReactive.add(leafItem("gRPC"));
        tree.add(webReactive);

        var database = parentItem("Database", false);
        database.add(leafItem("Persistence (Hibernate Reactive)"));
        database.add(leafItem("PostgreSQL"));
        database.add(leafItem("MySQL"));
        database.add(leafItem("Oracle"));
        database.add(leafItem("DB2"));
        database.add(leafItem("SQL Server"));
        database.add(leafItem("JDBC (generic)"));
        database.add(leafItem("MongoDB"));
        database.add(leafItem("Cassandra"));
        database.add(leafItem("Redis"));
        tree.add(database);

        var messaging = parentItem("Messaging", false);
        messaging.add(leafItem("RabbitMQ"));
        messaging.add(leafItem("Kafka"));
        messaging.add(leafItem("IBM MQ"));
        tree.add(messaging);

        var caching = parentItem("Caching", false);
        caching.add(leafItem("Hazelcast"));
        caching.add(leafItem("Vert.x clustering (Hazelcast)"));
        caching.add(leafItem("EhCache"));
        tree.add(caching);

        var auth = parentItem("Authentication", false);
        auth.add(leafItem("OAuth2 / OIDC"));
        auth.add(leafItem("JWT"));
        auth.add(leafItem("ABAC"));
        auth.add(leafItem("OTP / TOTP / HOTP"));
        auth.add(leafItem("Property File"));
        auth.add(leafItem("LDAP"));
        auth.add(leafItem("htpasswd"));
        auth.add(leafItem("htdigest"));
        tree.add(auth);

        var sdks = parentItem("Cloud SDKs", false);
        sdks.add(leafItem("Firebase Admin"));
        sdks.add(leafItem("Commercetools SDK"));

        var serviceDiscovery = parentItem("Service Discovery", false);
        serviceDiscovery.add(leafItem("Service Registry"));
        serviceDiscovery.add(leafItem("Service Discovery"));
        serviceDiscovery.add(leafItem("Consul"));
        serviceDiscovery.add(leafItem("Consul Service Resolver"));
        serviceDiscovery.add(leafItem("Runtime Autoconfigure"));
        sdks.add(serviceDiscovery);
        tree.add(sdks);

        var microProfile = parentItem("MicroProfile", false);
        microProfile.add(leafItem("Health"));
        microProfile.add(leafItem("Config"));
        microProfile.add(leafItem("Metrics"));
        microProfile.add(leafItem("Telemetry"));
        microProfile.add(leafItem("Fault Tolerance"));
        tree.add(microProfile);

        var mail = parentItem("Mail", false);
        mail.add(leafItem("Mail Client"));
        tree.add(mail);

        var serial = parentItem("Serial", false);
        serial.add(leafItem("Cerial"));
        tree.add(serial);

        var tests = parentItem("Tests", false);
        tests.add(leafItem("TestContainers"));
        tree.add(tests);

        tree.addAttribute("(wa-selection-change)", "onTreeSelectionChange($event)");

        treeColumn.add(tree);
        var clusteringNote = new NgIf("isClustered()");
        clusteringNote.add(bodyText("Vert.x clustering adds Hazelcast TCP membership and event-bus configuration. " +
                "The generated project includes two-node and single-instance environment examples.", "s"));
        treeColumn.add(clusteringNote);
        content.add(treeColumn);
        return buildSection("Select", "Modules",
                "Choose the modules for your generated project.",
                false, content);
    }

    private WaStack<?> buildPreview()
    {
        // ── Project preview ──
        var previewColumn = new WaStack<>();
        previewColumn.addClass("builder-preview");
        previewColumn.setGap(PageSize.Medium);
        previewColumn.add(headingText("h3", "m", "Preview"));

        var previewNote = bodyText("Key files that will be generated in your project ZIP.", "s");
        previewNote.setWaColorText("quiet");
        previewColumn.add(previewNote);

        var buildFileDetails = new WaDetails<>();
        buildFileDetails.setSummary("Build file");
        buildFileDetails.addClass("code-details");

        var pomMd = Markdown.fromData("getPomPreview()");
        pomMd.addClass("code-block");
        pomMd.addClass("wa-body-s");
        var pomIf = new NgIf("!app.useGradle()");
        pomIf.add(pomMd);
        buildFileDetails.add(pomIf);

        var gradleMd = Markdown.fromData("getGradlePreview()");
        gradleMd.addClass("code-block");
        gradleMd.addClass("wa-body-s");
        var gradleIf = new NgIf("app.useGradle()");
        gradleIf.add(gradleMd);
        buildFileDetails.add(gradleIf);

        previewColumn.add(buildFileDetails);

        var moduleInfoDetails = new WaDetails<>();
        moduleInfoDetails.setSummary("module-info.java");
        moduleInfoDetails.addClass("code-details");

        var moduleInfoMd = Markdown.fromData("getModuleInfoPreview()");
        moduleInfoMd.addClass("code-block");
        moduleInfoMd.addClass("wa-body-s");
        moduleInfoDetails.add(moduleInfoMd);

        previewColumn.add(moduleInfoDetails);

        var bootDetails = new WaDetails<>();
        bootDetails.addAttribute("[summary]", "isKotlin() ? 'Boot.kt' : 'Boot.java'");
        bootDetails.addClass("code-details");

        var bootMd = Markdown.fromData("getBootPreview()");
        bootMd.addClass("code-block");
        bootMd.addClass("wa-body-s");
        bootDetails.add(bootMd);

        previewColumn.add(bootDetails);

        var hazelcastIf = new NgIf("hasHazelcast()");
        var clusterDetails = new WaDetails<>();
        clusterDetails.setSummary("cluster/package-info.java");
        clusterDetails.addClass("code-details");
        clusterDetails.add(Markdown.fromData("getClusterOptionsPreview()").addClass("code-block").addClass("wa-body-s"));
        hazelcastIf.add(clusterDetails);
        previewColumn.add(hazelcastIf);

        var clusteredIf = new NgIf("isClustered()");
        var environmentDetails = new WaDetails<>();
        environmentDetails.setSummary("Cluster environment examples");
        environmentDetails.addClass("code-details");
        environmentDetails.add(Markdown.fromData("getClusterEnvironmentPreview()").addClass("code-block").addClass("wa-body-s"));
        clusteredIf.add(environmentDetails);
        clusteredIf.add(bodyText("The ZIP includes CLUSTERING.md. Load the example environment files explicitly into separate " +
                "processes; they are not read automatically. Choose reachable addresses for deployment.", "s"));
        previewColumn.add(clusteredIf);

        var nativeIf = new NgIf("isNative()");
        var nativeDetails = new WaDetails<>();
        nativeDetails.setSummary("Native image setup");
        nativeDetails.add(Markdown.fromData("getNativeGuide()"));
        nativeIf.add(nativeDetails);
        previewColumn.add(nativeIf);

        // Download button
        var buildBtn = new WaButton<>("Download ZIP", Variant.Brand);
        buildBtn.setAppearance(Appearance.Outlined);
        buildBtn.addAttribute("(click)", "downloadZip()");
        previewColumn.add(buildBtn);

        return previewColumn;
    }


    // ── Tree helpers ──────────────────────────────────

    private WaTreeItem<?> parentItem(String label, boolean expanded)
    {
        var item = new WaTreeItem<>();
        item.setText(label);
        if (expanded)
        {
            item.setExpanded(true);
        }
        return item;
    }

    private WaTreeItem<?> leafItem(String label)
    {
        var item = new WaTreeItem<>();
        item.setText(label);
        return item;
    }
}
