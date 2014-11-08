package it.cosenonjaviste;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class AuthorListTest extends CnjFragmentTest {

    @Inject MockWebServerWrapper server;

    public AuthorListTest() {
        super(AuthorListFragment.class);
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        server.initDispatcher(JsonStubs.AUTHORS);
    }

    public void testAuthorList() {
        showUi();
    }

    @Module(injects = {AuthorListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
