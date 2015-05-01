package it.cosenonjaviste.model;

import java.util.List;

public class PostResponse {
    private List<Post> posts;

    public PostResponse() {
    }

    public PostResponse(List<Post> posts) {
        this();
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
