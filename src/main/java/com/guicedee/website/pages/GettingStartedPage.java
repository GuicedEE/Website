package com.guicedee.website.pages;

import com.guicedee.website.App;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.angular.components.NgIf;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;

import java.util.ArrayList;
import java.util.List;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.details.WaDetails;

@NgComponent("guicedee-getting-started")
@NgRoutable(path = "getting-started")
@NgImportReference(value = "inject", reference = "@angular/core")
public class GettingStartedPage extends WebsitePage<GettingStartedPage> implements INgComponent<GettingStartedPage>
{
    @Override
    public List<String> fields()
    {
        List<String> f = new ArrayList<>();
        f.add("public app: App = inject(App);");
        return f;
    }

    public GettingStartedPage()
    {
        buildGettingStartedPage();
    }

    private void buildGettingStartedPage()
    {
        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        layout.add(buildIntro());
        layout.add(buildQuickestStart());
        layout.add(buildPrerequisites());
        layout.add(buildStep1Project());
        layout.add(buildStep2ModuleInfo());
        layout.add(buildStep3Bootstrap());
        layout.add(buildStep4Endpoint());
        layout.add(buildStep5Run());
        layout.add(buildWhatsHappening());
        layout.add(buildNextSteps());
    }

    // ── Intro ─────────────────────────────────────────

    private WaCard<?> buildIntro()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(headingText("h1", "xl", "Hello World with GuicedEE"));

