package it.cosenonjaviste.androidtest.base;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.model.WordPressServiceFactory;
import it.cosenonjaviste.page.PageUrlManager;
import it.cosenonjaviste.stubs.MockWebServerWrapper;
import retrofit.converter.GsonConverter;

@Module(library = true, overrides = true, includes = MvpTestModule.class)
public class MvpEspressoTestModule {

    @Provides @Singleton WordPressService provideWordPressService(MockWebServerWrapper mockWebServer) {
        return WordPressServiceFactory.create(mockWebServer.getUrl(true), true, new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()));
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }

    @Provides @Singleton PageUrlManager providePostDetailUrlManager(MockWebServerWrapper server) {
        return new PageUrlManager() {
            @Override public String getUrl(String url) {
                return server.getUrl(true) + url;
            }
        };
    }
}
