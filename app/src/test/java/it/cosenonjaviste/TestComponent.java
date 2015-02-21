package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.model.WordPressServiceTest;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.mvp.author.AuthorListPresenterTest;
import it.cosenonjaviste.mvp.category.CategoryListPresenterTest;
import it.cosenonjaviste.mvp.page.PagePresenterTest;
import it.cosenonjaviste.mvp.post.AuthorPostListPresenterTest;
import it.cosenonjaviste.mvp.post.CategoryPostListPresenterTest;
import it.cosenonjaviste.mvp.post.PostListPresenterTest;
import it.cosenonjaviste.mvp.twitter.TweetListPresenterTest;

@Singleton
@Component(modules = {MvpJUnitTestModule.class, BaseModule.class})
public interface TestComponent {
    void inject(WordPressServiceTest test);

    void inject(TweetListPresenterTest tweetListPresenterTest);

    void inject(AuthorListPresenterTest authorListPresenterTest);

    void inject(CategoryListPresenterTest categoryListPresenterTest);

    void inject(PagePresenterTest pagePresenterTest);

    void inject(AuthorPostListPresenterTest authorPostListPresenterTest);

    void inject(CategoryPostListPresenterTest categoryPostListPresenterTest);

    void inject(PostListPresenterTest postListPresenterTest);

}
