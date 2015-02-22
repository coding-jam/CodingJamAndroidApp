package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpPresenter;
import it.cosenonjaviste.utils.PresenterScope;

@PresenterScope
public class PagePresenter extends MvpPresenter<PageModel> {

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }
}
