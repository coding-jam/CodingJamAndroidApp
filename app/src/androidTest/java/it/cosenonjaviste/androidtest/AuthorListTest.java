package it.cosenonjaviste.androidtest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.androidtest.base.DaggerRule;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.MvpEspressoTestModule;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

@RunWith(AndroidJUnit4.class)
public class AuthorListTest {

    @Inject MockWebServerWrapper server;

    private final FragmentRule fragmentRule = FragmentRule.create(AuthorListFragment.class, new AuthorListModel());

    private final DaggerRule daggerRule = new DaggerRule(new TestModule(), objectGraph -> {
        objectGraph.inject(this);
        server.initDispatcher(JsonStubs.AUTHORS);
    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(fragmentRule);

    @Test
    public void testAuthorList() {
//        showUi();
    }

    @Module(injects = AuthorListTest.class, includes = MvpEspressoTestModule.class, overrides = true, library = true)
    public static class TestModule {
    }
}
