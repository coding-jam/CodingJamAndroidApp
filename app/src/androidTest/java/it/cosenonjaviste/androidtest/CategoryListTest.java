package it.cosenonjaviste.androidtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.DaggerTestComponent;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.TestComponent;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.model.WordPressService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

public class CategoryListTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(CategoryListFragment.class);

    @Before
    public void setUp() {
        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestComponent component = DaggerTestComponent.builder().build();
        ((CoseNonJavisteApp) app).setComponent(component);

        component.inject(this);
    }

    @Test public void testCategoryList() {
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3));

        fragmentRule.launchFragment(new CategoryListModel());

        onView(withText("cat 1")).check(matches(isDisplayed()));
    }
}
