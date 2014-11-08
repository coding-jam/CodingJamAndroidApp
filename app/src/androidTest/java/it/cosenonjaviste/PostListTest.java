package it.cosenonjaviste;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.base.BaseFragmentTest;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

public class PostListTest extends BaseFragmentTest {

    @Inject MockWebServer server;

    public PostListTest() {
        super(PostListFragment.class, true);
    }

    @Override protected Object[] getTestModules() {
        return new Object[]{new MvpTestModule(true), new TestModule()};
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        MockWebServerUtils.initDispatcher(server, JsonStubs.getPostList(WordPressService.POST_PAGE_SIZE));
    }

    public void testPostList() throws InterruptedException {
        showUi();
    }

    @Module(injects = {PostListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
