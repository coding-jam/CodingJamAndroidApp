package it.cosenonjaviste.androidtest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import javax.inject.Inject;

import it.cosenonjaviste.androidtest.base.DaggerRule;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;

public class PageTest {

    @Inject MockWebServerWrapper server;

    private final FragmentRule fragmentRule = FragmentRule.create(PageFragment.class, new PageModel("url"));

    private final DaggerRule daggerRule = new DaggerRule(component -> {
        component.inject(this);
        server.initDispatcher("<html><body>CoseNonJaviste</body></html>");
    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(fragmentRule);

    @Test public void testDetailFragment() {
//        showUi();
    }
}
