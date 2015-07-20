package it.cosenonjaviste.lib.mvp.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class TestSchedulerManager extends SchedulerManager {
    @Inject public TestSchedulerManager() {
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }
}
