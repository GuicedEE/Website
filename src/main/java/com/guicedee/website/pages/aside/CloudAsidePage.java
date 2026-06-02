package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.text.WaText;

/**
 * Aside component for the Cloud page.
 * Renders "On this page" anchor links for cloud/service-registry sections.
 */
@NgComponent("guicedee-cloud-aside")
@NgRoutable(path = "cloud", outlet = "aside")
public class CloudAsidePage extends DivSimple<CloudAsidePage> implements INgComponent<CloudAsidePage>
{
    public CloudAsidePage()
    {
        setTag("aside");
        addClass("page-aside");
        addStyle("position:sticky");
        addStyle("top:var(--wa-spacing-large)");
        addStyle("padding:0 var(--wa-spacing-large) var(--wa-spacing-large) var(--wa-spacing-large)");
        addStyle("min-width:14rem");

        var heading = new WaText<>();
        heading.setTag("div");
        heading.setWaCaption("s");
        heading.setWaFontWeight("semibold");
        heading.addClass("hero-eyebrow");
        heading.setText("On this page");
        add(heading);

        var list = new DivSimple<>();
        list.setTag("ul");
        list.addStyle("list-style:none");
        list.addStyle("padding:0");
        list.addStyle("margin:var(--wa-spacing-small) 0 0 0");
        list.addStyle("display:flex");
        list.addStyle("flex-direction:column");
        list.addStyle("gap:var(--wa-spacing-x-small)");

        list.add(asideLink("architecture", "How It Works"));
        list.add(asideLink("platforms", "Platform Detection"));
        list.add(asideLink("declaration", "Declaring Services"));
        list.add(asideLink("resolution", "URL Resolution"));
        list.add(asideLink("rest-client", "REST Client Integration"));
        list.add(asideLink("health", "Health Monitoring"));
        list.add(asideLink("spi", "SPI & Extensions"));
        list.add(asideLink("configuration", "Configuration"));
        list.add(asideLink("installation", "Getting Started"));

        add(list);
    }

    private DivSimple<?> asideLink(String anchorId, String label)
    {
        var li = new DivSimple<>();
        li.setTag("li");

        var link = new Link<>();
        link.setTag("a");
        link.addAttribute("href", "javascript:void(0)");
        link.addAttribute("onclick",
                "document.getElementById('" + anchorId + "').scrollIntoView({behavior:'smooth'})");
        link.setText(label);
        li.add(link);

        return li;
    }
}

