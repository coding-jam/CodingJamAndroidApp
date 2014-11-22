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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TweetListPresenterTest {

    @Mock TweetListView view;

    @Inject TweetListPresenter presenter;

    @Test public void testLoadTweets() {
        presenter.reloadData();
        assertThat(presenter.getModel().size()).isEqualTo(10);
    }

    @Test public void testLoadMoreTweets() {
        presenter.reloadData();

        presenter.loadNextPage();

        assertThat(presenter.getModel().size()).isEqualTo(20);
    }

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
        presenter.initAndSubscribe(new TweetListModel(), view);
    }

    @Module(injects = {TweetListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}
