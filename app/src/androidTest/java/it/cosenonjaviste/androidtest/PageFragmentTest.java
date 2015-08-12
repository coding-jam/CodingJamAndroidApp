package it.cosenonjaviste.androidtest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.core.model.Post;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.ui.page.PageFragment;

public class PageFragmentTest {

    @Inject MockWebServerWrapper server;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PageFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);

        server.initDispatcher("<html><body>CoseNonJaviste<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A</body></html>");
    }

    @Test public void testDetailFragment() {
        fragmentRule.launchFragment(new PageModel(new Post("Sviluppare applicazioni con React e Flux", "url")));
    }
}
