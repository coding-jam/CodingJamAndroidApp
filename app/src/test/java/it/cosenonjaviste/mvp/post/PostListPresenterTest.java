package it.cosenonjaviste.mvp.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.lib.mvp.utils.OptionalList;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.TestLifeCycle;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.post.PostListPresenter;
import it.cosenonjaviste.post.PostListView;
import rx.Observable;

import static it.cosenonjaviste.TestData.postResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest {

    @Mock PostListView view;

    private TestLifeCycle testLifeCycle = new TestLifeCycle();

    @Mock WordPressService wordPressService;

    @InjectMocks PostListPresenter presenter;

    @Captor ArgumentCaptor<PageModel> modelCaptor;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = new PostListModel();
        testLifeCycle.initAndResume(model, presenter, view);

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(postResponse(6));

        PostListModel model = new PostListModel();
        testLifeCycle.initAndResume(model, presenter, view);
        presenter.loadNextPage();

        OptionalList<Post> items = model.getItems();
        assertThat(items.size()).isEqualTo(16);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        PostListModel model = new PostListModel();
        testLifeCycle.initAndResume(model, presenter, view);
        assertThat(model.getItems().isError()).isTrue();

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(6));

        presenter.reloadData();

        assertThat(model.getItems().isError()).isFalse();
        assertThat(model.getItems().size()).isEqualTo(6);
    }

    @Test
    public void testGoToDetails() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = new PostListModel();
        testLifeCycle.initAndResume(model, presenter, view);
        Post firstPost = model.getItems().get(0);

        presenter.goToDetail(firstPost);

        verify(view).openDetail(modelCaptor.capture());

        PageModel detailModel = modelCaptor.getValue();
        String url = detailModel.getUrl();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.getUrl());
    }
}