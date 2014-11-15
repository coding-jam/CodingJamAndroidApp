package it.cosenonjaviste.mvp.base;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public interface RxMvpView<M> {
    void update(M model);

    void open(Class<? extends RxMvpView<?>> viewClass, PresenterArgs args);
}
