package it.cosenonjaviste;

import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.lib.mvp.BundlePresenterArgs;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;
import it.cosenonjaviste.twitter.Twitter4JService;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.converter.GsonConverter;

@Module()
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton WordPressService provideGitHubService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                        //http calls are executed in background thread using RxUtils
                .setExecutors(Runnable::run, new MainThreadExecutor())
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }

    @Provides TwitterService provideTwitterService() {
        return new Twitter4JService();
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }

    @Provides @Singleton PresenterArgsFactory providePresenterArgsFactory() {
        return new BundlePresenterArgs.Factory();
    }
}
