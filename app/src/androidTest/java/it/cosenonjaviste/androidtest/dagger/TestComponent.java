package it.cosenonjaviste.androidtest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.BaseModule;
import it.cosenonjaviste.androidtest.AuthorListTest;
import it.cosenonjaviste.androidtest.CategoryListFragmentTest;
import it.cosenonjaviste.androidtest.MainActivityTest;
import it.cosenonjaviste.androidtest.PageTest;
import it.cosenonjaviste.androidtest.PostListTest;
import it.cosenonjaviste.androidtest.TweetListFragmentTest;
import it.cosenonjaviste.contact.ContactFragmentTest;

@Singleton
@Component(modules = {MvpEspressoTestModule.class, BaseModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(AuthorListTest authorListTest);

    void inject(MainActivityTest mainActivityTest);

    void inject(PageTest pageTest);

    void inject(CategoryListFragmentTest categoryListFragmentTest);

    void inject(PostListTest postListTest);

    void inject(TweetListFragmentTest tweetListTest);

    void inject(ContactFragmentTest contactFragmentTest);
}
