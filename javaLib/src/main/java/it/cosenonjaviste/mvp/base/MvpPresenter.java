package it.cosenonjaviste.mvp.base;

import java.util.concurrent.atomic.AtomicLong;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;

public abstract class MvpPresenter<M> {
    protected M model;

    protected PresenterArgsFactory presenterArgsFactory;

    private long id;

    private static AtomicLong sequence = new AtomicLong(1);

    protected MvpPresenter(PresenterArgsFactory presenterArgsFactory) {
        this.presenterArgsFactory = presenterArgsFactory;
        id = sequence.getAndIncrement();
    }

    public void init(M model) {
        this.model = model;
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
