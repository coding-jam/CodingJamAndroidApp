package it.cosenonjaviste.ui.contact;

import android.support.test.espresso.action.ViewActions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Completable;
import it.cosenonjaviste.R;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.ui.CnjDaggerRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ContactFragmentTest {
    @Rule public FragmentRule fragmentRule = new FragmentRule(ContactFragment.class);

    @Mock MailJetService mailJetService;

    @Rule public final CnjDaggerRule daggerRule = new CnjDaggerRule();

    @Before public void setUp() {
        when(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Completable.complete());
    }

    @Test public void testContactFragment() {
        fragmentRule.launchFragment();

        onView(withId(R.id.name)).perform(ViewActions.typeText("name"));
        onView(withId(R.id.email)).perform(ViewActions.typeText("email@email.it"));
        onView(withId(R.id.message)).perform(ViewActions.typeText("message"));

        onView(withId(R.id.send_button)).perform(scrollTo(), click());
    }
}