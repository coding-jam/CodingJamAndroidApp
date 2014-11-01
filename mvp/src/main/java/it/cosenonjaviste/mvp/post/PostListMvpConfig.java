package it.cosenonjaviste.mvp.post;

import javax.inject.Provider;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class PostListMvpConfig extends MvpConfig<OptionalList<Post>, PostListView, PostListPresenter> {

    public PostListMvpConfig(Class<? extends PostListView> viewClass, Provider<PostListPresenter> presenter) {
        super(viewClass, presenter::get);
    }
}
