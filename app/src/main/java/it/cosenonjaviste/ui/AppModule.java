package it.cosenonjaviste.ui;

import android.app.Application;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nytimes.android.external.store2.base.impl.Store;
import com.nytimes.android.external.store2.base.impl.StoreBuilder;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.BuildConfig;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.utils.DenvelopingConverter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.model.MyAdapterFactory;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton public Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();
    }

    @Provides @Singleton public WordPressService provideWordPressService(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new DenvelopingConverter(gson))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://www.codingjam.it/")
                .build();

        return retrofit.create(WordPressService.class);
    }

    @Provides @Singleton public MailJetService provideMailJetService(Gson gson) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addNetworkInterceptor(chain -> {
            String userName = BuildConfig.MAILJET_USERNAME;
            String password = BuildConfig.MAILJET_PASSWORD;
            String string = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(), Base64.NO_WRAP);

            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            builder.header("Authorization", string);
            Request request = builder.build();

            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.mailjet.com/v3")
                .build();

        return retrofit.create(MailJetService.class);
    }

    @Provides @Singleton public TwitterService provideTwitterService() {
        return new TwitterService(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET, BuildConfig.ACCESS_TOKEN, BuildConfig.ACCESS_TOKEN_SECRET);
    }

    @Provides public Navigator provideNavigator() {
        return new AndroidNavigator();
    }

    @Provides @Singleton
    public Store<List<Post>, Integer> postListStore(WordPressService wordPressService) {
        return StoreBuilder.<Integer, List<Post>>key()
                .fetcher(integer -> wordPressService.listPosts(integer).toObservable())
//                .persister(persister)
                .open();
    }

    @Provides @Singleton
    public Store<List<Author>, Integer> authorListStore(WordPressService wordPressService) {
        return StoreBuilder.<Integer, List<Author>>key()
                .fetcher(integer -> wordPressService.listAuthors().toObservable())
//                .persister(persister)
                .open();
    }
}
