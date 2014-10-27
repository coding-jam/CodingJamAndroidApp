package it.cosenonjaviste.mvp.author;

import javax.inject.Inject;
import javax.inject.Provider;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class AuthorListMvpConfig extends MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> {

    @Inject Provider<AuthorListView> viewProvider;

    @Inject AuthorListPresenter presenter;

    @Override public AuthorListView createView() {
        return viewProvider.get();
    }

    @Override protected AuthorListPresenter createPresenter() {
        return presenter;
    }
}
