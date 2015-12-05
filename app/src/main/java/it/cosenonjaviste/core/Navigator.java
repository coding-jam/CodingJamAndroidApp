package it.cosenonjaviste.core;

import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ActivityHolder;

public interface Navigator {
    void openPostList(ActivityHolder activityHolder, PostListArgument postListArgument);

    void openDetail(ActivityHolder activityHolder, Post post);

    void share(ActivityHolder activityHolder, String title, String body);
}
