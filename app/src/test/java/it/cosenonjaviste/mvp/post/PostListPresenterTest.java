package it.cosenonjaviste.mvp.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import it.cosenonjaviste.DaggerUtils;
import it.cosenonjaviste.lib.mvp.utils.OptionalList;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.post.PostListPresenter;
import rx.Observable;

import static it.cosenonjaviste.TestData.postResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest {

    @Mock PostListFragment view;

    @Inject PostListPresenter presenter;

    @Inject WordPressService wordPressService;

    @Before
    public void setup() {
        DaggerUtils.getComponent().inject(this);
    }

    @Captor ArgumentCaptor<PageModel> modelCaptor;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = new PostListModel();
        presenter.initAndSubscribe(model, view);

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(postResponse(6));

        PostListModel model = new PostListModel();
        presenter.initAndSubscribe(model, view);
        presenter.loadNextPage();

        OptionalList<Post> items = model.getItems();
        assertThat(items.size()).isEqualTo(16);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        PostListModel model = new PostListModel();
        presenter.initAndSubscribe(model, view);
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
        presenter.initAndSubscribe(model, view);
        Post firstPost = model.getItems().get(0);

        presenter.goToDetail(firstPost);

        verify(view).open(any(), modelCaptor.capture());

        PageModel detailModel = modelCaptor.getValue();
        String url = detailModel.getUrl();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.getUrl());
    }
}