package it.cosenonjaviste.lib.mvp.utils;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class SchedulerManager {
    public static Scheduler io = Schedulers.io();

    public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable.subscribeOn(io).observeOn(mainThread());
    }
}
