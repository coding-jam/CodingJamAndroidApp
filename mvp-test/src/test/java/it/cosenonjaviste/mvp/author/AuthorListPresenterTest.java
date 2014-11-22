package it.cosenonjaviste.mvp.author;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuthorListPresenterTest {

    @Mock AuthorListView view;

    @Inject AuthorListPresenter presenter;

    @Inject MockWebServerWrapper server;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

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

        verify(view).openM(any(), modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(presenter.getModel().get(1));
    }

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
        server.initDispatcher(JsonStubs.AUTHORS);

        presenter.initAndSubscribe(new OptionalList<>(), view);
    }

    @Module(injects = AuthorListPresenterTest.class, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}