package it.cosenonjaviste.mvp.category;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryListPresenterTest extends PresenterTest<CategoryListView, CategoryListPresenter> {

    @Inject MockWebServer server;

    @Inject MvpConfig<CategoryListView, CategoryListPresenter> config;

    @Override public MvpConfig<CategoryListView, CategoryListPresenter> getConfig() {
        return config;
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        MockWebServerUtils.initDispatcher(server, JsonStubs.CATEGORIES);
    }

    @Test
    public void testLoad() {
        presenter.loadData();
        OptionalList<Category> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(3);
        Category category = model.get(0);
        assertThat(category.getId()).isEqualTo(602);
        assertThat(category.getTitle()).isEqualTo("Agile");
        assertThat(category.getPostCount()).isEqualTo(4);
    }

    @Test
    public void testGoToPosts() {
        presenter.loadData();
        presenter.goToPosts(1);
        PostListModel model = getLastModel();
        assertThat(model.getCategory()).isEqualTo(presenter.getModel().get(1));
    }

    @Module(injects = {CategoryListPresenterTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}