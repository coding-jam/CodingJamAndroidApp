package it.cosenonjaviste.lib.mvp;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class RxHolder {
    private SchedulerManager schedulerManager;

    private final CompositeSubscription connectableSubscriptions = new CompositeSubscription();

    private CompositeSubscription subscriptions = new CompositeSubscription();

    protected final List<ObservableWithObserver> observables = new ArrayList<>();

    public RxHolder(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        subscribe(observable, onNext, onError, null);
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onCompleted) {
        ConnectableObservable<T> replay = observable.compose(schedulerManager::bindObservable).replay();
        connectableSubscriptions.add(replay.connect());
        ObservableWithObserver<T> observableWithObserver = new ObservableWithObserver<>(replay, new Observer<T>() {
            @Override public void onCompleted() {
                if (onCompleted != null) {
                    onCompleted.call();
                }
            }

            @Override public void onError(Throwable e) {
                if (onError != null) {
                    onError.call(e);
                }
            }

            @Override public void onNext(T t) {
                if (onNext != null) {
                    onNext.call(t);
                }
            }
        });
        observables.add(observableWithObserver);
        subscribe(observableWithObserver);
    }


    private <T> void subscribe(ObservableWithObserver<T> observableWithObserver) {
        subscriptions.add(
                observableWithObserver.observable
                        .finallyDo(() -> observables.remove(observableWithObserver))
                        .subscribe(observableWithObserver.observer)
        );
    }

    public void resubscribePendingObservable() {
        ArrayList<ObservableWithObserver> observableCopy = new ArrayList<>(observables);
        for (ObservableWithObserver<?> observableWithObserver : observableCopy) {
            subscribe(observableWithObserver);
        }
    }

    public void pause() {
        subscriptions.unsubscribe();
        subscriptions = new CompositeSubscription();
    }

    public void destroy() {
        connectableSubscriptions.unsubscribe();
    }

    public CompositeSubscription getSubscriptions() {
        return subscriptions;
    }
}