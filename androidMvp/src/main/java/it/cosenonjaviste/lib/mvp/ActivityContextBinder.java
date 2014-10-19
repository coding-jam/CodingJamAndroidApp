package it.cosenonjaviste.lib.mvp;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.mvp.base.ContextBinder;
import rx.Observable;
import rx.Scheduler;
import rx.android.observables.AndroidObservable;
import rx.schedulers.Schedulers;

public class ActivityContextBinder implements ContextBinder {

    private static Scheduler io = Schedulers.io();

    public static <T> Observable<T> background(Activity activity, Observable<T> observable) {
        return AndroidObservable.bindActivity(activity, observable.subscribeOn(io));
    }

    public static <T> Observable<T> background(Object fragment, Observable<T> observable) {
        return AndroidObservable.bindFragment(fragment, observable.subscribeOn(io));
    }

    public static void setIo(Scheduler io) {
        ActivityContextBinder.io = io;
    }

    private FragmentActivity activity;

    public ActivityContextBinder(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return background(activity, observable);
    }
}
