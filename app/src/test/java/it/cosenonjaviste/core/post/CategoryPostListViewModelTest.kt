package it.cosenonjaviste.core.post

import it.cosenonjaviste.TestData
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.Category
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.post.PostListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`

class CategoryPostListViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var wordPressService: WordPressService

    @InjectFromComponent(PostListFragment::class) lateinit var viewModel: PostListViewModel

    @Test
    @Throws(InterruptedException::class)
    fun testLoad() {
        `when`(wordPressService.listCategoryPosts(eq(1L), eq(1)))
                .thenReturn(TestData.postResponse(1))

        val model = viewModel.initAndResume(PostListArgument.create(Category.create(1, "cat", 10)))

        assertThat(model.getItems().size).isEqualTo(1)
    }

    @Test
    fun testLoadMore() {
        `when`(wordPressService.listCategoryPosts(eq(1L), eq(1)))
                .thenReturn(TestData.postResponse(10))
        `when`(wordPressService.listCategoryPosts(eq(1L), eq(2)))
                .thenReturn(TestData.postResponse(5))

        val model = viewModel.initAndResume(PostListArgument.create(Category.create(1, "cat", 10)))
        viewModel.loadNextPage()

        assertThat(model.getItems().size).isEqualTo(15)
    }
}