package it.cosenonjaviste.mvp.post;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class PostListPresenterTest extends PresenterTest<PostListView, PostListPresenter> {

    @Inject MockWebServer server;

    @Inject MvpConfig<PostListView, PostListPresenter> config;

    @Override public MvpConfig<PostListView, PostListPresenter> getConfig() {
        return config;
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        MockWebServerUtils.initDispatcher(server, JsonStubs.getPostList(1));
    }

    @Test
    public void testLoad() {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        MockWebServerUtils.initDispatcher(server, JsonStubs.getPostList(20));
        presenter.reloadData();
        MockWebServerUtils.initDispatcher(server, JsonStubs.getPostList(5));
        presenter.loadNextPage();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(25);
    }

    @Test
    public void testGoToDetails() {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        Post firstPost = model.get(0);

        presenter.goToDetail(firstPost);

        PostDetailModel detailModel = getLastModel();
        Post detailPost = detailModel.getPost();

        assertThat(detailPost).isNotNull();
        assertThat(detailPost.getId()).isEqualTo(firstPost.getId());
    }

    @Module(injects = {PostListPresenterTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }

    @After public void shutdownServer() throws IOException {
        server.shutdown();
    }
}