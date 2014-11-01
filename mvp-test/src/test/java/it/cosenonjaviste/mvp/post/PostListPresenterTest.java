package it.cosenonjaviste.mvp.post;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.TestContextBinder;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class PostListPresenterTest {

    @Inject MockWebServer server;

    @Inject PostListMvpConfig config;

    @Mock PostListView view;

    private TestContextBinder contextBinder;

    private PostListPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        contextBinder = new TestContextBinder(this, new MvpTestModule(), new TestModule());

        MockWebServerUtils.initDispatcher(server, JsonStubs.POSTS);

        presenter = config.createAndInitPresenter(contextBinder, null);
        presenter.subscribe(view);
    }

    @Test
    public void testLoad() {
        presenter.loadData(0);
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(1);
    }

    @Test
    public void testGoToDetails() {
        presenter.loadData(0);
        OptionalList<Post> model = presenter.getModel();
        Post firstPost = model.get(0);

        presenter.goToDetail(firstPost);

        PostDetailModel detailModel = contextBinder.getLastModel();
        Post detailPost = detailModel.getPost();

        assertThat(detailPost).isNotNull();
        assertThat(detailPost.getId()).isEqualTo(firstPost.getId());
    }

    @Module(injects = {PostListPresenterTest.class, PostDetailMvpConfig.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
//        @Provides PostListView providePostListView() {
//            return mock(PostListView.class);
//        }
//
//        @Provides PostDetailView providePostDetailView() {
//            return mock(PostDetailView.class);
//        }
    }

    @After public void shutdownServer() throws IOException {
        server.shutdown();
    }
}