package it.cosenonjaviste.mvp.author;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.TestContextBinder;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AuthorListPresenterTest {

    @Inject MockWebServer server;

    @Inject MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> config;

    @Inject AuthorListView view;

    private AuthorListPresenter presenter;

    private TestContextBinder contextBinder;

    @Before
    public void setup() {
        contextBinder = new TestContextBinder(this, new MvpTestModule(), new TestModule());

        MockWebServerUtils.initDispatcher(server, JsonStubs.AUTHORS);

        presenter = config.createAndInitPresenter(contextBinder, null);
        presenter.subscribe(view);
    }

    @Test
    public void testLoad() {
        presenter.loadAuthors();
        OptionalList<Author> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(2);
    }

    @Module(injects = AuthorListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides AuthorListView provideView() {
            return mock(AuthorListView.class);
        }
    }

    @After public void shutdownServer() throws IOException {
        server.shutdown();
    }
}