package it.cosenonjaviste.core.list;

import android.databinding.ObservableBoolean;

public interface GenericRxListViewModel {
    ObservableBoolean isLoading();

    ObservableBoolean isLoadingPullToRefresh();

    ObservableBoolean isLoadingNextPage();

    ObservableBoolean isError();

    void reloadData();

    void loadDataPullToRefresh();
}
