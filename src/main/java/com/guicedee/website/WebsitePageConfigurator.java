package com.guicedee.website;

import com.jwebmp.core.Page;
import com.jwebmp.core.base.angular.client.services.TypescriptIndexPageConfigurator;
import com.jwebmp.core.base.references.CSSReference;
import com.jwebmp.core.services.IPage;
import com.jwebmp.core.services.IPageConfigurator;
import com.jwebmp.plugins.fontawesome5pro.FontAwesome5ProPageConfigurator;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

public class WebsitePageConfigurator implements IPageConfigurator<WebsitePageConfigurator>, TypescriptIndexPageConfigurator<WebsitePageConfigurator>
{
    @Override
    public IPage<?> configure(IPage<?> page)
    {
        page.addCssReference(new CSSReference("GuicedEELanding", 1.0, "/guicedee-landing.css"));
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
        FontAwesome5ProPageConfigurator.setKitCode("3f59d88b7f");
        Page<?> p = (Page<?>) page;
        p.getOptions().setFavIcon("/guicedee-logo.svg");
        p.getOptions().setIcon("/guicedee-logo.svg", "any");
        return page;
    }

    @Override
    public IPage<?> configureAngular(IPage<?> page) {
        return configure(page);
    }

    @Override
    public boolean enabled()
    {
        return true;
    }

    @Override
    public Integer sortOrder()
    {
        return 200;
    }
}
