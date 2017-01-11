package it.cosenonjaviste.core.post;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.post.PostListFragment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class CategoryPostListViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @Mock WordPressService wordPressService;

    @InjectFromComponent(PostListFragment.class) PostListViewModel viewModel;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listCategoryPosts(eq(1L), eq(1)))
                .thenReturn(TestData.postResponse(1));

        PostListModel model = viewModel.initAndResume(PostListArgument.create(Category.create(1, "cat", 10)));

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listCategoryPosts(eq(1L), eq(1)))
                .thenReturn(TestData.postResponse(10));
        when(wordPressService.listCategoryPosts(eq(1L), eq(2)))
                .thenReturn(TestData.postResponse(5));

        PostListModel model = viewModel.initAndResume(PostListArgument.create(Category.create(1, "cat", 10)));
        viewModel.loadNextPage();

        assertThat(model.getItems().size()).isEqualTo(15);
    }
}