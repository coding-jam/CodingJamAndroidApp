package it.cosenonjaviste.mvp;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;

@Singleton
public class TestSchedulerManager extends SchedulerManager {
    @Inject public TestSchedulerManager() {
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }
}
