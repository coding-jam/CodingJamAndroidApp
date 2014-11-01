package it.cosenonjaviste.mvp.category;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.TestContextBinder;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CategoryListPresenterTest {

    @Inject MockWebServer server;

    @Inject MvpConfig<OptionalList<Category>, CategoryListView, CategoryListPresenter> config;

    @Inject CategoryListView view;

    private TestContextBinder contextBinder;

    private CategoryListPresenter presenter;

    @Before
    public void setup() {
        contextBinder = new TestContextBinder(this, new MvpTestModule(), new TestModule());

        MockWebServerUtils.initDispatcher(server, JsonStubs.CATEGORIES);

        presenter = config.createAndInitPresenter(contextBinder, null);
        presenter.subscribe(view);
    }

    @Test
    public void testLoad() {
        presenter.loadData(0);
        OptionalList<Category> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(3);
        Category category = model.get(0);
        assertThat(category.getId()).isEqualTo(602);
        assertThat(category.getTitle()).isEqualTo("Agile");
        assertThat(category.getPostCount()).isEqualTo(4);
    }

    @Test
    public void testGoToPosts() {
        presenter.loadData(0);
        presenter.goToPosts(1);
        PostListModel model = contextBinder.getLastModel();
        assertThat(model.getCategory()).isEqualTo(presenter.getModel().get(1));
    }

    @Module(injects = {CategoryListPresenterTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides CategoryListView provideCategoryListView() {
            return mock(CategoryListView.class);
        }

//        @Provides PostListView providePostListView() {
//            return mock(PostListView.class);
//        }
    }
}