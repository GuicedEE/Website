package com.guicedee.website;

import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.tree.WaTree;
import com.jwebmp.webawesome.components.tree.WaTreeItem;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/** Shared page and section navigation for the desktop menu and mobile drawer. */
final class SiteNavigation {
    private record Section(String anchor, String label) {}
    private record Page(String route, String label, String icon, List<Section> sections) {}
    private static final List<Page> PAGES = List.of(
            new Page("home", "Home", "house", List.of(
                    new Section("highlights", "Highlights & benchmarks"),
                    new Section("how-it-works", "How it works"),
                    new Section("intellij-plugin", "IntelliJ plugin"),
                    new Section("explore-platform", "Explore the platform")
            )),
            new Page("releases", "Releases", "tag", List.of()),
            new Page("builder", "App Builder", "wand-magic-sparkles", List.of()),
            new Page("getting-started", "Getting Started", "rocket", List.of(
                    new Section("prerequisites", "Prerequisites"),
                    new Section("step-1", "Step 1: Create the project"),
                    new Section("step-2", "Step 2: Module descriptor"),
                    new Section("step-3", "Step 3: Bootstrap"),
                    new Section("step-4", "Step 4: REST endpoint"),
                    new Section("step-5", "Step 5: Run it"),
                    new Section("what-just-happened", "What just happened?"),
                    new Section("what-s-next", "Next steps")
            )),
            new Page("guides/end-to-end", "End-to-End Guide", "route", List.of(
                    new Section("project-setup", "Project setup"),
                    new Section("module-descriptor", "Module descriptor"),
                    new Section("guice-module-bootstrap", "Guice module & bootstrap"),
                    new Section("rest-endpoints", "REST endpoints"),
                    new Section("security", "Security"),
                    new Section("cors", "CORS"),
                    new Section("rest-client", "REST client"),
                    new Section("health-checks", "Health checks"),
                    new Section("configuration", "Configuration"),
                    new Section("persistence", "Persistence"),
                    new Section("verticle-isolation", "Verticle isolation"),
                    new Section("cloud-aware-logging", "Cloud logging"),
                    new Section("jlink-deployment", "JLink deployment"),
                    new Section("lifecycle-environment", "Lifecycle & env vars"),
                    new Section("module-deep-dives", "Module deep-dives")
            )),
            new Page("capabilities", "Capabilities", "star", List.of(
                    new Section("architecture", "Architecture"),
                    new Section("injection-lifecycle", "Injection & Lifecycle"),
                    new Section("http-server", "HTTP Server"),
                    new Section("http-proxy", "HTTP Proxy"),
                    new Section("service-discovery", "Service Discovery"),
                    new Section("runtime-autoconfigure", "Runtime Autoconfigure"),
                    new Section("redis", "Redis"),
                    new Section("rest-jax-rs", "REST (JAX-RS)"),
                    new Section("rest-client", "REST Client"),
                    new Section("webassembly", "WebAssembly · Experimental"),
                    new Section("security-authentication", "Security"),
                    new Section("verticles-deployment", "Verticles"),
                    new Section("persistence", "Persistence"),
                    new Section("websockets", "WebSockets"),
                    new Section("clustering-websocket-continuity", "Clustering & Continuity"),
                    new Section("messaging", "Messaging"),
                    new Section("observability", "Observability"),
                    new Section("logging", "Logging"),
                    new Section("configuration", "Configuration"),
                    new Section("fault-tolerance", "Fault Tolerance"),
                    new Section("jlink-deployment", "JLink & Deployment"),
                    new Section("full-module-catalog", "Full catalog")
            )),
            new Page("modules", "Modules", "cubes", List.of(
                    new Section("core-modules", "Core modules"),
                    new Section("web-rest", "Web & REST"),
                    new Section("data-persistence", "Data & persistence"),
                    new Section("observability", "Observability"),
                    new Section("integration", "Integration"),
                    new Section("migration-compatibility", "Migration")
            )),
            new Page("services", "Services", "puzzle-piece", List.of(
                    new Section("how-to-use", "How to use"),
                    new Section("cloud-sdks", "Firebase / Commercetools"),
                    new Section("apache-commons", "Apache Commons"),
                    new Section("apache-cxf-web-services", "Apache CXF"),
                    new Section("apache-poi", "Apache POI"),
                    new Section("database-driver", "Database"),
                    new Section("google", "Google"),
                    new Section("hibernate", "Hibernate"),
                    new Section("jakarta", "Jakarta"),
                    new Section("jcache", "JCache"),
                    new Section("jni", "JNI"),
                    new Section("library", "Library"),
                    new Section("microprofile", "MicroProfile"),
                    new Section("security", "Security / Nimbus"),
                    new Section("vert-x", "Vert.x")
            )),
            new Page("cloud", "Cloud", "cloud", List.of(
                    new Section("architecture", "How It Works"),
                    new Section("platforms", "Platform Detection"),
                    new Section("declaration", "Declaring Services"),
                    new Section("resolution", "URL Resolution"),
                    new Section("rest-client", "REST Client Integration"),
                    new Section("health", "Health Monitoring"),
                    new Section("spi", "SPI & Extensions"),
                    new Section("configuration", "Configuration"),
                    new Section("installation", "Getting Started")
            )),
            new Page("environment-variables", "Env Variables", "sliders", List.of(
                    new Section("configuration-search-order", "Search Order"),
                    new Section("annotation-text-across-modules", "Annotation Text"),
                    new Section("injection-runtime", "Inject"),
                    new Section("json", "JSON"),
                    new Section("http-server", "Web"),
                    new Section("vert-x-runtime-consumers", "Vert.x"),
                    new Section("eventbus", "EventBus"),
                    new Section("verticle-deployment", "Deployment"),
                    new Section("redis", "Redis"),
                    new Section("grpc", "gRPC"),
                    new Section("hazelcast-clustering", "Hazelcast"),
                    new Section("filesystem", "FileSystem"),
                    new Section("address-resolver", "Address Resolver"),
                    new Section("rest-client", "REST Client"),
                    new Section("rest-cors", "REST CORS"),
                    new Section("authentication-authorization", "Auth"),
                    new Section("health-checks", "Health"),
                    new Section("metrics", "Metrics"),
                    new Section("telemetry", "Telemetry"),
                    new Section("programmatic-options", "Programmatic Options"),
                    new Section("rabbitmq", "RabbitMQ"),
                    new Section("graphql", "GraphQL"),
                    new Section("kafka", "Kafka"),
                    new Section("ibm-mq", "IBM MQ"),
                    new Section("persistence", "Persistence"),
                    new Section("web-services", "Web Services"),
                    new Section("mcp", "MCP")
            )));

