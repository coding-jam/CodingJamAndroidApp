package it.cosenonjaviste.mvp.page;

import javax.inject.Inject;

import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public class PagePresenter extends RxMvpPresenter<PageModel> {

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    @Override public PageModel createModel(PresenterArgs args) {
        return new PageModel(args);
    }

    public String getPostUrl(String url) {
        return pageUrlManager.getUrl(url);
    }
}
