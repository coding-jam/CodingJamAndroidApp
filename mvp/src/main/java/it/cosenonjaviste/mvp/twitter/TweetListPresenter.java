package it.cosenonjaviste.mvp.twitter;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mvp.ListPresenter;
import rx.Observable;

public class TweetListPresenter extends ListPresenter<Tweet> {

    @Inject TwitterService twitterService;

    public void reloadData() {
        Observable<List<Tweet>> observable = twitterService.loadTweets();

        subscribeListObservable(observable);
    }
}
