package com.guicedee.website.pages.aside;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;

/** Compatibility route for old aside URLs; WebsiteBoot removes the named outlet. */
@NgComponent("guicedee-environment-variables-aside")
@NgRoutable(path = "environment-variables", outlet = "aside")
public class EnvironmentVariablesAsidePage extends DivSimple<EnvironmentVariablesAsidePage> implements INgComponent<EnvironmentVariablesAsidePage> {
    public EnvironmentVariablesAsidePage() { setTag("ng-container"); }
}
