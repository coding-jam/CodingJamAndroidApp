package it.cosenonjaviste.mvp.post;

import org.junit.Test;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorPostListPresenterTest extends PostListPresenterBaseTest {

    @Override protected void initAfterInject() {
        createComponent(TestComponent.class).inject(this);
        super.initAfterInject();
    }

    @Override protected PresenterArgs getArgs() {
        return PostListPresenter.open(contextBinder, new Author(145, "a", "b"));
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.AUTHOR_POSTS_URL);
        assertThat(lastUrl).contains("id=145");
    }

    @Singleton
    @Component(modules = MvpTestModule.class)
    public interface TestComponent {
        void inject(AuthorPostListPresenterTest test);
    }
}