package it.cosenonjaviste.mvp.twitter;

import it.cosenonjaviste.mvp.base.RxMvpView;

public interface TweetListView extends RxMvpView<TweetListModel> {
    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();
}
