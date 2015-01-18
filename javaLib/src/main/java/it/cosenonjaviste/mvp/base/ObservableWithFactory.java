package it.cosenonjaviste.mvp.base;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public class ObservableWithFactory<T> {
    public final Observable<T> observable;

    public final Func0<Subscriber<T>> subscriberFactory;

    ObservableWithFactory(Observable<T> observable, Func0<Subscriber<T>> subscriberFactory) {
        this.observable = observable;
        this.subscriberFactory = subscriberFactory;
    }
}
