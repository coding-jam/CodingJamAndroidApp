package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M> {

    @Inject protected RxHolder rxHolder;
    protected MvpView<M> view;

    @Inject void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }

    public abstract void resume();

    protected <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onAttach, onNext, onError);
    }

    public MvpView<M> getView() {
        return view;
    }
}