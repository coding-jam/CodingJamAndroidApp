package it.cosenonjaviste.mvp.baseCnj;

import rx.Observable;

/**
 * Created by fabiocollini on 14/09/14.
 */
public interface ContextBinder {
    <T> Observable<T> bindObservable(Observable<T> observable);
}
