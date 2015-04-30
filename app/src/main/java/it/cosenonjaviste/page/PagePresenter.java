package it.cosenonjaviste.page;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

@PresenterScope
public class PagePresenter extends RxMvpPresenter<PageModel, PageFragment> {

    protected PageFragment view;
    private PageModel model;

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return pageUrlManager.getUrl(model.getUrl());
    }

    @Override public void resume() {
        getView().update(model);
        rxHolder.resubscribePendingObservable();
    }

    public void init(PageModel model, PageFragment view) {
        this.model = model;
        this.view = view;
    }

    public PageFragment getView() {
        return view;
    }

    @Inject public void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }
}
