package it.cosenonjaviste.mvp.base;

import rx.Observable;
import rx.functions.Action1;

public interface ContextBinder {
    <T> Observable<T> bindObservable(Observable<T> observable);

    void showInActivity(String fragmentClassName, Action1<PresenterArgs> argsAction);

    <M> void startNewActivity(Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass, Action1<PresenterArgs> argsAction);

    <T> T getObject(Class<T> type);
}
