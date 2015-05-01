package it.cosenonjaviste.category;

import it.cosenonjaviste.post.PostListModel;

public interface CategoryListView {
    void update(CategoryListModel model);

    void startLoading();

    void openPostList(PostListModel model);
}
