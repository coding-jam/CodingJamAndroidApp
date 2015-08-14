package it.cosenonjaviste.core.mvp.author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.author.AuthorListModel;
import it.cosenonjaviste.core.author.AuthorListPresenter;
import it.cosenonjaviste.core.author.AuthorListView;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.core.post.PostListModel;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.authorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorListPresenterTest {

    @InjectMocks AuthorListPresenter presenter;

    @Mock WordPressService wordPressService;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Mock AuthorListView view;

    @Test
    public void testLoad() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = presenter.initAndResume(view);

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(Observable.error(new RuntimeException()));
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = presenter.initAndResume(view);

        presenter.reloadData();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel authorListModel = presenter.initAndResume(view);

        presenter.goToAuthorDetail(1);

        verify(view).openPostList(modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(authorListModel.get(1));
    }
}