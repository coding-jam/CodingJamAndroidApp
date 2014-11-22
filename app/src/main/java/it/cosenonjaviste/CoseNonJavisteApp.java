package it.cosenonjaviste;

import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.post.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;

public class CoseNonJavisteApp extends DaggerApplication {

    @Override public Object[] getModules() {
        return new Object[]{new AppModule(this)};
    }

    @Override public void onCreate() {
        ObjectGraphHolder.setOnCreateListener(objectGraph -> ConfigManager.singleton()
                        .register(PostListView.class, PostListFragment.class)
                        .register(AuthorListView.class, AuthorListFragment.class)
                        .register(CategoryListView.class, CategoryListFragment.class)
                        .register(TweetListView.class, TweetListFragment.class)
                        .register(PageView.class, PageFragment.class)
        );
        super.onCreate();

    }
}
