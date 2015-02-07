package it.cosenonjaviste.mvp.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListPresenter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListPresenterTest {

    @Inject MockWebServerWrapper server;

    @Inject CategoryListPresenter presenter;

    @Mock CategoryListFragment view;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
        server.initDispatcher(JsonStubs.CATEGORIES);
    }

    @Test
    public void testLoad() {
        presenter.initAndSubscribe(new OptionalList<>(), view);
        OptionalList<Category> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(3);
        Category category = model.get(0);
        assertThat(category.getId()).isEqualTo(602);
        assertThat(category.getTitle()).isEqualTo("Agile");
        assertThat(category.getPostCount()).isEqualTo(4);
    }

    @Test
    public void testGoToPosts() {
        presenter.initAndSubscribe(new OptionalList<>(), view);
        presenter.goToPosts(1);

        verify(view).open(any(), modelCaptor.capture());

        assertThat(modelCaptor.getValue().getCategory()).isEqualTo(presenter.getModel().get(1));
    }

    @Module(injects = {CategoryListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}