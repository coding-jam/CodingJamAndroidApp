package it.cosenonjaviste;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.base.MvpEspressoTestModule;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

public class AuthorListTest extends CnjFragmentTest<AuthorListModel> {

    @Inject MockWebServerWrapper server;

    public AuthorListTest() {
        super(AuthorListFragment.class, new AuthorListModel());
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

    @Module(injects = AuthorListTest.class, includes = MvpEspressoTestModule.class, overrides = true, library = true)
    public static class TestModule {
    }
}
