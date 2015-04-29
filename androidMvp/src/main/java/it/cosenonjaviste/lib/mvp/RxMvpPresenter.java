package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;

public abstract class RxMvpPresenter<M> {

    @Inject protected RxHolder rxHolder;
    protected MvpView<M> view;

    @Inject void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.subscribe(LifeCycle.EventType.RESUME, this::resume);
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
    }

    public abstract void resume();

    public MvpView<M> getView() {
        return view;
    }
}