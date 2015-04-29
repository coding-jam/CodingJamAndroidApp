package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

@PresenterScope
public class PagePresenter extends RxMvpPresenter<PageModel> {

    private PageModel model;

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }

    @Override public void resume() {
        view.update(model);
        rxHolder.resubscribePendingObservable();
    }

    public void init(it.cosenonjaviste.page.PageModel model, MvpView<PageModel> view) {
        this.model = model;
        this.view = view;
    }
}
