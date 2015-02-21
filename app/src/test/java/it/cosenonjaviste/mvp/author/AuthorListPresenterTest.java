package it.cosenonjaviste.mvp.author;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import it.cosenonjaviste.DaggerUtils;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.author.AuthorListPresenter;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

import static it.cosenonjaviste.TestData.authorResponse;
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
        DaggerUtils.getComponent().inject(this);
    }

    @Test
    public void testLoad() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = new AuthorListModel();
        presenter.initAndSubscribe(model, view);
        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(Observable.error(new RuntimeException()));

        AuthorListModel model = new AuthorListModel();
        presenter.initAndSubscribe(model, view);

        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        presenter.loadAuthors();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        presenter.initAndSubscribe(new AuthorListModel(), view);
        presenter.goToAuthorDetail(1);

        verify(view).open(any(), modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(presenter.getModel().get(1));
    }
}