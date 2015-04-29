package it.cosenonjaviste.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpView;
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

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public AuthorListPresenter() {
    }

    public void loadAuthors() {
        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .doOnNext(Collections::sort);

        subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading();
                },
                posts -> {
                    model.done(posts);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    @Override public void resume() {
        view.update(model);
        rxHolder.resubscribePendingObservable();
        if (model.isEmpty() && !loadStarted) {
            loadAuthors();
        }
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        getView().open(PostListFragment.class, new PostListModel(author));
    }

    @Override public AuthorListFragment getView() {
        return (AuthorListFragment) super.getView();
    }

    public void init(it.cosenonjaviste.author.AuthorListModel model, MvpView<AuthorListModel> view) {
        this.model = model;
        this.view = view;
    }
}
