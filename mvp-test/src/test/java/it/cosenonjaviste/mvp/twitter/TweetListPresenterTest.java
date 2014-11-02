package it.cosenonjaviste.mvp.twitter;

import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.MvpConfig;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetListPresenterTest extends PresenterTest<TweetListView, TweetListPresenter> {
    @Inject MvpConfig<TweetListView, TweetListPresenter> config;

    @Override public MvpConfig<TweetListView, TweetListPresenter> getConfig() {
        return config;
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Test public void testLoadTweets() {
        presenter.loadData(0);
        assertThat(presenter.getModel().isEmpty()).isFalse();
    }

    @Module(injects = {TweetListPresenterTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
