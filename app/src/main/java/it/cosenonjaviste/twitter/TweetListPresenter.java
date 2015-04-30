package it.cosenonjaviste.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@PresenterScope
public class TweetListPresenter extends RxMvpPresenter<TweetListModel, TweetListFragment> {

    protected TweetListFragment view;
    private TweetListModel model;

    @Inject TwitterService twitterService;

    private boolean loadStarted;

    @Inject public TweetListPresenter() {
    }

    public void reloadData() {
        Observable<List<Tweet>> observable = twitterService.loadTweets(1);

        rxHolder.subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading(model.isEmpty());
                },
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    getView().update(model);
                }, throwable -> {
                    model.error(throwable);
                    getView().update(model);
                });
    }

    @Override public void resume() {
        getView().update(model);
        rxHolder.resubscribePendingObservable();
        if (model.isEmpty() && !loadStarted) {
            reloadData();
        }
    }

    public void loadNextPage() {
        int page = calcNextPage(model.size(), TwitterService.PAGE_SIZE);
        Observable<List<Tweet>> observable = twitterService.loadTweets(page);

        Action0 onAttach = () -> getView().startMoreItemsLoading();
        Action1<? super List<Tweet>> onNext = posts -> {
            model.append(posts);
            model.setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
            getView().update(model);
        };
        Action1<Throwable> onError = throwable -> {
            model.error(throwable);
            getView().update(model);
        };
        rxHolder.subscribe(observable, onAttach, onNext, onError);

    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }

    public TweetListFragment getView() {
        return view;
    }

    public void init(TweetListModel model, TweetListFragment view) {
        this.model = model;
        this.view = view;
    }

    @Inject public void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }
}
