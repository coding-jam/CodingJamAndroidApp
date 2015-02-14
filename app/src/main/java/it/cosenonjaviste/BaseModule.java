package it.cosenonjaviste;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.model.WordPressServiceFactory;
import it.cosenonjaviste.twitter.Twitter4JService;
import retrofit.converter.GsonConverter;

@Module(library = true)
public class BaseModule {
    @Provides @Singleton Gson provideGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Provides @Singleton WordPressService provideGitHubService(Gson gson) {
        return WordPressServiceFactory.create("http://www.cosenonjaviste.it/", BuildConfig.DEBUG, new GsonConverter(gson));
    }

    @Provides TwitterService provideTwitterService() {
        return new Twitter4JService();
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }
}
