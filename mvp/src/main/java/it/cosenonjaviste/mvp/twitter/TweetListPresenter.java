package it.cosenonjaviste.mvp.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import rx.Observable;

public class TweetListPresenter extends RxMvpPresenter<TweetListModel> {

    @Inject TwitterService twitterService;

    @Inject public TweetListPresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    public void reloadData() {
        Observable<List<Tweet>> observable = twitterService.loadTweets(1);

        subscribePausable(observable,
                () -> getView().startLoading(model.isEmpty()),
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });

    }

    public void loadNextPage() {
        int page = calcNextPage(model.size(), TwitterService.PAGE_SIZE);
        Observable<List<Tweet>> observable = twitterService.loadTweets(page);

        subscribePausable(observable,
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

    @Override public TweetListModel createModel(PresenterArgs args) {
        return new TweetListModel();
    }

    @Override public TweetListView getView() {
        return (TweetListView) super.getView();
    }
}
