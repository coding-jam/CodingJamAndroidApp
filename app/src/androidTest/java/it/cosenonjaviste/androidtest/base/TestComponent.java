package it.cosenonjaviste.androidtest.base;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.BaseModule;
import it.cosenonjaviste.androidtest.AuthorListTest;
import it.cosenonjaviste.androidtest.CategoryListTest;
import it.cosenonjaviste.androidtest.MainActivityTest;
import it.cosenonjaviste.androidtest.PageTest;
import it.cosenonjaviste.androidtest.PostListTest;
import it.cosenonjaviste.androidtest.TweetListTest;

@Singleton
@Component(modules = {MvpEspressoTestModule.class, BaseModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(AuthorListTest authorListTest);

    void inject(MainActivityTest mainActivityTest);

    void inject(PageTest pageTest);

    void inject(CategoryListTest categoryListTest);

    void inject(PostListTest postListTest);

    void inject(TweetListTest tweetListTest);
}
