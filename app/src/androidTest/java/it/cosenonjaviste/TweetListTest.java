package it.cosenonjaviste;

import dagger.Module;
import it.cosenonjaviste.base.BaseFragmentTest;
import it.cosenonjaviste.twitter.TweetListFragment;

public class TweetListTest extends BaseFragmentTest {

    public TweetListTest() {
        super(TweetListFragment.class, true);
    }

    @Override protected Object[] getTestModules() {
        return new Object[]{new MvpTestModule(true), new TestModule()};
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
    }

    public void testPostList() throws InterruptedException {
        Thread.sleep(20000);
    }

    @Module(injects = {TweetListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
