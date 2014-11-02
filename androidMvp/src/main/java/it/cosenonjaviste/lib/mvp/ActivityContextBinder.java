package it.cosenonjaviste.lib.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpView;
import rx.Observable;
import rx.Scheduler;
import rx.android.observables.AndroidObservable;
import rx.schedulers.Schedulers;

public class ActivityContextBinder extends ContextBinder {

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

    @Override public void startNewActivity(MvpConfig<?, ?> config, PresenterArgs args) {
        Intent intent = SingleFragmentActivity.createIntent(activity, config.createView());
        Bundle bundle = createArgs(args);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override public <T> T createView(MvpConfig<?, ?> config, PresenterArgs args) {
        Class<? extends RxMvpView<?>> viewClass = config.createView();
        Fragment fragment = Fragment.instantiate(activity, viewClass.getName());
        Bundle bundle = createArgs(args);
        fragment.setArguments(bundle);
        return (T) fragment;
    }

    private Bundle createArgs(PresenterArgs args) {
        if (args != null) {
            return ((BundlePresenterArgs) args).getBundle();
        } else {
            return new Bundle();
        }
    }

    @Override public PresenterArgs createArgs() {
        return new BundlePresenterArgs();
    }
}
