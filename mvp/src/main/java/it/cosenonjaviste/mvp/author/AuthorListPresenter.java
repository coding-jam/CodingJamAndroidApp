package it.cosenonjaviste.mvp.author;

import javax.inject.Inject;

import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel> {

    @Inject WordPressService wordPressService;

    @Override public AuthorListModel createModel(PresenterArgs args) {
        return new AuthorListModel();
    }
}
