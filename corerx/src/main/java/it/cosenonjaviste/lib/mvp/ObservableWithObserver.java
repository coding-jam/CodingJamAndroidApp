package it.cosenonjaviste.lib.mvp;

import rx.Observer;
import rx.observables.ConnectableObservable;

public class ObservableWithObserver<T> {
    public final ConnectableObservable<T> observable;

    public final Observer<T> observer;

    public ObservableWithObserver(ConnectableObservable<T> observable, Observer<T> observer) {
        this.observable = observable;
        this.observer = observer;
    }
}
