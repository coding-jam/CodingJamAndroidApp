package it.cosenonjaviste.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ActivityHolder;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class AndroidNavigator implements Navigator {
    @Override public void openPostList(ActivityHolder activityHolder, PostListArgument argument) {
        SingleFragmentActivity.open((FragmentActivity) activityHolder.getActivity(), PostListFragment.class, argument);
    }

    @Override public void openDetail(ActivityHolder activityHolder, Post post) {
        SingleFragmentActivity.open((FragmentActivity) activityHolder.getActivity(), PageFragment.class, post);
    }

    @Override public void share(ActivityHolder activityHolder, String subject, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        activityHolder.startActivity(Intent.createChooser(sendIntent, activityHolder.getActivity().getResources().getText(R.string.share_post)));
    }
}
