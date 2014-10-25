package it.cosenonjaviste.mvp.post;

import javax.inject.Inject;
import javax.inject.Provider;

import it.cosenonjaviste.mvp.base.MvpConfig;

public class PostDetailMvpConfig implements MvpConfig<PostDetailModel, PostDetailView, PostDetailPresenter> {

    @Inject Provider<PostDetailView> viewProvider;

    @Inject PostDetailPresenter presenter;

    @Override public PostDetailView createView() {
        return viewProvider.get();
    }

    @Override public PostDetailPresenter createPresenter() {
        return presenter;
    }
}
