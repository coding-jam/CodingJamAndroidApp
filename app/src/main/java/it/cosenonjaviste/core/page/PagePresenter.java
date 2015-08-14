package it.cosenonjaviste.core.page;

import android.databinding.ObservableBoolean;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpPresenter;
import it.cosenonjaviste.model.Post;

public class PagePresenter extends MvpPresenter<PageModel, PageView> {

    public ObservableBoolean loading = new ObservableBoolean();

    @Inject public PagePresenter() {
    }

    public Post getPost() {
        return getModel().getPost();
    }

    @Override public void resume() {
        super.resume();
        loading.set(true);
    }

    public void htmlLoaded() {
        loading.set(false);
    }
}
