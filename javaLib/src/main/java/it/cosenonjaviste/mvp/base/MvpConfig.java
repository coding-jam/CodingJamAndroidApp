package it.cosenonjaviste.mvp.base;

public interface MvpConfig<M, V extends RxMvpView<M>, P extends RxMvpPresenter<M>> {
    V createView();

    P createPresenter();
}
