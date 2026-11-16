package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-modules-aside")
@NgRoutable(path = "modules", outlet = "aside")
public class ModulesAsidePage extends DivSimple<ModulesAsidePage> implements INgComponent<ModulesAsidePage> {
    public ModulesAsidePage() { setTag("ng-container"); }
}
