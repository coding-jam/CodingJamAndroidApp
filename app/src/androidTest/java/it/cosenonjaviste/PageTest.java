package it.cosenonjaviste;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.base.MvpEspressoTestModule;
import it.cosenonjaviste.mvp.page.PageModel;
import it.cosenonjaviste.mvp.page.PageUrlManager;
import it.cosenonjaviste.post.PageFragment;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class PageTest extends CnjFragmentTest<PageModel> {

    @Inject MockWebServerWrapper server;

    public PageTest() {
        super(PageFragment.class, new PageModel("url"));
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        server.initDispatcher("<html><body>CoseNonJaviste</body></html>");
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    public void testDetailFragment() {
        showUi();
    }

    @Module(injects = {PageTest.class}, includes = MvpEspressoTestModule.class, library = true)
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
