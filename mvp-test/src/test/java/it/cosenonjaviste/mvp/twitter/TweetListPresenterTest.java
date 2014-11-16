package it.cosenonjaviste.mvp.twitter;

import org.junit.Test;

import dagger.Module;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.PresenterTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetListPresenterTest extends PresenterTest<TweetListView, TweetListPresenter> {

    public TweetListPresenterTest() {
        super(TweetListView.class);
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Test public void testLoadTweets() {
        presenter.reloadData();
        assertThat(presenter.getModel().isEmpty()).isFalse();
    }

    @Module(injects = {TweetListPresenterTest.class}, addsTo = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}
