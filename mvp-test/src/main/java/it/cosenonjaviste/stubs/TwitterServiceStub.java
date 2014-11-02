package it.cosenonjaviste.stubs;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;

public class TwitterServiceStub implements TwitterService {

    @Inject public TwitterServiceStub() {
    }

    @Override public Observable<List<Tweet>> loadTweets() {
        return Observable.just(Arrays.asList(new Tweet()));
    }
}
