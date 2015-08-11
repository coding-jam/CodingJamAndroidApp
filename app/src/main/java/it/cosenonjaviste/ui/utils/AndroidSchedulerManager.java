package it.cosenonjaviste.ui.utils;

import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndroidSchedulerManager extends SchedulerManager {
    public static Scheduler io = Schedulers.io();

    public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable.subscribeOn(io).observeOn(AndroidSchedulers.mainThread());
    }
}
