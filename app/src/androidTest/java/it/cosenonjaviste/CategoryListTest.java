package it.cosenonjaviste;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.base.MvpEspressoTestModule;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class CategoryListTest extends CnjFragmentTest<CategoryListModel> {

    @Inject MockWebServerWrapper server;

    public CategoryListTest() {
        super(CategoryListFragment.class, new CategoryListModel());
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

    @Module(injects = {CategoryListTest.class}, includes = MvpEspressoTestModule.class)
    public static class TestModule {
    }
}
