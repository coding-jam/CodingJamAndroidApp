package it.cosenonjaviste.mvp.author;

import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorListPresenterTest extends PresenterTest<AuthorListView, AuthorListPresenter> {

    @Inject MvpConfig<AuthorListView, AuthorListPresenter> config;

    @Inject MockWebServerWrapper server;

    @Override protected MvpConfig<AuthorListView, AuthorListPresenter> getConfig() {
        return config;
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        server.initDispatcher(JsonStubs.AUTHORS);
    }

    @Test
    public void testLoad() {
        presenter.loadAuthors();
        OptionalList<Author> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        presenter.loadAuthors();
        presenter.goToAuthorDetail(1);
        PostListModel model = getLastModel();
        assertThat(model.getAuthor()).isEqualTo(presenter.getModel().get(1));
    }

    @Module(injects = AuthorListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}