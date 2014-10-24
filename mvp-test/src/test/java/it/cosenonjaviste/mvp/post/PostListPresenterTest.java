package it.cosenonjaviste.mvp.post;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.TestNavigator;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;
import it.cosenonjaviste.utils.PresenterTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PostListPresenterTest {

    @Inject MockWebServer server;

    @Inject PostListPresenter presenter;

    @Inject TestNavigator navigator;

    @Before
    public void setup() {
        ObjectGraph.create(new MvpTestModule(), new TestModule()).inject(this);

        MockWebServerUtils.initDispatcher(server, JsonStubs.POSTS);
    }

    @Test
    public void testLoad() {
        PostListModel model = PresenterTestUtils.init(presenter, null);
        assertNotNull(model.getPosts());
        assertEquals(1, model.getPosts().size());
    }

    @Test
    public void testGoToDetails() {
        PostListModel model = PresenterTestUtils.init(presenter, null);
        Post firstPost = model.getPosts().get(0);
        presenter.goToDetail(firstPost);
        PostDetailModel detailModel = navigator.getLastModel();
        assertNotNull(detailModel.getPost());
        assertEquals(firstPost.getId(), detailModel.getPost().getId());
    }

    @Module(injects = PostListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}