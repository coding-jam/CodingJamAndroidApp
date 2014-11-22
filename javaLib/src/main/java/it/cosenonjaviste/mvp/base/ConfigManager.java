package it.cosenonjaviste.mvp.base;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static ConfigManager singleton = new ConfigManager();

    private Map<Class<? extends MvpView<?>>, Class<?>> viewClasses = new HashMap<>();

    private Map<Class<? extends MvpView<?>>, PresenterFactory<?>> presenterCreators = new HashMap<>();

    private ConfigManager() {
    }

    public static ConfigManager singleton() {
        return singleton;
    }

    public <M, P extends MvpPresenter<M>> P createAndInitPresenter(Class<? extends MvpView<?>> viewClass, M restoredModel, P restoredPresenter) {
        P presenter;
        if (restoredPresenter == null) {
            presenter = this.<M, P>createPresenter(viewClass);
        } else {
            presenter = restoredPresenter;
        }
        presenter.init(restoredModel);
        return presenter;
    }

    public <V extends MvpView<?>> ConfigManager register(Class<V> key, Class<? extends V> value) {
        viewClasses.put(key, value);
        return this;
    }

    public <T> Class<T> get(Class<? extends MvpView<?>> view) {
        Class<T> ret = (Class<T>) viewClasses.get(view);
        if (ret == null) {
            throw new RuntimeException("Unable to find implementation of " + view.getName() + " interface");
        }
        return ret;
    }

    public <M, P extends RxMvpPresenter<M>> void registerPresenter(Class<? extends MvpView<M>> key, PresenterFactory<P> value) {
        presenterCreators.put(key, value);
    }

    private <M, P extends MvpPresenter<M>> P createPresenter(Class<? extends MvpView<?>> key) {
        return (P) presenterCreators.get(key).create();
    }

    public interface PresenterFactory<P extends RxMvpPresenter<?>> {
        P create();
    }
}
