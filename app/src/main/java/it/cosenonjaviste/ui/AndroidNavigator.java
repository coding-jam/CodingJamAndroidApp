package it.cosenonjaviste.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class AndroidNavigator implements Navigator {
    @Override public void openPostList(Activity activity, PostListArgument argument) {
        SingleFragmentActivity.open((FragmentActivity) activity, PostListFragment.class, argument);
    }

    @Override public void openDetail(Activity activity, Post post) {
        SingleFragmentActivity.open((FragmentActivity) activity, PageFragment.class, post);
    }

    @Override public void share(Activity activity, String subject, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, activity.getResources().getText(R.string.share_post)));
    }
}
