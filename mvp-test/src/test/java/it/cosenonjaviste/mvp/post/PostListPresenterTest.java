package it.cosenonjaviste.mvp.post;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;
import it.cosenonjaviste.utils.TestContextBinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class PostListPresenterTest {

    @Inject MockWebServer server;

    @Inject PostListMvpConfig config;

    @Inject PostListView view;

    private TestContextBinder contextBinder;

    private PostListPresenter presenter;

    @Before
    public void setup() {
        ObjectGraph objectGraph = ObjectGraph.create(new MvpTestModule(), new TestModule());
        objectGraph.inject(this);
        contextBinder = new TestContextBinder(objectGraph);

        MockWebServerUtils.initDispatcher(server, JsonStubs.POSTS);

        presenter = config.createAndInitPresenter(contextBinder, null);
        presenter.subscribe(view);
    }

    @Test
    public void testLoad() {
        presenter.listPosts(0);
        PostListModel model = presenter.getModel();
        assertEquals(1, model.getPosts().size());
    }

    @Test
    public void testGoToDetails() {
        presenter.listPosts(0);
        PostListModel model = presenter.getModel();
        Post firstPost = model.getPosts().get(0);

        presenter.goToDetail(firstPost);

        PostDetailModel detailModel = contextBinder.getLastModel();
        assertNotNull(detailModel.getPost());
        assertEquals(firstPost.getId(), detailModel.getPost().getId());
    }

    @Module(injects = {PostListPresenterTest.class, PostDetailMvpConfig.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides PostListView providePostListView() {
            return mock(PostListView.class);
        }

        @Provides PostDetailView providePostDetailView() {
            return mock(PostDetailView.class);
        }
    }
}