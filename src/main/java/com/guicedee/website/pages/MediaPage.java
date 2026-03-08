package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.webawesome.components.PageSize;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.WaCluster;
import com.jwebmp.webawesome.components.WaGrid;
import com.jwebmp.webawesome.components.WaStack;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.card.WaCard;

@NgComponent("guicedee-media")
@NgRoutable(path = "media")
public class MediaPage extends WebsitePage<MediaPage> implements INgComponent<MediaPage>
{
    public MediaPage()
    {
        buildMediaPage();
    }

    private void buildMediaPage()
    {
        getMain().setPageSize(PageSize.ExtraLarge);

        var layout = new WaStack();
        layout.setGap(PageSize.ExtraLarge);
        getMain().add(layout);

        // Header
        var introContent = new WaStack();
        introContent.setGap(PageSize.Medium);
        introContent.add(headingText("h1", "xl", "Media & Resources"));
        var desc = bodyText("Presentations, diagrams, logos, and educational materials " +
                "for learning about and evangelizing the GuicedEE platform.", "l");
        desc.setWaColorText("quiet");
        introContent.add(desc);

        var introCard = new WaCard<>();
        introCard.setAppearance(Appearance.Filled);
        introCard.add(introContent);
        layout.add(introCard);

        // Architecture resources
        var archGrid = new WaGrid<>();
        archGrid.setMinColumnSize("16rem");
        archGrid.setGap(PageSize.Medium);
        archGrid.add(featureCard("Architecture diagrams",
                "C4 model, sequence diagrams, and ERD diagrams for all GuicedEE modules. " +
                        "Available as Mermaid sources under docs/architecture/.",
                "Mermaid + SVG"));
        archGrid.add(featureCard("Module relationship map",
                "Visual map showing how GuicedEE modules depend on each other " +
                        "and which JPMS services they provide.",
                "Interactive"));
        archGrid.add(featureCard("Lifecycle sequence",
                "Step-by-step visualization of the IGuiceContext startup lifecycle " +
                        "showing SPI discovery, scanning, and hook execution.",
                "Animated"));
        layout.add(buildSection("Architecture", "Visual documentation",
                "Diagrams and visualizations to help you understand the platform.", true, archGrid));

        // Ecosystem resources
        var ecoGrid = new WaGrid<>();
        ecoGrid.setMinColumnSize("16rem");
        ecoGrid.setGap(PageSize.Medium);
        ecoGrid.add(featureCard("AIRules",
                "AI agent rules, prompts, and workspace policies for " +
                        "consistent AI-assisted development.",
                "github.com/GuicedEE/AIRules"));
        ecoGrid.add(featureCard("JWebMP",
                "Server-side web component framework with Vert.x rendering. " +
                        "Angular + WebAwesome UI components built from Java.",
                "github.com/JWebMP"));
        ecoGrid.add(featureCard("EntityAssist",
                "Reactive entity CRUD framework built on GuicedEE Persistence. " +
                        "Type-safe queries, automatic auditing, and soft deletes.",
                "github.com/GuicedEE/EntityAssist"));
        ecoGrid.add(featureCard("ActivityMaster",
                "Domain-driven application modules for profiles, tasks, payments, " +
                        "notifications, forums, and more.",
                "github.com/GuicedEE/ActivityMaster"));

        layout.add(buildSection("Ecosystem", "Related projects",
                "GuicedEE is part of a larger ecosystem of modular Java projects.", false, ecoGrid));

        // CTA
        var ctaContent = new WaStack();
        ctaContent.setGap(PageSize.Medium);
        var ctaDesc = bodyText("Explore the platform and start building your next project.", "m");
        ctaContent.add(ctaDesc);
        var ctas = new WaCluster<>();
        ctas.setGap(PageSize.Small);
        ctas.add(buildCta("Get started", "getting-started", Variant.Brand, Appearance.Filled));
        ctas.add(buildCta("View capabilities", "capabilities", Variant.Neutral, Appearance.Outlined));
        ctaContent.add(ctas);
        layout.add(buildSection("Ready?", "Start building with GuicedEE",
                null, true, ctaContent));
    }
}
