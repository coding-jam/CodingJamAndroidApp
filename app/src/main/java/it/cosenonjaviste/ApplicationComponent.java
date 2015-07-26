package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.author.AuthorListPresenter;
import it.cosenonjaviste.category.CategoryListPresenter;
import it.cosenonjaviste.contact.ContactPresenter;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PagePresenter;
import it.cosenonjaviste.page.PageUrlManager;
import it.cosenonjaviste.post.PostListPresenter;
import it.cosenonjaviste.twitter.TweetListPresenter;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    SchedulerManager providesSchedulerManager();

    WordPressService providesWordPressService();

    MailJetService providesMailJetService();

    PageUrlManager providesPageUrlManager();

    TwitterService providesTwitterService();

    AuthorListPresenter getAuthorListPresenter();

    ContactPresenter getContactPresenter();

    CategoryListPresenter getCategoryListPresenter();

    PostListPresenter getPostListPresenter();

    TweetListPresenter getTweetListPresenter();

    PagePresenter getPagePresenter();
}
