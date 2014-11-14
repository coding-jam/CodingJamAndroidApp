package it.cosenonjaviste.mvp.author;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorListPresenterTest extends PresenterTest<AuthorListView, AuthorListPresenter> {

    @Inject MockWebServerWrapper server;

    @Override protected Class<AuthorListView> getConfig() {
        return AuthorListView.class;
    }

    @Override protected void initAfterInject() {
        createComponent(TestComponent.class).inject(this);
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

    @Singleton
    @Component(modules = MvpTestModule.class)
    public interface TestComponent {
        void inject(AuthorListPresenterTest authorListPresenterTest);
    }
}