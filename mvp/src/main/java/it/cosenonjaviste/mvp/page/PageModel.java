package it.cosenonjaviste.mvp.page;

import it.cosenonjaviste.model.Post;

public class PageModel {
    String url;

    public String getUrl() {
        return url;
    }

    public void setPost(Post post) {
        this.url = post.getUrl();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
