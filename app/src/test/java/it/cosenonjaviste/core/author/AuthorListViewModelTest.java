package it.cosenonjaviste.core.author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

import static it.cosenonjaviste.TestData.authorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorListViewModelTest {

    @InjectMocks AuthorListViewModel viewModel;

    @Mock WordPressService wordPressService;

    @Captor ArgumentCaptor<PostListArgument> argumentCaptor;

    @Mock Navigator navigator;

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
                .thenReturn(authorResponse(10));

        AuthorListModel model = viewModel.initAndResume();

        assertThat(model.size()).isEqualTo(10);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(
                        Observable.error(new RuntimeException()),
                        authorResponse(2)
                );

        AuthorListModel model = viewModel.initAndResume();

        assertThat(model.size()).isEqualTo(0);

        viewModel.reloadData();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AuthorListModel authorListModel = viewModel.initAndResume();

        viewModel.goToAuthorDetail(1);

        verify(navigator).openPostList(argumentCaptor.capture());

        PostListArgument argument = argumentCaptor.getValue();
        assertThat(argument.getAuthor()).isEqualTo(authorListModel.get(1));
    }
}