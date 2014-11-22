package it.cosenonjaviste.mvp.post;

import org.junit.Test;

import dagger.Module;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorPostListPresenterTest extends PostListPresenterBaseTest {

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected PresenterArgs getArgs() {
        return PostListModel.open(presenterArgsFactory, new Author(145, "a", "b"));
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        PostListModel model = presenter.getModel();
        assertThat(model.getItems().size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.AUTHOR_POSTS_URL);
        assertThat(lastUrl).contains("id=145");
    }

    @Module(injects = {AuthorPostListPresenterTest.class}, addsTo = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}