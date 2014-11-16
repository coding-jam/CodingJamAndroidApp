package it.cosenonjaviste.mvp.base;

import java.util.HashMap;
import java.util.Map;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public class ConfigManager {

    private static ConfigManager singleton = new ConfigManager();

    private Map<Class<? extends RxMvpView<?>>, Class<?>> viewClasses = new HashMap<>();

    private Map<Class<? extends RxMvpView<?>>, PresenterFactory<?>> presenterCreators = new HashMap<>();

//    private Set<Class<?>> viewImplementations;

    private ConfigManager() {
    }

    public static ConfigManager singleton() {
        return singleton;
    }

    public <M, P extends RxMvpPresenter<M>> P createAndInitPresenter(Class<? extends RxMvpView<?>> viewClass, PresenterArgs args) {
        return this.<M, P>createAndInitPresenter(viewClass, null, null, args);
    }

    public <M, P extends RxMvpPresenter<M>> P createAndInitPresenter(Class<? extends RxMvpView<?>> viewClass, M restoredModel, P restoredPresenter, PresenterArgs args) {
        P presenter;
        if (restoredPresenter == null) {
            presenter = this.<M, P>createPresenter(viewClass);
        } else {
            presenter = restoredPresenter;
        }
        M model;
        if (restoredModel != null) {
            model = restoredModel;
        } else {
            model = presenter.createModel(args != null ? args : PresenterArgs.EMPTY);
        }
        presenter.init(model);
        return presenter;
    }

    public ConfigManager register(Class<? extends RxMvpView<?>> key, Class<?> value) {
        viewClasses.put(key, value);
        return this;
    }

    public <T> Class<T> get(Class<? extends RxMvpView<?>> view) {
        Class<T> ret = (Class<T>) viewClasses.get(view);
        if (ret == null) {
//            if (viewImplementations == null) {
//                loadViewImplementations();
//            }
//            for (Class<?> viewImplementation : viewImplementations) {
//                if (view.isAssignableFrom(viewImplementation)) {
//                    return (Class<T>) viewImplementation;
//                }
//            }

            throw new RuntimeException("Unable to find implementation of " + view.getName() + " interface");
        }
        return ret;
    }

//    private void loadViewImplementations() {
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
//    }

    public <P extends RxMvpPresenter<?>> void registerPresenter(Class<? extends RxMvpView<?>> key, PresenterFactory<P> value) {
        presenterCreators.put(key, value);
    }

    private <M, P extends RxMvpPresenter<M>> P createPresenter(Class<? extends RxMvpView<?>> key) {
        return (P) presenterCreators.get(key).create();
    }

    public interface PresenterFactory<P extends RxMvpPresenter<?>> {
        P create();
    }
}
