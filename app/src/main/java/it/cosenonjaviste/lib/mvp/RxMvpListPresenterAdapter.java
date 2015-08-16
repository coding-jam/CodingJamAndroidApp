package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.List;

import rx.functions.Action1;

public abstract class RxMvpListPresenterAdapter<T, M extends ListModelAdapter<T>, V> extends RxMvpPresenter<M, V> implements RxMvpListPresenter {
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
        return getModel().error;
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !loading.get()) {
            reloadData();
        }
    }

    public void done(List<T> items) {
        getModel().append(items);
        getModel().error.set(false);
    }

    public void error() {
        getModel().clear();
        getModel().error.set(true);
    }

    public void setListChangeListener(Action1<List<T>> listChangeListener) {
        getModel().getItems().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override public void onChanged(ObservableList<T> sender) {
                listChangeListener.call(sender);
            }

            @Override public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                listChangeListener.call(sender);
            }

            @Override public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                listChangeListener.call(sender);
            }

            @Override public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                listChangeListener.call(sender);
            }

            @Override public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                listChangeListener.call(sender);
            }
        });
        if (listChangeListener != null && getModel().getItems() != null) {
            listChangeListener.call(getModel().getItems());
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
