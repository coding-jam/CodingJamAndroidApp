package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.optional.OptionalList;


public class PostListModel {

    OptionalList postsModel = new OptionalList<>();

    public OptionalList<Post> getPosts() {
        return postsModel;
    }
}
