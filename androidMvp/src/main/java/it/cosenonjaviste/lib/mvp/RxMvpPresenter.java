package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M, V> {

    @Inject RxHolder rxHolder;

    private M model;

    private V view;

    public void resume() {
        rxHolder.resubscribePendingObservable();
    }

    public final void init(M model, V view) {
        this.model = model;
        this.view = view;
    }

    @Inject public final void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }

    public final V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }

    public <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onAttach, onNext, onError);
    }

    public boolean isTaskRunning() {
        return rxHolder.isTaskRunning();
    }
}