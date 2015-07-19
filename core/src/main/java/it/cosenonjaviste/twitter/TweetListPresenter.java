package it.cosenonjaviste.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;
import rx.functions.Action1;

@PresenterScope
public class TweetListPresenter extends RxMvpPresenter<TweetListModel, TweetListView> {

    @Inject TwitterService twitterService;

    @Inject public TweetListPresenter() {
    }


    public void loadDataPullToRefresh() {
        reloadData(b -> getModel().loadingPullToRefresh.set(b));
    }

    public void reloadData() {
        reloadData(b -> getModel().loading.set(b));
    }

    public void reloadData(Action1<Boolean> loadingAction) {
        Observable<List<Tweet>> observable = twitterService.loadTweets(1).finallyDo(() -> loadingAction.call(false));

        subscribe(observable,
                () -> loadingAction.call(true),
                posts -> {
                    getModel().done(posts);
                    getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                }, throwable -> getModel().error());
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !isTaskRunning()) {
            reloadData();
        }
    }

    public void loadNextPage() {
        int page = calcNextPage(getModel().getItems().size(), TwitterService.PAGE_SIZE);
        Observable<List<Tweet>> observable = twitterService.loadTweets(page).finallyDo(() -> getModel().loadingNextPage.set(false));

        subscribe(observable,
                () -> getModel().loadingNextPage.set(true),
                posts -> {
                    getModel().append(posts);
                    getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                },
                throwable -> getModel().error());

    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
