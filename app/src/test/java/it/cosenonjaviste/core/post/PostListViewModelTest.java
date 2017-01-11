package it.cosenonjaviste.core.post;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.post.PostListFragment;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostListViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @Mock WordPressService wordPressService;

    @Mock Navigator navigator;

    @Captor ArgumentCaptor<Post> captor;

    @InjectFromComponent(PostListFragment.class) PostListViewModel viewModel;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.INSTANCE.postResponse(1));

        PostListModel model = viewModel.initAndResume();

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.INSTANCE.postResponse(10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(TestData.INSTANCE.postResponse(6));

        PostListModel model = viewModel.initAndResume();
        viewModel.loadNextPage();

        List<Post> items = model.getItems();
        assertThat(items.size()).isEqualTo(16);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        PostListModel model = viewModel.initAndResume();
        assertThat(viewModel.isError().get()).isTrue();

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.INSTANCE.postResponse(6));

        viewModel.reloadData();

        assertThat(viewModel.isError().get()).isFalse();
        assertThat(model.getItems().size()).isEqualTo(6);
    }

    @Test
    public void testGoToDetails() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.INSTANCE.postResponse(1));

        PostListModel model = viewModel.initAndResume();
        Post firstPost = model.getItems().get(0);

        viewModel.goToDetail(0);

        verify(navigator).openDetail(captor.capture());
        Post detailPost = captor.getValue();
        String url = detailPost.url();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.url());
    }

    @Test
    public void testToolbalTitleNotVisible() {
        viewModel.initAndResume();

        assertThat(viewModel.isToolbarVisible()).isFalse();
        assertThat(viewModel.getToolbarTitle()).isNull();
    }

    @Test
    public void testToolbalTitleAuthor() {
        viewModel.initAndResume(PostListArgument.create(TestData.INSTANCE.createAuthor(1)));

        assertThat(viewModel.isToolbarVisible()).isTrue();
        assertThat(viewModel.getToolbarTitle()).isEqualTo("name 1 last name 1");
    }

    @Test
    public void testToolbalTitle() {
        viewModel.initAndResume(PostListArgument.create(Category.create(123, "aaa", 1)));

        assertThat(viewModel.isToolbarVisible()).isTrue();
        assertThat(viewModel.getToolbarTitle()).isEqualTo("aaa");
    }
}