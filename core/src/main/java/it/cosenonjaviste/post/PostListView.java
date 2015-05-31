package it.cosenonjaviste.post;

import it.cosenonjaviste.lib.mvp.RxMvpListView;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.page.PageModel;

public interface PostListView extends RxMvpListView<Post> {
    void openDetail(PageModel model);
}
