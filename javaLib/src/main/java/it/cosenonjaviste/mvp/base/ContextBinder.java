package it.cosenonjaviste.mvp.base;

import rx.Observable;

public abstract class ContextBinder {
    public abstract <T> Observable<T> bindObservable(Observable<T> observable);

    public abstract void startNewActivity(MvpConfig<?> config, PresenterArgs args);

    public abstract <T> T createView(MvpConfig<?> config, PresenterArgs args);

    public abstract PresenterArgs createArgs();
}
