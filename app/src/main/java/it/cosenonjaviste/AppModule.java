package it.cosenonjaviste;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(library = true, includes = BaseModule.class)
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton WordPressService provideGitHubService(Gson gson) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                        //http calls are executed in background thread using SchedulerManager
                .setExecutors(Runnable::run, null)
                .setConverter(new GsonConverter(gson))
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }

    @Provides TwitterService provideTwitterService() {
        return new TwitterService();
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }
}
