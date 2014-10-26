package it.cosenonjaviste.utils;

import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MapPresenterArgs;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import rx.Observable;

public class TestContextBinder extends ContextBinder {

    private ObjectGraph objectGraph;
    private RxMvpView<?> lastView;
    private RxMvpPresenter<?> lastPresenter;

    public TestContextBinder(Object testObject, Object... modules) {
        objectGraph = ObjectGraph.create(modules);
        objectGraph.inject(testObject);
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }

    @Override public void startNewActivity(Class<? extends MvpConfig<?, ?, ?>> config, PresenterArgs args) {
        createView(createConfig(config), args);
    }

    @Override public <T> T createView(MvpConfig<?, ?, ?> config, PresenterArgs args) {
        RxMvpView<?> view = config.createView();
        RxMvpPresenter<?> presenter = config.createAndInitPresenter(this, args);

        lastView = view;
        lastPresenter = presenter;
        return (T) view;
    }

    @Override public MvpConfig<?, ?, ?> createConfig(Class<? extends MvpConfig<?, ?, ?>> type) {
        return objectGraph.get(type);
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
