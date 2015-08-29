package it.cosenonjaviste.core.author;

import android.databinding.ObservableBoolean;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.rx.SchedulerManager;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class AuthorListViewModel extends RxListViewModel<AuthorListModel, AuthorListView> {

    private WordPressService wordPressService;

    @Inject public AuthorListViewModel(SchedulerManager schedulerManager, WordPressService wordPressService) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
    }

    @Override public AuthorListModel createDefaultModel() {
        return new AuthorListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        loadingAction.set(true);

        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .doOnNext(Collections::sort)
                .finallyDo(() -> loadingAction.set(false));

        subscribe(observable,
                l -> getModel().done(l),
                throwable -> getModel().error());
    }

    public void goToAuthorDetail(int position) {
        Author author = getModel().get(position);
        getView().openPostList(new PostListModel(author));
    }
}