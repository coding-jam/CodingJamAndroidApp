package it.cosenonjaviste;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.post.PostDetailPresenter;
import it.cosenonjaviste.mvp.post.PostDetailView;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.stubs.MockWebServerUtils;
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
                .setEndpoint(MockWebServerUtils.getUrl(mockWebServer, initInBackgroundThread))
                .setExecutors(Runnable::run, Runnable::run)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(WordPressService.class);
    }

    @Provides @Singleton TwitterService provideTwitterService(TwitterServiceStub twitterServiceStub) {
        return twitterServiceStub;
    }

    @Provides MvpConfig<AuthorListView, AuthorListPresenter> provideAuthorListMvpConfig(Provider<AuthorListPresenter> presenter) {
        return MvpConfig.create(AuthorListView.class, presenter::get);
    }

    @Provides MvpConfig<CategoryListView, CategoryListPresenter> provideCategoryListMvpConfig(Provider<CategoryListPresenter> presenter) {
        return MvpConfig.create(CategoryListView.class, presenter::get);
    }

    @Provides MvpConfig<PostListView, PostListPresenter> providePostListMvpConfig(Provider<PostListPresenter> presenter) {
        return MvpConfig.create(PostListView.class, presenter::get);
    }

    @Provides MvpConfig<PostDetailView, PostDetailPresenter> providePostDetailMvpConfig(Provider<PostDetailPresenter> presenter) {
        return MvpConfig.create(PostDetailView.class, presenter::get);
    }

    @Provides MvpConfig<TweetListView, TweetListPresenter> provideTweetListMvpConfig(Provider<TweetListPresenter> presenter) {
        return MvpConfig.create(TweetListView.class, presenter::get);
    }
}
