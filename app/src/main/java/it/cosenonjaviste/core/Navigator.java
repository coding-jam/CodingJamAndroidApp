package it.cosenonjaviste.core;

import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;

public interface Navigator {
    void openPostList(PostListArgument argument);

    void openDetail(Post post);

    void share(String subject, String text);
}
