package it.cosenonjaviste.lib.mvp;

import java.util.List;

public interface RxMvpListView<T> {
    void update(List<T> items);

    void showError();

    void startLoading();
}
