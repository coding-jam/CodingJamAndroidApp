package it.cosenonjaviste.twitter;

public interface TweetListView {
    void update(TweetListModel model);

    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();

    void showError();
}
