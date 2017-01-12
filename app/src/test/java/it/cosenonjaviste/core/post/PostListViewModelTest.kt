package it.cosenonjaviste.core.post

import it.cosenonjaviste.TestData
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.core.Navigator
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.Category
import it.cosenonjaviste.model.Post
import it.cosenonjaviste.model.PostResponse
import it.cosenonjaviste.model.WordPressService
import it.cosenonjaviste.ui.post.PostListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import rx.Observable

class PostListViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var wordPressService: WordPressService

    @Mock lateinit var navigator: Navigator

    @Captor lateinit var captor: ArgumentCaptor<Post>

    @InjectFromComponent(PostListFragment::class) lateinit var viewModel: PostListViewModel

    @Test
    @Throws(InterruptedException::class)
    fun testLoad() {
        `when`(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(1))

        val model = viewModel.initAndResume()

        assertThat(model.getItems().size).isEqualTo(1)
    }

    @Test
    fun testLoadMore() {
        `when`(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(10))
        `when`(wordPressService.listPosts(eq(2)))
                .thenReturn(TestData.postResponse(6))

        val model = viewModel.initAndResume()
        viewModel.loadNextPage()

        val items = model.getItems()
        assertThat(items.size).isEqualTo(16)
    }

    @Test
    fun testRetryAfterError() {
        `when`(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error<PostResponse>(RuntimeException()))

        val model = viewModel.initAndResume()
        assertThat(viewModel.isError.get()).isTrue()

        `when`(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(6))

        viewModel.reloadData()

        assertThat(viewModel.isError.get()).isFalse()
        assertThat(model.getItems().size).isEqualTo(6)
    }

    @Test
    fun testGoToDetails() {
        `when`(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(1))

        val model = viewModel.initAndResume()
        val firstPost = model.getItems()[0]

        viewModel.goToDetail(0)

        verify<Navigator>(navigator).openDetail(captor.capture())
        val detailPost = captor.value
        val url = detailPost.url()

        assertThat(url).isNotNull()
        assertThat(url).isEqualTo(firstPost.url())
    }

    @Test
    fun testToolbalTitleNotVisible() {
        viewModel.initAndResume()

        assertThat(viewModel.isToolbarVisible).isFalse()
        assertThat(viewModel.toolbarTitle).isNull()
    }

    @Test
    fun testToolbalTitleAuthor() {
        viewModel.initAndResume(PostListArgument.create(TestData.createAuthor(1)))

        assertThat(viewModel.isToolbarVisible).isTrue()
        assertThat(viewModel.toolbarTitle).isEqualTo("name 1 last name 1")
    }

    @Test
    fun testToolbalTitle() {
        viewModel.initAndResume(PostListArgument.create(Category.create(123, "aaa", 1)))

        assertThat(viewModel.isToolbarVisible).isTrue()
        assertThat(viewModel.toolbarTitle).isEqualTo("aaa")
    }
}