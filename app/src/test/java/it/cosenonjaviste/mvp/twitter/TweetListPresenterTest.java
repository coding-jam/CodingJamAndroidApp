package it.cosenonjaviste.mvp.twitter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mvp.TestSchedulerManager;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListModel;
import it.cosenonjaviste.twitter.TweetListPresenter;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetListPresenterTest {

    @Mock TweetListFragment view;

    @Mock TwitterService twitterService;

    private TweetListPresenter presenter;

    @Before
    public void setup() {
        presenter = new TweetListPresenter(new TestSchedulerManager(), twitterService);
    }

    @Test public void testLoadTweets() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel model = new TweetListModel();
        presenter.initAndSubscribe(model, view);
        assertThat(model.size()).isEqualTo(10);
    }

    @Test public void testRetryAfterError() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        TweetListModel model = new TweetListModel();
        presenter.initAndSubscribe(model, view);

        assertThat(model.isError()).isTrue();

        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets());

        presenter.reloadData();

        assertThat(model.isError()).isFalse();
        assertThat(model.size()).isEqualTo(10);
    }

    @Test public void testLoadMoreTweets() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel tweetListModel = new TweetListModel();
        presenter.initAndSubscribe(tweetListModel, view);

        presenter.loadNextPage();

        assertThat(tweetListModel.size()).isEqualTo(20);
    }
}
