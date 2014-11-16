package it.cosenonjaviste.mvp.base;

import it.cosenonjaviste.mvp.base.optional.OptionalList;

public interface MvpListView<M> extends MvpView<OptionalList<M>> {
    void startLoading();
}
