package it.cosenonjaviste.mvp.base;

public interface RxMvpView<M> {
    void update(M model);

    void open(Class<? extends RxMvpView<?>> viewClass, PresenterArgs args);
}
