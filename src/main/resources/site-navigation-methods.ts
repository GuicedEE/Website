onNavigationExpanded(event: Event, route: string) {
    const item = event.target as HTMLElement & {expanded?: boolean};
    if (item?.getAttribute('data-navigation-route') === route && item.expanded) {
        if (this.expandedNavigation() !== route) this.expandedNavigationGroup.set('');
        this.expandedNavigation.set(route);
    }
}

onNavigationCollapsed(event: Event, route: string) {
    const item = event.target as HTMLElement & {expanded?: boolean};
    if (item?.getAttribute('data-navigation-route') === route && !item.expanded && this.expandedNavigation() === route) {
        this.expandedNavigation.set('');
        this.expandedNavigationGroup.set('');
    }
}

onNavigationPageClick(event: MouseEvent, route: string) {
    if (event.button !== 0 || event.ctrlKey || event.metaKey || event.shiftKey || event.altKey) return;
    // Also restore the current page's branch when Angular skips a same-URL navigation.
    this.expandedNavigation.set(route);
    this.expandedNavigationGroup.set('');
    const page = this.document.querySelector('wa-page') as any;
    if (page) page.navOpen = false;
}

openNavigationSection(event: MouseEvent, route: string, anchor: string) {
    if (event.button !== 0 || event.ctrlKey || event.metaKey || event.shiftKey || event.altKey) return;
    event.preventDefault();
    void this.openSearchResult({route, anchor});
}

onNavigationGroupExpanded(event: Event, key: string) {
    const item = event.target as HTMLElement & {expanded?: boolean};
    if (item?.getAttribute('data-navigation-group') === key && item.expanded) {
        this.expandedNavigationGroup.set(key);
    }
}

onNavigationGroupCollapsed(event: Event, key: string) {
    const item = event.target as HTMLElement & {expanded?: boolean};
    if (item?.getAttribute('data-navigation-group') === key && !item.expanded && this.expandedNavigationGroup() === key) {
        this.expandedNavigationGroup.set('');
    }
}

toggleNavigationGroup(event: Event, key: string) {
    event.preventDefault();
    event.stopPropagation();
    this.expandedNavigationGroup.set(this.expandedNavigationGroup() === key ? '' : key);
}

onNavigationGroupKey(event: KeyboardEvent, key: string) {
    if (event.target === event.currentTarget && ['Enter', ' '].includes(event.key)) {
        this.toggleNavigationGroup(event, key);
    }
}

async revealNavigationGroup(route: string, target: HTMLElement) {
    const groups = this.navigationGroups[route] || {};
    let element: HTMLElement | null = target;
    while (element && !groups[element.id]) element = element.parentElement;
    this.expandedNavigationGroup.set(element ? groups[element.id] : '');
    // Nested tree items can reset their expanded attribute during the kit's first
    // custom-element upgrade. Reapply the current state after that render settles.
    const token = this.searchRevealToken;
    await customElements.whenDefined('wa-tree-item');
    const items = Array.from(this.document.querySelectorAll('.navigation-group')) as any[];
    await Promise.all(items.map(item => item.updateComplete));
    if (token !== this.searchRevealToken || !target.isConnected) return;
    for (const item of items) {
        item.expanded = item.getAttribute('data-navigation-group') === this.expandedNavigationGroup()
            && this.expandedNavigation() === route;
    }
}
