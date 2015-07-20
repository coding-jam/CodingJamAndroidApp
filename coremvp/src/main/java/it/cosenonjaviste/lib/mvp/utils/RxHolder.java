package it.cosenonjaviste.lib.mvp.utils;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.lib.mvp.ObservableWithFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func0;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class RxHolder {
    private SchedulerManager schedulerManager;

    private final CompositeSubscription connectableSubscriptions = new CompositeSubscription();

    private CompositeSubscription subscriptions = new CompositeSubscription();

    protected final List<ObservableWithFactory> observables = new ArrayList<>();

    public RxHolder(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        subscribe(observable, onNext, onError, null);
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onCompleted) {
        ConnectableObservable<T> replay = schedulerManager.bindObservable(observable).replay();
        connectableSubscriptions.add(replay.connect());
        Func0<Subscriber<T>> factory = () -> new Subscriber<T>() {
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
        };
        ObservableWithFactory<T> observableWithFactory = new ObservableWithFactory<>(replay, factory);
        observables.add(observableWithFactory);
        subscribe(observableWithFactory);
    }


    private <T> void subscribe(ObservableWithFactory<T> observableWithFactory) {
        Observable<T> observable = observableWithFactory.observable;
        Subscriber<T> subscriber = observableWithFactory.subscriberFactory.call();
        subscriptions.add(observable.subscribe(
                Actions.empty(),
                t -> removeObservableFactory(observableWithFactory),
                () -> removeObservableFactory(observableWithFactory)));
        subscriptions.add(observable.subscribe(subscriber));
    }


    private <T> boolean removeObservableFactory(ObservableWithFactory<T> observableWithFactory) {
        return observables.remove(observableWithFactory);
    }

    public void resubscribePendingObservable() {
        ArrayList<ObservableWithFactory> observableCopy = new ArrayList<>(observables);
        for (ObservableWithFactory<?> observableWithFactory : observableCopy) {
            subscribe(observableWithFactory);
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

    public boolean isTaskRunning() {
        return !observables.isEmpty();
    }
}