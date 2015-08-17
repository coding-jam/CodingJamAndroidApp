package it.cosenonjaviste.ui.author;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.core.author.AuthorListModel;
import it.cosenonjaviste.model.WordPressService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

public class AuthorListFragmentTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(AuthorListFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);
    }

    @Test
    public void testAuthorList() {
        when(wordPressService.listAuthors())
                .thenReturn(TestData.authorResponse(2));

        fragmentRule.launchFragment(new AuthorListModel());

        onView(withText("name 1")).check(matches(isDisplayed()));
    }
}
