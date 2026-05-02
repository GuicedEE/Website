package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.Link;
import com.jwebmp.webawesome.components.input.InputSize;
import com.jwebmp.webawesome.components.input.WaInput;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.text.WaText;

import java.util.ArrayList;
import java.util.List;

/**
 * Aside component for the Environment Variables page.
 * Renders "On this page" anchor links and a search filter for environment variable sections.
 */
@NgComponent("guicedee-environment-variables-aside")
@NgRoutable(path = "environment-variables", outlet = "aside")
public class EnvironmentVariablesAsidePage extends DivSimple<EnvironmentVariablesAsidePage> implements INgComponent<EnvironmentVariablesAsidePage>
{
    public EnvironmentVariablesAsidePage()
    {
        setTag("aside");
        addClass("page-aside");
        addStyle("position:sticky");
        addStyle("top:var(--wa-spacing-large)");
        addStyle("padding:0 var(--wa-spacing-large) var(--wa-spacing-large) var(--wa-spacing-large)");
        addStyle("min-width:14rem");

        // Search input
        var search = new WaInput<>();
        search.setPlaceholder("Filter variables\u2026");
        search.setSize(InputSize.Small);
        search.setClearable(true);
        search.addAttribute("(wa-input)", "onFilterChange($event)");
        search.addAttribute("(wa-clear)", "onFilterChange($event)");

        var searchIcon = new WaIcon<>("magnifying-glass");
        searchIcon.addAttribute("slot", "start");
        search.add(searchIcon);
        search.addStyle("margin-bottom", "var(--wa-spacing-medium)");
        add(search);

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

        list.add(asideLink("injection-runtime", "Inject"));
        list.add(asideLink("json", "JSON"));
        list.add(asideLink("http-server", "Web"));
        list.add(asideLink("vert-x-runtime-consumers", "Vert.x"));
        list.add(asideLink("eventbus", "EventBus"));
        list.add(asideLink("filesystem", "FileSystem"));
        list.add(asideLink("address-resolver", "Address Resolver"));
        list.add(asideLink("rest-client", "REST Client"));
        list.add(asideLink("rest-cors", "REST CORS"));
        list.add(asideLink("authentication-authorization", "Auth"));
        list.add(asideLink("health-checks", "Health"));
        list.add(asideLink("metrics", "Metrics"));
        list.add(asideLink("telemetry", "Telemetry"));
        list.add(asideLink("rabbitmq", "RabbitMQ"));
        list.add(asideLink("kafka", "Kafka"));
        list.add(asideLink("ibm-mq", "IBM MQ"));
        list.add(asideLink("persistence", "Persistence"));
        list.add(asideLink("web-services", "Web Services"));
        list.add(asideLink("mcp", "MCP"));

        add(list);
    }

    @Override
    public List<String> methods()
    {
        var m = new ArrayList<String>();
        m.add("""
                onFilterChange(event: any) {
                    const query = (event?.target?.value || '').toLowerCase().trim();
                    const sections = document.querySelectorAll('.content-section');
                    sections.forEach((section: any) => {
                        const text = section.textContent?.toLowerCase() || '';
                        if (!query || text.includes(query)) {
                            section.style.display = '';
                        } else {
                            section.style.display = 'none';
                        }
                    });
                }
                """);
        return m;
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



