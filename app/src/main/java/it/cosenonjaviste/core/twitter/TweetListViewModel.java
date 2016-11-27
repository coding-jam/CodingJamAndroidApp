package it.cosenonjaviste.core.twitter;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;

public class TweetListViewModel extends RxListViewModel<Void, TweetListModel> {

    @Inject TwitterService twitterService;

    @Inject public TweetListViewModel() {
    }

    @NonNull @Override protected TweetListModel createModel() {
        return new TweetListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        subscribe(loadingAction::set,
                twitterService.loadTweets(1),
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                }, throwable -> model.error());
    }

    public void loadNextPage() {
        if (!isLoadingNextPage().get() && model.isMoreDataAvailable()) {
            int page = calcNextPage(model.getItems().size(), TwitterService.PAGE_SIZE);
            Observable<List<Tweet>> observable = twitterService.loadTweets(page);

            subscribe(
                    loadingNextPage::set,
                    observable,
                    posts -> {
                        model.append(posts);
                        model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    },
                    throwable -> model.error());
        }
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
