package com.guicedee.website.pages;

import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesomepro.components.page.WaPage;

public abstract class WebsitePage<J extends WebsitePage<J>> extends WaPage<J>
{
    protected WebsitePage()
    {
        configureLayout();
    }

    private void configureLayout()
    {
        getHeader().add("GuicedEE Public Website");
        configureMenu();
    }

    private void configureMenu()
    {
        var menu = getMenu();
        menu.add(createNavLink("Home", "home"));
        menu.add(createNavLink("Capabilities", "capabilities"));
        menu.add(createNavLink("Services", "services"));
        menu.add(createNavLink("App Builder", "builder"));
        menu.add(createNavLink("Media", "media"));
        menu.add(createNavLink("Releases", "releases"));
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
