package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

public class PagePresenter extends RxMvpPresenter<PageModel, PageView> {

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(getModel().getUrl());
    }

    @Override public void resume() {
        super.resume();
        getView().update(getModel());
    }
}
