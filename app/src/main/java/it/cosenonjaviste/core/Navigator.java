package it.cosenonjaviste.core;

import it.codingjam.lifecyclebinder.DefaultLifeCycleAware;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Post;

public abstract class Navigator extends DefaultLifeCycleAware<Object> {
    public abstract void openPostList(PostListArgument argument);

    public abstract void openDetail(Post post);

    public abstract void share(String subject, String text);

    public abstract void showMessage(int message);
}
