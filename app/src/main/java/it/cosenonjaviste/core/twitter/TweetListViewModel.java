package it.cosenonjaviste.core.twitter;

import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Observable;

public class TweetListViewModel extends RxListViewModel<TweetListModel> {

    private TwitterService twitterService;

    @Inject public TweetListViewModel(SchedulerManager schedulerManager, TwitterService twitterService) {
        super(schedulerManager);
        this.twitterService = twitterService;
    }

    @Override public TweetListModel createDefaultModel() {
        return new TweetListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        loadingAction.set(true);

        Observable<List<Tweet>> observable = twitterService.loadTweets(1).finallyDo(() -> loadingAction.set(false));

        subscribe(observable,
                posts -> {
                    getModel().done(posts);
                    getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                }, throwable -> getModel().error());
    }

    public void loadNextPage() {
        if (!isLoadingNextPage().get() && getModel().isMoreDataAvailable()) {
            loadingNextPage.set(true);
            int page = calcNextPage(getModel().getItems().size(), TwitterService.PAGE_SIZE);
            Observable<List<Tweet>> observable = twitterService.loadTweets(page).finallyDo(() -> loadingNextPage.set(false));

            subscribe(observable,
                    posts -> {
                        getModel().append(posts);
                        getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    },
                    throwable -> getModel().error());
        }
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
