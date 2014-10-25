package it.cosenonjaviste.lib.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import dagger.ObjectGraph;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import rx.Observable;
import rx.Scheduler;
import rx.android.observables.AndroidObservable;
import rx.functions.Action1;
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

    public void showInActivity(String fragmentClassName, Action1<PresenterArgs> argsAction) {
        if (activity instanceof MultiFragmentActivity) {
            Fragment fragment = instantiate(fragmentClassName, argsAction);
            ((MultiFragmentActivity) activity).showFragment(fragment);
        } else {
            throw new RuntimeException("Actvivity class " + activity.getClass().getName() + " must implement " + MultiFragmentActivity.class.getName());
        }
    }

    private Fragment instantiate(String fragmentClassName, Action1<PresenterArgs> argsAction) {
        return Fragment.instantiate(activity, fragmentClassName, createArgs(argsAction));
    }

    @Override public <M> void startNewActivity(Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass, Action1<PresenterArgs> argsAction) {
        Intent intent = SingleFragmentActivity.createIntent(activity, viewClass, presenterClass);
        Bundle bundle = createArgs(argsAction);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override public <T, M> T createFragment(Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass, Action1<PresenterArgs> argsAction) {
        Fragment fragment = (Fragment) getObject(viewClass);
        Bundle args = createArgs(argsAction);
        args.putString(SingleFragmentActivity.PRESENTER_CLASS, presenterClass.getName());
        fragment.setArguments(args);
        return (T) fragment;
    }

    private Bundle createArgs(Action1<PresenterArgs> argsAction) {
        Bundle args = new Bundle();
        if (argsAction != null) {
            BundlePresenterArgs bundleArgs = new BundlePresenterArgs(args);
            argsAction.call(bundleArgs);
        }
        return args;
    }

    @Override public <T> T getObject(Class<T> type) {
        ObjectGraph objectGraph = ObjectGraphHolder.getObjectGraph((DaggerApplication) activity.getApplication());
        return objectGraph.get(type);
    }
}
