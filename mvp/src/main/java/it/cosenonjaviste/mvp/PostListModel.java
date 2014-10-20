package it.cosenonjaviste.mvp;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.model.Post;


public class PostListModel {

    List<Post> posts = new ArrayList<>();

    boolean reloadVisible;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean isReloadVisible() {
        return reloadVisible;
    }

    public void setReloadVisible(boolean reloadVisible) {
        this.reloadVisible = reloadVisible;
    }
}
