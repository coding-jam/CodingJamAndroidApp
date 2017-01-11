package it.cosenonjaviste.ui;

import android.support.annotation.VisibleForTesting;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.ui.author.AuthorListFragment;
import it.cosenonjaviste.ui.category.CategoryListFragment;
import it.cosenonjaviste.ui.contact.ContactFragment;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.twitter.TweetListFragment;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    @VisibleForTesting Gson gson();

    void inject(PostListFragment postListFragment);

    void inject(AuthorListFragment authorListFragment);

    void inject(PageFragment pageFragment);

    void inject(TweetListFragment tweetListFragment);

    void inject(ContactFragment contactFragment);

    void inject(CategoryListFragment categoryListFragment);
}
