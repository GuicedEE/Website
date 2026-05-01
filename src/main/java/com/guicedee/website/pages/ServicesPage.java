package com.guicedee.website.pages;

import com.guicedee.website.builder.ApplicationBuilderService;
import com.guicedee.website.catalog.ServiceDefinition;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NgComponent("guicedee-services")
@NgRoutable(path = "services")
public class ServicesPage extends WebsitePage<ServicesPage> implements INgComponent<ServicesPage> {
    public ServicesPage() {
        renderServices();
    }

    private void renderServices() {

        var layout = new WaStack<>();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Intro
        var introContent = new WaStack<>();
        introContent.setGap(PageSize.Medium);
        introContent.add(headingText("h1", "xl", "JPMS Service Modules"));
        var intro = bodyText("GuicedEE repackages over 50 third-party libraries as proper JPMS modules " +
                "with explicit module-info.java descriptors. These service modules plug cleanly into the " +
                "Java Module System so you can use them in JLink and JPackage builds.", "l");
        intro.setWaColorText("quiet");
        introContent.add(intro);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("50+ services", Variant.Brand));
        tags.add(buildTag("JPMS Level 3", Variant.Neutral));
        tags.add(buildTag("Version aligned", Variant.Success));
        introContent.add(tags);

        var introCard = new WaCard<>();
        introCard.setAppearance(Appearance.Filled);
        introCard.add(introContent);
        layout.add(introCard);

        // Group services by family
        Map<String, List<ServiceDefinition>> grouped = ApplicationBuilderService.getInstance()
                .getAvailableServices()
                .stream()
                .collect(Collectors.groupingBy(
                        s -> {
                            String desc = s.getDescription();
                            if (desc.contains(":")) {
                                String family = desc.substring(0, desc.indexOf(":")).trim();
                                if (family.endsWith(" integration service")) {
                                    family = family.substring(0, family.length() - " integration service".length()).trim();
                                }
                                return family;
                            }
                            return "Miscellaneous";
                        },
                        LinkedHashMap::new,
                        Collectors.toList()));


        // Usage section (at top)
        var usageContent = new WaStack<>();
        usageContent.setGap(PageSize.Medium);

        usageContent.add(codeBlockWithTitle("Using a service module",
                "<!-- Just add the dependency — version comes from the BOM -->\n" +
                        "<dependency>\n" +
                        "    <groupId>com.guicedee.modules.services</groupId>\n" +
                        "    <artifactId>postgresql</artifactId>\n" +
                        "</dependency>"));

        usageContent.add(codeBlockWithTitle("In your module-info.java",
                "module my.app {\n" +
                        "    requires com.guicedee.guicedinjection;\n" +
                        "    // Service modules export their JPMS packages\n" +
                        "    requires io.vertx.client.sql.pg;\n" +
                        "}"));

        var note = bodyText("Service modules handle the JPMS packaging so you don't have to. " +
                "Each one provides proper exports, opens, and requires directives. " +
                "They're tested together with the BOM to ensure compatibility.", "m");
        note.setWaColorText("quiet");
        usageContent.add(note);

        layout.add(buildSection("How to use", "Just add the dependency",
                "Service modules are version-aligned with the BOM. No extra configuration needed.",
                false, usageContent));

        // Render each family (excluding Misc)
        boolean alt = false;
        for (var entry : grouped.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("Misc") || entry.getKey().equalsIgnoreCase("Miscellaneous")) {
                continue;
            }
            var familyGrid = new WaGrid<>();
            familyGrid.setMinColumnSize("16rem");
            familyGrid.setGap(PageSize.Small);

            for (ServiceDefinition service : entry.getValue()) {
                familyGrid.add(serviceCard(service));
            }

            var familySection = buildSection(entry.getValue().size() + " modules", entry.getKey(),
                    "JPMS-wrapped service modules for " + entry.getKey().toLowerCase() + " integration.",
                    alt, familyGrid);
            // Override ID to use family name slug for aside navigation
            String slug = entry.getKey().toLowerCase()
                               .replaceAll("[^a-z0-9]+", "-")
                               .replaceAll("^-+|-+$", "");
            familySection.setID(slug);
            layout.add(familySection);
            alt = !alt;
        }
    }
    private WaCard<?> serviceCard(ServiceDefinition service)
    {
        var card = new WaCard<>();
        card.setAppearance(Appearance.Outlined);

        var header = new DivSimple<>();
        var headerCluster = new WaCluster<>();
        headerCluster.setGap(PageSize.Small);
        headerCluster.setSplit();
        headerCluster.add(headingText("h4", "s", service.getArtifactId()));
        var actionCluster = new WaCluster<>();
        actionCluster.setGap(PageSize.Small);
        actionCluster.add(serviceHeaderIcon(service.getModulePath()));
        headerCluster.add(actionCluster);
        header.add(headerCluster);
        card.withHeader(header);

        var stack = new WaStack<>();
        stack.setGap(PageSize.Small);

        var desc = bodyText(service.getDescription(), "s");
        desc.setWaColorText("quiet");
        stack.add(desc);

        card.add(stack);

        var footer = new DivSimple<>();
        footer.add(coordinateBlock(service.getGroupId() + ":" + service.getArtifactId() + ":" + service.getVersion()));
        card.withFooter(footer);

        return card;
    }

}
