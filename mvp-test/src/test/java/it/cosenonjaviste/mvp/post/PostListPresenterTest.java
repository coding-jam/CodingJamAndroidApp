package it.cosenonjaviste.mvp.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import dagger.Module;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.page.PageModel;
import it.cosenonjaviste.stubs.JsonStubs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest extends PostListPresenterBaseTest {

    @Captor ArgumentCaptor<PageModel> modelCaptor;

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected PostListModel getModel() {
        return new PostListModel();
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        PostListModel model = presenter.getModel();
        assertThat(model.getItems().size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        System.out.println(lastUrl);
//        int requestCount = server.getRequestCount();
//        System.out.println(recordedRequest);
    }

    @Test
    public void testLoadMore() {
        server.initDispatcher(JsonStubs.getPostList(20));
        presenter.reloadData();
        server.initDispatcher(JsonStubs.getPostList(5));
        presenter.loadNextPage();
        PostListModel model = presenter.getModel();
        assertThat(model.getItems().size()).isEqualTo(25);
    }

    @Test
    public void testGoToDetails() {
        presenter.reloadData();
        PostListModel model = presenter.getModel();
        Post firstPost = model.getItems().get(0);

        presenter.goToDetail(firstPost);

        verify(view).openM(any(), modelCaptor.capture());

        PageModel detailModel = modelCaptor.getValue();
        String url = detailModel.getUrl();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.getUrl());
    }

    @Module(injects = {PostListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }

}