    private SiteNavigation() {}

    static WaTree<?> tree() {
        var tree = new WaTree<>();
        tree.addClass("site-navigation-tree");
        tree.addAttribute("aria-label", "Page navigation");
        tree.setIndentSize("2px");
        tree.setIndentGuideColor("var(--wa-color-neutral-300)");
        for (var page : PAGES) {
            var item = new WaTreeItem<>();
            item.addClass("page-navigation");
            item.addAttribute("data-navigation-route", page.route());
            item.bindExpanded("expandedNavigation() === '" + page.route() + "'");
            item.addAttribute("(wa-expand)", "onNavigationExpanded($event, '" + page.route() + "')");
            item.addAttribute("(wa-collapse)", "onNavigationCollapsed($event, '" + page.route() + "')");
            var link = new Link<>("/" + page.route());
            link.addAttribute("routerLink", "/" + page.route());
            link.addAttribute("(click)", "onNavigationPageClick($event, '" + page.route() + "')");
            link.addAttribute("[attr.aria-current]", "activeNavigation() === '" + page.route() + "' ? 'page' : null");
            var icon = new WaIcon<>(page.icon());
            icon.setFamily("sharp-duotone");
            icon.addStyle("color", "var(--wa-color-brand-on-normal)");
            link.add(icon);
            link.setText("&nbsp;" + page.label());
            link.setRenderTextBeforeChildren(false);
            item.add(link);
            for (var section : page.sections()) {
                var child = new WaTreeItem<>();
                child.addClass("navigation-section");
                var sectionLink = new Link<>("/" + page.route() + "#" + section.anchor());
                sectionLink.setText(section.label());
                sectionLink.addAttribute("(click)", "openNavigationSection($event, '" + page.route() + "', '" + section.anchor() + "')");
                sectionLink.addAttribute("[attr.aria-current]", "activeNavigation() === '" + page.route() + "' && navigationFragment() === '" + section.anchor() + "' ? 'location' : null");
                child.add(sectionLink);
                item.add(child);
            }
            tree.add(item);
        }
        return tree;
    }

    static String fields() {
        String anchors = PAGES.stream().map(page -> quote(page.route()) + ":[" + page.sections().stream()
                .map(section -> quote(section.anchor())).collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining(","));
        return "activeNavigation = signal('home');\nexpandedNavigation = signal('home');\nnavigationFragment = signal('');\n"
                + "private navigationAnchors: Record<string, string[]> = {" + anchors + "};";
    }

    static String methods() {
        try (var input = SiteNavigation.class.getResourceAsStream("/site-navigation-methods.ts")) {
            if (input == null) throw new IllegalStateException("Missing site navigation methods");
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException error) { throw new IllegalStateException(error); }
    }

    private static String quote(String value) {
        return "\"" + StringEscapeUtils.escapeJson(value) + "\"";
    }
}
