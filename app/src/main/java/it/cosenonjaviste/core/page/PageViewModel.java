package it.cosenonjaviste.core.page;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ViewModel;

public class PageViewModel extends ViewModel<Post, PageModel> {

    public ObservableBoolean loading = new ObservableBoolean();

    private Navigator navigator;

    @Inject public PageViewModel(Navigator navigator) {
        this.navigator = navigator;
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
        navigator.share(activityHolder, post.getTitle(), post.getTitle() + " - " + post.getUrl());
    }
}
