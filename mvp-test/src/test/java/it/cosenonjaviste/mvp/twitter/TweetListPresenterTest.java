package it.cosenonjaviste.mvp.twitter;

import org.junit.Test;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.PresenterTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetListPresenterTest extends PresenterTest<TweetListView, TweetListPresenter> {
    @Override public Class<TweetListView> getConfig() {
        return TweetListView.class;
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
