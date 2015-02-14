package it.cosenonjaviste.androidtest;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.androidtest.base.CnjFragmentTest;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.base.MvpEspressoTestModule;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;

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
    }
}
