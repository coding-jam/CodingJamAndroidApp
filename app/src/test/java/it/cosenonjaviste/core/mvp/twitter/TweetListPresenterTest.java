package it.cosenonjaviste.core.mvp.twitter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.core.model.TwitterService;
import it.cosenonjaviste.core.twitter.TweetListModel;
import it.cosenonjaviste.core.twitter.TweetListPresenter;
import it.cosenonjaviste.core.twitter.TweetListView;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class TweetListPresenterTest {

    @Mock TweetListView view;

    @Mock TwitterService twitterService;

    @InjectMocks TweetListPresenter presenter;

    @Test public void testLoadTweets() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel model = presenter.initAndResume(view);

        Assertions.assertThat(model.getItems()).hasSize(10);
    }

    @Test public void testRetryAfterError() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        TweetListModel model = presenter.initAndResume(view);

        Assertions.assertThat(presenter.isError().get()).isTrue();

        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        presenter.reloadData();

        Assertions.assertThat(presenter.isError().get()).isFalse();
        Assertions.assertThat(model.getItems()).hasSize(10);
    }

    @Test public void testLoadMoreTweets() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel tweetListModel = presenter.initAndResume(view);

        presenter.loadNextPage();

        Assertions.assertThat(tweetListModel.getItems()).hasSize(20);
    }
}
