package it.cosenonjaviste.ui;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.core.contact.ContactViewModel;
import it.cosenonjaviste.core.page.PageViewModel;
import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.core.twitter.TweetListViewModel;
import it.cosenonjaviste.ui.author.AuthorListFragment;
import it.cosenonjaviste.ui.category.CategoryListFragment;
import it.cosenonjaviste.ui.contact.ContactFragment;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.twitter.TweetListFragment;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    AuthorListViewModel getAuthorListViewModel();

    ContactViewModel getContactViewModel();

    CategoryListViewModel getCategoryListViewModel();

    PostListViewModel getPostListViewModel();

    TweetListViewModel getTweetListViewModel();

    PageViewModel getPageViewModel();

    void inject(PostListFragment postListFragment);

    void inject(AuthorListFragment authorListFragment);

    void inject(PageFragment pageFragment);

    void inject(TweetListFragment tweetListFragment);

    void inject(ContactFragment contactFragment);

    void inject(CategoryListFragment categoryListFragment);
}
