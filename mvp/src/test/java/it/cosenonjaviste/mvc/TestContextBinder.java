package it.cosenonjaviste.mvc;

import it.cosenonjaviste.mvp.baseCnj.ContextBinder;
import rx.Observable;

public class TestContextBinder implements ContextBinder {

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }
}
