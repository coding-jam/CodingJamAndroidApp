package it.cosenonjaviste.androidtest.dagger;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.androidtest.base.EspressoSchedulerManager;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.core.model.MailJetService;
import it.cosenonjaviste.core.model.TwitterService;
import it.cosenonjaviste.core.model.WordPressService;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;

@Module
public class MvpEspressoTestModule {

    @Provides @Singleton WordPressService provideWordPressService(MockWebServerWrapper mockWebServer) {
        return Mockito.mock(WordPressService.class);
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new EspressoSchedulerManager();
    }

    @Provides @Singleton TwitterService provideTwitterService() {
        return Mockito.mock(TwitterService.class);
    }

    @Provides @Singleton MailJetService provideMailJetService() {
        return Mockito.mock(MailJetService.class);
    }
}
