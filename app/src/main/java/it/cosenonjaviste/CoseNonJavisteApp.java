package it.cosenonjaviste;

import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.post.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.utils.ComponentBuilder;

public class CoseNonJavisteApp extends DaggerApplication {
    private AppComponent component;

    @Override public Object[] getModules() {
        return null;// new Object[]{new AppModule(this)};
    }

    @Override public void onCreate() {
        super.onCreate();
        component = ComponentBuilder.build(AppComponent.class, new AppModule(this));

        component.presenterConfig().init()
                .register(PostListView.class, PostListFragment.class)
                .register(AuthorListView.class, AuthorListFragment.class)
                .register(CategoryListView.class, CategoryListFragment.class)
                .register(TweetListView.class, TweetListFragment.class)
                .register(PageView.class, PageFragment.class);
    }

    public AppComponent getComponent() {
        return component;
    }
}
