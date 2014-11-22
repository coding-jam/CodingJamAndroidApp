package it.cosenonjaviste.mvp.page;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public class PageModel {
    private static final String URL = "url";

    String url;

    PageModel() {
    }

    public PageModel(PresenterArgs args) {
        url = args.getObject(URL);
    }

    public PageModel(String url) {
        this.url = url;
    }

    public static PresenterArgs populateArgs(PresenterArgs args, String url) {
        args.putObject(URL, url);
        return args;
    }

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
