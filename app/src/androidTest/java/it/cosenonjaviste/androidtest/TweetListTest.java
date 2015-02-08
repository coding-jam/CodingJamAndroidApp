package it.cosenonjaviste.androidtest;

import dagger.Module;
import it.cosenonjaviste.androidtest.base.CnjFragmentTest;
import it.cosenonjaviste.androidtest.base.MvpEspressoTestModule;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListModel;

public class TweetListTest extends CnjFragmentTest<TweetListModel> {

    public TweetListTest() {
        super(TweetListFragment.class, new TweetListModel());
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    public void testPostList() throws InterruptedException {
        showUi();
    }

    @Module(injects = TweetListTest.class, includes = MvpEspressoTestModule.class)
    public static class TestModule {
    }
}
