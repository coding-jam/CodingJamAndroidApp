package it.cosenonjaviste;

import it.cosenonjaviste.bind.BindableBoolean;

public interface ListModel {
    BindableBoolean isLoadingMore();

    BindableBoolean isLoading();

    BindableBoolean isEmptyLayoutVisible();

    BindableBoolean isListVisible();

    BindableBoolean isError();
}
