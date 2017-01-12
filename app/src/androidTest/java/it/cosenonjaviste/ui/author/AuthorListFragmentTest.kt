package it.cosenonjaviste.ui.author

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import it.cosenonjaviste.TestData
import it.cosenonjaviste.androidtest.base.FragmentRule
import it.cosenonjaviste.core.author.AuthorListModel
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.CnjDaggerRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class AuthorListFragmentTest {

    @Mock lateinit var wordPressService: WordPressService

    @get:Rule var fragmentRule = FragmentRule(AuthorListFragment::class.java)

    @get:Rule val daggerRule = CnjDaggerRule()

    @Test
    fun testAuthorList() {
        `when`(wordPressService.listAuthors())
                .thenReturn(TestData.authorResponse(8))

        fragmentRule.launchFragment(AuthorListModel())

        onView(withText("name 1")).check(matches(isDisplayed()))
    }
}
