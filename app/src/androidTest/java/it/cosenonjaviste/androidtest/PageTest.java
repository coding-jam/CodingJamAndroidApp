package it.cosenonjaviste.androidtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.androidtest.base.DaggerTestComponent;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.base.TestComponent;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;

public class PageTest {

    @Inject MockWebServerWrapper server;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PageFragment.class);

    @Before
    public void setUp() {
        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestComponent component = DaggerTestComponent.builder().build();
        ((CoseNonJavisteApp) app).setComponent(component);

        component.inject(this);

        server.initDispatcher("<html><body>CoseNonJaviste</body></html>");
    }

    @Test public void testDetailFragment() {
        fragmentRule.launchFragment(new PageModel("url"));
    }
}
