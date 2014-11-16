package it.cosenonjaviste.base;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.mvp.base.SchedulerManager;

@Module(library = true, overrides = true)
public class MvpEspressoTestModule {

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }
}
