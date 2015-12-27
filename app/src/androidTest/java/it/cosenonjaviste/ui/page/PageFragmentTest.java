package it.cosenonjaviste.ui.page;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.utils.TestUtils;
import it.cosenonjaviste.ui.CnjDaggerRule;

public class PageFragmentTest {

    @Mock MockWebServerWrapper server;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PageFragment.class);

    @Rule public final CnjDaggerRule daggerRule = new CnjDaggerRule();

    @Before public void setUp() {
        server.initDispatcher("<html><body>CoseNonJaviste<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A</body></html>");
    }

    @Test public void testDetailFragment() {
        fragmentRule.launchFragment(TestData.createPost(1, server.getUrl(false) + "abc"));
        TestUtils.sleep(100);
    }
}
