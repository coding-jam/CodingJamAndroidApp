package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class PostListMvpConfig extends MvpConfig<OptionalList<Post>, PostListView, PostListPresenter> {

    public PostListMvpConfig(Class<? extends PostListView> viewClass, PostListPresenter presenter) {
        super(viewClass, presenter);
    }
}
