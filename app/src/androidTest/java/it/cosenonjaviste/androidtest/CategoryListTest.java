package it.cosenonjaviste.androidtest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.model.WordPressService;

import static org.mockito.Mockito.when;

public class CategoryListTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(CategoryListFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);
    }

    @Test public void testCategoryList() {
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3).doOnNext(categoryResponse -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                }));

        fragmentRule.launchFragment(new CategoryListModel());

//        onView(withText("cat 1")).check(matches(isDisplayed()));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
