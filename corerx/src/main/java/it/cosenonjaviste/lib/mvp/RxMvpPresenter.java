package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M, V> extends MvpPresenter<M, V> {

    private RxHolder rxHolder;

    @Inject public final void initLifeCycle(SchedulerManager schedulerManager) {
        rxHolder = new RxHolder(schedulerManager);
    }

    @Override public void resume() {
        if (rxHolder != null) {
            rxHolder.resubscribePendingObservable();
        }
    }

    @Override public void pause() {
        if (rxHolder != null) {
            rxHolder.pause();
        }
    }

    @Override public void destroy() {
        if (rxHolder != null) {
            rxHolder.destroy();
        }
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        if (rxHolder == null) {
            rxHolder = new RxHolder(SchedulerManager.IDENTITY);
        }
        rxHolder.subscribe(observable, onNext, onError);
    }
}