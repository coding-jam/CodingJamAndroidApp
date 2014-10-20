package it.cosenonjaviste.utils;

import it.cosenonjaviste.mvp.base.ContextBinder;
import rx.Observable;

public class TestContextBinder implements ContextBinder {

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }
}
