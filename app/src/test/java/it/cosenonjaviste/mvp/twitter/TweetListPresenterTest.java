package it.cosenonjaviste.mvp.twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.utils.RxHolder;
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

    @Spy RxHolder rxHolder = new RxHolder(new TestSchedulerManager(), new LifeCycle());

    @Mock TwitterService twitterService;

    @InjectMocks TweetListPresenter presenter;

    @Test public void testLoadTweets() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel model = new TweetListModel();
        presenter.init(model, view);
        presenter.resume();
        assertThat(model.size()).isEqualTo(10);
    }

    @Test public void testRetryAfterError() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        TweetListModel model = new TweetListModel();
        presenter.init(model, view);
        presenter.resume();

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
        presenter.init(tweetListModel, view);
        presenter.resume();

        presenter.loadNextPage();

        assertThat(tweetListModel.size()).isEqualTo(20);
    }
}
