package it.cosenonjaviste.mvp.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CategoryPostListPresenterTest {

    @Mock PostListView view;

    @Inject PostListPresenter presenter;

    @Inject MockWebServerWrapper server;

    @Before
    public void setup() {
        ObjectGraph.create(getTestModule()).inject(this);
        server.initDispatcher(JsonStubs.getPostList(1));
    }

    protected Object getTestModule() {
        return new TestModule();
    }

    protected PostListModel createModel() {
        return new PostListModel(new Category(1, "cat", 10));
    }

    @Test
    public void testLoad() throws InterruptedException {
        PostListModel model = createModel();
        presenter.initAndSubscribe(model, view);
        assertThat(model.getItems().size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.CATEGORY_POSTS_URL);
        assertThat(lastUrl).contains("id=1");
    }

    @Test
    public void testLoadMore() {
        server.initDispatcher(JsonStubs.getPostList(20));
        PostListModel model = createModel();
        presenter.initAndSubscribe(model, view);
        server.initDispatcher(JsonStubs.getPostList(5));
        presenter.loadNextPage();
        assertThat(model.getItems().size()).isEqualTo(25);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.CATEGORY_POSTS_URL);
        assertThat(lastUrl).contains("id=1");
    }

    @Module(injects = {CategoryPostListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }

}