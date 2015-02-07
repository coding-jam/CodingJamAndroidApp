package it.cosenonjaviste.mvp.twitter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListModel;
import it.cosenonjaviste.twitter.TweetListPresenter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TweetListPresenterTest {

    @Mock TweetListFragment view;

    @Inject TweetListPresenter presenter;

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
    }

    @Test public void testLoadTweets() {
        presenter.initAndSubscribe(new TweetListModel(), view);
        assertThat(presenter.getModel().size()).isEqualTo(10);
    }

    @Test public void testLoadMoreTweets() {
        presenter.initAndSubscribe(new TweetListModel(), view);

        presenter.loadNextPage();

        assertThat(presenter.getModel().size()).isEqualTo(20);
    }

    @Module(injects = {TweetListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}
