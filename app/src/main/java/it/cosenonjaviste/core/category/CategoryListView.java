package it.cosenonjaviste.core.category;

import it.cosenonjaviste.core.model.Category;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListView;

public interface CategoryListView extends RxMvpListView<Category> {
    void openPostList(PostListModel model);
}
