package it.cosenonjaviste.mvp.base;

import rx.functions.Action1;

public interface Navigator {
    void show(ContextBinder contextBinder, Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction);

    <T> T createFragment(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction);

    void open(String url);
}
