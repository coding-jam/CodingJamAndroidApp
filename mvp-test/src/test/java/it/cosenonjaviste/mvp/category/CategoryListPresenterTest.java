package it.cosenonjaviste.mvp.category;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.PresenterTest;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryListPresenterTest extends PresenterTest<CategoryListView, CategoryListPresenter> {

    @Inject MockWebServerWrapper server;

    @Override public Class<CategoryListView> getConfig() {
        return CategoryListView.class;
    }

    @Override protected void initAfterInject() {
        createComponent(TestComponent.class).inject(this);
        server.initDispatcher(JsonStubs.CATEGORIES);
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

    @Singleton
    @Component(modules = MvpTestModule.class)
    public interface TestComponent {
        void inject(CategoryListPresenterTest test);
    }
}