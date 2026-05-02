package com.guicedee.website;

import com.jwebmp.core.Page;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportProvider;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.references.NgComponentReference;
import com.jwebmp.core.base.angular.client.services.TypescriptIndexPageConfigurator;
import com.jwebmp.core.base.references.CSSReference;
import com.jwebmp.core.services.IPage;
import com.jwebmp.core.services.IPageConfigurator;
import com.jwebmp.plugins.fontawesome5pro.FontAwesome5ProPageConfigurator;
import com.jwebmp.webawesome.components.WebAwesomePageConfigurator;

@NgComponentReference(MarkdownClipboardButton.class)
@NgBootImportProvider(value = "provideMarkdown({ mermaidOptions: { provide: MERMAID_OPTIONS, useValue: { startOnLoad: false } }, clipboardOptions: { provide: CLIPBOARD_OPTIONS, useValue: { buttonComponent: MarkdownClipboardButton } } })", overrides = true)
@NgBootImportReference(value = "provideMarkdown", reference = "ngx-markdown")
@NgBootImportReference(value = "MERMAID_OPTIONS", reference = "ngx-markdown")
@NgBootImportReference(value = "CLIPBOARD_OPTIONS", reference = "ngx-markdown")
@NgBootImportReference(value = "MarkdownClipboardButton", reference = "./com/guicedee/website/MarkdownClipboardButton/MarkdownClipboardButton")
public class WebsitePageConfigurator implements IPageConfigurator<WebsitePageConfigurator>, TypescriptIndexPageConfigurator<WebsitePageConfigurator>
{
    @Override
    public IPage<?> configure(IPage<?> page)
    {
        page.addCssReference(new CSSReference("GuicedEEBase", 1.0, "/base.css"));
        page.addCssReference(new CSSReference("GuicedEELayout", 1.0, "/layout.css"));
        page.addCssReference(new CSSReference("GuicedEEComponents", 1.0, "/components.css"));
        page.addCssReference(new CSSReference("GuicedEEFeatures", 1.0, "/features.css"));
        page.addCssReference(new CSSReference("GuicedEECode", 1.0, "/code.css"));
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
