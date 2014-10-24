package it.cosenonjaviste.mvp.base;

import it.cosenonjaviste.mvp.base.pausable.CompositePausableSubscription;
import it.cosenonjaviste.mvp.base.pausable.PausableSubscriptions;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observers.Observers;

public abstract class RxMvpPausablePresenter<M> extends MvpPresenter<M> {
    protected final CompositePausableSubscription pausableSubscriptions = new CompositePausableSubscription();

    public void pause() {
        pausableSubscriptions.pause();
    }

    public void destroy() {
        pausableSubscriptions.destroy();
    }

    protected <T> void subscribePausable(Observable<T> observable, Observer<T> observer) {
        pausableSubscriptions.add(PausableSubscriptions.subscribe(contextBinder.bindObservable(observable), observer));
    }

    protected <T> void subscribePausable(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        pausableSubscriptions.add(PausableSubscriptions.subscribe(contextBinder.bindObservable(observable), Observers.create(onNext, onError)));
    }

    protected <T> void subscribePausable(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        pausableSubscriptions.add(PausableSubscriptions.subscribe(contextBinder.bindObservable(observable), onAttach, Observers.create(onNext, onError)));
    }

    protected <T> void subscribePausable(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onCompleted) {
        pausableSubscriptions.add(PausableSubscriptions.subscribe(contextBinder.bindObservable(observable), onAttach, Observers.create(onNext, onError, onCompleted)));
    }

    protected <T> void subscribePausable(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError, Scheduler scheduler) {
        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler);
        }
        observable = contextBinder.bindObservable(observable);
        pausableSubscriptions.add(PausableSubscriptions.subscribe(observable, Observers.create(onNext, onError)));
    }

    protected <T> void subscribePausable(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onCompleted) {
        pausableSubscriptions.add(PausableSubscriptions.subscribe(contextBinder.bindObservable(observable), Observers.create(onNext, onError, onCompleted)));
    }
}
