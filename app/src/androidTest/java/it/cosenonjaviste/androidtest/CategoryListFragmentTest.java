package it.cosenonjaviste.androidtest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.core.category.CategoryListModel;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.category.CategoryListFragment;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryListFragmentTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(CategoryListFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);
    }

    @Test public void testCategoryList() {
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3));

        fragmentRule.launchFragment(new CategoryListModel());

        onView(withText("cat 1")).check(matches(isDisplayed()));
    }

    @Test public void testCategoryEmptyList() {
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(0));

        fragmentRule.launchFragment(new CategoryListModel());

        verify(wordPressService).listCategories();

        onView(withText("Nothing here.")).check(matches(isDisplayed()));
    }

    @Test public void testCategoryError() {
        when(wordPressService.listCategories())
                .thenReturn(Observable.error(new IOException("bla bla bla")));

        fragmentRule.launchFragment(new CategoryListModel());

        verify(wordPressService).listCategories();
    }
}
