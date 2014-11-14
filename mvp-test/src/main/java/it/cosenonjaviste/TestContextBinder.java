package it.cosenonjaviste;

import org.mockito.Mockito;

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

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }

    @Override public void startNewActivity(Class<? extends RxMvpView<?>> view, PresenterArgs args) {
        createView(view, args);
    }

    @Override public <T> T createView(Class<? extends RxMvpView<?>> view, PresenterArgs args) {
        Class<? extends RxMvpView<?>> viewClass = ConfigManager.singleton().get(view);
        RxMvpPresenter presenter = ConfigManager.createAndInitPresenter(view, this, args);

        lastView = Mockito.mock(viewClass);
        presenter.subscribe(lastView);
        lastPresenter = presenter;
        return (T) lastView;
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
