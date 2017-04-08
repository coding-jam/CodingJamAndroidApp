package it.cosenonjaviste.core.author;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.nytimes.android.external.store2.base.impl.Store;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Author;

public class AuthorListViewModel extends RxListViewModel<Void, AuthorListModel> {

    @Inject Store<List<Author>, Integer> authorsStore;

    @Inject @BindLifeCycle Navigator navigator;

    @Inject public AuthorListViewModel() {
    }

    @NonNull @Override protected AuthorListModel createModel() {
        return new AuthorListModel();
    }

    @Override protected Disposable reloadData(ObservableBoolean loadingAction, boolean forceFetch) {
        loadingAction.set(true);
        return (forceFetch ? authorsStore.fetch(0) : authorsStore.get(0))
                .singleOrError()
                .doOnSuccess(Collections::sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.set(false))
                .subscribe(model::done, throwable -> model.error());
    }

    public void goToAuthorDetail(int position) {
        Author author = model.get(position);
        navigator.openPostList(PostListArgument.create(author));
    }
}
