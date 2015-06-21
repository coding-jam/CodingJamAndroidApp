package it.cosenonjaviste.contact;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;

public class ContactFragmentTest {
    @Rule public FragmentRule fragmentRule = new FragmentRule(ContactFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);
    }

    @Test public void testContactFragment() {
        fragmentRule.launchFragment(new ContactModel());

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}