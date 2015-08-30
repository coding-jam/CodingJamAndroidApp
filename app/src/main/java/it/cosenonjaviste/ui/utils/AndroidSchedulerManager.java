package it.cosenonjaviste.ui.utils;

import it.cosenonjaviste.lib.rx.SchedulerManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndroidSchedulerManager extends SchedulerManager {

    public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
