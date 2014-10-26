package it.cosenonjaviste;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(library = true)
public class MvpTestModule {

    @Provides @Singleton MockWebServer provideMockWebServer() {
        MockWebServer server = new MockWebServer();
        try {
            server.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return server;
    }

    @Provides @Singleton WordPressService provideGitHubService(MockWebServer mockWebServer) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(mockWebServer.getUrl("/").toString())
                .setExecutors(Runnable::run, Runnable::run)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(WordPressService.class);
    }
}
