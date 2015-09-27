package it.cosenonjaviste.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class AndroidNavigator implements Navigator {
    private FragmentActivity activity;

    @Override public void openPostList(PostListArgument argument) {
        SingleFragmentActivity.open(activity, PostListFragment.class, argument);
    }

    @Override public void openDetail(Post post) {
        SingleFragmentActivity.open(activity, PageFragment.class, post);
    }

    @Override public void setActivity(Activity activity) {
        this.activity = (FragmentActivity) activity;
    }
}
