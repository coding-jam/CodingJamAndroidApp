package it.cosenonjaviste.lib.mvp;


import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M, V> {

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

    @Inject public final void initLifeCycle(LifeCycle lifeCycle, SchedulerManager schedulerManager) {
        lifeCycle.asObservable()
                .filter(e -> e.getType() == LifeCycle.EventType.RESUME)
                .subscribe(e -> {
                    view = (V) e.getSource();
                    resume();
                });
        lifeCycle.subscribe(LifeCycle.EventType.DESTROY_VIEW, () -> this.view = null);
        lifeCycle.subscribeState(saver -> saver.call(MODEL, getModel()), loader -> {
            model = (M) loader.call(MODEL);
            if (model == null) {
                model = createDefaultModel();
            }
        });

        rxHolder = new RxHolder(schedulerManager, lifeCycle);
    }

    public final V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }

    public <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onAttach, onNext, onError);
    }

    public boolean isTaskRunning() {
        return rxHolder.isTaskRunning();
    }
}