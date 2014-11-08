package it.cosenonjaviste;

import android.content.Intent;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.post.PostDetailPresenter;
import it.cosenonjaviste.mvp.post.PostDetailUrlManager;
import it.cosenonjaviste.post.PostDetailFragment;
import it.cosenonjaviste.stubs.MockWebServerUtils;

public class PostDetailTest extends CnjFragmentTest {

    @Inject MockWebServer server;

    public PostDetailTest() {
        super(PostDetailFragment.class);
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        MockWebServerUtils.initDispatcher(server, "<html><body>CoseNonJaviste</body></html>");
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected Intent createActivityIntent() {
        Post post = new Post(123, "Title", "url");
        return createIntent(super.createActivityIntent(), PostDetailPresenter::populateArgs, post);
    }

    public void testDetailFragment() {
        showUi();
    }

    @Module(injects = {PostDetailTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
        @Provides @Singleton PostDetailUrlManager providePostDetailUrlManager(MockWebServer server) {
            return new PostDetailUrlManager() {
                @Override public String getUrl(Post post) {
                    return MockWebServerUtils.getUrl(server, true);
                }
            };
        }
    }
}
