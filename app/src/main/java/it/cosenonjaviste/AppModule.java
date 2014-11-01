package it.cosenonjaviste;

import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
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
import it.cosenonjaviste.post.PostDetailFragment;
import it.cosenonjaviste.post.PostFragment;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.converter.GsonConverter;

@Module(injects = {
        MainActivity.class,
        PostFragment.class,
        PostDetailFragment.class,
        AuthorListFragment.class,
        CategoryListFragment.class
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

    @Provides MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> provideAuthorListMvpConfig(Provider<AuthorListPresenter> presenter) {
        return new MvpConfig<>(AuthorListFragment.class, presenter::get);
    }

    @Provides MvpConfig<OptionalList<Category>, CategoryListView, CategoryListPresenter> provideCategoryListMvpConfig(Provider<CategoryListPresenter> presenter) {
        return new MvpConfig<>(CategoryListFragment.class, presenter::get);
    }

    @Provides MvpConfig<OptionalList<Post>, PostListView, PostListPresenter> providePostListMvpConfig(Provider<PostListPresenter> presenter) {
        return new MvpConfig<>(PostFragment.class, presenter::get);
    }

    @Provides MvpConfig<PostDetailModel, PostDetailView, PostDetailPresenter> providePostDetailMvpConfig(Provider<PostDetailPresenter> presenter) {
        return new MvpConfig<>(PostDetailFragment.class, presenter::get);
    }

}
