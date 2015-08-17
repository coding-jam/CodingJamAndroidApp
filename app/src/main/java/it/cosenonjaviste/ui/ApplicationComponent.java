package it.cosenonjaviste.ui;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.core.contact.ContactViewModel;
import it.cosenonjaviste.core.page.PageViewModel;
import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.core.twitter.TweetListViewModel;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    AuthorListViewModel getAuthorListViewModel();

    ContactViewModel getContactViewModel();

    CategoryListViewModel getCategoryListViewModel();

    PostListViewModel getPostListViewModel();

    TweetListViewModel getTweetListViewModel();

    PageViewModel getPageViewModel();
}
