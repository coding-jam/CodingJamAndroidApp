package it.cosenonjaviste.core.page;

import it.cosenonjaviste.model.Post;

public class PageModel {

    Post post;

    PageModel() {
    }

    public PageModel(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
