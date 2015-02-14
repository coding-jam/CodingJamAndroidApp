package it.cosenonjaviste.mvp;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.BaseModule;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.stubs.TestSchedulerManager;

@Module(library = true, overrides = true, includes = BaseModule.class)
public class MvpJUnitTestModule {

    @Provides @Singleton WordPressService provideWordPressService() {
        return Mockito.mock(WordPressService.class);
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager(TestSchedulerManager testSchedulerManager) {
        return testSchedulerManager;
    }

    @Provides @Singleton TwitterService provideTwitterService() {
        return Mockito.mock(TwitterService.class);
    }
}
