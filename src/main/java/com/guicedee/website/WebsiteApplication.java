package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgApp;
import com.jwebmp.core.base.angular.services.NGApplication;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

@NgApp(value = "guicedee-website", bootComponent = WebsiteBoot.class)
public class WebsiteApplication extends NGApplication<WebsiteApplication>
{
    public WebsiteApplication()
    {
        getOptions().setTitle("GuicedEE — E2E Modular Java, Reactive by Nature");
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
    }

}
