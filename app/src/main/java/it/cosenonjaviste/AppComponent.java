package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.post.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.twitter.TweetListFragment;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(PostListFragment postListFragment);

    void inject(CategoryListFragment categoryListFragment);

    void inject(PageFragment pageFragment);

    void inject(AuthorListFragment authorListFragment);

    void inject(TweetListFragment tweetListFragment);

    CnjPresenterConfig presenterConfig();
}
