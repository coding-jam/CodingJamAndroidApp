package it.cosenonjaviste.ui;

import android.app.Application;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.BuildConfig;
import it.cosenonjaviste.core.MessageManager;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import it.cosenonjaviste.ui.utils.AndroidSchedulerManager;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton Gson provideGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Provides @Singleton WordPressService provideWordPressService(Gson gson) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setExecutors(Runnable::run, null)
                .setConverter(new GsonConverter(gson))
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }

    @Provides @Singleton MailJetService provideMailJetService(Gson gson) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mailjet.com/v3")
                .setExecutors(Runnable::run, null)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(request -> {
                    String userName = BuildConfig.MAILJET_USERNAME;
                    String password = BuildConfig.MAILJET_PASSWORD;
                    String string = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(), Base64.NO_WRAP);
                    request.addHeader("Authorization", string);
                }).build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(MailJetService.class);
    }

    @Provides @Singleton TwitterService provideTwitterService() {
        return new TwitterService(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET, BuildConfig.ACCESS_TOKEN, BuildConfig.ACCESS_TOKEN_SECRET);
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new AndroidSchedulerManager();
    }

    @Provides Navigator provideNavigator() {
        return new AndroidNavigator();
    }

    @Provides MessageManager provideMessageManager() {
        return new SnackbarMessageManager();
    }
}
