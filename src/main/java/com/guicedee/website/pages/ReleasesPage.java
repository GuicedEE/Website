package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.webawesome.components.WaStack;

@NgRoutable(path = "releases")
public class ReleasesPage extends WebsitePage<ReleasesPage>
{
    public ReleasesPage()
    {
        newMainSection().add("Release announcements, changelogs, and deployment dashboards.");
    }
}

