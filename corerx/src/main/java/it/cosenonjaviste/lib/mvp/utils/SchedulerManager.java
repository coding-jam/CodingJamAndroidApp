package it.cosenonjaviste.lib.mvp.utils;

import rx.Observable;

public abstract class SchedulerManager {
    public abstract <T> Observable<T> bindObservable(Observable<T> observable);
}
