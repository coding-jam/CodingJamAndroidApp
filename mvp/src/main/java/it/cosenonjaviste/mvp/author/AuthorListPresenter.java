package it.cosenonjaviste.mvp.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.mvp.post.PostListView;
import rx.Observable;

public class AuthorListPresenter extends RxMvpPresenter<OptionalList<Author>> {

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

        subscribePausable(observable,
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

    @Override public void subscribe(MvpView<OptionalList<Author>> view) {
        super.subscribe(view);
        if (model.isEmpty() && !loadStarted) {
            loadAuthors();
        }
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        getView().open(PostListView.class, new PostListModel(author));
    }

    @Override public AuthorListView getView() {
        return (AuthorListView) super.getView();
    }
}
