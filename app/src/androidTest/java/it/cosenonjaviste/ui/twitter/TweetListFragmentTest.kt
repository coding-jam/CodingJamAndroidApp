package it.cosenonjaviste.ui.twitter

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import it.cosenonjaviste.TestData
import it.cosenonjaviste.androidtest.base.FragmentRule
import it.cosenonjaviste.core.twitter.TweetListModel
import it.cosenonjaviste.model.TwitterService
import it.cosenonjaviste.ui.CnjDaggerRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`

class TweetListFragmentTest {

    @Mock lateinit var twitterService: TwitterService

    @get:Rule val fragmentRule = FragmentRule(TweetListFragment::class.java)

    @get:Rule val daggerRule = CnjDaggerRule()

    @Test fun testTweetList() {
        `when`(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets(10))

        fragmentRule.launchFragment(TweetListModel())

        onView(withText("tweet text 1")).check(matches(isDisplayed()))
    }
}
