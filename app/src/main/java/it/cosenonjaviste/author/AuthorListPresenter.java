package it.cosenonjaviste.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

@PresenterScope
public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel> {

    protected AuthorListModel model;
    protected AuthorListFragment view;

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public AuthorListPresenter() {
    }

    public void loadAuthors() {
        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .doOnNext(Collections::sort);

        rxHolder.subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading();
                },
                posts -> {
                    model.done(posts);
                    getView().update(model);
                }, throwable -> {
                    model.error(throwable);
                    getView().update(model);
                });
    }

    @Override public void resume() {
        rxHolder.resubscribePendingObservable();
        if (model.isEmpty() && !loadStarted) {
            loadAuthors();
        } else {
            getView().update(model);
        }
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        getView().open(PostListFragment.class, new PostListModel(author));
    }

    public AuthorListFragment getView() {
        return view;
    }

    public void init(AuthorListModel model, AuthorListFragment view) {
        this.model = model;
        this.view = view;
    }

    @Inject public void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }
}
