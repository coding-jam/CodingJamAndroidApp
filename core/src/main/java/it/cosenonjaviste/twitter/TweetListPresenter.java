package it.cosenonjaviste.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;

@PresenterScope
public class TweetListPresenter extends RxMvpPresenter<TweetListModel, TweetListView> {

    @Inject TwitterService twitterService;

    @Inject public TweetListPresenter() {
    }

    public void reloadData() {
        Observable<List<Tweet>> observable = twitterService.loadTweets(1);

        subscribe(observable,
                () -> getView().startLoading(getModel().isEmpty()),
                posts -> {
                    getModel().done(posts);
                    getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    getView().update(getModel().getItems());
                }, throwable -> {
                    getModel().error();
                    getView().showError();
                });
    }

    @Override public void resume() {
        super.resume();
        if (getModel().isEmpty() && !isTaskRunning()) {
            reloadData();
        } else if (getModel().isError()) {
            getView().showError();
        } else {
            getView().update(getModel().getItems());
        }
    }

    public void loadNextPage() {
        int page = calcNextPage(getModel().size(), TwitterService.PAGE_SIZE);
        Observable<List<Tweet>> observable = twitterService.loadTweets(page);

        subscribe(observable,
                () -> getView().startMoreItemsLoading(),
                posts -> {
                    getModel().append(posts);
                    getModel().setMoreDataAvailable(posts.size() == TwitterService.PAGE_SIZE);
                    getView().update(getModel().getItems());
                },
                throwable -> {
                    getModel().error();
                    getView().showError();
                });

    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
