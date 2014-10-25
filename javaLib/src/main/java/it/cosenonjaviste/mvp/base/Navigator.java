package it.cosenonjaviste.mvp.base;

import rx.functions.Action1;

public interface Navigator {
    <M> void show(ContextBinder contextBinder, Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass, Action1<PresenterArgs> argsAction);

    <T, M> T createFragment(ContextBinder contextBinder, Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass, Action1<PresenterArgs> argsAction);

    void open(String url);
}
