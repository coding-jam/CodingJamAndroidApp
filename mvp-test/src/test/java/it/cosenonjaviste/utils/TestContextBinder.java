package it.cosenonjaviste.utils;

import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MapPresenterArgs;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import rx.Observable;
import rx.functions.Action1;

public class TestContextBinder implements ContextBinder {

    private ObjectGraph objectGraph;
    private RxMvpView<?> lastView;
    private RxMvpPresenter<?> lastPresenter;

    public TestContextBinder(ObjectGraph objectGraph) {
        this.objectGraph = objectGraph;
    }

    @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
        return observable;
    }

    @Override public void showInActivity(String fragmentClassName, Action1<PresenterArgs> argsAction) {
    }

    @Override public void startNewActivity(Class<? extends MvpConfig<?, ?, ?>> config, Action1<PresenterArgs> argsAction) {
        createFragment(getObject(config), argsAction);
    }

    @Override public <T> T createFragment(MvpConfig<?, ?, ?> config, Action1<PresenterArgs> argsAction) {
        RxMvpView<?> view = config.createView();
        RxMvpPresenter<?> presenter = config.createPresenter();
        presenter.init(this, null, getArgs(argsAction));

        lastView = view;
        lastPresenter = presenter;
        return (T) view;
    }

    private MapPresenterArgs getArgs(Action1<PresenterArgs> argsAction) {
        MapPresenterArgs args = new MapPresenterArgs();
        if (argsAction != null) {
            argsAction.call(args);
        }
        return args;
    }


    @Override public <T> T getObject(Class<T> type) {
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
}
