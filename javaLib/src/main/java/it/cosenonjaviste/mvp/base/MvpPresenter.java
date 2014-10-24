package it.cosenonjaviste.mvp.base;

import java.util.concurrent.atomic.AtomicLong;

public abstract class MvpPresenter<M> {
    protected M model;

    protected ContextBinder contextBinder;

    protected boolean newModelCreated;

    private long id;

    private static AtomicLong sequence = new AtomicLong(1);

    protected MvpPresenter() {
        id = sequence.getAndIncrement();
    }

    public M init(ContextBinder contextBinder, M restoredModel, PresenterArgs args) {
        this.contextBinder = contextBinder;
        if (restoredModel != null) {
            model = restoredModel;
        } else {
            newModelCreated = true;
            model = createModel(args);
        }
        return model;
    }

    protected void loadOnFirstStart() {
    }

    protected abstract M createModel(PresenterArgs args);

    public long getId() {
        return id;
    }

    public M getModel() {
        return model;
    }
}
