package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PagePresenter;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

@Singleton
@Component(modules = { MvpTestModule.class })
public interface MvpTestComponent {
    WordPressService wordPressService();

    MockWebServerWrapper mockWebServerWrapper();

    AuthorListPresenter authorListPresenter();

    MvpConfig<AuthorListView> authorListConfig();

    PostListPresenter postListPresenter();

    MvpConfig<PostListView> postListConfig();

    PagePresenter pagePresenter();

    MvpConfig<PageView> pageViewConfig();

    CategoryListPresenter categoryListPresenter();

    MvpConfig<CategoryListView> categoryListViewConfig();

    TweetListPresenter tweetListPresenter();

    MvpConfig<TweetListView> tweetListViewConfig();
}
