package it.cosenonjaviste.mvp.post;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import it.cosenonjaviste.CnjNavigator;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;
import it.cosenonjaviste.utils.PresenterTestUtils;
import it.cosenonjaviste.utils.TestContextBinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class PostListPresenterTest {

    @Inject MockWebServer server;

    @Inject PostListPresenter presenter;

    @Inject CnjNavigator navigator;

    @Inject PostListView view;

    private ObjectGraph objectGraph;

    @Before
    public void setup() {
        objectGraph = ObjectGraph.create(new MvpTestModule(), new TestModule());
        objectGraph.inject(this);

        MockWebServerUtils.initDispatcher(server, JsonStubs.POSTS);
    }

    @Test
    public void testLoad() {
        PostListModel model = PresenterTestUtils.init(objectGraph, presenter, null, view);
        assertNotNull(model.getPosts());
        assertEquals(1, model.getPosts().size());
    }

    @Test
    public void testGoToDetails() {
        TestContextBinder contextBinder = new TestContextBinder(objectGraph);
//        navigator.show(contextBinder, PostDetailView.class, PostDetailPresenter.class, null);
//
//        PostDetailView postDetailView = contextBinder.getLastView();
        PostListModel model = presenter.init(contextBinder, null, null);
        presenter.subscribe(view);
        Post firstPost = model.getPosts().get(0);
        presenter.goToDetail(firstPost);

        PostDetailModel detailModel = contextBinder.getLastModel();
        assertNotNull(detailModel.getPost());
        assertEquals(firstPost.getId(), detailModel.getPost().getId());
    }

    @Module(injects = {PostListPresenterTest.class, PostDetailView.class, PostListView.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides PostListView providePostListView() {
            return mock(PostListView.class);
        }
        @Provides PostDetailView providePostDetailView() {
            return mock(PostDetailView.class);
        }
    }
}