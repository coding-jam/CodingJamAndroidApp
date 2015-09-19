package it.cosenonjaviste.core;

import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.mv2m.ActivityAware;

public interface Navigator extends ActivityAware {
    void openPostList(PostListModel postListModel);

    void openDetail(PageModel pageModel);
}
