package it.cosenonjaviste.ui;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.core.author.AuthorListPresenter;
import it.cosenonjaviste.core.category.CategoryListPresenter;
import it.cosenonjaviste.core.contact.ContactPresenter;
import it.cosenonjaviste.core.page.PagePresenter;
import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.core.twitter.TweetListPresenter;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    SchedulerManager providesSchedulerManager();

    WordPressService providesWordPressService();

    MailJetService providesMailJetService();

    TwitterService providesTwitterService();

    AuthorListPresenter getAuthorListPresenter();

    ContactPresenter getContactPresenter();

    CategoryListPresenter getCategoryListPresenter();

    PostListPresenter getPostListPresenter();

    TweetListPresenter getTweetListPresenter();

    PagePresenter getPagePresenter();
}
