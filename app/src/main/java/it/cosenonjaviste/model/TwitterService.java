package it.cosenonjaviste.model;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
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

    public Single<List<Tweet>> loadTweets(int page) {
        return Observable.fromCallable(() -> twitter.getUserTimeline(251259751, new Paging(page, PAGE_SIZE)))
                .flatMapIterable(l -> l)
                .map(this::createTweet).toList();
    }

    private Tweet createTweet(Status s) {
        User user = s.getUser();
        return Tweet.create(s.getId(), s.getText(), s.getCreatedAt(), user.getProfileImageURL(), user.getName());
    }
}
