package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.stubs.TwitterServiceStub;

@Module(library = true, overrides = true, includes = BaseModule.class)
public class MvpTestModule {
    @Provides @Singleton TwitterService provideTwitterService(TwitterServiceStub twitterServiceStub) {
        return twitterServiceStub;
    }
}
