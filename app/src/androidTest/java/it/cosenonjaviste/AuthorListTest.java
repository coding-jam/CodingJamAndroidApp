package it.cosenonjaviste;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
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
        return null;
    }

    @Override protected void initAfterInject(Object component) {
        ((TestComponent) component).inject(this);
//        createComponent(TestComponent.class).inject(this);
        System.out.println("initAfterInject " + server);
        server.initDispatcher(JsonStubs.AUTHORS);
    }

    @Override protected Class<? extends Object> getComponentClass() {
        return TestComponent.class;
    }

    public void testAuthorList() {
        showUi();
    }

    @Singleton
    @Component(modules = MvpTestModule.class, dependencies = AppComponent.class)
    public interface TestComponent extends AppComponent {
        void inject(AuthorListTest authorListTest);
    }
}
