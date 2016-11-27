package it.cosenonjaviste.core.page;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.jakewharton.rxrelay.PublishRelay;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ViewModel;

public class PageViewModel extends ViewModel<Post, PageModel> {

    public ObservableBoolean loading = new ObservableBoolean();

    public final PublishRelay<Pair<String, String>> shareEvents = PublishRelay.create();

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
        shareEvents.call(Pair.create(post.title(), post.title() + " - " + post.url()));
    }
}
