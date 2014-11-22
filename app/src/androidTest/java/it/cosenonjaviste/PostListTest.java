package it.cosenonjaviste;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.base.MvpEspressoTestModule;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class PostListTest extends CnjFragmentTest<PostListModel> {

    @Inject MockWebServerWrapper server;

    public PostListTest() {
        super(PostListFragment.class, new PostListModel());
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        server.initDispatcher(JsonStubs.getPostList(WordPressService.POST_PAGE_SIZE));
    }

    public void testPostList() throws InterruptedException {
        showUi();
    }

    @Module(injects = {PostListTest.class}, includes = MvpEspressoTestModule.class)
    public static class TestModule {
    }
}
