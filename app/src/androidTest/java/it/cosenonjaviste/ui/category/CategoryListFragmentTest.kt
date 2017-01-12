package it.cosenonjaviste.ui.category

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import it.cosenonjaviste.TestData
import it.cosenonjaviste.androidtest.base.FragmentRule
import it.cosenonjaviste.core.category.CategoryListModel
import it.cosenonjaviste.model.CategoryResponse
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.CnjDaggerRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import rx.Observable
import java.io.IOException

class CategoryListFragmentTest {

    @Mock lateinit var wordPressService: WordPressService

    @get:Rule var fragmentRule = FragmentRule(CategoryListFragment::class.java)

    @get:Rule val daggerRule = CnjDaggerRule()

    @Test fun testCategoryList() {
        `when`(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3))

        fragmentRule.launchFragment(CategoryListModel())

        onView(withText("cat 1")).check(matches(isDisplayed()))
    }

    @Test fun testCategoryError() {
        `when`(wordPressService.listCategories())
                .thenReturn(Observable.error<CategoryResponse>(IOException("bla bla bla")))

        fragmentRule.launchFragment(CategoryListModel())

        verify<WordPressService>(wordPressService).listCategories()
    }
}
