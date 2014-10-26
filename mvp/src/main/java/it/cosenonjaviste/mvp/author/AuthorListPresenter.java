package it.cosenonjaviste.mvp.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import rx.Observable;

public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel> {

    @Inject WordPressService wordPressService;

    @Override public AuthorListModel createModel(PresenterArgs args) {
        return new AuthorListModel();
    }

    public void loadAuthors() {
        Observable<List<Author>> observable = wordPressService.listAuthors().map(AuthorResponse::getAuthors);
        subscribePausable(observable,
                () -> getView().startLoading(),
                posts -> {
                    Collections.sort(posts);
                    model.getAuthors().done(posts);
                    view.update(model);
                }, throwable -> {
                    model.getAuthors().error(throwable);
                    view.update(model);
                });
    }

    @Override public AuthorListView getView() {
        return (AuthorListView) super.getView();
    }
}
