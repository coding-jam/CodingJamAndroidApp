package it.cosenonjaviste.contact;

import android.support.test.espresso.action.ViewActions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.R;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.androidtest.utils.TestUtils;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.ui.contact.ContactFragment;
import retrofit.client.Response;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ContactFragmentTest {
    @Rule public FragmentRule fragmentRule = new FragmentRule(ContactFragment.class);

    @Inject MailJetService mailJetService;

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);

        when(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.<Response>just(null).doOnNext(TestUtils.sleepAction()));
    }

    @Test public void testContactFragment() {
        fragmentRule.launchFragment(new it.cosenonjaviste.core.contact.ContactModel());

        onView(withId(R.id.name)).perform(ViewActions.typeText("name"));
        onView(withId(R.id.email)).perform(ViewActions.typeText("email@email.it"));
        onView(withId(R.id.message)).perform(ViewActions.typeText("message"));

        onView(withId(R.id.send_button)).perform(scrollTo(), click());
    }
}