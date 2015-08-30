package it.cosenonjaviste.core.author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.authorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorListViewModelTest {

    @InjectMocks AuthorListViewModel viewModel;

    @Mock WordPressService wordPressService;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Mock AuthorListView view;

    @Test
    public void testParcelable() {
        AuthorListModel model = new AuthorListModel();
        ParcelableTester.check(model, AuthorListModel.CREATOR);

        model.done(Arrays.asList(TestData.createAuthor(1), TestData.createAuthor(2)));
        ParcelableTester.check(model, AuthorListModel.CREATOR);
    }

    @Test
    public void testLoad() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel model = viewModel.initAndResume(view);

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(
                        Observable.error(new RuntimeException()),
                        authorResponse(2)
                );

        AuthorListModel model = viewModel.initAndResume(view);

        assertThat(model.size()).isEqualTo(0);

        viewModel.reloadData();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel authorListModel = viewModel.initAndResume(view);

        viewModel.goToAuthorDetail(1);

        verify(view).openPostList(modelCaptor.capture());

        PostListModel model = modelCaptor.getValue();
        assertThat(model.getAuthor()).isEqualTo(authorListModel.get(1));
    }
}