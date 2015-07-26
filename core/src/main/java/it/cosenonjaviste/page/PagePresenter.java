package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

public class PagePresenter extends RxMvpPresenter<PageModel, PageView> {

    @Inject PageUrlManager pageUrlManager;

    public BindableBoolean loading = new BindableBoolean();

    public BindableBoolean webViewVisible = loading.not();

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(getModel().getUrl());
    }

    @Override public void resume() {
        super.resume();
        loading.set(true);
        getView().update(getModel());
    }
}
