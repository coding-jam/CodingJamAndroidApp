package it.cosenonjaviste.mvp;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.stubs.MockWebServerUtils;
import it.cosenonjaviste.stubs.PostJson;
import it.cosenonjaviste.utils.PresenterTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PostListPresenterTest {

    @Inject MockWebServer server;

    @Inject PostListPresenter presenter;

    private PostListModel model;

    @Before
    public void setup() {
        ObjectGraph.create(new MvpTestModule(), new TestModule()).inject(this);

        MockWebServerUtils.initDispatcher(server, PostJson.JSON);

        model = PresenterTestUtils.init(presenter);
    }

    @Test
    public void testLoad() {
        presenter.listPosts(0);
        assertNotNull(model.getPosts());
        assertEquals(1, model.getPosts().size());
    }

    @Module(injects = PostListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}