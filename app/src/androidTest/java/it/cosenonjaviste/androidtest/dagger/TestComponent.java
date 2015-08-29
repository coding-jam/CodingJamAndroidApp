package it.cosenonjaviste.androidtest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.ui.ApplicationComponent;
import it.cosenonjaviste.ui.MainActivityTest;
import it.cosenonjaviste.ui.author.AuthorListFragmentTest;
import it.cosenonjaviste.ui.category.CategoryListFragmentTest;
import it.cosenonjaviste.ui.contact.ContactFragmentTest;
import it.cosenonjaviste.ui.page.PageFragmentTest;
import it.cosenonjaviste.ui.post.PostListFragmentTest;
import it.cosenonjaviste.ui.twitter.TweetListFragmentTest;

@Singleton
@Component(modules = {EspressoTestModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(AuthorListFragmentTest authorListFragmentTest);

    void inject(MainActivityTest mainActivityTest);

    void inject(PageFragmentTest pageFragmentTest);

    void inject(CategoryListFragmentTest categoryListFragmentTest);

    void inject(PostListFragmentTest postListFragmentTest);

    void inject(TweetListFragmentTest tweetListTest);

    void inject(ContactFragmentTest contactFragmentTest);
}
