package it.cosenonjaviste.twitter;

import java.util.List;

import it.cosenonjaviste.BuildConfig;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;
import rx.Subscriber;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter4JService implements TwitterService {

    private final Twitter twitter;

    public Twitter4JService() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(BuildConfig.CONSUMER_KEY)
                .setOAuthConsumerSecret(BuildConfig.CONSUMER_SECRET)
                .setOAuthAccessToken(BuildConfig.ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(BuildConfig.ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override public Observable<List<Tweet>> loadTweets() {
        return Observable.create((Subscriber<? super List<Status>> subscriber) -> {
            try {
                List<Status> statuses = twitter.getUserTimeline(251259751);
                subscriber.onNext(statuses);
                subscriber.onCompleted();
            } catch (TwitterException e) {
                subscriber.onError(e);
            }
        }).flatMap(Observable::from).map(this::createTweet).toList();
    }

    private Tweet createTweet(Status s) {
        Tweet tweet = new Tweet();
        tweet.setId(s.getId());
        tweet.setText(s.getText());
        return tweet;
    }
}
