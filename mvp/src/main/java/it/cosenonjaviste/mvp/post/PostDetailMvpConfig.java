package it.cosenonjaviste.mvp.post;

import javax.inject.Provider;

import it.cosenonjaviste.mvp.base.MvpConfig;

public class PostDetailMvpConfig extends MvpConfig<PostDetailModel, PostDetailView, PostDetailPresenter> {

    public PostDetailMvpConfig(Class<? extends PostDetailView> viewClass, Provider<PostDetailPresenter> presenter) {
        super(viewClass, presenter::get);
    }
}
