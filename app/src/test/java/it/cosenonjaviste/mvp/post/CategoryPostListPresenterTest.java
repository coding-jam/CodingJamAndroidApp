package it.cosenonjaviste.mvp.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.TestLifeCycle;
import it.cosenonjaviste.mvp.TestSchedulerManager;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.post.PostListPresenter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryPostListPresenterTest {

    @Mock PostListFragment view;

    private TestLifeCycle testLifeCycle = new TestLifeCycle();

    @Mock WordPressService wordPressService;

    @InjectMocks PostListPresenter presenter;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listCategoryPosts(eq(1L), eq(1)))
                .thenReturn(TestData.postResponse(1));
        PostListModel model = new PostListModel(new Category(1, "cat", 10));
        testLifeCycle.initAndResume(model, presenter, view);
        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listCategoryPosts(eq(1L), eq(1)))
                .thenReturn(TestData.postResponse(10));
        when(wordPressService.listCategoryPosts(eq(1L), eq(2)))
                .thenReturn(TestData.postResponse(5));

        PostListModel model = new PostListModel(new Category(1, "cat", 10));
        testLifeCycle.initAndResume(model, presenter, view);
        presenter.loadNextPage();
        assertThat(model.getItems().size()).isEqualTo(15);
    }
}