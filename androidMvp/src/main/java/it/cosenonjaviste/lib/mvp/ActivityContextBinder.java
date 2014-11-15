package it.cosenonjaviste.lib.mvp;

import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import rx.Observable;

public class ActivityContextBinder extends ContextBinder {

    public ActivityContextBinder(FragmentActivity activity) {
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return SchedulerManager.background(observable);
    }

    @Override public PresenterArgs createArgs() {
        return new BundlePresenterArgs();
    }
}
