package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpPresenter;

public class PagePresenter extends MvpPresenter<PageModel> {

    @Inject PageUrlManager pageUrlManager;

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }
}
