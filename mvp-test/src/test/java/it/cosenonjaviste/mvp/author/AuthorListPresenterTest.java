package it.cosenonjaviste.mvp.author;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;
import it.cosenonjaviste.utils.TestContextBinder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AuthorListPresenterTest {

    @Inject MockWebServer server;

    @Inject AuthorListMvpConfig config;

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
        AuthorListModel model = presenter.getModel();
        assertThat(model.getAuthors().size()).isEqualTo(2);
    }

    @Module(injects = AuthorListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides AuthorListView provideView() {
            return mock(AuthorListView.class);
        }
    }
}