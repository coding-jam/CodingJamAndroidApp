package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M> {

    @Inject RxHolder rxHolder;
    protected M model;
    protected MvpView<M> view;
    private LifeCycle lifeCycle;

    @Inject void initLifeCycle(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::subscribe);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
        lifeCycle.subscribeOnSaveInstanceState(() -> model);
    }

    public void subscribe() {
        view.update(model);
        rxHolder.resubscribePendingObservable();
    }

    protected <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onAttach, onNext, onError);
    }

    public void init(M model, MvpView<M> view) {
        this.model = model;
        this.view = view;
    }

    public void initAndSubscribe(M model, MvpView<M> view) {
        init(model, view);
        subscribe();
    }

    public MvpView<M> getView() {
        return view;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }
}