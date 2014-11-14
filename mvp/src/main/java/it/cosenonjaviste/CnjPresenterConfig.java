package it.cosenonjaviste;

import javax.inject.Inject;
import javax.inject.Provider;

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

public class CnjPresenterConfig {

    @Inject Provider<PostListPresenter> postListPresenterProvider;

    @Inject Provider<AuthorListPresenter> authorListPresenterProvider;

    @Inject Provider<CategoryListPresenter> categoryListPresenterProvider;

    @Inject Provider<PagePresenter> pagePresenterProvider;

    @Inject Provider<TweetListPresenter> tweetListPresenterProvider;

    @Inject public CnjPresenterConfig() {
    }

    public void init() {
        ConfigManager.singleton().registerPresenter(PostListView.class, postListPresenterProvider::get);
        ConfigManager.singleton().registerPresenter(AuthorListView.class, authorListPresenterProvider::get);
        ConfigManager.singleton().registerPresenter(CategoryListView.class, categoryListPresenterProvider::get);
        ConfigManager.singleton().registerPresenter(PageView.class, pagePresenterProvider::get);
        ConfigManager.singleton().registerPresenter(TweetListView.class, tweetListPresenterProvider::get);
    }
}
