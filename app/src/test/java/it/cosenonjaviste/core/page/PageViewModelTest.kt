package it.cosenonjaviste.core.page

import it.cosenonjaviste.TestData
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.core.Navigator
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.Author
import it.cosenonjaviste.model.Post
import it.cosenonjaviste.ui.page.PageFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify

class PageViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var navigator: Navigator

    @InjectFromComponent(PageFragment::class) lateinit var viewModel: PageViewModel

    @Test
    fun testLoad() {
        viewModel.initAndResume(Post.create(1, Author.create(1, "name", "last", "email"), "title", null, "url", null))

        assertThat(viewModel.post.url()).isEqualTo("url")
    }

    @Test
    fun testShare() {
        viewModel.initAndResume(TestData.createPost(1))

        viewModel.share()

        verify<Navigator>(navigator).share(anyString(), anyString())
    }
}