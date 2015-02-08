package it.cosenonjaviste.stubs;

import java.util.ArrayList;
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
        List<Tweet> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tweet tweet = new Tweet();
            tweet.setId(123);
            tweet.setAuthor("author");
            tweet.setCreatedAt(new Date());
            tweet.setText("tweet text " + i);
            list.add(tweet);
        }
        return Observable.just(list);
    }
}
