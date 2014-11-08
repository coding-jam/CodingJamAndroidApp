package it.cosenonjaviste;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import javax.inject.Inject;

import dagger.Module;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.base.CnjFragmentTest;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

public class AuthorListTest extends CnjFragmentTest {

    @Inject MockWebServer server;

    public AuthorListTest() {
        super(AuthorListFragment.class);
    }

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Override protected void initAfterInject() {
        super.initAfterInject();
        MockWebServerUtils.initDispatcher(server, JsonStubs.AUTHORS);
    }

    public void testAuthorList() {
        showUi();
    }

    @Module(injects = {AuthorListTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}
