package it.cosenonjaviste.core.post;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.WordPressService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorPostListViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @Mock WordPressService wordPressService;

    @InjectFromComponent PostListViewModel viewModel;

    @Test
    public void testParcelable() {
        PostListModel model = new PostListModel();
        ParcelableTester.check(model, PostListModel.CREATOR);

        model.done(Arrays.asList(TestData.createPost(1), TestData.createPost(2)));
        ParcelableTester.check(model, PostListModel.CREATOR);
    }

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listAuthorPosts(anyLong(), anyInt()))
                .thenReturn(TestData.postResponse(1));

        PostListModel model = viewModel.initAndResume(PostListArgument.create(TestData.createAuthor(145)));

        assertThat(model.getItems().size()).isEqualTo(1);
        verify(wordPressService).listAuthorPosts(eq(145L), eq(1));
    }
}