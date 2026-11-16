package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-end-to-end-aside")
@NgRoutable(path = "guides/end-to-end", outlet = "aside")
public class EndToEndGuideAsidePage extends DivSimple<EndToEndGuideAsidePage> implements INgComponent<EndToEndGuideAsidePage> {
    public EndToEndGuideAsidePage() { setTag("ng-container"); }
}
