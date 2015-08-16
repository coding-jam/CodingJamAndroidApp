package it.cosenonjaviste.ui;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.core.author.AuthorListPresenter;
import it.cosenonjaviste.core.category.CategoryListPresenter;
import it.cosenonjaviste.core.contact.ContactPresenter;
import it.cosenonjaviste.core.page.PagePresenter;
import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.core.twitter.TweetListPresenter;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    AuthorListPresenter getAuthorListPresenter();

    ContactPresenter getContactPresenter();

    CategoryListPresenter getCategoryListPresenter();

    PostListPresenter getPostListPresenter();

    TweetListPresenter getTweetListPresenter();

    PagePresenter getPagePresenter();
}
