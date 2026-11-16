package com.guicedee.website;

import com.jwebmp.core.base.angular.components.NgFor;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.webawesome.components.Variant;
import com.jwebmp.webawesome.components.button.Appearance;
import com.jwebmp.webawesome.components.button.WaButton;
import com.jwebmp.webawesome.components.input.InputSize;
import com.jwebmp.webawesome.components.input.WaInput;
import com.jwebmp.webawesome.components.icon.WaIcon;
import com.jwebmp.webawesome.components.popup.WaPopup;
import com.jwebmp.webawesome.components.popup.WaPopupPlacements;
import com.jwebmp.webawesome.components.text.WaText;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** The same search controls appear above Home on desktop and in the mobile drawer. */
final class SiteSearch extends DivSimple<SiteSearch> {
    SiteSearch(String location) {
        setTag("search");
        addClass("site-search");
        addAttribute("aria-label", "Search GuicedEE documentation");
        addAttribute("(keydown.escape)", "closeSiteSearch($event)");
        addAttribute("(document:pointerdown)", "dismissSiteSearch($event)");
        var input = new WaInput<>();
        input.setPlaceholder("Search…");
        input.setSize(InputSize.Medium);
        input.setClearable(true);
        var icon = new WaIcon<>();
        icon.setSrc("/search.svg");
        icon.addClass("site-search-icon");
        icon.addAttribute("aria-hidden", "true");
        input.setStart(icon);
        input.addAttribute("aria-label", "Search GuicedEE");
        input.addAttribute("[value]", "searchQuery");
        input.addAttribute("(input)", "searchLocation = '" + location + "'; updateSiteSearch($any($event.target).value)");
        input.addAttribute("(wa-clear)", "updateSiteSearch('')");
        input.addAttribute("(focusin)", "searchLocation = '" + location + "'; searchExpanded = !!searchQuery.trim()");
        input.addAttribute("(keydown.enter)", "openFirstSearchResult($event)");
        input.addAttribute("(keydown.arrowdown)", "focusSearchResults($event)");
        input.addAttribute("aria-controls", "site-search-results-" + location);
        input.addAttribute("[attr.aria-expanded]", "searchExpanded && !!searchQuery.trim()");
        var popup = new WaPopup<>();
        popup.addClass("site-search-popup");
        popup.setPlacement(WaPopupPlacements.BottomStart);
        popup.setStrategy("fixed");
        popup.setDistance(8);
        popup.setShift(true);
        popup.setShiftPadding(12);
        popup.setFlip(true);
        popup.addAttribute("[active]", "searchExpanded && !!searchQuery.trim() && searchLocation === '" + location + "'");
        input.addAttribute("slot", "anchor");
        popup.add(input);
        add(popup);

        var results = new DivSimple<>();
        results.setID("site-search-results-" + location);
        results.addClass("site-search-results");
        results.addAttribute("[hidden]", "!searchExpanded || !searchQuery.trim() || searchLocation !== '" + location + "'");
        results.addAttribute("aria-label", "Search results");
        results.addAttribute("(keydown)", "onSearchResultsKey($event)");
        var count = new WaText<>().setTag("p").setWaCaption("s");
        count.addClass("site-search-count");
        count.addAttribute("role", "status");
        count.setText("{{searchResultCount === 0 ? 'No matches. Try a module, feature or annotation.' : searchResultCount + ' matches' + (searchResultCount > 12 ? ' · showing 12' : '')}}");
        results.add(count);
        var rows = new NgFor<>("result", "searchResults", "result.route + '#' + result.anchor");
        var result = new WaButton<>();
        result.setAppearance(Appearance.Plain);
        result.setVariant(Variant.Neutral);
        result.addClass("site-search-result");
        result.addAttribute("(click)", "openSearchResult(result)");
        result.addAttribute("[attr.aria-label]", "result.page + ': ' + result.title");
        result.addAttribute("[attr.data-search-destination]", "result.route + '#' + result.anchor");
        result.add(new WaText<>().setTag("span").setWaCaption("s").setText("{{result.page}}").addClass("site-search-page"));
        result.add(new WaText<>().setTag("span").setWaBody("s").setText("{{result.title}}").addClass("site-search-title"));
        result.add(new WaText<>().setTag("span").setWaCaption("s").setText("{{result.snippet}}").addClass("site-search-snippet"));
        rows.add(result);
        results.add(rows);
        popup.add(results);
    }

    static String fields() {
        return """
                searchQuery = '';
                searchExpanded = false;
                searchLocation = '';
                searchResultCount = 0;
                searchResults: Array<{route: string; page: string; anchor: string; title: string; text: string; snippet: string}> = [];
                private searchRevealToken = 0;
                private siteNavigationSubscription?: {unsubscribe(): void};
                private searchHighlightTimer?: ReturnType<typeof setTimeout>;
                """ + "private searchEntries = " + SiteSearchIndex.javascript() + ";";
    }

    static String methods() {
        try (var input = SiteSearch.class.getResourceAsStream("/site-search-methods.ts")) {
            if (input == null) throw new IllegalStateException("Missing site search methods");
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException error) { throw new IllegalStateException(error); }
    }
}
