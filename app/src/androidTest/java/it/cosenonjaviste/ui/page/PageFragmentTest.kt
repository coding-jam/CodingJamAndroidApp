package it.cosenonjaviste.ui.page

import it.cosenonjaviste.TestData
import it.cosenonjaviste.androidtest.base.FragmentRule
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper
import it.cosenonjaviste.androidtest.utils.TestUtils
import it.cosenonjaviste.ui.CnjDaggerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PageFragmentTest {

    internal var server = MockWebServerWrapper()

    @get:Rule val fragmentRule = FragmentRule(PageFragment::class.java)

    @get:Rule val daggerRule = CnjDaggerRule()

    @Before fun setUp() {
        server.initDispatcher("<html><body>CoseNonJaviste<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A<br>A</body></html>")
    }

    @Test fun testDetailFragment() {
        fragmentRule.launchFragment(TestData.createPost(1, server.getUrl(false) + "abc"))
        TestUtils.sleep(100)
    }
}
