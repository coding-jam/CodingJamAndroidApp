package it.cosenonjaviste;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.stubs.MockWebServerWrapper;
import it.cosenonjaviste.stubs.TestSchedulerManager;
import it.cosenonjaviste.stubs.TwitterServiceStub;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(library = true, overrides = true)
public class MvpTestModule {

    private boolean initInBackgroundThread;

    public MvpTestModule() {
        this(false);
    }

    public MvpTestModule(boolean initInBackgroundThread) {
        this.initInBackgroundThread = initInBackgroundThread;
    }

    @Provides @Singleton WordPressService provideWordPressService(MockWebServerWrapper mockWebServer) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(mockWebServer.getUrl(initInBackgroundThread))
                .setExecutors(Runnable::run, Runnable::run)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(WordPressService.class);
    }

    @Provides @Singleton TwitterService provideTwitterService(TwitterServiceStub twitterServiceStub) {
        return twitterServiceStub;
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager(TestSchedulerManager testSchedulerManager) {
        return testSchedulerManager;
    }
}
