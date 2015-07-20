package it.cosenonjaviste.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;

@PresenterScope
public class TweetListPresenter extends RxMvpListPresenterAdapter<Tweet, TweetListModel, TweetListView> {

    @Inject TwitterService twitterService;

    @Inject public TweetListPresenter() {
    }

    @Override public TweetListModel createDefaultModel() {
        return new TweetListModel();
    }

    public void loadDataPullToRefresh() {
        reloadData(loadingPullToRefresh);
    }

    public void reloadData() {
        reloadData(loading);
    }

    public void reloadData(BindableBoolean loadingAction) {
        loadingAction.set(true);

        Observable<List<Tweet>> observable = twitterService.loadTweets(1).finallyDo(() -> loadingAction.set(false));

        subscribe(observable,
                posts -> {
                    done(posts);
                    getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                }, throwable -> error());
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !loading.get()) {
            reloadData();
        }
    }

    public void loadNextPage() {
        if (!isLoadingNextPage().get()) {
            loadingNextPage.set(true);
            int page = calcNextPage(getModel().getItems().size(), TwitterService.PAGE_SIZE);
            Observable<List<Tweet>> observable = twitterService.loadTweets(page).finallyDo(() -> loadingNextPage.set(false));

            subscribe(observable,
                    posts -> {
                        getModel().append(posts);
                        getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    },
                    throwable -> error());
        }
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
