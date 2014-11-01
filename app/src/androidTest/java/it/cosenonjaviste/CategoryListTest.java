package it.cosenonjaviste;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.base.BaseFragmentTest;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

public class CategoryListTest extends BaseFragmentTest {

    @Inject MockWebServer server;

    public CategoryListTest() {
        super(CategoryListFragment.class, true);
    }

    @Override protected Object[] getTestModules() {
        return new Object[]{new MvpTestModule(true), new TestModule()};
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        MockWebServerUtils.initDispatcher(server, JsonStubs.CATEGORIES);
    }

    public void testPostList() throws InterruptedException {
        Thread.sleep(20000);
    }

    @Module(injects = {CategoryListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
