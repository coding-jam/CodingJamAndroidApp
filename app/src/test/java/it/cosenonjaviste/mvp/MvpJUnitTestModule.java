package it.cosenonjaviste.mvp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.model.WordPressServiceFactory;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.stubs.MockWebServerWrapper;
import it.cosenonjaviste.stubs.TestSchedulerManager;

@Module(library = true, overrides = true, includes = MvpTestModule.class)
public class MvpJUnitTestModule {

    @Provides @Singleton WordPressService provideWordPressService(MockWebServerWrapper mockWebServer) {
        return WordPressServiceFactory.create(mockWebServer.getUrl(false), true);
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager(TestSchedulerManager testSchedulerManager) {
        return testSchedulerManager;
    }
}
