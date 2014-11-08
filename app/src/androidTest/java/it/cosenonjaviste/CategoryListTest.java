package it.cosenonjaviste;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

public class CategoryListTest extends CnjFragmentTest {

    @Inject MockWebServer server;

    public CategoryListTest() {
        super(CategoryListFragment.class);
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        MockWebServerUtils.initDispatcher(server, JsonStubs.CATEGORIES);
    }

    public void testCategoryList() throws InterruptedException {
        showUi();
    }

    @Module(injects = {CategoryListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
