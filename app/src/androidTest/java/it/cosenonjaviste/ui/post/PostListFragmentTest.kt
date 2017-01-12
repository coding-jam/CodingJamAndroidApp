package it.cosenonjaviste.ui.post

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import it.cosenonjaviste.R
import it.cosenonjaviste.TestData
import it.cosenonjaviste.androidtest.base.FragmentRule
import it.cosenonjaviste.core.post.PostListModel
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.CnjDaggerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`

class PostListFragmentTest {

    @Mock lateinit var wordPressService: WordPressService

    @get:Rule var fragmentRule = FragmentRule(PostListFragment::class.java)

    @get:Rule val daggerRule = CnjDaggerRule()

    @Before fun setUp() {
        `when`(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(0, 10))
        `when`(wordPressService.listPosts(eq(2)))
                .thenReturn(TestData.postResponse(10, 10))
    }

    @Test @Throws(InterruptedException::class)
    fun testPostList() {
        fragmentRule.launchFragment(PostListModel())

        onView(withText("post title 1")).check(matches(isDisplayed()))
    }

    @Test fun testGoToPostDetail() {
        fragmentRule.launchFragment(PostListModel())

        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
    }
}
