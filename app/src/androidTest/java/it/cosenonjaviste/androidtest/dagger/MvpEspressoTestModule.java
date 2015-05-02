package it.cosenonjaviste.androidtest.dagger;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.androidtest.base.EspressoSchedulerManager;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PageUrlManager;

@Module
public class MvpEspressoTestModule {

    @Provides @Singleton WordPressService provideWordPressService(MockWebServerWrapper mockWebServer) {
        return Mockito.mock(WordPressService.class);
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new EspressoSchedulerManager();
    }

    @Provides @Singleton PageUrlManager providePostDetailUrlManager(MockWebServerWrapper server) {
        return new PageUrlManager() {
            @Override public String getUrl(String url) {
                return server.getUrl(true) + url;
            }
        };
    }

    @Provides @Singleton TwitterService provideTwitterService() {
        return Mockito.mock(TwitterService.class);
    }
}
