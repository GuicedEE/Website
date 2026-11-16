package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgApp;
import com.jwebmp.core.base.angular.services.NGApplication;
import com.guicedee.client.IGuiceContext;
import com.jwebmp.core.base.angular.modules.services.base.EnvironmentModule;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;
import com.jwebmp.core.base.html.Meta;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.core.base.html.Script;

@NgApp(value = "guicedee-website", bootComponent = WebsiteBoot.class)
public class WebsiteApplication extends NGApplication<WebsiteApplication>
{
    public WebsiteApplication()
    {
        getOptions().setTitle("GuicedEE — E2E Modular Java, Reactive by Nature");
        addAttribute("lang", "en");
        String description = "Build modular Java and Kotlin services with Guice, Vert.x, reactive persistence, cloud SDKs, MicroProfile, jlink and Native-Image support.";
        getHead().add(new Meta(Meta.MetadataFields.Description, description));
        getHead().add(new Meta().addAttribute("name", "robots").addAttribute("content", "index, follow"));
        getHead().add(new Meta().addAttribute("property", "og:type").addAttribute("content", "website"));
        getHead().add(new Meta().addAttribute("property", "og:site_name").addAttribute("content", "GuicedEE"));
        getHead().add(new Meta().addAttribute("property", "og:title").addAttribute("content", "GuicedEE — Modular Java and Kotlin"));
        getHead().add(new Meta().addAttribute("property", "og:description").addAttribute("content", description));
        getHead().add(new Meta().addAttribute("name", "twitter:card").addAttribute("content", "summary"));
        getHead().add(new Meta().addAttribute("name", "twitter:description").addAttribute("content", description));
        getHead().add(new Link<>().setTag("link").addAttribute("rel", "describedby").addAttribute("href", "/llms.txt").addAttribute("type", "text/plain"));
        getHead().add(new Link<>().setTag("link").addAttribute("rel", "manifest").addAttribute("href", "/manifest.webmanifest"));
        getHead().add(new Script<>().addAttribute("type", "application/ld+json").setText("""
                {"@context":"https://schema.org","@type":"WebSite","name":"GuicedEE","url":"https://guicedee.com/","inLanguage":"en","description":"Modular Java and Kotlin services with Guice and Vert.x"}
                """));
        WebAwesomePageConfigurator.setWaKitCode("6ea54e8336d3409b");
        IGuiceContext.get(EnvironmentModule.class).getEnvironmentOptions().setProduction(true);
    }

}
