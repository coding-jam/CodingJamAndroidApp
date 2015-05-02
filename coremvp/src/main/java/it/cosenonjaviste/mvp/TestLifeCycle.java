package it.cosenonjaviste.mvp;

import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;

public class TestLifeCycle extends LifeCycle {

    private TestSchedulerManager schedulerManager = new TestSchedulerManager();

    public <M, V> void initAndResume(M model, RxMvpPresenter<M, V> presenter, V view) {
        presenter.initLifeCycle(this, schedulerManager);
        loadState(key -> model);
        emit(view, EventType.RESUME);
    }
}
