package it.cosenonjaviste.core.post;

import it.cosenonjaviste.core.model.Post;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.lib.mvp.RxMvpListView;

public interface PostListView extends RxMvpListView<Post> {
    void openDetail(PageModel model);
}
