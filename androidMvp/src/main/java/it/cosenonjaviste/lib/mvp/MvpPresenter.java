package it.cosenonjaviste.lib.mvp;

public abstract class MvpPresenter<M> {
    protected M model;

    protected MvpView<M> view;

    public void init(M model) {
        this.model = model;
    }

    public void initAndSubscribe(M model, MvpView<M> view) {
        init(model);
        subscribe(view);
    }

    public M getModel() {
        return model;
    }

    public void destroy() {
    }

    public void subscribe(MvpView<M> view) {
        this.view = view;
        view.update(model);
    }

    public void pause() {
        view = null;
    }

    public MvpView<M> getView() {
        return view;
    }
}
