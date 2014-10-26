package it.cosenonjaviste;

import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.author.AuthorListMvpConfig;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.post.PostDetailMvpConfig;
import it.cosenonjaviste.mvp.post.PostDetailView;
import it.cosenonjaviste.mvp.post.PostListMvpConfig;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.post.PostDetailFragment;
import it.cosenonjaviste.post.PostFragment;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.converter.GsonConverter;

@Module(injects = {
        MainActivity.class,
        PostListMvpConfig.class,
        PostFragment.class,
        PostDetailMvpConfig.class,
        PostDetailFragment.class,
        AuthorListMvpConfig.class,
        AuthorListFragment.class
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

    @Provides PostDetailView providePostDetailView() {
        return new PostDetailFragment();
    }

    @Provides PostListView providePostListView() {
        return new PostFragment();
    }

    @Provides AuthorListView provideAuthorListView() {
        return new AuthorListFragment();
    }
}
