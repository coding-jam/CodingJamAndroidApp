package it.cosenonjaviste.core.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.postResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListViewModelTest {

    @Mock PostListView view;

    @Mock WordPressService wordPressService;

    @InjectMocks PostListViewModel viewModel;

    @Captor ArgumentCaptor<PageModel> modelCaptor;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = viewModel.initAndResume(view);

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(postResponse(6));

        PostListModel model = viewModel.initAndResume(view);
        viewModel.loadNextPage();

        List<Post> items = model.getItems();
        assertThat(items.size()).isEqualTo(16);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        PostListModel model = viewModel.initAndResume(view);
        assertThat(viewModel.isError().get()).isTrue();

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(6));

        viewModel.reloadData();

        assertThat(viewModel.isError().get()).isFalse();
        assertThat(model.getItems().size()).isEqualTo(6);
    }

    @Test
    public void testGoToDetails() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = viewModel.initAndResume(view);
        Post firstPost = model.getItems().get(0);

        viewModel.goToDetail(firstPost);

        Mockito.verify(view).openDetail(modelCaptor.capture());

        PageModel detailModel = modelCaptor.getValue();
        String url = detailModel.getPost().getUrl();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.getUrl());
    }

    @Test
    public void testToolbalTitleNotVisible() {
        viewModel.initModel(new PostListModel());

        assertThat(viewModel.isToolbarVisible()).isFalse();
        assertThat(viewModel.getToolbarTitle()).isNull();
    }

    @Test
    public void testToolbalTitleAuthor() {
        viewModel.initModel(new PostListModel(TestData.createAuthor(1)));

        assertThat(viewModel.isToolbarVisible()).isTrue();
        assertThat(viewModel.getToolbarTitle()).isEqualTo("name 1 last name 1");
    }

    @Test
    public void testToolbalTitle() {
        viewModel.initModel(new PostListModel(new Category(123, "aaa", 1)));

        assertThat(viewModel.isToolbarVisible()).isTrue();
        assertThat(viewModel.getToolbarTitle()).isEqualTo("aaa");
    }
}