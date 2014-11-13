package it.cosenonjaviste;

import com.google.gson.GsonBuilder;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PagePresenter;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.stubs.MockWebServerWrapper;
import it.cosenonjaviste.stubs.TwitterServiceStub;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.functions.Func0;

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

    @Provides MvpConfig<AuthorListView> provideAuthorListMvpConfig(AuthorListPresenter presenter) {
        return MvpConfig.create(AuthorListView.class, new Func0<RxMvpPresenter<OptionalList<Author>>>() {
            @Override public RxMvpPresenter<OptionalList<Author>> call() {
                return presenter;
            }
        });
    }

    @Provides MvpConfig<CategoryListView> provideCategoryListMvpConfig(Provider<CategoryListPresenter> presenter) {
        return MvpConfig.create(CategoryListView.class, presenter::get);
    }

    @Provides MvpConfig<PostListView> providePostListMvpConfig(Provider<PostListPresenter> presenter) {
        return MvpConfig.create(PostListView.class, presenter::get);
    }

    @Provides MvpConfig<PageView> providePostDetailMvpConfig(Provider<PagePresenter> presenter) {
        return MvpConfig.create(PageView.class, presenter::get);
    }

    @Provides MvpConfig<TweetListView> provideTweetListMvpConfig(Provider<TweetListPresenter> presenter) {
        return MvpConfig.create(TweetListView.class, presenter::get);
    }
}
