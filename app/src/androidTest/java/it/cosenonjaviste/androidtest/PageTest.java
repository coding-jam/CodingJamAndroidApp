package it.cosenonjaviste.androidtest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;

public class PageTest {

    @Inject MockWebServerWrapper server;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PageFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);

        server.initDispatcher("<html><body>CoseNonJaviste</body></html>");
    }

    @Test public void testDetailFragment() {
        fragmentRule.launchFragment(new PageModel("url"));
    }
}
