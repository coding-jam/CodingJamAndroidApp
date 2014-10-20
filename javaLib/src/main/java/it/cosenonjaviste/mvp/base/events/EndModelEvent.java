package it.cosenonjaviste.mvp.base.events;

public abstract class EndModelEvent<M> extends ModelEvent<M> {
    public EndModelEvent(M model) {
        super(model);
    }

    public EndModelEvent(M model, Object extra) {
        super(model, extra);
    }

    @Override public boolean isStart() {
        return false;
    }

    @Override public boolean isEndOrError() {
        return true;
    }
}
