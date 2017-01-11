package it.cosenonjaviste.ui.author;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.core.author.AuthorListModel;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.CnjDaggerRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

public class AuthorListFragmentTest {

    @Mock WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(AuthorListFragment.class);

    @Rule public final CnjDaggerRule daggerRule = new CnjDaggerRule();

    @Test
    public void testAuthorList() {
        when(wordPressService.listAuthors())
                .thenReturn(TestData.INSTANCE.authorResponse(8));

        fragmentRule.launchFragment(new AuthorListModel());

        onView(withText("name 1")).check(matches(isDisplayed()));
    }
}
