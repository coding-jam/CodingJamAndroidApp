package it.cosenonjaviste.core.twitter;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.model.TwitterService;

public class TweetListViewModel extends RxListViewModel<Void, TweetListModel> {

    @Inject TwitterService twitterService;

    @Inject public TweetListViewModel() {
    }

    @NonNull @Override protected TweetListModel createModel() {
        return new TweetListModel();
    }

    @Override protected Disposable reloadData(ObservableBoolean loadingAction, boolean forceFetch) {
        loadingAction.set(true);
        return twitterService.loadTweets(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.set(false))
                .subscribe(posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                }, throwable -> model.error());
    }

    public void loadNextPage() {
        if (!isLoadingNextPage().get() && model.isMoreDataAvailable()) {
            int page = calcNextPage(model.getItems().size(), TwitterService.PAGE_SIZE);

            loadingNextPage.set(true);
            disposable.add(twitterService.loadTweets(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(() -> loadingNextPage.set(false))
                    .subscribe(posts -> {
                        model.append(posts);
                        model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    }, throwable -> model.error())
            );
        }
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
