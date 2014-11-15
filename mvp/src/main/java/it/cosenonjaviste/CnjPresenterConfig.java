package it.cosenonjaviste;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PagePresenter;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;

@Singleton
public class CnjPresenterConfig {

    @Inject Provider<PostListPresenter> postListPresenterProvider;

    @Inject Provider<AuthorListPresenter> authorListPresenterProvider;

    @Inject Provider<CategoryListPresenter> categoryListPresenterProvider;

    @Inject Provider<PagePresenter> pagePresenterProvider;

    @Inject Provider<TweetListPresenter> tweetListPresenterProvider;

    @Inject public CnjPresenterConfig() {
    }

    public ConfigManager init() {
        ConfigManager configManager = ConfigManager.singleton();
        configManager.registerPresenter(PostListView.class, postListPresenterProvider::get);
        configManager.registerPresenter(AuthorListView.class, authorListPresenterProvider::get);
        configManager.registerPresenter(CategoryListView.class, categoryListPresenterProvider::get);
        configManager.registerPresenter(PageView.class, pagePresenterProvider::get);
        configManager.registerPresenter(TweetListView.class, tweetListPresenterProvider::get);
        return configManager;
    }
}
