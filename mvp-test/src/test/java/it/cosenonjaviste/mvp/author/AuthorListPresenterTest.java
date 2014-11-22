package it.cosenonjaviste.mvp.author;

import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorListPresenterTest extends PresenterTest<AuthorListView, AuthorListPresenter> {

    @Inject MockWebServerWrapper server;

    public AuthorListPresenterTest() {
        super(AuthorListView.class);
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
        PostListModel model = new PostListModel(getLastArgs());
        assertThat(model.getAuthor()).isEqualTo(presenter.getModel().get(1));
    }

    @Module(injects = AuthorListPresenterTest.class, addsTo = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}