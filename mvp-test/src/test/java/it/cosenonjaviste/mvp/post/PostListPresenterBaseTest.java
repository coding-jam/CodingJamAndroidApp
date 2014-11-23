package it.cosenonjaviste.mvp.post;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;

import javax.inject.Inject;

import dagger.ObjectGraph;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public abstract class PostListPresenterBaseTest {

    @Mock PostListView view;

    @Inject PostListPresenter presenter;

    @Inject MockWebServerWrapper server;

    @After public void shutdownServer() throws IOException {
        server.shutdown();
    }

    @Before
    public void setup() {
        ObjectGraph.create(getTestModule()).inject(this);
        server.initDispatcher(JsonStubs.getPostList(1));
    }

    protected abstract Object getTestModule();
}
