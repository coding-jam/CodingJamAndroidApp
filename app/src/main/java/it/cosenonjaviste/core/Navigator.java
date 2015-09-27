package it.cosenonjaviste.core;

import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.ActivityAware;

public interface Navigator extends ActivityAware {
    void openPostList(PostListArgument postListArgument);

    void openDetail(Post post);
}
