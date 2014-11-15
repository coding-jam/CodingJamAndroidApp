package it.cosenonjaviste.lib.mvp;

import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ActivityContextBinder extends ContextBinder {

    private static Scheduler io = Schedulers.io();

    public static <T> Observable<T> background(Observable<T> observable) {
        return observable.subscribeOn(io).observeOn(mainThread());
    }

    public static void setIo(Scheduler io) {
        ActivityContextBinder.io = io;
    }

    public ActivityContextBinder(FragmentActivity activity) {
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return background(observable);
    }

    @Override public PresenterArgs createArgs() {
        return new BundlePresenterArgs();
    }
}
