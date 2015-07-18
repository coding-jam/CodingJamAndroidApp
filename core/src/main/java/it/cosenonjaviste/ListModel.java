package it.cosenonjaviste;

import org.parceler.Transient;

import it.cosenonjaviste.bind.BindableBoolean;

public abstract class ListModel {
    public BindableBoolean empty = new BindableBoolean(true);

    public BindableBoolean error = new BindableBoolean();

    @Transient
    public BindableBoolean loading = new BindableBoolean();

    @Transient
    public BindableBoolean loadingMore = new BindableBoolean();

    public BindableBoolean isLoadingMore() {
        return loadingMore;
    }

    public BindableBoolean isLoading() {
        return loading;
    }

    public BindableBoolean isEmptyLayoutVisible() {
        return empty.and(loading.not());
    }

    public BindableBoolean isListVisible() {
        return empty.not().and(loading.not());
    }

    public BindableBoolean isError() {
        return error;
    }
}
