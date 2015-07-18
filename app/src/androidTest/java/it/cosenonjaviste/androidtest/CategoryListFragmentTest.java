package it.cosenonjaviste.androidtest;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;
import rx.functions.Action1;

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
                .thenReturn(TestData.categoryResponse(3).doOnNext(sleepOnNext()));

        fragmentRule.launchFragment(new CategoryListModel());

        sleep();

        onView(withText("cat 1")).check(matches(isDisplayed()));
    }

    private void sleep() {
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @NonNull private Action1<CategoryResponse> sleepOnNext() {
        return categoryResponse -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        };
    }

    @Test public void testCategoryEmptyList() {
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(0).doOnNext(sleepOnNext()));

        fragmentRule.launchFragment(new CategoryListModel());

        verify(wordPressService).listCategories();

        sleep();

        onView(withText("Nothing here")).check(matches(isDisplayed()));
    }

    @Test public void testCategoryError() {
        when(wordPressService.listCategories())
                .thenReturn(Observable.error(new IOException("bla bla bla")));

        fragmentRule.launchFragment(new CategoryListModel());

        verify(wordPressService).listCategories();

        sleep();
    }
}
