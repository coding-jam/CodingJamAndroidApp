package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableBoolean;

public interface RxMvpListPresenter {
    ObservableBoolean isLoading();

    ObservableBoolean isLoadingPullToRefresh();

    ObservableBoolean isLoadingNextPage();

    ObservableBoolean isError();

    void reloadData();

    void loadDataPullToRefresh();
}
