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
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.author.AuthorListPresenter;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.TestData;
import it.cosenonjaviste.post.PostListModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorListPresenterTest {

    @Mock AuthorListFragment view;

    @Inject AuthorListPresenter presenter;

    @Inject WordPressService wordPressService;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
        when(wordPressService.listAuthors()).thenReturn(TestData.authorResponse(2));
    }

    @Test
    public void testLoad() {
        presenter.testFromJUnit();
        presenter.initAndSubscribe(new AuthorListModel(), view);
        AuthorListModel model = presenter.getModel();
        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        presenter.initAndSubscribe(new AuthorListModel(), view);
        presenter.goToAuthorDetail(1);

        verify(view).open(any(), modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(presenter.getModel().get(1));
    }

    @Module(injects = AuthorListPresenterTest.class, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}