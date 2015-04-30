package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;

public abstract class RxMvpPresenter<M, V> {

    @Inject protected RxHolder rxHolder;

    protected M model;

    protected V view;

    public abstract void resume();

    @Inject public abstract void initLifeCycle(LifeCycle lifeCycle);

    public final V getView() {
        return view;
    }
}