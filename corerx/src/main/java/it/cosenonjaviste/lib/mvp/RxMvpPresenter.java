package it.cosenonjaviste.lib.mvp;


import android.os.Parcelable;

import rx.Observable;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M extends Parcelable, V> extends MvpPresenter<M, V> {

    private RxHolder rxHolder;

    public RxMvpPresenter(SchedulerManager schedulerManager) {
        rxHolder = new RxHolder(schedulerManager != null ? schedulerManager : SchedulerManager.IDENTITY);
    }

    @Override public void resume() {
        rxHolder.resubscribePendingObservable();
    }

    @Override public void pause() {
        rxHolder.pause();
    }

    @Override public void destroy() {
        rxHolder.destroy();
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onNext, onError);
    }
}