package it.cosenonjaviste.ui.contact

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.matcher.ViewMatchers.withId
import it.cosenonjaviste.R
import it.cosenonjaviste.androidtest.base.FragmentRule
import it.cosenonjaviste.androidtest.utils.TestUtils
import it.cosenonjaviste.model.MailJetService
import it.cosenonjaviste.ui.CnjDaggerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import retrofit.client.Response
import rx.Observable

class ContactFragmentTest {
    @get:Rule var fragmentRule = FragmentRule(ContactFragment::class.java)

    @Mock lateinit var mailJetService: MailJetService

    @get:Rule val daggerRule = CnjDaggerRule()

    @Before fun setUp() {
        `when`(mailJetService!!.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just<Response>(null).doOnNext { a -> TestUtils.sleep(1) })
    }

    @Test fun testContactFragment() {
        fragmentRule.launchFragment()

        onView(withId(R.id.name)).perform(ViewActions.typeText("name"))
        onView(withId(R.id.email)).perform(ViewActions.typeText("email@email.it"))
        onView(withId(R.id.message)).perform(ViewActions.typeText("message"))

        onView(withId(R.id.send_button)).perform(scrollTo(), click())
    }
}