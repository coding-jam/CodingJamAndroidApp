package it.cosenonjaviste;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class CategoryListTest extends CnjFragmentTest {

    @Inject MockWebServerWrapper server;

    public CategoryListTest() {
        super(CategoryListFragment.class);
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        server.initDispatcher(JsonStubs.CATEGORIES);
    }

    public void testCategoryList() throws InterruptedException {
        showUi();
    }

    @Module(injects = {CategoryListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}