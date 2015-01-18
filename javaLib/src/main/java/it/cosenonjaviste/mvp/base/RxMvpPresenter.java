package it.cosenonjaviste.mvp.base;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func0;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public abstract class RxMvpPresenter<M> extends MvpPresenter<M> {

    private SchedulerManager schedulerManager;

    private final CompositeSubscription connectableSubscriptions = new CompositeSubscription();

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private final List<ObservableWithFactory> observables = new ArrayList<>();

    public RxMvpPresenter(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public void pause() {
        super.pause();
        subscriptions.unsubscribe();
        subscriptions = new CompositeSubscription();
    }

    @Override public void subscribe(MvpView<M> view) {
        super.subscribe(view);
        for (ObservableWithFactory<?> observableWithFactory : observables) {
            subscribe(observableWithFactory);
        }
    }

    public void destroy() {
        connectableSubscriptions.unsubscribe();
    }

    protected <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        ConnectableObservable<T> replay = schedulerManager.bindObservable(observable).replay();
        connectableSubscriptions.add(replay.connect());
        Func0<Subscriber<T>> factory = () -> new Subscriber<T>() {
            @Override public void onStart() {
                if (onAttach != null) {
                    onAttach.call();
                }
            }

            @Override public void onCompleted() {

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
                t -> observables.remove(observableWithFactory),
                () -> observables.remove(observableWithFactory)));
        subscriptions.add(observable.subscribe(subscriber));
    }
}