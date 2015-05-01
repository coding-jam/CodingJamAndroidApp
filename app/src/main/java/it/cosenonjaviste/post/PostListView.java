package it.cosenonjaviste.post;

import it.cosenonjaviste.page.PageModel;

public interface PostListView {
    void update(PostListModel model);

    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();

    void openDetail(PageModel model);
}
