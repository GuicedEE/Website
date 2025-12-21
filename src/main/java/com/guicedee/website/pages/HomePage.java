package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesomepro.components.page.WaPage;
import com.jwebmp.webawesomepro.components.page.WaPageContentsMain;
import com.jwebmp.webawesomepro.components.page.WaPageNavigationToggle;
import com.jwebmp.webawesomepro.components.page.WaPageFooter;

@NgRoutable(path = "home")
public class HomePage extends WaPage<HomePage>
{
    public HomePage()
    {
        getMain().add("Welcome to the GuicedEE website.");
    }
}

