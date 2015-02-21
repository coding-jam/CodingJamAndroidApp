package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpPresenter;

public class PagePresenter extends MvpPresenter<PageModel> {

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }
}
