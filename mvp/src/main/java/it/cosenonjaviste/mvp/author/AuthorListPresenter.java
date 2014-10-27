package it.cosenonjaviste.mvp.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.ListPresenter;
import rx.Observable;

public class AuthorListPresenter extends ListPresenter<Author> {

    @Inject WordPressService wordPressService;

    @Override protected Observable<List<Author>> getObservable(int page) {
        return wordPressService.listAuthors().map(AuthorResponse::getAuthors).doOnNext(Collections::sort);
    }

    public void loadAuthors() {
        loadData(0);
    }
}
