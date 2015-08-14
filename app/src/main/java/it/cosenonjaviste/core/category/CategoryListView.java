package it.cosenonjaviste.core.category;

import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListView;
import it.cosenonjaviste.model.Category;

public interface CategoryListView extends RxMvpListView<Category> {
    void openPostList(PostListModel model);
}
