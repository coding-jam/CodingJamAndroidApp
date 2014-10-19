package it.cosenonjaviste;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.mvp.PostListPresenter;
import it.cosenonjaviste.testableandroidapps.model.RepoService;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import it.cosenonjaviste.utils.Clock;
import it.cosenonjaviste.utils.ClockImpl;
import it.cosenonjaviste.utils.DatePrefsSaver;
import it.cosenonjaviste.utils.DatePrefsSaverImpl;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module(injects = {PostFragment.class}, library = true)
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Clock provideClock() {
        return new ClockImpl();
    }

    @Provides @Singleton
    public DatePrefsSaver provideDatePrefsSaver() {
        return new DatePrefsSaverImpl(application, "welcome_dialog_last_date");
    }

    @Provides @Singleton
    public Client provideOkHttpClient() {
        return new OkClient(new OkHttpClient());
    }

    @Provides @Singleton
    public WordPressService provideGitHubService(Client client) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setClient(client)
                        //http calls are executed in background thread using RxUtils
                .setExecutors(Runnable::run, new MainThreadExecutor())
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }

    @Provides @Singleton
    public RepoService provideRepoService(WordPressService wordPressService) {
        return new RepoService(wordPressService);
    }

    @Provides
    public PostListPresenter providePostListPresenter(RepoService repoService) {
        return new PostListPresenter(repoService);
    }
}
