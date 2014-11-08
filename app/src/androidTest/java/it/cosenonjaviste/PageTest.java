package it.cosenonjaviste;

import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.mvp.page.PagePresenter;
import it.cosenonjaviste.mvp.page.PageUrlManager;
import it.cosenonjaviste.post.PageFragment;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class PageTest extends CnjFragmentTest {

    @Inject MockWebServerWrapper server;

    public PageTest() {
        super(PageFragment.class);
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        server.initDispatcher("<html><body>CoseNonJaviste</body></html>");
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected Intent createActivityIntent() {
        return createIntent(super.createActivityIntent(), PagePresenter::populateArgs, "url");
    }

    public void testDetailFragment() {
        showUi();
    }

    @Module(injects = {PageTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides @Singleton PageUrlManager providePostDetailUrlManager(MockWebServerWrapper server) {
            return new PageUrlManager() {
                @Override public String getUrl(String url) {
                    return server.getUrl(true);
                }
            };
        }
    }
}
