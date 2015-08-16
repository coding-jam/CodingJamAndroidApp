package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableBoolean;

public abstract class RxMvpListPresenterAdapter<M extends ListModel<?>, V> extends RxMvpPresenter<M, V> implements RxMvpListPresenter {
    protected ObservableBoolean loading = new ObservableBoolean();

    protected ObservableBoolean loadingNextPage = new ObservableBoolean();

    protected ObservableBoolean loadingPullToRefresh = new ObservableBoolean();

    public RxMvpListPresenterAdapter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

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
        return getModel().error;
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !loading.get()) {
            reloadData();
        }
    }

    public void reloadData() {
        reloadData(loading);
    }

    public final void loadDataPullToRefresh() {
        reloadData(loadingPullToRefresh);
    }

    protected abstract void reloadData(ObservableBoolean loadingAction);
}
