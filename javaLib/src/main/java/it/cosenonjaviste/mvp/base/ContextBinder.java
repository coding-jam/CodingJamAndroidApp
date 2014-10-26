package it.cosenonjaviste.mvp.base;

import rx.Observable;

public abstract class ContextBinder {
    public abstract <T> Observable<T> bindObservable(Observable<T> observable);

    public abstract void startNewActivity(Class<? extends MvpConfig<?, ?, ?>> config, PresenterArgs args);

    public abstract <T> T createView(MvpConfig<?, ?, ?> config, PresenterArgs args);

    public MvpConfig<?, ?, ?> createConfig(String configClass) {
        return createConfig(getType(configClass));
    }

    private Class<MvpConfig<?, ?, ?>> getType(String className) {
        try {
            return (Class<MvpConfig<?, ?, ?>>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public abstract MvpConfig<?, ?, ?> createConfig(Class<? extends MvpConfig<?, ?, ?>> type);

    public abstract PresenterArgs createArgs();
}
