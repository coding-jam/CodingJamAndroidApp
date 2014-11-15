package it.cosenonjaviste.mvp.base;


public abstract class RxMvpPresenter<M> extends RxMvpPausablePresenter<M> {

    protected RxMvpView<M> view;

    public RxMvpPresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    public void subscribe(RxMvpView<M> view) {
        this.view = view;
        view.update(model);
        pausableSubscriptions.resume();
    }

    @Override public void pause() {
        view = null;
        super.pause();
    }

    public RxMvpView<M> getView() {
        return view;
    }
}