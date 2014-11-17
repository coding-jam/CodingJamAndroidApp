package it.cosenonjaviste.mvp.post;

import org.junit.Test;

import dagger.Module;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.stubs.JsonStubs;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryPostListPresenterTest extends PostListPresenterBaseTest {

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected PresenterArgs getArgs() {
        return PostListPresenter.open(presenterArgsFactory, new Category(1, "cat", 10));
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        PostListModel model = presenter.getModel();
        assertThat(model.getItems().size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.CATEGORY_POSTS_URL);
        assertThat(lastUrl).contains("id=1");
    }

    @Test
    public void testLoadMore() {
        server.initDispatcher(JsonStubs.getPostList(20));
        presenter.reloadData();
        server.initDispatcher(JsonStubs.getPostList(5));
        presenter.loadNextPage();
        PostListModel model = presenter.getModel();
        assertThat(model.getItems().size()).isEqualTo(25);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.CATEGORY_POSTS_URL);
        assertThat(lastUrl).contains("id=1");
    }

    @Module(injects = {CategoryPostListPresenterTest.class}, addsTo = MvpJUnitTestModule.class)
    public static class TestModule {
    }

}