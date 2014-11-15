package it.cosenonjaviste.mvp.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import rx.functions.Func0;

public class ConfigManager {

    private static ConfigManager singleton = new ConfigManager();

    private Map<Class<? extends RxMvpView<?>>, Class<?>> viewClasses = new HashMap<>();

    private Map<Class<? extends RxMvpView<?>>, Func0<? extends RxMvpPresenter<?>>> presenterCreators = new HashMap<>();

    private Set<Class<?>> viewImplementations;

    private ConfigManager() {
    }

    public static ConfigManager singleton() {
        return singleton;
    }

    public <P extends RxMvpPresenter<?>> P createAndInitPresenter(Class<? extends RxMvpView<?>> viewClass, PresenterArgs args) {
        return createAndInitPresenter(viewClass, null, null, args);
    }

    public <M, P extends RxMvpPresenter<?>> P createAndInitPresenter(Class<? extends RxMvpView<?>> viewClass, M restoredModel, P restoredPresenter, PresenterArgs args) {
        P presenter;
        if (restoredPresenter == null) {
            presenter = createPresenter(viewClass);
        } else {
            presenter = restoredPresenter;
        }
        M model;
        if (restoredModel != null) {
            model = restoredModel;
        } else {
            model = (M) presenter.createModel(args != null ? args : PresenterArgs.EMPTY);
        }
        ((RxMvpPresenter) presenter).init(model);
        return presenter;
    }

    public ConfigManager register(Class<? extends RxMvpView<?>> key, Class<?> value) {
        viewClasses.put(key, value);
        return this;
    }

    public <T> Class<T> get(Class<? extends RxMvpView<?>> view) {
        Class<T> ret = (Class<T>) viewClasses.get(view);
        if (ret == null) {
            if (viewImplementations == null) {
                loadViewImplementations();
            }
            for (Class<?> viewImplementation : viewImplementations) {
                if (view.isAssignableFrom(viewImplementation)) {
                    return (Class<T>) viewImplementation;
                }
            }

            throw new RuntimeException("Unable to find implementation of " + view.getName() + " interface");
        }
        return ret;
    }

    private void loadViewImplementations() {
//        viewImplementations = new HashSet<>();
//        try {
//            DexFile df = new DexFile(application.getPackageCodePath());
//            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
//                String s = iter.nextElement();
//                if (s.startsWith("it.cosenonjaviste")) {
//                    try {
//                        Class<?> c = Class.forName(s);
//                        if (c.isAnnotationPresent(ViewImplementation.class)) {
//                            viewImplementations.add(c);
//                        }
//                    } catch (ClassNotFoundException ignored) {
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public <P extends RxMvpPresenter<?>> void registerPresenter(Class<? extends RxMvpView<?>> key, Func0<P> value) {
        presenterCreators.put(key, value);
    }

    private <P extends RxMvpPresenter<?>> P createPresenter(Class<? extends RxMvpView<?>> key) {
        return (P) presenterCreators.get(key).call();
    }
}
