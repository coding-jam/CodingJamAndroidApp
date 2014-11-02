package it.cosenonjaviste.mvp.author;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorListPresenterTest extends PresenterTest<AuthorListView, AuthorListPresenter> {

    @Inject MvpConfig<AuthorListView, AuthorListPresenter> config;

    @Inject MockWebServer server;

    @Override protected MvpConfig<AuthorListView, AuthorListPresenter> getConfig() {
        return config;
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        MockWebServerUtils.initDispatcher(server, JsonStubs.AUTHORS);
    }

    @Test
    public void testLoad() {
        presenter.loadAuthors();
        OptionalList<Author> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(2);
    }

    @Module(injects = AuthorListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}