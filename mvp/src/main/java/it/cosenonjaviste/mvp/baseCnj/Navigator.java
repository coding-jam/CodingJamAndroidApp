package it.cosenonjaviste.mvp.baseCnj;

import rx.functions.Action1;

public interface Navigator {
    void show(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction);

    void open(String url);
}
