package it.cosenonjaviste.mvp.base;

import java.util.concurrent.atomic.AtomicLong;

public abstract class MvpPresenter<M> {
    protected M model;

    protected ContextBinder contextBinder;

    private long id;

    private static AtomicLong sequence = new AtomicLong(1);

    protected MvpPresenter() {
        id = sequence.getAndIncrement();
    }

    public void init(ContextBinder contextBinder, M model) {
        this.model = model;
        this.contextBinder = contextBinder;
    }

    public abstract M createModel(PresenterArgs args);

    public long getId() {
        return id;
    }

    public M getModel() {
        return model;
    }

    public void destroy() {
    }
}
