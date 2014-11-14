package it.cosenonjaviste;

import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.utils.ComponentBuilder;

public class CoseNonJavisteApp extends DaggerApplication {
    private AppComponent component;

    @Override public Object[] getModules() {
        return null;// new Object[]{new AppModule(this)};
    }

    @Override public void onCreate() {
        super.onCreate();
        component = ComponentBuilder.build(AppComponent.class, new AppModule(this));
//        ConfigManager configManager = ConfigManager.singleton();
//        configManager.register(PostListView.class, PostListFragment.class);
//        configManager.register(AuthorListView.class, AuthorListFragment.class);
//        configManager.register(CategoryListView.class, CategoryListFragment.class);
//        configManager.register(TweetListView.class, TweetListFragment.class);
//        configManager.register(PageView.class, PageFragment.class);
    }

    public AppComponent getComponent() {
        return component;
    }
}
