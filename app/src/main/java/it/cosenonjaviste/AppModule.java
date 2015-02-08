package it.cosenonjaviste;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.author.ui.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.model.WordPressServiceFactory;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.Twitter4JService;

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
        return WordPressServiceFactory.create("http://www.cosenonjaviste.it/", BuildConfig.DEBUG);
    }

    @Provides TwitterService provideTwitterService() {
        return new Twitter4JService();
    }

    @Provides @Singleton SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }
}
