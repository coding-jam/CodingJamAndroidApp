package it.cosenonjaviste;

import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PagePresenter;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.post.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.Twitter4JService;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.converter.GsonConverter;

@Module(injects = {
        MainActivity.class,
        PostListFragment.class,
        PageFragment.class,
        AuthorListFragment.class,
        CategoryListFragment.class,
        TweetListFragment.class
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

    @Provides TwitterService provideTwitterService() {
        return new Twitter4JService();
    }

    @Provides MvpConfig<AuthorListView> provideAuthorListMvpConfig(Provider<AuthorListPresenter> presenter) {
        return MvpConfig.create(AuthorListFragment.class, presenter::get);
    }

    @Provides MvpConfig<CategoryListView> provideCategoryListMvpConfig(Provider<CategoryListPresenter> presenter) {
        return MvpConfig.create(CategoryListFragment.class, presenter::get);
    }

    @Provides MvpConfig<PostListView> providePostListMvpConfig(Provider<PostListPresenter> presenter) {
        return MvpConfig.create(PostListFragment.class, presenter::get);
    }

    @Provides MvpConfig<PageView> providePostDetailMvpConfig(Provider<PagePresenter> presenter) {
        return MvpConfig.create(PageFragment.class, presenter::get);
    }

    @Provides MvpConfig<TweetListView> provideTweetListMvpConfig(Provider<TweetListPresenter> presenter) {
        return MvpConfig.create(TweetListFragment.class, presenter::get);
    }
}
