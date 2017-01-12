package it.cosenonjaviste.core.page;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ViewModel;

public class PageViewModel extends ViewModel<Post, PageModel> {

    public ObservableBoolean loading = new ObservableBoolean();

    @Inject @BindLifeCycle Navigator navigator;

    @Inject public PageViewModel() {
    }

    @NonNull @Override protected PageModel createModel() {
        return new PageModel();
    }

    public Post getPost() {
        return model.getPost();
    }

    @Override public void resume() {
        super.resume();
        if (model.getPost() == null) {
            model.setPost(getArgument());
            loading.set(true);
        }
    }

    public void htmlLoaded() {
        loading.set(false);
    }

    public void share() {
        Post post = model.getPost();
        navigator.share(post.title(), post.title() + " - " + post.url());
    }
}
