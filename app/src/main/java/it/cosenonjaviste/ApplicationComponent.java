package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListPresenter;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListPresenter;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PagePresenter;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListPresenter;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListPresenter;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(PostListFragment fragment);

    void inject(PageFragment fragment);

    void inject(AuthorListFragment fragment);

    void inject(CategoryListFragment fragment);

    void inject(TweetListFragment fragment);

    PostListPresenter getPostListProvider();

    PagePresenter getPagePresenter();

    TweetListPresenter getTweetListPresenter();

    CategoryListPresenter getCategoryListPresenter();

    AuthorListPresenter getAuthorListPresenter();
}
