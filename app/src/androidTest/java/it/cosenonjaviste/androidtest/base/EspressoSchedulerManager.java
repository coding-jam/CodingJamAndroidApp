package it.cosenonjaviste.androidtest.base;

import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class EspressoSchedulerManager extends SchedulerManager {

    private Scheduler scheduler = Schedulers.from(EspressoExecutor.newCachedThreadPool());

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable.subscribeOn(scheduler).observeOn(mainThread());
    }
}
