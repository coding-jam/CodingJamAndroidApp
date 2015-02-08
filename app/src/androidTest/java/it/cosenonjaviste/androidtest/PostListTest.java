package it.cosenonjaviste.androidtest;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.androidtest.base.CnjFragmentTest;
import it.cosenonjaviste.androidtest.base.MvpEspressoTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

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
        JsonStubs.initAllStubs();
    }

    public void testPostList() throws InterruptedException {
        showUi();
    }

    public void testGoToPostDetail() {
        onData(is(instanceOf(Post.class))).inAdapterView(withId(android.R.id.list))
                .atPosition(3).perform(click());
    }

    @Module(injects = {PostListTest.class}, includes = MvpEspressoTestModule.class)
    public static class TestModule {
    }
}
