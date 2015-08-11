package it.cosenonjaviste.core.page;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PageUrlManager {

    @Inject public PageUrlManager() {
    }

    public String getUrl(String url) {
        return url;
    }
}
