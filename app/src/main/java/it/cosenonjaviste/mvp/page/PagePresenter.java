package it.cosenonjaviste.mvp.page;

import javax.inject.Inject;

import it.cosenonjaviste.mvp.base.MvpPresenter;

public class PagePresenter extends MvpPresenter<PageModel> {

    @Inject PageUrlManager pageUrlManager;

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }
}
