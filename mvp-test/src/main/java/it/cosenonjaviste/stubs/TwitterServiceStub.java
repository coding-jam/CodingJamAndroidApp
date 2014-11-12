package it.cosenonjaviste.stubs;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;

public class TwitterServiceStub implements TwitterService {

    @Inject public TwitterServiceStub() {
    }

    @Override public Observable<List<Tweet>> loadTweets(int page) {
        Tweet tweet = new Tweet();
        tweet.setId(123);
        tweet.setAuthor("author");
        tweet.setCreatedAt(new Date());
        tweet.setText("text");
        return Observable.just(Arrays.asList(tweet));
    }
}
