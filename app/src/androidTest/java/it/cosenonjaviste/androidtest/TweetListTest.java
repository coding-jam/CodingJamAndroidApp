package it.cosenonjaviste.androidtest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListModel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TweetListTest {

    @Inject TwitterService twitterService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(TweetListFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);
    }

    @Test public void testPostList() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets());

        fragmentRule.launchFragment(new TweetListModel());

        onView(withText("tweet text 1")).check(matches(isDisplayed()));
    }
}
