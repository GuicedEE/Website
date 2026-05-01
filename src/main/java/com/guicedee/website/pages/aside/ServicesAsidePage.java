package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.text.WaText;

/**
 * Aside component for the Services page.
 * Renders "On this page" anchor links for service family groups.
 */
@NgComponent("guicedee-services-aside")
@NgRoutable(path = "services", outlet = "aside")
public class ServicesAsidePage extends DivSimple<ServicesAsidePage> implements INgComponent<ServicesAsidePage>
{
    public ServicesAsidePage()
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

        list.add(asideLink("how-to-use", "How to use"));
        list.add(asideLink("apache-commons", "Apache Commons"));
        list.add(asideLink("apache-cxf-web-services", "Apache CXF"));
        list.add(asideLink("apache-poi", "Apache POI"));
        list.add(asideLink("database-driver", "Database"));
        list.add(asideLink("google", "Google"));
        list.add(asideLink("hibernate", "Hibernate"));
        list.add(asideLink("jakarta", "Jakarta"));
        list.add(asideLink("jcache", "JCache"));
        list.add(asideLink("jni", "JNI"));
        list.add(asideLink("library", "Library"));
        list.add(asideLink("microprofile", "MicroProfile"));
        list.add(asideLink("vert-x", "Vert.x"));

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

