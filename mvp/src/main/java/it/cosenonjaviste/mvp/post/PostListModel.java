package it.cosenonjaviste.mvp.post;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.LoadableModel;


public class PostListModel {

    LoadableModel postsModel = new LoadableModel<>(new ArrayList<>());

    public List<Post> getPosts() {
        return getPostsModel().getObject();
    }

    public LoadableModel<List<Post>> getPostsModel() {
        return postsModel;
    }
}
