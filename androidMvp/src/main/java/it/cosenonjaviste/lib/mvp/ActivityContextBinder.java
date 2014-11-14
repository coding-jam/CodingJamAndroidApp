package it.cosenonjaviste.lib.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import dalvik.system.DexFile;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpView;
import it.cosenonjaviste.mvp.base.ViewImplementation;
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

    @Override public void startNewActivity(Class<? extends RxMvpView<?>> view, PresenterArgs args) {
        Class<? extends RxMvpView<?>> fragmentClass = getRxMvpViewClass(view);
        Intent intent = SingleFragmentActivity.createIntent(activity, fragmentClass);
        Bundle bundle = createArgs(args);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private Set<Class<?>> viewImplementations;

    protected Class<RxMvpView<?>> getRxMvpViewClass(Class<? extends RxMvpView<?>> view) {
        Class<RxMvpView<?>> ret = ConfigManager.singleton().get(view);
        if (ret == null) {
            if (viewImplementations == null) {
                loadViewImplementations();
            }
            for (Class<?> viewImplementation : viewImplementations) {
                if (view.isAssignableFrom(viewImplementation)) {
                    return (Class<RxMvpView<?>>) viewImplementation;
                }
            }
        }

        return ret;
    }

    private void loadViewImplementations() {
        viewImplementations = new HashSet<>();
        try {
            DexFile df = new DexFile(activity.getPackageCodePath());
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String s = iter.nextElement();
                if (s.startsWith("it.cosenonjaviste")) {
                    try {
                        Class<?> c = Class.forName(s);
                        if (c.isAnnotationPresent(ViewImplementation.class)) {
                            viewImplementations.add(c);
                        }
                    } catch (ClassNotFoundException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public <T> T createView(Class<? extends RxMvpView<?>> view, PresenterArgs args) {
        Class<? extends RxMvpView<?>> fragmentClass = getRxMvpViewClass(view);
        Fragment fragment = Fragment.instantiate(activity, fragmentClass.getName());
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
