package it.cosenonjaviste.category;

import it.cosenonjaviste.lib.mvp.RxMvpListView;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.post.PostListModel;

public interface CategoryListView extends RxMvpListView<Category> {
    void openPostList(PostListModel model);
}
