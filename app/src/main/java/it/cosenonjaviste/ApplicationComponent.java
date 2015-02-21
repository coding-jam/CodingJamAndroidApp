package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListPresenter;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PagePresenter;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListPresenter;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(PageFragment fragment);

    void inject(CategoryListFragment fragment);

    void inject(TweetListFragment fragment);

    PagePresenter getPagePresenter();

    TweetListPresenter getTweetListPresenter();

    CategoryListPresenter getCategoryListPresenter();

    SchedulerManager providesSchedulerManager();

    WordPressService providesWordPressService();
}
