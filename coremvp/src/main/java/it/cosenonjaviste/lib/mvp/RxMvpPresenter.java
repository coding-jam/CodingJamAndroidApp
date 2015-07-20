package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M, V> implements LifeCycleListener<V>, InstanceStateListener {

    public static final String MODEL = "model";

    private RxHolder rxHolder;

    private M model;

    private V view;

    public void resume() {
        rxHolder.resubscribePendingObservable();
    }

    public M createDefaultModel() {
        return null;
    }

    @Inject public final void initLifeCycle(SchedulerManager schedulerManager) {
        rxHolder = new RxHolder(schedulerManager);
    }

    @Override public void saveState(ObjectSaver saver) {
        saver.save(MODEL, getModel());
    }

    @Override public void loadState(ObjectLoader loader) {
        model = loader.load(MODEL);
        if (model == null) {
            model = createDefaultModel();
        }
    }

    @Override public void resume(V view) {
        this.view = view;
        resume();
    }

    @Override public void pause() {
        rxHolder.pause();
    }

    @Override public void detachView() {
        this.view = null;
    }

    @Override public void destroy() {
        rxHolder.destroy();
    }

    public final V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onNext, onError);
    }

    public boolean isTaskRunning() {
        return rxHolder.isTaskRunning();
    }
}