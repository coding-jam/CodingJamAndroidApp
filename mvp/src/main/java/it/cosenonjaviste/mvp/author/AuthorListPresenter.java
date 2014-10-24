package it.cosenonjaviste.mvp.author;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.events.EndLoadingModelEvent;
import it.cosenonjaviste.mvp.base.events.ErrorModelEvent;
import it.cosenonjaviste.mvp.base.events.StartLoadingModelEvent;
import rx.Observable;

public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel> {

    @Inject WordPressService wordPressService;

    @Override protected AuthorListModel createModel(PresenterArgs args) {
        return new AuthorListModel();
    }

    @Override protected void loadOnFirstStart() {
        Observable<List<Author>> observable = wordPressService.listAuthors().map(AuthorResponse::getAuthors);

        subscribePausable(observable,
                () -> publish(new StartLoadingModelEvent<>(model)),
                authors -> {
                    model.getAuthorsModel().done(authors);
                    publish(new EndLoadingModelEvent<>(model));
                }, throwable -> {
                    model.getAuthorsModel().error(throwable);
                    publish(new ErrorModelEvent<>(model, throwable));
                });
    }
}
