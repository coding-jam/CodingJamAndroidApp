package it.cosenonjaviste.mvp.author;

import javax.inject.Provider;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class AuthorListMvpConfig extends MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> {

    public AuthorListMvpConfig(Class<? extends AuthorListView> viewClass, Provider<AuthorListPresenter> presenter) {
        super(viewClass, presenter::get);
    }
}
