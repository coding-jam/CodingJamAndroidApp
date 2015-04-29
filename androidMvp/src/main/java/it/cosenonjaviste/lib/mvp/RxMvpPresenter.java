package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;

public abstract class RxMvpPresenter {

    @Inject protected RxHolder rxHolder;

    public abstract void resume();

    @Inject public abstract void initLifeCycle(LifeCycle lifeCycle);
}