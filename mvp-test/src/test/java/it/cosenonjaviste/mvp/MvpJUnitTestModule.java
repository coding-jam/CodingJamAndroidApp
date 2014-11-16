package it.cosenonjaviste.mvp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.stubs.TestSchedulerManager;

@Module(library = true, overrides = true, includes = MvpTestModule.class)
public class MvpJUnitTestModule {

    @Provides @Singleton SchedulerManager provideSchedulerManager(TestSchedulerManager testSchedulerManager) {
        return testSchedulerManager;
    }

}
