package com.guicedee.website;

import com.jwebmp.core.base.angular.client.services.TypescriptIndexPageConfigurator;
import com.jwebmp.core.base.references.CSSReference;
import com.jwebmp.core.services.IPage;
import com.jwebmp.core.services.IPageConfigurator;

public class WebsitePageConfigurator implements IPageConfigurator<WebsitePageConfigurator>, TypescriptIndexPageConfigurator<WebsitePageConfigurator>
{
    @Override
    public IPage<?> configure(IPage<?> page)
    {
        page.addCssReference(new CSSReference("GuicedEELanding", 1.0, "/guicedee-landing.css"));
        return page;
    }

    @Override
    public IPage<?> configureAngular(IPage<?> page) {
        page.addCssReference(new CSSReference("GuicedEELanding", 1.0, "/guicedee-landing.css"));
        return IPageConfigurator.super.configureAngular(page);
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
