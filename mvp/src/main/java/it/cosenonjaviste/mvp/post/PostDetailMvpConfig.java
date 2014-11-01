package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.mvp.base.MvpConfig;

public class PostDetailMvpConfig extends MvpConfig<PostDetailModel, PostDetailView, PostDetailPresenter> {

    public PostDetailMvpConfig(Class<? extends PostDetailView> viewClass, PostDetailPresenter presenter) {
        super(viewClass, presenter);
    }
}
