package it.cosenonjaviste.mvp.post;

import org.junit.After;

import java.io.IOException;

import javax.inject.Inject;

import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public abstract class PostListPresenterBaseTest extends PresenterTest<PostListView, PostListPresenter> {
    @Inject MockWebServerWrapper server;

    protected PostListPresenterBaseTest() {
        super(PostListView.class);
    }

    @Override protected void initAfterInject() {
        server.initDispatcher(JsonStubs.getPostList(1));
    }

    @After public void shutdownServer() throws IOException {
        server.shutdown();
    }
}
