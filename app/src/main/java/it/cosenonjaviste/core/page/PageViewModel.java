package it.cosenonjaviste.core.page;

import android.databinding.ObservableBoolean;

import javax.inject.Inject;

import it.cosenonjaviste.lib.ViewModel;
import it.cosenonjaviste.model.Post;

public class PageViewModel extends ViewModel<PageModel, PageView> {

    public ObservableBoolean loading = new ObservableBoolean();

    @Inject public PageViewModel() {
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
