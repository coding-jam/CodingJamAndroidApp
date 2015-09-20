package it.cosenonjaviste.ui.page;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.androidtest.utils.TestUtils;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.page.PageModel;

public class PageFragmentTest {

    @Inject MockWebServerWrapper server;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PageFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);

        server.initDispatcher("<html><body>CoseNonJaviste<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A</body></html>");
    }

    @Test public void testDetailFragment() {
        fragmentRule.launchFragment(new PageModel(TestData.createPost(1, server.getUrl(false) + "abc")));
        TestUtils.sleep(100);
    }
}
