package it.cosenonjaviste.core.mvp.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import it.cosenonjaviste.core.model.Post;
import it.cosenonjaviste.core.model.WordPressService;
import it.cosenonjaviste.core.mvp.ViewMock;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.core.post.PostListView;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.postResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest {

    private ViewMock<PostListView> view = new ViewMock<>(PostListView.class);

    @Mock WordPressService wordPressService;

    @InjectMocks PostListPresenter presenter;

    @Captor ArgumentCaptor<PageModel> modelCaptor;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = view.initAndResume(presenter);

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(postResponse(6));

        PostListModel model = view.initAndResume(presenter);
        presenter.loadNextPage();

        List<Post> items = model.getItems();
        assertThat(items.size()).isEqualTo(16);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        PostListModel model = view.initAndResume(presenter);
        assertThat(presenter.isError().get()).isTrue();

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(6));

        presenter.reloadData();

        assertThat(presenter.isError().get()).isFalse();
        assertThat(model.getItems().size()).isEqualTo(6);
    }

    @Test
    public void testGoToDetails() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = view.initAndResume(presenter);
        Post firstPost = model.getItems().get(0);

        presenter.goToDetail(firstPost);

        view.verify().openDetail(modelCaptor.capture());

        PageModel detailModel = modelCaptor.getValue();
        String url = detailModel.getPost().getUrl();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.getUrl());
    }
}