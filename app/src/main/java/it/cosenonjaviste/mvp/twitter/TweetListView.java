package it.cosenonjaviste.mvp.twitter;

import it.cosenonjaviste.mvp.base.MvpView;

public interface TweetListView extends MvpView<TweetListModel> {
    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();
}
