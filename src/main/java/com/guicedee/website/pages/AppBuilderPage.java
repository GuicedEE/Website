package com.guicedee.website.pages;

import com.guicedee.website.builder.ApplicationBuilderService;
import com.guicedee.website.catalog.ModuleEntry;
import com.guicedee.website.catalog.ServiceDefinition;
import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.card.WaCard;

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
        var stack = newMainSection();
        stack.add("Configure modules and services, then click Build App to receive a ZIP.");

        ApplicationBuilderService.getInstance().getAvailableTemplates().forEach(template ->
        {
            var card = new com.jwebmp.webawesome.components.card.WaCard<>();
            card.add(template);
            stack.add(card);
        });

        stack.add("Modules");
        ApplicationBuilderService.getInstance()
                                 .getAvailableModules()
                                 .forEach(module -> addModuleCard(stack, module));

        stack.add("Services");
        ApplicationBuilderService.getInstance()
                                 .getAvailableServices()
                                 .forEach(service -> addServiceCard(stack, service));
    }

    private void addModuleCard(WaStack target, ModuleEntry module)
    {
        var card = new WaCard<>();
        card.add(module.getName() + " — " + module.getDescription());
        card.add("Boot: " + module.getBootClass());
        card.add("Coordinates: " + module.getGroupId() + ":" + module.getArtifactId() + ":" + module.getVersion());
        card.add("Docs: " + module.getReadmePath());
        card.add("Rules: " + module.getRulesPath());
        target.add(card);
    }

    private void addServiceCard(WaStack target, ServiceDefinition service)
    {
        var card = new WaCard<>();
        card.add(service.getArtifactId() + " — " + service.getDescription());
        card.add("Coordinates: " + service.getGroupId() + ":" + service.getArtifactId() + ":" + service.getVersion());
        card.add("Module path: " + service.getModule());
        target.add(card);
    }
}
