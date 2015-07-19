package it.cosenonjaviste.lib.mvp;

import java.util.List;

import it.cosenonjaviste.bind.BindableBoolean;
import rx.functions.Action1;

public abstract class RxMvpListPresenterAdapter<T, M extends ListModelAdapter<T>, V> extends RxMvpPresenter<M, V> implements RxMvpListPresenter {
    protected BindableBoolean loading = new BindableBoolean();

    protected BindableBoolean loadingNextPage = new BindableBoolean();

    protected BindableBoolean loadingPullToRefresh = new BindableBoolean();

    protected Action1<List<T>> listChangeListener;

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
        return getModel().empty.and(loading.not());
    }

    @Override public BindableBoolean isListVisible() {
        return getModel().empty.not().and(loading.not());
    }

    @Override public BindableBoolean isError() {
        return getModel().error;
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
