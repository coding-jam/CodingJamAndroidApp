package it.cosenonjaviste;

import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.post.PostDetailView;
import it.cosenonjaviste.post.PostDetailFragment;
import it.cosenonjaviste.post.PostFragment;
import it.cosenonjaviste.post.PostFragment2;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.converter.GsonConverter;

@Module(injects = {
        PostFragment.class,
        PostFragment2.class,
        PostDetailView.class,
        PostDetailFragment.class
}, library = true)
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

    @Provides @Singleton Navigator provideNavigator(CnjNavigator navigator) {
        return navigator;
    }

    @Provides PostDetailView providePostDetailView() {
        return new PostDetailFragment();
    }
}
