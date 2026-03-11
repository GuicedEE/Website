package com.guicedee.website.pages;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.routing.NgRoutable;
import com.jwebmp.core.base.html.Script;

@NgComponent("guicedee-github")
@NgRoutable(path = "github")
public class GitHubPage extends WebsitePage<GitHubPage>
{
    public GitHubPage()
    {
        redirectToGitHub();
    }

    private void redirectToGitHub()
    {
        String url = "https://github.com/GuicedEE";
        var section = newMainSection();
        section.add("Redirecting to " + url);

        var script = new Script<>();
        script.setText("window.location.replace('" + url + "');");
        getMain().add(script);
    }
}
