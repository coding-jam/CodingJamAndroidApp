package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;

public abstract class RxMvpPresenter<M, V> {

    @Inject protected RxHolder rxHolder;

    protected M model;

    protected V view;

    public abstract void resume();

    public final void init(M model, V view) {
        this.model = model;
        this.view = view;
    }

    @Inject public final void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }

    public final V getView() {
        return view;
    }
}