package it.cosenonjaviste.core.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.model.WordPressService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorPostListViewModelTest {

    @Mock WordPressService wordPressService;

    @InjectMocks PostListViewModel viewModel;

    @Test
    public void testParcelable() {
        PostListModel model = new PostListModel(TestData.createAuthor(145));
        ParcelableTester.check(model, PostListModel.CREATOR);

        model.done(Arrays.asList(TestData.createPost(1), TestData.createPost(2)));
        ParcelableTester.check(model, PostListModel.CREATOR);
    }

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listAuthorPosts(anyLong(), anyInt()))
                .thenReturn(TestData.postResponse(1));

        PostListModel model = viewModel.initAndResume(new PostListModel(TestData.createAuthor(145)));

        assertThat(model.getItems().size()).isEqualTo(1);
        verify(wordPressService).listAuthorPosts(eq(145L), eq(1));
    }
}