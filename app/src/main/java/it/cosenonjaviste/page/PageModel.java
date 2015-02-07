package it.cosenonjaviste.page;

import org.parceler.Parcel;

@Parcel
public class PageModel {

    String url;

    PageModel() {
    }

    public PageModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
