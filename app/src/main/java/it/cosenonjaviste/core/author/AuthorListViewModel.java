package it.cosenonjaviste.core.author;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.jakewharton.rxrelay.PublishRelay;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class AuthorListViewModel extends RxListViewModel<Void, AuthorListModel> {

    private WordPressService wordPressService;

    public final PublishRelay<PostListArgument> postListNavigationEvents = PublishRelay.create();

    @Inject public AuthorListViewModel(WordPressService wordPressService) {
        this.wordPressService = wordPressService;
    }

    @NonNull @Override protected AuthorListModel createModel() {
        return new AuthorListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::authors)
                .doOnNext(Collections::sort);

        subscribe(loadingAction::set,
                observable,
                model::done,
                throwable -> model.error());
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        postListNavigationEvents.call(new PostListArgument(author));
    }
}
