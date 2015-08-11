package it.cosenonjaviste.core.mvp.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.category.CategoryListModel;
import it.cosenonjaviste.core.category.CategoryListPresenter;
import it.cosenonjaviste.core.category.CategoryListView;
import it.cosenonjaviste.core.model.Category;
import it.cosenonjaviste.core.model.WordPressService;
import it.cosenonjaviste.core.mvp.ViewMock;
import it.cosenonjaviste.core.post.PostListModel;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.categoryResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListPresenterTest {

    @Mock WordPressService wordPressService;

    @InjectMocks CategoryListPresenter presenter;

    private ViewMock<CategoryListView> view = new ViewMock<>(CategoryListView.class);

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Test
    public void testLoad() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel model = view.initAndResume(presenter);

        assertThat(model.getItems()).hasSize(3);
        Category category = model.get(2);
        assertThat(category.getId()).isEqualTo(2);
        assertThat(category.getTitle()).isEqualTo("cat 2");
        assertThat(category.getPostCount()).isEqualTo(12);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listCategories())
                .thenReturn(Observable.error(new RuntimeException()));

        CategoryListModel model = view.initAndResume(presenter);

        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        presenter.loadData();

        assertThat(model.getItems()).hasSize(3);
    }

    @Test
    public void testGoToPosts() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel categoryListModel = view.initAndResume(presenter);

        presenter.goToPosts(1);

        view.verify().openPostList(modelCaptor.capture());

        assertThat(modelCaptor.getValue().getCategory()).isEqualTo(categoryListModel.get(1));
    }
}