package it.cosenonjaviste.mvp.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.category.CategoryListPresenter;
import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.TestLifeCycle;
import it.cosenonjaviste.mvp.TestSchedulerManager;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

import static it.cosenonjaviste.TestData.categoryResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListPresenterTest {

    @Mock WordPressService wordPressService;

    private TestLifeCycle testLifeCycle = new TestLifeCycle();

    @InjectMocks CategoryListPresenter presenter;

    @Mock CategoryListFragment view;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Test
    public void testLoad() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel model = new CategoryListModel();
        testLifeCycle.initAndResume(model, presenter, view);

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
        testLifeCycle.initAndResume(model, presenter, view);

        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        presenter.loadData();

        assertThat(model.size()).isEqualTo(3);
    }

    @Test
    public void testGoToPosts() {
        when(wordPressService.listCategories())
                .thenReturn(categoryResponse(3));

        CategoryListModel categoryListModel = new CategoryListModel();
        testLifeCycle.initAndResume(categoryListModel, presenter, view);

        presenter.goToPosts(1);

        verify(view).open(any(), modelCaptor.capture());

        assertThat(modelCaptor.getValue().getCategory()).isEqualTo(categoryListModel.get(1));
    }
}