package it.cosenonjaviste.core.model;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {

    public static final int PAGE_SIZE = 20;

    private final Twitter twitter;

    public TwitterService(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public Observable<List<Tweet>> loadTweets(int page) {
        return Observable.create((Subscriber<? super List<Status>> subscriber) -> {
            try {
                List<Status> statuses = twitter.getUserTimeline(251259751, new Paging(page, PAGE_SIZE));
                subscriber.onNext(statuses);
                subscriber.onCompleted();
            } catch (TwitterException e) {
                subscriber.onError(e);
            }
        }).flatMap(Observable::from).map(this::createTweet).toList();
    }

    private Tweet createTweet(Status s) {
        User user = s.getUser();
        return new Tweet(s.getId(), s.getText(), s.getCreatedAt(), user.getProfileImageURL(), user.getName());
    }
}
