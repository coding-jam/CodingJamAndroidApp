package it.cosenonjaviste.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import rx.Observable;

public class TweetListPresenter extends RxMvpPresenter<TweetListModel> {

    @Inject TwitterService twitterService;

    private boolean loadStarted;

    @Inject public TweetListPresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    public void reloadData() {
        Observable<List<Tweet>> observable = twitterService.loadTweets(1);

        subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading(model.isEmpty());
                },
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    @Override public void subscribe(MvpView<TweetListModel> view) {
        super.subscribe(view);
        if (model.isEmpty() && !loadStarted) {
            reloadData();
        }
    }

    public void loadNextPage() {
        int page = calcNextPage(model.size(), TwitterService.PAGE_SIZE);
        Observable<List<Tweet>> observable = twitterService.loadTweets(page);

        subscribe(observable,
                () -> getView().startMoreItemsLoading(),
                posts -> {
                    model.append(posts);
                    model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });

    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }

    @Override public TweetListFragment getView() {
        return (TweetListFragment) super.getView();
    }
}
