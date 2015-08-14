package it.cosenonjaviste.core.mvp.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.core.post.PostListView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorPostListPresenterTest {

    @Mock PostListView view;

    @Mock WordPressService wordPressService;

    @InjectMocks PostListPresenter presenter;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listAuthorPosts(anyLong(), anyInt()))
                .thenReturn(TestData.postResponse(1));

        PostListModel model = presenter.initAndResume(new PostListModel(TestData.createAuthor(145)), view);

        assertThat(model.getItems().size()).isEqualTo(1);
        verify(wordPressService).listAuthorPosts(eq(145L), eq(1));
    }
}