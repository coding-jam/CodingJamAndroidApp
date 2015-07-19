package it.cosenonjaviste.lib.mvp;

import it.cosenonjaviste.bind.BindableBoolean;

public interface RxMvpListPresenter {
    BindableBoolean isLoading();

    BindableBoolean isLoadingPullToRefresh();

    BindableBoolean isLoadingNextPage();

    BindableBoolean isEmptyLayoutVisible();

    BindableBoolean isListVisible();

    BindableBoolean isError();
}
