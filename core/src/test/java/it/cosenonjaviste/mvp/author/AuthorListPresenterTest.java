package it.cosenonjaviste.mvp.author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.author.AuthorListPresenter;
import it.cosenonjaviste.author.AuthorListView;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.TestLifeCycle;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

import static it.cosenonjaviste.TestData.authorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorListPresenterTest {

    @Mock AuthorListView view;

    @InjectMocks AuthorListPresenter presenter;

    @Mock WordPressService wordPressService;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    private TestLifeCycle testLifeCycle = new TestLifeCycle();

    @Test
    public void testLoad() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = new AuthorListModel();
        testLifeCycle.initAndResume(model, presenter, view);

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(Observable.error(new RuntimeException()));
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = new AuthorListModel();
        testLifeCycle.initAndResume(model, presenter, view);

        presenter.loadAuthors();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel authorListModel = new AuthorListModel();
        testLifeCycle.initAndResume(authorListModel, presenter, view);

        presenter.goToAuthorDetail(1);

        verify(view).openPostList(modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(authorListModel.get(1));
    }
}