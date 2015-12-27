package it.cosenonjaviste.androidtest.base;

import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class EspressoSchedulerManager implements SchedulerManager {

    private Scheduler scheduler = Schedulers.from(EspressoExecutor.newCachedThreadPool());

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable.subscribeOn(scheduler).observeOn(mainThread());
    }

    @Override public void logException(Throwable throwable) {
        throwable.printStackTrace();
    }
}
