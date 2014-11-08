package it.cosenonjaviste.mvp.post;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.cosenonjaviste.model.Post;

@Singleton
public class PostDetailUrlManager {

    @Inject public PostDetailUrlManager() {
    }

    public String getUrl(Post post) {
        return post.getUrl();
    }
}
