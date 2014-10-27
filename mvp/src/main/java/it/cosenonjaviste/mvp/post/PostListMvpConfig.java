package it.cosenonjaviste.mvp.post;

import javax.inject.Inject;
import javax.inject.Provider;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class PostListMvpConfig extends MvpConfig<OptionalList<Post>, PostListView, PostListPresenter> {

    @Inject Provider<PostListView> viewProvider;

    @Inject PostListPresenter presenter;

    @Override public PostListView createView() {
        return viewProvider.get();
    }

    @Override protected PostListPresenter createPresenter() {
        return presenter;
    }
}
