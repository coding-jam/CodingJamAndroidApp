package it.cosenonjaviste.androidtest;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.CnjFragmentTest;
import it.cosenonjaviste.androidtest.base.MvpEspressoTestModule;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.model.WordPressService;

import static org.mockito.Mockito.when;

public class CategoryListTest extends CnjFragmentTest<CategoryListModel> {

    @Inject WordPressService wordPressService;

    public CategoryListTest() {
        super(CategoryListFragment.class, new CategoryListModel());
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3));
    }

    public void testCategoryList() throws InterruptedException {
        showUi();
    }

    @Module(injects = {CategoryListTest.class}, includes = MvpEspressoTestModule.class)
    public static class TestModule {
    }
}
