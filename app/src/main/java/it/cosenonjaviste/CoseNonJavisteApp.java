package it.cosenonjaviste;

import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;

public class CoseNonJavisteApp extends DaggerApplication {

    @Override public Object[] getModules() {
        return new Object[]{new AppModule(this)};
    }

    @Override public void onCreate() {
        ObjectGraphHolder.setOnCreateListener(objectGraph -> ConfigManager.singleton()
                        .register(PostListFragment.class, PostListFragment.class)
                        .register(AuthorListFragment.class, AuthorListFragment.class)
                        .register(CategoryListFragment.class, CategoryListFragment.class)
                        .register(TweetListFragment.class, TweetListFragment.class)
                        .register(PageFragment.class, PageFragment.class)
        );
        super.onCreate();

    }
}
