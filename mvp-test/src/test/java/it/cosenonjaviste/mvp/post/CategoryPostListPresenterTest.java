package it.cosenonjaviste.mvp.post;

import org.junit.Test;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.stubs.JsonStubs;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryPostListPresenterTest extends PostListPresenterBaseTest {

    @Override protected PresenterArgs getArgs() {
        return PostListPresenter.open(contextBinder, new Category(1, "cat", 10));
    }

    @Override protected void initAfterInject() {
        createComponent(TestComponent.class).inject(this);
        super.initAfterInject();
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(1);
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
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(25);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.CATEGORY_POSTS_URL);
        assertThat(lastUrl).contains("id=1");
    }

    @Singleton
    @Component(modules = MvpTestModule.class)
    public interface TestComponent {
        void inject(CategoryPostListPresenterTest test);
    }
}