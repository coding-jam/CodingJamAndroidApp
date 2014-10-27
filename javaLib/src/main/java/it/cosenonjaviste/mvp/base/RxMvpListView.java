package it.cosenonjaviste.mvp.base;

import it.cosenonjaviste.mvp.base.optional.OptionalList;

public interface RxMvpListView<M> extends RxMvpView<OptionalList<M>> {
    void startLoading();
}