        var intro = bodyText("Get a reactive REST endpoint running in under 5 minutes. " +
                "The quickest way is to use the IntelliJ plugin — or follow the manual steps below. " +
                "Either way: one endpoint, zero XML, zero configuration files. Just Java.", "l");
        intro.setWaColorText("quiet");
        content.add(intro);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("5 minutes", Variant.Brand));
        tags.add(buildTag("Zero XML", Variant.Warning));
        tags.add(buildTag("Hello World", Variant.Success));
        content.add(tags);

        var card = new WaCard<>();
        card.setAppearance(Appearance.Filled);
        card.add(content);
        return card;
    }

    // ── Quickest Start: IntelliJ Plugin ─────────────────

    private WaCard<?> buildQuickestStart()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(headingText("h2", "l", "Quickest Start — IntelliJ Plugin"));

        var desc = bodyTextHtml("The fastest way to get a GuicedEE project running is to install the " +
                brandCode("Guiced") + " IntelliJ IDEA plugin. It creates a fully configured project — " +
                brandCode("pom.xml") + ", " + brandCode("module-info.java") + ", bootstrap class, and REST endpoint — " +
                "in a single wizard. No manual setup required.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCardHtml("1. Install the plugin",
                "Open <strong>Settings → Plugins → Marketplace</strong> and search for " + brandCode("Guiced") +
                        ", or install directly from the <a href=\"https://plugins.jetbrains.com/plugin/com.guicedee.intellij.GEEIntelliJPlugin\" " +
                        "target=\"_blank\" style=\"color:var(--wa-color-brand)\">JetBrains Marketplace</a>.",
                null));

        grid.add(featureCard("2. New Project → GuicedEE",
                "Use File → New Project and select the GuicedEE template. " +
                        "Pick your modules (REST, Persistence, WebSockets, etc.) and the wizard generates everything.",
                null));

        grid.add(featureCard("3. Run",
                "Click the green play button next to your Boot class. " +
                        "Your REST endpoints are live at localhost:8080.",
                null));

        content.add(grid);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("30 seconds", Variant.Brand));
        tags.add(buildTag("Zero manual setup", Variant.Success));
        tags.add(buildTag("IntelliJ 2024.2+", Variant.Neutral));
        content.add(tags);

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        var pluginBtn = new WaButton<>(escapeAngular("Install from Marketplace"), Variant.Brand);
        pluginBtn.setAppearance(Appearance.Filled);
        pluginBtn.setAsLink("https://plugins.jetbrains.com/plugin/com.guicedee.intellij.GEEIntelliJPlugin", "_blank", null);
        ctas.add(pluginBtn);
        content.add(ctas);

        var card = new WaCard<>();
        card.setAppearance(Appearance.Filled);
        card.add(content);
        return card;
    }

    // ── Prerequisites ─────────────────────────────────

    private WaStack buildPrerequisites()
    {
        var content = new WaStack();
        content.setGap(PageSize.Small);

        // Maven prerequisites
        var mavenPrereqs = new NgIf("!app.useGradle()");

        var mavenGrid = new WaGrid<>();
        mavenGrid.setMinColumnSize("14rem");
        mavenGrid.setGap(PageSize.Small);
        mavenGrid.add(featureCard("JDK 25+", "Download from adoptium.net or use SDKMAN.", "Required"));
        mavenGrid.add(featureCard("Maven 3.9+ / 4+", "Any modern Maven version works. GuicedEE itself is built with Maven 4.", "Required"));
        mavenGrid.add(featureCard("Your IDE", "IntelliJ IDEA, VS Code, or Eclipse — all work.", "Recommended"));
        mavenPrereqs.add(mavenGrid);
        content.add(mavenPrereqs);

        // Gradle prerequisites
        var gradlePrereqs = new NgIf("app.useGradle()");

        var gradleGrid = new WaGrid<>();
        gradleGrid.setMinColumnSize("14rem");
        gradleGrid.setGap(PageSize.Small);
        gradleGrid.add(featureCard("JDK 25+", "Download from adoptium.net or use SDKMAN.", "Required"));
        gradleGrid.add(featureCard("Gradle 8.6+", "Any modern Gradle version with JPMS support.", "Required"));
        gradleGrid.add(featureCard("Your IDE", "IntelliJ IDEA, VS Code, or Eclipse — all work.", "Recommended"));
        gradlePrereqs.add(gradleGrid);
        content.add(gradlePrereqs);

        return buildSection("Prerequisites", "What you need",
                "GuicedEE targets the latest Java and tooling.", false, content);
    }

    // ── Step 1: Create the project ────────────────────

    private WaStack buildStep1Project()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Create a new Maven project with the GuicedEE BOM and two dependencies: " +
                "the core injection engine and the REST module. That's all you need for Hello World.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        content.add(mavenGradleCodeBlock("Project build file",
                """
                        <?xml version="1.0" encoding="UTF-8"?>
                        <project xmlns="http://maven.apache.org/POM/4.0.0"
                                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                                 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                            <modelVersion>4.0.0</modelVersion>
                        
                            <groupId>com.example</groupId>
                            <artifactId>hello-guicedee</artifactId>
                            <version>1.0.0-SNAPSHOT</version>
                        
                            <properties>
                                <maven.compiler.release>25</maven.compiler.release>
                                <guicedee.version>2.0.0-RC11</guicedee.version>
                            </properties>
                        
                            <dependencyManagement>
                                <dependencies>
                                    <dependency>
                                        <groupId>com.guicedee</groupId>
                                        <artifactId>guicedee-bom</artifactId>
                                        <version>${guicedee.version}</version>
                                        <type>pom</type>
                                        <scope>import</scope>
                                    </dependency>
                                </dependencies>
                            </dependencyManagement>
                        
                            <dependencies>
                                <dependency>
                                    <groupId>com.guicedee</groupId>
                                    <artifactId>rest</artifactId>
                                </dependency>
                            </dependencies>
                        </project>""",
                """
                        // build.gradle.kts
                        plugins {
                            java
                        }
                        
                        group = "com.example"
                        version = "1.0.0-SNAPSHOT"
                        
                        java {
                            toolchain {
                                languageVersion.set(JavaLanguageVersion.of(25))
                            }
                        }
                        
                        dependencies {
                            implementation platform("com.guicedee:guicedee-bom:2.0.0-RC11")
                            implementation("com.guicedee:rest")
                        }"""));

        var tip = new WaDetails<>();
        tip.setSummary("Why just one dependency?");
        tip.add("The rest module transitively brings in inject (" + brandCode("Guice") + " + " + brandCode("ClassGraph") + ") and web (" + brandCode("Vert.x") + " HTTP server). " +
                "The BOM aligns all versions. You only declare what you directly use.");
        content.add(tip);

        return buildSection("Step 1", "Create the project",
                "One BOM, one dependency. That's the entire pom.xml.", true, content);
    }

    // ── Step 2: Module descriptor ─────────────────────

    private WaStack buildStep2ModuleInfo()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyTextHtml("GuicedEE uses the Java Platform Module System. Create a " + brandCode("module-info.java") +
                " that opens your package for injection.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        content.add(codeBlockWithTitle("src/main/java/module-info.java",
                """
                        module hello.guicedee {
                            requires transitive com.guicedee.rest;
                            // If using AOP or injecting private fields
                            opens com.example.hello to com.google.guice;
                        }"""));

        var explain = bodyTextHtml("That's the entire module descriptor. " + brandCode("requires transitive com.guicedee.rest") + " pulls in everything. " +
                brandCode("opens") + " lets " + brandCode("Guice") + " and " + brandCode("ClassGraph") + " inspect your package for injection and route discovery.", "s");
        explain.setWaColorText("quiet");
        content.add(explain);

        return buildSection("Step 2", "Create the module descriptor",
                "Three lines. Explicit, safe, and JLink-ready.", false, content);
    }

    // ── Step 3: Bootstrap ─────────────────────────────

    private WaStack buildStep3Bootstrap()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("Boot.java",
                """
                        package com.example.hello;
                        
                        import com.guicedee.client.IGuiceContext;
                        
                        public class Boot {
                            public static void main(String[] args) {
                                IGuiceContext.registerModuleForScanning
                                    .add("com.example.hello");
                                IGuiceContext.instance();
                            }
                        }"""));

        var explain = bodyTextHtml("Two lines of code. Register your package for scanning, then call " + brandCode("instance()") + ". " +
                "GuicedEE discovers your classes, creates the " + brandCode("Guice") + " injector, starts the " + brandCode("Vert.x") + " HTTP server, " +
                "and registers your REST routes — all automatically.", "m");
        explain.setWaColorText("quiet");
        content.add(explain);

        return buildSection("Step 3", "Write the bootstrap",
                "Two lines. Everything else is automatic.", true, content);
    }

    // ── Step 4: REST endpoint ─────────────────────────

    private WaStack buildStep4Endpoint()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(codeBlockWithTitle("HelloResource.java",
                """
                        package com.example.hello;
                        
                        import jakarta.ws.rs.GET;
                        import jakarta.ws.rs.Path;
                        import jakarta.ws.rs.PathParam;
                        import jakarta.ws.rs.Produces;
                        import jakarta.ws.rs.core.MediaType;
                        
                        @Path("/hello")
                        public class HelloResource {
                        
                            @GET
                            @Produces(MediaType.TEXT_PLAIN)
                            public String hello() {
                                return "Hello, GuicedEE!";
                            }
                        
                            @GET
                            @Path("/{name}")
                            @Produces(MediaType.TEXT_PLAIN)
                            public String greet(@PathParam("name") String name) {
                                return "Hello, " + name + "!";
                            }
                        }"""));

        var explain = bodyTextHtml("Standard Jakarta REST annotations. " + brandCode("ClassGraph") + " finds this class at startup, " +
                "the REST module maps it to " + brandCode("Vert.x") + " routes, and it's live. No registration code.", "m");
        explain.setWaColorText("quiet");
        content.add(explain);

        return buildSection("Step 4", "Write a REST endpoint",
                "Standard @Path, @GET — nothing proprietary.", false, content);
    }

    // ── Step 5: Run it ────────────────────────────────

    private WaStack buildStep5Run()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        content.add(mavenGradleCodeBlock("Build and run",
                """
                        mvn clean package
                        java -jar target/hello-guicedee.jar""",
                """
                        gradle build
                        java -jar build/libs/hello-guicedee.jar"""));

        content.add(codeBlockWithTitle("Test it",
                """
                        curl http://localhost:8080/hello
                        # => Hello, GuicedEE!
                        
                        curl http://localhost:8080/hello/World
                        # => Hello, World!"""));

        var congrats = bodyTextHtml("That's it. A reactive REST service running on " + brandCode("Vert.x 5") + ", " +
                "with " + brandCode("Guice") + " DI, JPMS modules, and zero configuration files. " +
                "The entire project is 4 files: " + brandCode("pom.xml") + ", " + brandCode("module-info.java") + ", " + brandCode("Boot.java") + ", " + brandCode("HelloResource.java") + ".", "m");
        content.add(congrats);

        return buildSection("Step 5", "Run it",
                "Build, run, test. Your Hello World is live.", true, content);
    }

    // ── What just happened? ───────────────────────────

    private WaStack buildWhatsHappening()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyTextHtml("When you called " + brandCode("IGuiceContext.instance()") + ", GuicedEE executed this lifecycle:", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        content.add(mermaidDiagramWithTitle("GuicedEE Bootstrap Lifecycle",
                """
                        graph TD
                            A["IGuiceContext.instance()"] --> B["ClassGraph scans com.example.hello"]
                            B --> C["Discovers HelloResource - @Path"]
                            C --> D["Loads Guice modules via ServiceLoader"]
                            D --> E["Creates the Guice injector"]
                            E --> F["Starts Vert.x HTTP server on :8080"]
                            F --> G["Maps @Path/@GET to Vert.x Router routes"]
                            G --> H["Ready to serve requests"]"""));

        var grid = new WaGrid<>();
        grid.setMinColumnSize("14rem");
        grid.setGap(PageSize.Small);

        grid.add(featureCardHtml("No Application class",
                "No framework-specific base class to extend. Just a " + brandCode("main()") + " method.",
                null));

        grid.add(featureCardHtml("No registration code",
                "Routes are discovered from " + brandCode("@Path") + " annotations. No manual route wiring.",
                null));

        grid.add(featureCardHtml("No XML / YAML",
                "No " + brandCode("web.xml") + ", no " + brandCode("application.yml") + ", no " + brandCode("beans.xml") + ". Configuration is code + env vars.",
                null));

        grid.add(featureCardHtml("JPMS Level 3",
                "Real " + brandCode("module-info.java") + " with explicit " + brandCode("requires") + "/" + brandCode("opens") + ". JLink-ready from day one.",
                null));

        content.add(grid);

        return buildSection("What just happened?", "Zero-magic bootstrapping",
                "Everything is discovered through " + brandCode("ClassGraph") + " scanning and SPI — no reflection hacks.",
                false, content);
    }

    // ── Next steps ────────────────────────────────────

    private WaStack buildNextSteps()
    {
        var content = new WaStack();
        content.setGap(PageSize.Medium);

        var desc = bodyText("Now that you have a running Hello World, explore what GuicedEE can really do.", "m");
        desc.setWaColorText("quiet");
        content.add(desc);

        var grid = new WaGrid<>();
        grid.setMinColumnSize("16rem");
        grid.setGap(PageSize.Medium);

        grid.add(featureCard("End-to-end guide",
                "Build a real-world microservice with security, persistence, " +
                        "REST clients, CORS, cloud logging, and JLink deployment.",
                "/guides/end-to-end"));

        grid.add(featureCardHtml("REST services",
                "Full JAX-RS adapter — CORS, security, reactive returns, " +
                        "exception mapping, and " + brandCode("RestInterceptor") + " SPI.",
                "/modules/rest"));

        grid.add(featureCardHtml("REST client",
                brandCode("@Endpoint") + " annotation, typed " + brandCode("RestClient&lt;S,R&gt;") + ", path parameters, " +
                        "Bearer/Basic/ApiKey auth, env-var secrets.",
                "/modules/rest-client"));

        grid.add(featureCardHtml("Persistence",
                brandCode("Hibernate Reactive 7") + " with " + brandCode("Mutiny") + ". Multi-database, " +
                        "env-var driven, fully managed by " + brandCode("Guice") + ".",
                "/modules/persistence"));

        grid.add(featureCardHtml("Security",
                brandCode("@RolesAllowed") + ", " + brandCode("@PermitAll") + ", pluggable " + brandCode("Vert.x") + " auth handlers, " +
                        "HTTPS/TLS, call-scoped isolation.",
                "/modules/security"));

        grid.add(featureCardHtml("Cloud & deployment",
                brandCode("CLOUD=true") + " for JSON logging, JLink custom runtimes, " +
                        "distroless Docker, ~300 ms cold start.",
                "/guides/deployment"));

        grid.add(featureCardHtml("Verticles & SPI",
                brandCode("@Verticle") + " isolation, 3 server SPI hooks, " +
                        "per-pool threading, " + brandCode("VertxRouterConfigurator") + ".",
                "/modules/vertx"));

        grid.add(featureCard("All modules",
                "Health, Metrics, Telemetry, Config, WebSockets, RabbitMQ, " +
                        "OpenAPI, Swagger UI, Fault Tolerance, and more.",
                "/capabilities"));

        content.add(grid);

        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("End-to-end guide", "guides/end-to-end", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("Explore capabilities", "capabilities", Variant.Neutral, Appearance.Outlined));
        ctas.add(buildCta("Open the app builder", "builder", Variant.Neutral, Appearance.Outlined));
        content.add(ctas);

        return buildSection("What's next?", "Go deeper",
                "Hello World was just the beginning. Each module has its own dedicated guide.",
                true, content);
    }
}

