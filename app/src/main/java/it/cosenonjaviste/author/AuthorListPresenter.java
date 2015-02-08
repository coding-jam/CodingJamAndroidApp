package it.cosenonjaviste.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.author.ui.AuthorListFragment;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel> {

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public AuthorListPresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
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

    @Override public void subscribe(MvpView<AuthorListModel> view) {
        super.subscribe(view);
        if (model.isEmpty() && !loadStarted) {
            loadAuthors();
        }
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        getView().open(PostListFragment.class, new PostListModel(author));
    }

    public void testFromJUnit() {
        System.out.println(123);
    }

    public void testFromFragment() {
        System.out.println(123);
    }

    @Override public AuthorListFragment getView() {
        return (AuthorListFragment) super.getView();
    }
}
