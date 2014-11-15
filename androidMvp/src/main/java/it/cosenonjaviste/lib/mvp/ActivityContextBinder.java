package it.cosenonjaviste.lib.mvp;

import android.app.Application;
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

    private FragmentActivity activity;

    private Application application;

    public ActivityContextBinder(FragmentActivity activity) {
        this.activity = activity;
        application = activity.getApplication();
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return background(observable);
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
        if (ret != null) {
            return ret;
        }
        if (viewImplementations == null) {
            loadViewImplementations();
        }
        for (Class<?> viewImplementation : viewImplementations) {
            if (view.isAssignableFrom(viewImplementation)) {
                return (Class<RxMvpView<?>>) viewImplementation;
            }
        }

        throw new RuntimeException("Unable to find implementation of " + view.getName() + " interface");
    }

    private void loadViewImplementations() {
        viewImplementations = new HashSet<>();
        try {
            DexFile df = new DexFile(application.getPackageCodePath());
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
        Fragment fragment = Fragment.instantiate(application, fragmentClass.getName());
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
