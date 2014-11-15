package it.cosenonjaviste.mvp.base;


import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;

public abstract class RxMvpPresenter<M> extends RxMvpPausablePresenter<M> {

    protected RxMvpView<M> view;

    public RxMvpPresenter(SchedulerManager schedulerManager, PresenterArgsFactory presenterArgsFactory) {
        super(schedulerManager, presenterArgsFactory);
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