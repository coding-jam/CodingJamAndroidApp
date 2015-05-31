package it.cosenonjaviste.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

@PresenterScope
public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel, AuthorListView> {

    @Inject WordPressService wordPressService;

    @Inject public AuthorListPresenter() {
    }

    public void loadAuthors() {
        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .doOnNext(Collections::sort);

        subscribe(observable,
                () -> getView().startLoading(),
                posts -> {
                    getModel().done(posts);
                    getView().update(getModel().getItems());
                }, throwable -> {
                    getModel().error();
                    getView().showError();
                });
    }

    @Override public void resume() {
        super.resume();
        if (getModel().isEmpty() && !isTaskRunning()) {
            loadAuthors();
        } else if (getModel().isError()) {
            getView().showError();
        } else {
            getView().update(getModel().getItems());
        }
    }

    public void goToAuthorDetail(int position) {
        Author author = getModel().get(position);
        getView().openPostList(new PostListModel(author));
    }
}
