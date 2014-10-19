package it.cosenonjaviste.mvp.base;

import rx.functions.Action1;

public interface Navigator {
    void show(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction);

    <T> T createFragment(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction);

    void open(String url);
}
