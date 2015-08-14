package it.cosenonjaviste.core.post;

import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.lib.mvp.RxMvpListView;
import it.cosenonjaviste.model.Post;

public interface PostListView extends RxMvpListView<Post> {
    void openDetail(PageModel model);
}
