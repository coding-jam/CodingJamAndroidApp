package it.cosenonjaviste.mvp.author;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel> {

    @Inject WordPressService wordPressService;

    @Override public AuthorListModel createModel(PresenterArgs args) {
        return new AuthorListModel();
    }

    public void loadAuthors() {
        subscribePausable(wordPressService.listAuthors().<List<Author>>map(AuthorResponse::getAuthors),
                () -> getView().startLoading(),
                posts -> {
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
