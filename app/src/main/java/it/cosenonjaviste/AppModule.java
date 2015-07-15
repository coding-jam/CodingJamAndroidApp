package it.cosenonjaviste;

import android.app.Application;
import android.util.Base64;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(includes = BaseModule.class)
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
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
                    String userName = "d44e612d91a6914226cdee5118b0873d";
                    String password = "896c727efc4ad822c00f23fa76db585f";
                    String string = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(), Base64.NO_WRAP);
                    request.addHeader("Authorization", string);
                }).build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(MailJetService.class);
    }

    @Provides TwitterService provideTwitterService() {
        return new TwitterService(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET, BuildConfig.ACCESS_TOKEN, BuildConfig.ACCESS_TOKEN_SECRET);
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }
}
