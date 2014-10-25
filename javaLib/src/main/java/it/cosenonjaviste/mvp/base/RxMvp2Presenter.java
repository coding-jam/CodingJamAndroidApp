package it.cosenonjaviste.mvp.base;


public abstract class RxMvp2Presenter<M, V extends RxMvpView<M>> extends RxMvpPausablePresenter<M> {

    protected V view;

    public void subscribe(V view) {
        this.view = view;
        pausableSubscriptions.resume();
        view.update(model);
        if (newModelCreated) {
            loadOnFirstStart();
            newModelCreated = false;
        }
    }

    @Override public void pause() {
        view = null;
        super.pause();
    }
}
