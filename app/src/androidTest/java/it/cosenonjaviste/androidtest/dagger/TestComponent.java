package it.cosenonjaviste.androidtest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.androidtest.AuthorListTest;
import it.cosenonjaviste.androidtest.CategoryListFragmentTest;
import it.cosenonjaviste.androidtest.MainActivityTest;
import it.cosenonjaviste.androidtest.PageFragmentTest;
import it.cosenonjaviste.androidtest.PostListTest;
import it.cosenonjaviste.androidtest.TweetListFragmentTest;
import it.cosenonjaviste.contact.ContactFragmentTest;
import it.cosenonjaviste.ui.ApplicationComponent;
import it.cosenonjaviste.ui.BaseModule;

@Singleton
@Component(modules = {MvpEspressoTestModule.class, BaseModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(AuthorListTest authorListTest);

    void inject(MainActivityTest mainActivityTest);

    void inject(PageFragmentTest pageFragmentTest);

    void inject(CategoryListFragmentTest categoryListFragmentTest);

    void inject(PostListTest postListTest);

    void inject(TweetListFragmentTest tweetListTest);

    void inject(ContactFragmentTest contactFragmentTest);
}
