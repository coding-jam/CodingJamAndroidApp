package it.cosenonjaviste.core.list;

import android.databinding.ObservableBoolean;

import io.reactivex.disposables.Disposable;
import it.cosenonjaviste.core.base.RxViewModel;

public abstract class RxListViewModel<A, M extends ListModel<?>> extends RxViewModel<A, M> implements GenericRxListViewModel {
    protected ObservableBoolean loading = new ObservableBoolean();

    protected ObservableBoolean loadingNextPage = new ObservableBoolean();

    protected ObservableBoolean loadingPullToRefresh = new ObservableBoolean();

    @Override public ObservableBoolean isLoading() {
        return loading;
    }

    @Override public ObservableBoolean isLoadingPullToRefresh() {
        return loadingPullToRefresh;
    }

    @Override public ObservableBoolean isLoadingNextPage() {
        return loadingNextPage;
    }

    @Override public ObservableBoolean isError() {
        return model.error;
    }

    @Override public void resume() {
        super.resume();
        reloadData();
    }

    public void reloadData() {
        reloadData(loading, false);
    }

    public final void loadDataPullToRefresh() {
        reloadData(loadingPullToRefresh, true);
    }

    protected abstract Disposable reloadData(ObservableBoolean loadingAction, boolean forceFetch);
}
