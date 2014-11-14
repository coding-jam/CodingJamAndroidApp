package it.cosenonjaviste.mvp.base;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func0;

public class ConfigManager {

    private static ConfigManager singleton = new ConfigManager();

    private Map<Class<? extends RxMvpView<?>>, Class<?>> viewClasses = new HashMap<>();

    private Map<Class<? extends RxMvpView<?>>, Func0<? extends RxMvpPresenter<?>>> presenterCreators = new HashMap<>();

    private ConfigManager() {
    }

    public static ConfigManager singleton() {
        return singleton;
    }

    public static <P extends RxMvpPresenter<?>> P createAndInitPresenter(Class<? extends RxMvpView<?>> viewClass, ContextBinder contextBinder, PresenterArgs args) {
        return createAndInitPresenter(viewClass, contextBinder, null, null, args);
    }

    public static <M, P extends RxMvpPresenter<?>> P createAndInitPresenter(Class<? extends RxMvpView<?>> viewClass, ContextBinder contextBinder, M restoredModel, P restoredPresenter, PresenterArgs args) {
        P presenter;
        if (restoredPresenter == null) {
            presenter = singleton().createPresenter(viewClass);
        } else {
            presenter = restoredPresenter;
        }
        M model;
        if (restoredModel != null) {
            model = restoredModel;
        } else {
            model = (M) presenter.createModel(args != null ? args : PresenterArgs.EMPTY);
        }
        ((RxMvpPresenter) presenter).init(contextBinder, model);
        return presenter;
    }

    public void register(Class<? extends RxMvpView<?>> key, Class<?> value) {
        viewClasses.put(key, value);
    }

    public <T> Class<T> get(Class<? extends RxMvpView<?>> key) {
        Class<T> ret = (Class<T>) viewClasses.get(key);
        if (ret == null) {
            return null;
        }
        return ret;
    }

    public <P extends RxMvpPresenter<?>> void registerPresenter(Class<? extends RxMvpView<?>> key, Func0<P> value) {
        presenterCreators.put(key, value);
    }

    public <P extends RxMvpPresenter<?>> P createPresenter(Class<? extends RxMvpView<?>> key) {
        return (P) presenterCreators.get(key).call();
    }
}
