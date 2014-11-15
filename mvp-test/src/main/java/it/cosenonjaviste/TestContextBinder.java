package it.cosenonjaviste;

import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MapPresenterArgs;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import rx.Observable;

public class TestContextBinder extends ContextBinder {

    private RxMvpView<?> lastView;
    private RxMvpPresenter<?> lastPresenter;
    private ConfigManager configManager;

    public TestContextBinder(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }

    public <V> V getLastView() {
        return (V) lastView;
    }

    public <M> M getLastModel() {
        return (M) lastPresenter.getModel();
    }

    public <P> P getLastPresenter() {
        return (P) lastPresenter;
    }

    @Override public PresenterArgs createArgs() {
        return new MapPresenterArgs();
    }
}
