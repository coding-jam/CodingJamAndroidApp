package it.cosenonjaviste.mvp.base.events;

public class StartLoadingModelEvent<M> extends ModelEvent<M> {
    public StartLoadingModelEvent(M model) {
        super(model);
    }

    public StartLoadingModelEvent(M model, Object extra) {
        super(model, extra);
    }

    @Override public boolean isStart() {
        return true;
    }

    @Override public boolean isEndOrError() {
        return false;
    }
}
