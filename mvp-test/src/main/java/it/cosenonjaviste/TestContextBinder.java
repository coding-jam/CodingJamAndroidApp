package it.cosenonjaviste;

import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import it.cosenonjaviste.mvp.base.args.MapPresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public class TestContextBinder extends ContextBinder {

    private RxMvpView<?> lastView;
    private RxMvpPresenter<?> lastPresenter;
    private ConfigManager configManager;

    public TestContextBinder(ConfigManager configManager) {
        this.configManager = configManager;
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
