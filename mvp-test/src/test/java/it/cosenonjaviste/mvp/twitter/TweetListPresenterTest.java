package it.cosenonjaviste.mvp.twitter;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.MvpConfig;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetListPresenterTest extends PresenterTest<TweetListView, TweetListPresenter> {
    @Inject MvpConfig<TweetListView> config;

    @Override public MvpConfig<TweetListView> getConfig() {
        return config;
    }

    @Override protected void initAfterInject() {
        createComponent(TestComponent.class).inject(this);
    }

    @Test public void testLoadTweets() {
        presenter.reloadData();
        assertThat(presenter.getModel().isEmpty()).isFalse();
    }

    @Singleton
    @Component(modules = MvpTestModule.class)
    public interface TestComponent {
        void inject(TweetListPresenterTest test);
    }
}
