package it.cosenonjaviste;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.post.PostDetailModel;
import it.cosenonjaviste.mvp.post.PostDetailPresenter;
import it.cosenonjaviste.mvp.post.PostDetailView;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(library = true, overrides = true, injects = TestContextBinder.class)
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
                .setEndpoint(getUrl(mockWebServer))
                .setExecutors(Runnable::run, Runnable::run)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(WordPressService.class);
    }

    private String getUrl(MockWebServer mockWebServer) {
        if (initInBackgroundThread) {
            try {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                return executorService.submit(() -> getUrlSync(mockWebServer)).get();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            return getUrlSync(mockWebServer);
        }
    }

    private String getUrlSync(MockWebServer mockWebServer) {
        return mockWebServer.getUrl("/").toString();
    }

    @Provides MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> provideAuthorListMvpConfig(Provider<AuthorListPresenter> presenter) {
        return new MvpConfig<>(AuthorListView.class, presenter::get);
    }

    @Provides MvpConfig<OptionalList<Category>, CategoryListView, CategoryListPresenter> provideCategoryListMvpConfig(Provider<CategoryListPresenter> presenter) {
        return new MvpConfig<>(CategoryListView.class, presenter::get);
    }

    @Provides MvpConfig<OptionalList<Post>, PostListView, PostListPresenter> providePostListMvpConfig(Provider<PostListPresenter> presenter) {
        return new MvpConfig<>(PostListView.class, presenter::get);
    }

    @Provides MvpConfig<PostDetailModel, PostDetailView, PostDetailPresenter> providePostDetailMvpConfig(Provider<PostDetailPresenter> presenter) {
        return new MvpConfig<>(PostDetailView.class, presenter::get);
    }
}
