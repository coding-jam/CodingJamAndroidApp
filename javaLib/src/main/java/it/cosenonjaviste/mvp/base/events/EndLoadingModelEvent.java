package it.cosenonjaviste.mvp.base.events;

public class EndLoadingModelEvent<M> extends EndModelEvent<M> {
    public EndLoadingModelEvent(M model) {
        super(model);
    }

    public EndLoadingModelEvent(M model, Object extra) {
        super(model, extra);
    }
}
