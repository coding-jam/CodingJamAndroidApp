package it.cosenonjaviste.core.page;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ViewModel;

public class PageViewModel extends ViewModel<Post, PageModel> {

    public ObservableBoolean loading = new ObservableBoolean();

    @Inject public PageViewModel() {
    }

    @NonNull @Override protected PageModel createModel() {
        return new PageModel();
    }

    public Post getPost() {
        return getModel().getPost();
    }

    @Override public void resume() {
        super.resume();
        getModel().setPost(getArgument());
        loading.set(true);
    }

    public void htmlLoaded() {
        loading.set(false);
    }
}
