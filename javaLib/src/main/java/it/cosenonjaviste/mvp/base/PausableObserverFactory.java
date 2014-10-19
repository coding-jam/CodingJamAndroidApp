package it.cosenonjaviste.mvp.base;

import rx.Observer;

/**
 * Created by fabiocollini on 04/10/14.
 */
public interface PausableObserverFactory<P, T> {
    Observer<T> create(P item);
}
