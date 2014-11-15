package it.cosenonjaviste.mvp.base;

import rx.Observable;

public abstract class ContextBinder {
    public abstract <T> Observable<T> bindObservable(Observable<T> observable);

    public abstract PresenterArgs createArgs();
}
