package it.cosenonjaviste.ui.post;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.R;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.model.WordPressService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PostListFragmentTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PostListFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(0, 10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(TestData.postResponse(10, 10));
    }

    @Test public void testPostList() throws InterruptedException {
        fragmentRule.launchFragment(new PostListModel());

        onView(withText("post title 1")).check(matches(isDisplayed()));
    }

    @Test public void testGoToPostDetail() {
        fragmentRule.launchFragment(new PostListModel());

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }
}
