package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

@PresenterScope
public class PagePresenter extends RxMvpPresenter<PageModel, PageFragment> {

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(getModel().getUrl());
    }

    @Override public void resume() {
        getView().update(getModel());
        super.resume();
    }
}
