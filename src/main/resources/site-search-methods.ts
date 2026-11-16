private searchWords(value: string): string[] {
    return value.normalize('NFKD').replace(/\p{M}/gu, '').toLowerCase().match(/[\p{L}\p{N}]+/gu) || [];
}

updateSiteSearch(value: string) {
    this.searchQuery = value || '';
    const terms = this.searchWords(this.searchQuery).slice(0, 12);
    this.searchExpanded = !!this.searchQuery.trim();
    if (!terms.length) {
        this.searchResults = [];
        this.searchResultCount = 0;
        return;
    }
    const ranked = this.searchEntries.map(entry => {
        const title = this.searchWords(entry.title).join(' ');
        const text = this.searchWords(entry.page + ' ' + entry.title + ' ' + entry.text).join(' ');
        if (!terms.every(term => text.includes(term))) return null;
        const score = terms.reduce((sum, term) => sum + (title.includes(term) ? 8 : 1), 0)
            + (title === terms.join(' ') ? 20 : 0)
            + (text.includes(terms.join(' ')) ? 20 : 0);
        const body = entry.text.toLowerCase();
        const phrasePosition = body.indexOf(this.searchQuery.trim().toLowerCase());
        const longestTerm = [...terms].sort((a, b) => b.length - a.length)[0];
        const position = phrasePosition >= 0 ? phrasePosition : body.indexOf(longestTerm);
        const start = Math.max(0, position - (this.searchQuery.trim().length > 24 ? 0 : 25));
        const excerpt = entry.text.slice(start, start + 150);
        return {...entry, score, snippet: (start ? '…' : '') + excerpt + (start + 150 < entry.text.length ? '…' : '')};
    }).filter((entry): entry is NonNullable<typeof entry> => entry !== null)
      .sort((a, b) => b.score - a.score || a.page.localeCompare(b.page) || a.title.localeCompare(b.title));
    this.searchResultCount = ranked.length;
    this.searchResults = ranked.slice(0, 12);
}

dismissSiteSearch(event: Event) {
    if (!event.composedPath().some(node => node instanceof HTMLElement && node.classList.contains('site-search'))) {
        this.searchExpanded = false;
    }
}

closeSiteSearch(event: Event) {
    event.preventDefault();
    event.stopPropagation();
    this.searchExpanded = false;
    const input = (event.target as HTMLElement)?.closest('.site-search')?.querySelector('wa-input') as any;
    input?.focus();
    // Focus can reopen the list; Escape explicitly dismisses it.
    this.searchExpanded = false;
}

openFirstSearchResult(event: Event) {
    event.preventDefault();
    if (this.searchResults[0]) void this.openSearchResult(this.searchResults[0]);
}

focusSearchResults(event: Event) {
    const result = (event.target as HTMLElement)?.closest('.site-search')?.querySelector('.site-search-result') as any;
    if (result) { event.preventDefault(); result.focus(); }
}

onSearchResultsKey(event: KeyboardEvent) {
    if (!['ArrowDown', 'ArrowUp', 'Home', 'End'].includes(event.key)) return;
    const container = (event.target as HTMLElement).closest('.site-search-results');
    const buttons = Array.from(container?.querySelectorAll('.site-search-result') || []) as any[];
    const current = buttons.indexOf((event.target as HTMLElement).closest('.site-search-result'));
    if (current < 0 || !buttons.length) return;
    event.preventDefault();
    if (event.key === 'ArrowUp' && current === 0) {
        (container?.closest('.site-search')?.querySelector('wa-input') as any)?.focus();
        return;
    }
    const next = event.key === 'Home' ? 0 : event.key === 'End' ? buttons.length - 1
        : Math.max(0, Math.min(buttons.length - 1, current + (event.key === 'ArrowDown' ? 1 : -1)));
    buttons[next].focus();
}

async openSearchResult(result: {route: string; anchor: string}) {
    this.searchExpanded = false;
    const page = this.document.querySelector('wa-page') as any;
    if (page) page.navOpen = false;
    const tree = this.router.createUrlTree(['/' + result.route], {fragment: result.anchor});
    // A repeat selection of the current fragment still reveals the destination.
    const current = this.router.parseUrl(this.router.url);
    const currentRoute = (current.root.children['primary']?.segments || []).map(segment => segment.path).join('/');
    if (currentRoute === result.route && current.fragment === result.anchor) {
        await this.revealSearchFragment(this.router.url);
    } else {
        await this.router.navigateByUrl(tree);
        await this.revealSearchFragment(this.router.url);
    }
}

async revealSearchFragment(url: string) {
    const token = ++this.searchRevealToken;
    const parsed = this.router.parseUrl(url);
    const route = (parsed.root.children['primary']?.segments || []).map(segment => segment.path).join('/');
    const entry = this.searchEntries.find(item => item.route === route && item.anchor === parsed.fragment);
    this.document.querySelectorAll('.site-search-target').forEach(element => element.classList.remove('site-search-target'));
    if (!entry) return;
    await new Promise(resolve => setTimeout(resolve, 350));
    // NavigationEnd may precede the final DOM update or the aside synchronization.
    let target: HTMLElement | null = null;
    for (let attempt = 0; attempt < 90; attempt++) {
        if (token !== this.searchRevealToken) return;
        target = this.document.getElementById(entry.anchor);
        if (target) break;
        await new Promise(resolve => setTimeout(resolve, 16));
    }
    if (!target || token !== this.searchRevealToken) return;
    const ancestors: HTMLElement[] = [];
    for (let parent = target.parentElement; parent; parent = parent.parentElement) ancestors.unshift(parent);
    for (const ancestor of ancestors) {
        if (ancestor.localName === 'wa-details') {
            await customElements.whenDefined('wa-details');
            if (token !== this.searchRevealToken) return;
            (ancestor as any).open = true;
        }
        if (ancestor.localName === 'details') (ancestor as any).open = true;
        if (ancestor.localName === 'wa-tab-panel') {
            (ancestor.closest('wa-tab-group') as any)?.show(ancestor.getAttribute('name'));
        }
        if (ancestor.localName === 'wa-carousel-item') {
            const carousel = ancestor.closest('wa-carousel') as any;
            if (carousel) {
                await customElements.whenDefined('wa-carousel');
                if (token !== this.searchRevealToken) return;
                const slides = Array.from(carousel.querySelectorAll(':scope > wa-carousel-item'));
                carousel.goToSlide(slides.indexOf(ancestor), 'auto');
            }
        }
    }
    // Let disclosure animations and the carousel's content height settle before scrolling.
    await new Promise(resolve => setTimeout(resolve, 400));
    if (token !== this.searchRevealToken || !target.isConnected) return;
    target.classList.add('site-search-target');
    target.scrollIntoView({block: 'center', behavior: 'auto'});
    target.focus({preventScroll: true});
    clearTimeout(this.searchHighlightTimer);
    this.searchHighlightTimer = setTimeout(() => target?.classList.remove('site-search-target'), 6500);
}
