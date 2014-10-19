package it.cosenonjaviste.mvp.base;

import java.util.List;

import rx.Observable;

public abstract class RxMvpListPresenter<M extends ListModel, T> extends RxMvpPresenter<M> {
    public final void loadAllData(int page) {
        //TODO
//        model.start(page);
//        notifyModelChanged();
//        subscribePausable(getDataListObservable(page), apps -> {
//            updateItems(page, apps);
//            notifyModelChanged();
//        }, throwable -> {
//            model.error(page, throwable);
//            notifyModelChanged();
//        });
    }

    public final void loadNextPage() {
        loadAllData(model.getLastLoadedPage() + 1);
    }

    protected abstract void updateItems(int page, List<T> items);

    protected abstract Observable<List<T>> getDataListObservable(int page);
}
