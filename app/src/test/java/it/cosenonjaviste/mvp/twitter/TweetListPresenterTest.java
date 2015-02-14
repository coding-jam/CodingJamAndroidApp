package it.cosenonjaviste.mvp.twitter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
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

    @Inject TweetListPresenter presenter;

    @Inject TwitterService twitterService;

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
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

        presenter.initAndSubscribe(new TweetListModel(), view);

        presenter.loadNextPage();

        assertThat(presenter.getModel().size()).isEqualTo(20);
    }

    @Module(injects = {TweetListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}
