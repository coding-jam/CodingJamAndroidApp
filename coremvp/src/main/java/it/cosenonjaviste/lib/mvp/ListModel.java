package it.cosenonjaviste.lib.mvp;

import it.cosenonjaviste.bind.BindableBoolean;

public interface ListModel {
    BindableBoolean isLoading();

    BindableBoolean isLoadingPullToRefresh();

    BindableBoolean isLoadingNextPage();

    BindableBoolean isEmptyLayoutVisible();

    BindableBoolean isListVisible();

    BindableBoolean isError();
}
