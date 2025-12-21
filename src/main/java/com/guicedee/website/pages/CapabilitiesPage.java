package com.guicedee.website.pages;

import com.guicedee.website.catalog.ModuleCatalog;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.card.WaCard;
import com.jwebmp.webawesomepro.components.page.WaPageContentsAside;

@NgRoutable(path = "capabilities")
public class CapabilitiesPage extends WebsitePage<CapabilitiesPage>
{
    public CapabilitiesPage()
    {
        packCapabilities();
    }

    private void packCapabilities()
    {
        var hero = newMainSection();
        hero.add("Showcase of features, architecture diagrams, and module-to-service relationships.");

        var catalogSection = newMainSection();
        catalogSection.add("GuicedEE Module Catalog (from README)");
        ModuleCatalog.getModules().forEach(module ->
        {
            var card = new WaCard<>();
            card.add(module.getName() + " (" + module.getId() + ")");
            card.add("Boot: " + module.getBootClass());
            card.add("Docs: " + module.getReadmePath());
            card.add("Rules: " + module.getRulesPath());
            catalogSection.add(card);
        });

        var aside = new WaPageContentsAside<>();
        aside.add("Technical deep dives")
             .add("Module story")
             .add("Media galleries");
        getAside().add(aside);
    }
}

