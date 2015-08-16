package it.cosenonjaviste.core.mvp.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.category.CategoryListModel;
import it.cosenonjaviste.core.category.CategoryListPresenter;
import it.cosenonjaviste.core.category.CategoryListView;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

import static it.cosenonjaviste.core.TestData.categoryResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListPresenterTest {

    @Mock WordPressService wordPressService;

    @InjectMocks CategoryListPresenter presenter;

    @Mock CategoryListView view;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Test
    public void testLoad() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel model = presenter.initAndResume(view);

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

        CategoryListModel model = presenter.initAndResume(view);

        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        presenter.reloadData();

        assertThat(model.getItems()).hasSize(3);
    }

    @Test
    public void testGoToPosts() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel categoryListModel = presenter.initAndResume(view);

        presenter.goToPosts(1);

        Mockito.verify(view).openPostList(modelCaptor.capture());

        assertThat(modelCaptor.getValue().getCategory()).isEqualTo(categoryListModel.get(1));
    }
}