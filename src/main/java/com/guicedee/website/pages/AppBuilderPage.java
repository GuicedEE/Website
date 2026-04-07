package com.guicedee.website.pages;

import com.guicedee.website.builder.ApplicationBuilderService;
import com.guicedee.website.catalog.ModuleEntry;
import com.guicedee.website.catalog.ServiceDefinition;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesome.components.checkbox.WaCheckbox;

@NgComponent("guicedee-app-builder")
@NgRoutable(path = "builder")
public class AppBuilderPage extends WebsitePage<AppBuilderPage> implements INgComponent<AppBuilderPage>
{
    public AppBuilderPage()
    {
        renderBuilder();
    }

    private void renderBuilder()
    {

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Hero / intro
        var introContent = new WaStack();
        introContent.setGap(PageSize.Medium);
        introContent.add(headingText("h1", "xl", "Application Builder"));
        var desc = bodyText("Select the GuicedEE modules and services you need, pick a project template, " +
                "and download a ready-to-run ZIP with Maven POM, module-info.java, Boot class, and wiring snippets.", "l");
        desc.setWaColorText("quiet");
        introContent.add(desc);

        var tags = new WaCluster<>();
        tags.setGap(PageSize.Small);
        tags.add(buildTag("Interactive", Variant.Brand));
        tags.add(buildTag("Download ZIP", Variant.Success));
        tags.add(buildTag("Production ready", Variant.Neutral));
        introContent.add(tags);

        var introCard = new WaCard<>();
        introCard.setAppearance(Appearance.Filled);
        introCard.add(introContent);
        layout.add(introCard);

        // Templates
        var templateContent = new WaStack();
        templateContent.setGap(PageSize.Medium);

        var templateGrid = new WaGrid<>();
        templateGrid.setMinColumnSize("16rem");
        templateGrid.setGap(PageSize.Medium);

        for (String template : ApplicationBuilderService.getInstance().getAvailableTemplates())
        {
            var card = new WaCard<>();
            card.setAppearance(Appearance.Outlined);
            var stack = new WaStack();
            stack.setGap(PageSize.Small);
            stack.add(headingText("h3", "m", template));

            String templateDesc = switch (template)
            {
                case "WebAwesome SPA" ->
                        "A single-page application with JWebMP and WebAwesome components. " +
                        "Includes Angular TypeScript client, WaPage layout, and hot-reload development flow.";
                case "JWebMP Module" ->
                        "A server-rendered JWebMP module with Vert.x backend. " +
                        "WebAwesome components, JPMS-ready, and minimal frontend tooling.";
                case "Mixed Client/App" ->
                        "Combines a JWebMP frontend with full GuicedEE backend services. " +
                        "REST, persistence, health, and optional WebSocket support included.";
                default -> "Project template for GuicedEE applications.";
            };
            var body = bodyText(templateDesc, "s");
            body.setWaColorText("quiet");
            stack.add(body);

            var selectBtn = new WaButton<>("Select " + template, Variant.Neutral);
            selectBtn.setAppearance(Appearance.Outlined);
            stack.add(selectBtn);

            card.add(stack);
            templateGrid.add(card);
        }

        templateContent.add(templateGrid);
        layout.add(buildSection("Step 1", "Choose a project template",
                "Each template generates a complete, buildable Maven project with module-info.java and Boot class.",
                true, templateContent));

        // Modules selection
        var modulesContent = new WaStack();
        modulesContent.setGap(PageSize.Medium);

        var moduleInfo = bodyText("Check the modules you want included in your project. " +
                "The inject and client modules are always included. " +
                "The web module is included when any HTTP-based module is selected.", "m");
        moduleInfo.setWaColorText("quiet");
        modulesContent.add(moduleInfo);

        var moduleGrid = new WaGrid<>();
        moduleGrid.setMinColumnSize("18rem");
        moduleGrid.setGap(PageSize.Small);

        for (ModuleEntry module : ApplicationBuilderService.getInstance().getAvailableModules())
        {
            var card = new WaCard<>();
            card.setAppearance(Appearance.Outlined);
            var stack = new WaStack();
            stack.setGap(PageSize.Small);

            var checkbox = new WaCheckbox<>();
            checkbox.setText(module.getName());
            stack.add(checkbox);

            var modDesc = bodyText(module.getDescription(), "s");
            modDesc.setWaColorText("quiet");
            stack.add(modDesc);

            var coords = captionText(module.getGroupId() + ":" + module.getArtifactId());
            coords.setWaColorText("quiet");
            stack.add(coords);

            card.add(stack);
            moduleGrid.add(card);
        }

        modulesContent.add(moduleGrid);
        layout.add(buildSection("Step 2", "Select GuicedEE modules",
                "Each module adds its JPMS descriptor, Maven dependency, and wiring snippet to the generated project.",
                false, modulesContent));

        // Services selection
        var servicesContent = new WaStack();
        servicesContent.setGap(PageSize.Medium);

        var svcInfo = bodyText("Optionally include JPMS-wrapped service modules for third-party library integration. " +
                "These are pre-packaged with proper module-info.java descriptors.", "m");
        svcInfo.setWaColorText("quiet");
        servicesContent.add(svcInfo);

        var svcGrid = new WaGrid<>();
        svcGrid.setMinColumnSize("16rem");
        svcGrid.setGap(PageSize.Small);

        for (ServiceDefinition service : ApplicationBuilderService.getInstance().getAvailableServices())
        {
            var card = new WaCard<>();
            card.setAppearance(Appearance.Outlined);
            var stack = new WaStack();
            stack.setGap(PageSize.Small);

            var checkbox = new WaCheckbox<>();
            checkbox.setText(service.getArtifactId());
            stack.add(checkbox);

            var svcDesc = bodyText(service.getDescription(), "s");
            svcDesc.setWaColorText("quiet");
            stack.add(svcDesc);

            card.add(stack);
            svcGrid.add(card);
        }

        servicesContent.add(svcGrid);
        layout.add(buildSection("Step 3", "Add service modules (optional)",
                "JPMS-wrapped third-party libraries ready for JLink and JPackage.",
                true, servicesContent));

        // Build button section
        var buildContent = new WaStack();
        buildContent.setGap(PageSize.Medium);

        var buildDesc = bodyText("Click Build App to generate a ZIP archive containing your configured project. " +
                "The ZIP includes a Maven POM with all selected dependencies, a module-info.java with proper " +
                "requires/provides directives, a Boot class, and service-specific wiring snippets.", "l");
        buildContent.add(buildDesc);

        var buildCtas = new WaCluster<>();
        buildCtas.setGap(PageSize.Small);
        var buildBtn = new WaButton<>("Build App & Download ZIP", Variant.Brand);
        buildBtn.setAppearance(Appearance.Filled);
        buildCtas.add(buildBtn);
        buildContent.add(buildCtas);

        var whatYouGet = new WaGrid<>();
        whatYouGet.setMinColumnSize("14rem");
        whatYouGet.setGap(PageSize.Small);
        whatYouGet.add(featureCard("Maven POM", "Parent/child structure wired for Java 25 with BOM imports.", null));
        whatYouGet.add(featureCard("module-info.java", "Proper JPMS descriptor with requires, provides, opens.", null));
        whatYouGet.add(featureCard("Boot.java", "One-line bootstrap calling IGuiceContext.instance().", null));
        whatYouGet.add(featureCard("Wiring snippets", "Service-specific code showing how to configure each module.", null));
        buildContent.add(whatYouGet);

        layout.add(buildSection("Step 4", "Generate your project",
                "Download a production-ready ZIP and start coding immediately.",
                false, buildContent));
    }
}
