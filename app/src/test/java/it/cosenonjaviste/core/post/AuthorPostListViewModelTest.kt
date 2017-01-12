package it.cosenonjaviste.core.post

import it.cosenonjaviste.TestData
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.post.PostListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class AuthorPostListViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var wordPressService: WordPressService

    @InjectFromComponent(PostListFragment::class) lateinit var viewModel: PostListViewModel

    @Test
    @Throws(InterruptedException::class)
    fun testLoad() {
        `when`(wordPressService.listAuthorPosts(anyLong(), anyInt()))
                .thenReturn(TestData.postResponse(1))

        val model = viewModel.initAndResume(PostListArgument.create(TestData.createAuthor(145)))

        assertThat(model.getItems().size).isEqualTo(1)
        verify<WordPressService>(wordPressService).listAuthorPosts(eq(145L), eq(1))
    }
}