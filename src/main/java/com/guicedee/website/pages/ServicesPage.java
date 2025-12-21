package com.guicedee.website.pages;

import com.guicedee.website.builder.ApplicationBuilderService;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.WaStack;

@NgRoutable(path = "services")
public class ServicesPage extends WebsitePage<ServicesPage>
{
    public ServicesPage()
    {
        renderServices();
    }

    private void renderServices()
    {
        var stack = newMainSection();
        ApplicationBuilderService.getInstance().getAvailableServices().forEach(service ->
        {
            var block = new WaStack();
            block.add(service.getArtifactId() + " — " + service.getDescription());
            stack.add(block);
        });
    }
}

