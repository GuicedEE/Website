package com.guicedee.website.pages;

import com.guicedee.website.App;
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
        f.add("""
                moduleMap: Record<string, {groupId: string, artifactId: string, moduleName: string}> = {
                    'REST Services': {groupId: 'com.guicedee', artifactId: 'rest', moduleName: 'com.guicedee.rest'},
                    'REST Client': {groupId: 'com.guicedee', artifactId: 'rest-client', moduleName: 'com.guicedee.rest.client'},
                    'WebSockets': {groupId: 'com.guicedee', artifactId: 'websockets', moduleName: 'com.guicedee.websockets'},
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
                    'Hazelcast': {groupId: 'com.guicedee.services', artifactId: 'hazelcast', moduleName: 'com.guicedee.services.hazelcast'},
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
                    'TestContainers': {groupId: 'com.guicedee.services', artifactId: 'testcontainers', moduleName: 'com.guicedee.services.testcontainers'}
                };
                """);
        return f;
    }

    @Override
    public List<String> methods()
    {
        var m = new ArrayList<>(super.methods());
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
                    const seen = new Set<string>();
                    const deps: {groupId: string, artifactId: string, moduleName: string}[] = [];
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
                    const deps = this.getSelectedDeps();
                    const depXml = deps.length === 0
                        ? '        <!-- Select modules from the tree -->'
                        : deps.map(d => `        <dependency>\\n            <groupId>${d.groupId}</groupId>\\n            <artifactId>${d.artifactId}</artifactId>\\n        </dependency>`).join('\\n');
                    return '```xml\\n' + `<?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <groupId>${this.groupId}</groupId>
                    <artifactId>${this.artifactId}</artifactId>
                    <version>${this.version}</version>

                    <properties>
                        <maven.compiler.release>25</maven.compiler.release>
                        <guicedee.version>2.0.0</guicedee.version>
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
                ${depXml}
                    </dependencies>
                </project>` + '\\n```';
                }
                """);
        m.add("""
                getGradlePreview(): string {
                    const deps = this.getSelectedDeps();
                    const depLines = deps.length === 0
                        ? '    // Select modules from the tree'
                        : deps.map(d => `    implementation("${d.groupId}:${d.artifactId}")`).join('\\n');
                    return '```groovy\\n' + `plugins {
                    java
                }

                group = "${this.groupId}"
                version = "${this.version}"

                java {
                    toolchain {
                        languageVersion.set(JavaLanguageVersion.of(25))
                    }
                }

                dependencies {
                    implementation platform("com.guicedee:guicedee-bom:2.0.0")
                ${depLines}
                }` + '\\n```';
                }
                """);
        m.add("""
                getModuleInfoPreview(): string {
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
                    return '```java\\n' + `module ${modName} {
                ${requires}${extras}

                    opens ${this.packageName} to com.google.guice;

                    provides com.guicedee.client.services.lifecycle.IGuiceModule
                        with ${this.packageName}.AppModule;
                }` + '\\n```';
                }
                """);
        m.add("""
                getBootPreview(): string {
                    return '```java\\n' + `package ${this.packageName};

                import com.guicedee.client.IGuiceContext;

                public class Boot {
                    public static void main(String[] args) {
                        IGuiceContext.registerModuleForScanning
                            .add("${this.packageName}");
                        IGuiceContext.instance().inject();
                    }
                }` + '\\n```';
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
                        <guicedee.version>2.0.0</guicedee.version>
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
                ${depXml}
                    </dependencies>
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
                }

                group = "${this.groupId}"
                version = "${this.version}"

                java {
                    toolchain {
                        languageVersion.set(JavaLanguageVersion.of(25))
                    }
                }

                dependencies {
                    implementation platform("com.guicedee:guicedee-bom:2.0.0")
                ${depLines}
                }`;
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
                ${requires}${extras}

                    opens ${this.packageName} to com.google.guice;

                    provides com.guicedee.client.services.lifecycle.IGuiceModule
                        with ${this.packageName}.AppModule;
                }`;
                }
                """);
        m.add("""
                getRawBoot(): string {
                    return `package ${this.packageName};

                import com.guicedee.client.IGuiceContext;

                public class Boot {
                    public static void main(String[] args) {
                        IGuiceContext.registerModuleForScanning
                            .add("${this.packageName}");
                        IGuiceContext.instance().inject();
                    }
                }`;
                }
                """);
        m.add("""
                getRawAppModule(): string {
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
                    zip.file(base + '/src/main/java/' + pkgPath + '/Boot.java', this.getRawBoot());
                    zip.file(base + '/src/main/java/' + pkgPath + '/AppModule.java', this.getRawAppModule());

                    const blob = await zip.generateAsync({type: 'blob'});
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
        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildMetadataSection());
        layout.add(buildModuleSelectionSection());
    }

    // ── Metadata ─────────────────────────────────────

    private WaStack buildMetadataSection()
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

        content.add(metadataGrid);

        return buildSection("Configure", "Project metadata",
                "Maven/Gradle coordinates and base package for your generated project.",
                true, content);
    }

    // ── Module selection + Preview ────────────────────

    private WaStack buildModuleSelectionSection()
    {
        var content = new WaStack<>();
        content.setGap(PageSize.Medium);

        var columns = new WaGrid<>();
        columns.setMinColumnSize("22rem");
        columns.setGap(PageSize.Large);

        // ── Left column: Module tree ──
        var treeColumn = new WaStack<>();
        treeColumn.setGap(PageSize.Small);
        treeColumn.add(headingText("h3", "m", "Modules"));

        var tree = new WaTree<>();
        tree.setSelection(TreeSelectionMode.Multiple);

        var webReactive = parentItem("Web Reactive", true);
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
        columns.add(treeColumn);

        // ── Right column: Preview ──
        var previewColumn = new WaStack<>();
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
        bootDetails.setSummary("Boot.java");
        bootDetails.addClass("code-details");

        var bootMd = Markdown.fromData("getBootPreview()");
        bootMd.addClass("code-block");
        bootMd.addClass("wa-body-s");
        bootDetails.add(bootMd);

        previewColumn.add(bootDetails);

        // Download button
        var buildBtn = new WaButton<>("Download ZIP", Variant.Brand);
        buildBtn.setAppearance(Appearance.Outlined);
        buildBtn.addAttribute("(click)", "downloadZip()");
        previewColumn.add(buildBtn);

        columns.add(previewColumn);
        content.add(columns);

        return buildSection("Select", "Modules & preview",
                "Choose modules on the left — preview the generated files on the right.",
                false, content);
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
