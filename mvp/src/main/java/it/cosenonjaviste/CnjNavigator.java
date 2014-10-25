package it.cosenonjaviste;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import rx.functions.Action1;

@Singleton
public class CnjNavigator implements Navigator {

    @Inject
    public CnjNavigator() {
    }

    @Override public <M> void show(ContextBinder contextBinder, Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass, Action1<PresenterArgs> argsAction) {
        contextBinder.startNewActivity(viewClass, presenterClass, argsAction);
    }

    @Override public <T> T createFragment(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction) {
        return null;
    }

    @Override public void open(String url) {

    }
}
