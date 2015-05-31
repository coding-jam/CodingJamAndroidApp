package it.cosenonjaviste.lib.mvp;

import java.util.List;

public interface RxMvpListView<T> {
    void startLoading(boolean showMainLoading);

    void startMoreItemsLoading();

    void update(List<T> items);

    void showError();

    void startLoading();
}
