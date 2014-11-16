package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.mvp.base.MvpView;

public interface PostListView extends MvpView<PostListModel> {
    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();
}
