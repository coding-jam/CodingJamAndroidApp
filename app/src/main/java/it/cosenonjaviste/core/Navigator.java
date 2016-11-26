package it.cosenonjaviste.core;

import android.app.Activity;

import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;

public interface Navigator {
    void openPostList(Activity activity, PostListArgument postListArgument);

    void openDetail(Activity activity, Post post);

    void share(Activity activity, String title, String body);
}
