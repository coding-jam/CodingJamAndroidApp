package it.cosenonjaviste.mvp.page;

import javax.inject.Inject;

import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;

public class PagePresenter extends RxMvpPresenter<PageModel> {

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    public String getPostUrl(String url) {
        return pageUrlManager.getUrl(url);
    }
}
