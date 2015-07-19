package it.cosenonjaviste.mvp.twitter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mvp.TestLifeCycle;
import it.cosenonjaviste.twitter.TweetListModel;
import it.cosenonjaviste.twitter.TweetListPresenter;
import it.cosenonjaviste.twitter.TweetListView;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class TweetListPresenterTest {

    @Mock TweetListView view;

    private TestLifeCycle testLifeCycle = new TestLifeCycle();

    @Mock TwitterService twitterService;

    @InjectMocks TweetListPresenter presenter;

    @Test public void testLoadTweets() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel model = new TweetListModel();
        testLifeCycle.initAndResume(model, presenter, view);
        Assertions.assertThat(model.getItems()).hasSize(10);
    }

    @Test public void testRetryAfterError() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        TweetListModel model = new TweetListModel();
        testLifeCycle.initAndResume(model, presenter, view);

        Assertions.assertThat(model.isError().get()).isTrue();

        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        presenter.reloadData();

        Assertions.assertThat(model.isError().get()).isFalse();
        Assertions.assertThat(model.getItems()).hasSize(10);
    }

    @Test public void testLoadMoreTweets() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel tweetListModel = new TweetListModel();
        testLifeCycle.initAndResume(tweetListModel, presenter, view);

        presenter.loadNextPage();

        Assertions.assertThat(tweetListModel.getItems()).hasSize(20);
    }
}
