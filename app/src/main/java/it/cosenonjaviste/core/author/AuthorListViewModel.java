package it.cosenonjaviste.core.author;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Observable;

public class AuthorListViewModel extends RxListViewModel<Void, AuthorListModel> {

    private WordPressService wordPressService;

    private Navigator navigator;

    @Inject public AuthorListViewModel(SchedulerManager schedulerManager, WordPressService wordPressService, Navigator navigator) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
        this.navigator = navigator;
    }

    @NonNull @Override protected AuthorListModel createModel() {
        return new AuthorListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .doOnNext(Collections::sort);

        subscribe(loadingAction::set,
                observable,
                model::done,
                throwable -> model.error());
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        navigator.openPostList(activity, new PostListArgument(author));
    }
}
