package it.cosenonjaviste.ui.twitter;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.core.twitter.TweetListModel;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.ui.CnjDaggerRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TweetListFragmentTest {

    @Mock TwitterService twitterService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(TweetListFragment.class);

    @Rule public final CnjDaggerRule daggerRule = new CnjDaggerRule();

    @Test public void testTweetList() {
        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets(10));

        fragmentRule.launchFragment(new TweetListModel());

        onView(withText("tweet text 1")).check(matches(isDisplayed()));
    }
}
