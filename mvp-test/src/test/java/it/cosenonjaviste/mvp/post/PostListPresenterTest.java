package it.cosenonjaviste.mvp.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.page.PageModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest {

    @Mock PostListView view;

    @Inject PostListPresenter presenter;

    @Inject MockWebServerWrapper server;

    @Before
    public void setup() {
        ObjectGraph.create(getTestModule()).inject(this);
        server.initDispatcher(JsonStubs.getPostList(1));
    }

    @Captor ArgumentCaptor<PageModel> modelCaptor;

    protected Object getTestModule() {
        return new TestModule();
    }

    @Test
    public void testLoad() throws InterruptedException {
        PostListModel model = new PostListModel();
        presenter.initAndSubscribe(model, view);
        assertThat(model.getItems().size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
//        int requestCount = server.getRequestCount();
//        System.out.println(recordedRequest);
    }

    @Test
    public void testLoadMore() {
        server.initDispatcher(JsonStubs.getPostList(20));
        PostListModel model = new PostListModel();
        presenter.initAndSubscribe(model, view);
        server.initDispatcher(JsonStubs.getPostList(5));
        presenter.loadNextPage();
        OptionalList<Post> items = model.getItems();
        int size = items.size();
        assertThat(size).isEqualTo(25);
    }

    @Test
    public void testGoToDetails() {
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

    @Module(injects = {PostListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }

}