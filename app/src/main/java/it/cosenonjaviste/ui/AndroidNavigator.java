package it.cosenonjaviste.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class AndroidNavigator implements Navigator {
    private FragmentActivity activity;

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(activity, PostListFragment.class, model);
    }

    @Override public void openDetail(PageModel model) {
        SingleFragmentActivity.open(activity, PageFragment.class, model);
    }

    @Override public void setActivity(Activity activity) {
        this.activity = (FragmentActivity) activity;
    }
}
