onNavigationExpanded(event: Event, route: string) {
    const item = event.target as HTMLElement & {expanded?: boolean};
    if (item?.getAttribute('data-navigation-route') === route && item.expanded) {
        this.expandedNavigation.set(route);
    }
}

onNavigationCollapsed(event: Event, route: string) {
    const item = event.target as HTMLElement & {expanded?: boolean};
    if (item?.getAttribute('data-navigation-route') === route && !item.expanded && this.expandedNavigation() === route) {
        this.expandedNavigation.set('');
    }
}

onNavigationPageClick(event: MouseEvent, route: string) {
    if (event.button !== 0 || event.ctrlKey || event.metaKey || event.shiftKey || event.altKey) return;
    // Also restore the current page's branch when Angular skips a same-URL navigation.
    this.expandedNavigation.set(route);
    const page = this.document.querySelector('wa-page') as any;
    if (page) page.navOpen = false;
}

openNavigationSection(event: MouseEvent, route: string, anchor: string) {
    if (event.button !== 0 || event.ctrlKey || event.metaKey || event.shiftKey || event.altKey) return;
    event.preventDefault();
    void this.openSearchResult({route, anchor});
}
