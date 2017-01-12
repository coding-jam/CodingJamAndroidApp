package it.cosenonjaviste.core.author

import it.cosenonjaviste.TestData.authorResponse
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.core.Navigator
import it.cosenonjaviste.core.post.PostListArgument
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.AuthorResponse
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.author.AuthorListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import rx.Observable

class AuthorListViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @InjectFromComponent(AuthorListFragment::class) lateinit var viewModel: AuthorListViewModel

    @Mock lateinit var wordPressService : WordPressService

    @Mock lateinit var navigator : Navigator

    @Test
    fun testLoad() {
        given(wordPressService.listAuthors())
                .willReturn(authorResponse(10))

        val model = viewModel.initAndResume()

        assertThat(model.size()).isEqualTo(10)
    }

    @Test
    fun testRetryAfterError() {
        given(wordPressService.listAuthors())
                .willReturn(
                        Observable.error<AuthorResponse>(RuntimeException()),
                        authorResponse(2)
                )

        val model = viewModel.initAndResume()

        assertThat(model.size()).isEqualTo(0)

        viewModel.reloadData()

        assertThat(model.size()).isEqualTo(2)
    }

    @Test
    fun testGoToDetail() {
        given(wordPressService.listAuthors())
                .willReturn(authorResponse(2))

        val authorListModel = viewModel.initAndResume()

        viewModel.goToAuthorDetail(1)

        verify<Navigator>(navigator).openPostList(PostListArgument.create(authorListModel.get(1)))
    }
}