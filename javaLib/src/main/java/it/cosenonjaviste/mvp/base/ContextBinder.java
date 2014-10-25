package it.cosenonjaviste.mvp.base;

import rx.Observable;

public interface ContextBinder {
    <T> Observable<T> bindObservable(Observable<T> observable);

    void showInActivity(String fragmentClassName, PresenterArgs args);

    void startNewActivity(Class<? extends MvpConfig<?, ?, ?>> config, PresenterArgs args);

    <T> T createFragment(MvpConfig<?, ?, ?> config, PresenterArgs args);

    <T> T getObject(Class<T> type);

    PresenterArgs createArgs();
}
