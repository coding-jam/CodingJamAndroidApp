package it.cosenonjaviste.utils;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import rx.Observable;
import rx.functions.Action1;

public class TestContextBinder implements ContextBinder {

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }

    @Override public void showInActivity(String fragmentClassName, Action1<PresenterArgs> argsAction) {
    }

    @Override public void startNewActivity(String fragmentClassName, Action1<PresenterArgs> argsAction) {

    }
}
