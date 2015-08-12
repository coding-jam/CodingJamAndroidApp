package it.cosenonjaviste.core.page;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

public class PagePresenter extends RxMvpPresenter<PageModel, PageView> {

    public BindableBoolean loading = new BindableBoolean();

    public BindableBoolean webViewVisible = loading.not();

    @Inject public PagePresenter() {
    }

    public String getPostUrl() {
        return getModel().getPost().getUrl();
    }

    @Override public void resume() {
        super.resume();
        loading.set(true);
        getView().update(getModel());
    }

    public void htmlLoaded() {
        loading.set(false);
    }
}
