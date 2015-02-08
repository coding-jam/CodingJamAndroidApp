package it.cosenonjaviste.androidtest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.MainActivity;
import it.cosenonjaviste.androidtest.base.ActivityRule;
import it.cosenonjaviste.androidtest.base.DaggerRule;
import it.cosenonjaviste.androidtest.base.MvpEspressoTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Inject MockWebServerWrapper server;

    private final ActivityRule<MainActivity> fragmentRule = new ActivityRule<>(MainActivity.class);

    private final DaggerRule daggerRule = new DaggerRule(new TestModule(), objectGraph -> {
        objectGraph.inject(this);
        JsonStubs.initAllStubs();
    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(fragmentRule);

    @Test public void showMainActivity() {
        onData(is(instanceOf(Post.class))).inAdapterView(withId(android.R.id.list))
                .atPosition(3).perform(click());
    }

    @Module(injects = MainActivityTest.class, includes = MvpEspressoTestModule.class, overrides = true, library = true)
    public static class TestModule {
    }
}
