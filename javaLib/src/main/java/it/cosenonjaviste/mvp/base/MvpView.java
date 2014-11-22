package it.cosenonjaviste.mvp.base;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public interface MvpView<M> {
    void update(M model);

    void open(Class<? extends MvpView<?>> viewClass, PresenterArgs args);

    PresenterArgs createArgs();
}
