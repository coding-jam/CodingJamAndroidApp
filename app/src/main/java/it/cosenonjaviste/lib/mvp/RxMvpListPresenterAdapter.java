package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableBoolean;

import java.util.List;

import rx.functions.Action1;

public abstract class RxMvpListPresenterAdapter<T, M extends ListModelAdapter<T>, V> extends RxMvpPresenter<M, V> implements RxMvpListPresenter {
    protected ObservableBoolean loading = new ObservableBoolean();

    protected ObservableBoolean loadingNextPage = new ObservableBoolean();

    protected ObservableBoolean loadingPullToRefresh = new ObservableBoolean();

    protected Action1<List<T>> listChangeListener;

    @Override public ObservableBoolean isLoading() {
        return loading;
    }

    @Override public ObservableBoolean isLoadingPullToRefresh() {
        return loadingPullToRefresh;
    }

    @Override public ObservableBoolean isLoadingNextPage() {
        return loadingNextPage;
    }

//    @Override public ObservableBoolean isEmptyLayoutVisible() {
//        return getModel().empty.and(loading.not());
//    }
//
//    @Override public ObservableBoolean isListVisible() {
//        return getModel().empty.not().and(loading.not());
//    }

    @Override public ObservableBoolean isError() {
        return getModel().error;
    }

    @Override public ObservableBoolean isEmpty() {
        return getModel().empty;
    }

    public void done(List<T> items) {
        getModel().setItems(items);
        getModel().error.set(false);
        getModel().empty.set(items.isEmpty());
        if (listChangeListener != null) {
            listChangeListener.call(items);
        }
    }

    public void error() {
        getModel().setItems(null);
        getModel().error.set(true);
        getModel().empty.set(false);
    }

    public void setListChangeListener(Action1<List<T>> listChangeListener) {
        this.listChangeListener = listChangeListener;
        if (listChangeListener != null && getModel().getItems() != null) {
            listChangeListener.call(getModel().getItems());
        }
    }
}
