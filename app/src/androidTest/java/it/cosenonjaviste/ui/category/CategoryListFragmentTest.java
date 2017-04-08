package it.cosenonjaviste.ui.category;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import io.reactivex.Single;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.core.category.CategoryListModel;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.CnjDaggerRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryListFragmentTest {

    @Mock WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(CategoryListFragment.class);

    @Rule public final CnjDaggerRule daggerRule = new CnjDaggerRule();

    @Test public void testCategoryList() {
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3));

        fragmentRule.launchFragment(new CategoryListModel());

        onView(withText("cat 1")).check(matches(isDisplayed()));
    }

    @Test public void testCategoryError() {
        when(wordPressService.listCategories())
                .thenReturn(Single.error(new IOException("bla bla bla")));

        fragmentRule.launchFragment(new CategoryListModel());

        verify(wordPressService).listCategories();
    }
}
