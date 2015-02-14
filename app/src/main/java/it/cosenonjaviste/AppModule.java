package it.cosenonjaviste;

import android.app.Application;

import dagger.Module;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;

@Module(injects = {
        MainActivity.class,
        PostListFragment.class,
        PageFragment.class,
        AuthorListFragment.class,
        CategoryListFragment.class,
        TweetListFragment.class
}, library = true, includes = BaseModule.class)
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }
}
