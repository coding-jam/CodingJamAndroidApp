package it.cosenonjaviste.core.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.categoryResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListViewModelTest {

    @Mock WordPressService wordPressService;

    @InjectMocks CategoryListViewModel viewModel;

    @Mock CategoryListView view;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Test
    public void testParcelable() {
        CategoryListModel model = new CategoryListModel();
        ParcelableTester.check(model, CategoryListModel.CREATOR);

        model.done(Arrays.asList(new Category(123, "abc", 3), new Category(123456, "abcdef", 6)));
        ParcelableTester.check(model, CategoryListModel.CREATOR);
    }

    @Test
    public void testLoad() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel model = viewModel.initAndResume(view);

        assertThat(model.getItems()).hasSize(3);
        Category category = model.get(2);
        assertThat(category.getId()).isEqualTo(2);
        assertThat(category.getTitle()).isEqualTo("cat 2");
        assertThat(category.getPostCount()).isEqualTo(12);
    }

    @Test
    public void testLoadAndPullToRefresh() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3), categoryResponse(2));

        CategoryListModel model = viewModel.initAndResume(view);

        assertThat(model.getItems()).hasSize(3);

        viewModel.loadDataPullToRefresh();

        assertThat(model.getItems()).hasSize(2);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listCategories())
                .thenReturn(Observable.error(new RuntimeException()));

        CategoryListModel model = viewModel.initAndResume(view);

        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        viewModel.reloadData();

        assertThat(model.getItems()).hasSize(3);
    }

    @Test
    public void testGoToPosts() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel categoryListModel = viewModel.initAndResume(view);

        viewModel.goToPosts(1);

        Mockito.verify(view).openPostList(modelCaptor.capture());

        assertThat(modelCaptor.getValue().getCategory()).isEqualTo(categoryListModel.get(1));
    }
}