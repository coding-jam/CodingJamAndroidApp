package it.cosenonjaviste.mvp.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import it.cosenonjaviste.DaggerUtils;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.category.CategoryListPresenter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

import static it.cosenonjaviste.TestData.categoryResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListPresenterTest {

    @Inject WordPressService wordPressService;

    @Inject CategoryListPresenter presenter;

    @Mock CategoryListFragment view;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Before
    public void setup() {
        DaggerUtils.getComponent().inject(this);
    }

    @Test
    public void testLoad() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel model = new CategoryListModel();
        presenter.initAndSubscribe(model, view);
        assertThat(model.size()).isEqualTo(3);
        Category category = model.get(2);
        assertThat(category.getId()).isEqualTo(2);
        assertThat(category.getTitle()).isEqualTo("cat 2");
        assertThat(category.getPostCount()).isEqualTo(12);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listCategories())
                .thenReturn(Observable.error(new RuntimeException()));

        CategoryListModel model = new CategoryListModel();
        presenter.initAndSubscribe(model, view);

        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        presenter.loadData();

        assertThat(model.size()).isEqualTo(3);
    }

    @Test
    public void testGoToPosts() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        presenter.initAndSubscribe(new CategoryListModel(), view);
        presenter.goToPosts(1);

        verify(view).open(any(), modelCaptor.capture());

        assertThat(modelCaptor.getValue().getCategory()).isEqualTo(presenter.getModel().get(1));
    }
}