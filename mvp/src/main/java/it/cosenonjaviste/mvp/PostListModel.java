package it.cosenonjaviste.mvp;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.model.Post;


public class PostListModel {

    LoadableModel postsModel = new LoadableModel<>(new ArrayList<>());

    public List<Post> getPosts() {
        return getPostsModel().getObject();
    }

    public LoadableModel<List<Post>> getPostsModel() {
        return postsModel;
    }
}
