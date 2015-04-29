package it.cosenonjaviste.mvp.author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.author.AuthorListPresenter;
import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.TestSchedulerManager;
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

    @Spy RxHolder rxHolder = new RxHolder(new TestSchedulerManager(), new LifeCycle());

    @InjectMocks AuthorListPresenter presenter;

    @Mock WordPressService wordPressService;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Test
    public void testLoad() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = new AuthorListModel();
        presenter.init(model, view);
        presenter.resume();
        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(Observable.error(new RuntimeException()));

        AuthorListModel model = new AuthorListModel();
        presenter.init(model, view);
        presenter.resume();

        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        presenter.loadAuthors();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel authorListModel = new AuthorListModel();
        presenter.init(authorListModel, view);
        presenter.resume();
        presenter.goToAuthorDetail(1);

        verify(view).open(any(), modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(authorListModel.get(1));
    }
}