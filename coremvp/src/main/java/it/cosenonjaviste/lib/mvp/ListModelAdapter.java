package it.cosenonjaviste.lib.mvp;

import org.parceler.Transient;

import java.util.List;

import it.cosenonjaviste.bind.BindableBoolean;
import rx.functions.Action1;

public abstract class ListModelAdapter<T> implements ListModel {
    public BindableBoolean empty = new BindableBoolean(true);

    public BindableBoolean error = new BindableBoolean();

    @Transient
    public BindableBoolean loading = new BindableBoolean();

    @Transient
    public BindableBoolean loadingNextPage = new BindableBoolean();

    @Transient
    public BindableBoolean loadingPullToRefresh = new BindableBoolean();

    @Transient
    Action1<List<T>> listChangeListener;

    @Override public BindableBoolean isLoading() {
        return loading;
    }

    @Override public BindableBoolean isLoadingPullToRefresh() {
        return loadingPullToRefresh;
    }

    @Override public BindableBoolean isLoadingNextPage() {
        return loadingNextPage;
    }

    @Override public BindableBoolean isEmptyLayoutVisible() {
        return empty.and(loading.not());
    }

    @Override public BindableBoolean isListVisible() {
        return empty.not().and(loading.not());
    }

    @Override public BindableBoolean isError() {
        return error;
    }

    public void done(List<T> items) {
        setItems(items);
        error.set(false);
        empty.set(items.isEmpty());
        if (listChangeListener != null) {
            listChangeListener.call(items);
        }
    }

    public void error() {
        setItems(null);
        error.set(true);
        empty.set(false);
    }

    public void setListChangeListener(Action1<List<T>> listChangeListener) {
        this.listChangeListener = listChangeListener;
        if (listChangeListener != null && getItems() != null) {
            listChangeListener.call(getItems());
        }
    }

    protected abstract List<T> getItems();

    protected abstract void setItems(List<T> items);

    public boolean isLoaded() {
        return getItems() != null || error.get();
    }
}
