package it.cosenonjaviste.mvp.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.post.PostListPresenter;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AuthorPostListPresenterTest {

    @Mock PostListFragment view;

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

    @Test
    public void testLoad() throws InterruptedException {
        PostListModel model = new PostListModel(new Author(145, "a", "b"));

        presenter.initAndSubscribe(model, view);

        assertThat(model.getItems().size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        assertThat(lastUrl).startsWith(WordPressService.AUTHOR_POSTS_URL);
        assertThat(lastUrl).contains("id=145");
    }

    @Module(injects = {AuthorPostListPresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}