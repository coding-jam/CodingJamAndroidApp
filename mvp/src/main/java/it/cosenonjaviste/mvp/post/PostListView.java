package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.mvp.base.RxMvpView;

public interface PostListView extends RxMvpView<PostListModel> {
    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();
}
