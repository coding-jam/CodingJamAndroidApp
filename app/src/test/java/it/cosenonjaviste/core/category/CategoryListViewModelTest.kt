package it.cosenonjaviste.core.category

import it.cosenonjaviste.TestData
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.core.Navigator
import it.cosenonjaviste.core.post.PostListArgument
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.CategoryResponse
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.category.CategoryListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import rx.Observable

class CategoryListViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var wordPressService: WordPressService

    @Mock lateinit var navigator: Navigator

    @InjectFromComponent(CategoryListFragment::class) lateinit var viewModel: CategoryListViewModel

    @Test
    fun testLoad() {
        given(wordPressService.listCategories())
                .willReturn(TestData.categoryResponse(3))

        val model = viewModel.initAndResume()

        assertThat(model.getItems()).hasSize(3)
        val category = model.get(2)
        assertThat(category.id()).isEqualTo(2)
        assertThat(category.title()).isEqualTo("cat 2")
        assertThat(category.postCount()).isEqualTo(12)
    }

    @Test
    fun testLoadAndPullToRefresh() {
        given(wordPressService.listCategories())
                .willReturn(TestData.categoryResponse(3), TestData.categoryResponse(2))

        val model = viewModel.initAndResume()

        assertThat(model.getItems()).hasSize(3)

        viewModel.loadDataPullToRefresh()

        assertThat(model.getItems()).hasSize(2)
    }

    @Test
    fun testRetryAfterError() {
        given(wordPressService.listCategories())
                .willReturn(Observable.error<CategoryResponse>(RuntimeException()))

        val model = viewModel.initAndResume()

        given(wordPressService.listCategories())
                .willReturn(TestData.categoryResponse(3))

        viewModel.reloadData()

        assertThat(model.getItems()).hasSize(3)
    }

    @Test
    fun testGoToPosts() {
        given(wordPressService.listCategories())
                .willReturn(TestData.categoryResponse(3))

        val categoryListModel = viewModel.initAndResume()

        viewModel.goToPosts(1)

        verify(navigator).openPostList(PostListArgument.create(categoryListModel.get(1)))
    }
}