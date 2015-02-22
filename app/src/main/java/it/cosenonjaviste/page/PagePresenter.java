package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpPresenter;
import it.cosenonjaviste.utils.PresenterScope;

@PresenterScope
public class PagePresenter extends MvpPresenter<PageModel> {

    private PageUrlManager pageUrlManager;

    @Inject public PagePresenter(PageUrlManager pageUrlManager) {
        this.pageUrlManager = pageUrlManager;
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }
}
