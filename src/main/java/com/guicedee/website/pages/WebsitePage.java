package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesomepro.components.page.WaPage;

public abstract class WebsitePage<J extends WebsitePage<J>> extends WaPage<J> implements INgComponent<J>
{
    protected WebsitePage()
    {
        configureLayout();
    }

    private void configureLayout()
    {
        getHeader().add("GuicedEE Public Website - v2.0.0-SNAPSHOT");
        configureMenu();
    }

    private void configureMenu()
    {
        var menu = getMenu();
        menu.add(createNavLink("Home", "/home"));
        menu.add(createNavLink("Capabilities", "/capabilities"));
        menu.add(createNavLink("Services", "/services"));
        menu.add(createNavLink("App Builder", "/builder"));
        menu.add(createNavLink("Media", "/media"));
        menu.add(createNavLink("Releases", "/releases"));
        menu.add(createNavLink("GitHub", "/github"));
    }

    private Link<?> createNavLink(String label, String route)
    {
        var link = new Link<>();
        link.addClass("wa-page-menu-link");
        link.addAttribute("[routerLink]", "['" + route + "']");
        link.add(label);
        return link;
    }

    protected WaStack newMainSection()
    {
        var stack = new WaStack();
        getMain().add(stack);
        return stack;
    }
}
