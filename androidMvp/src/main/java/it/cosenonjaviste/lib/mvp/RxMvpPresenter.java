package it.cosenonjaviste.lib.mvp;


import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M> {

    private final RxHolder rxHolder;
    protected M model;
    protected MvpView<M> view;

    public RxMvpPresenter(SchedulerManager schedulerManager) {
        rxHolder = new RxHolder(schedulerManager);
    }

    public void pause() {
        rxHolder.pause();
    }

    public void subscribe() {
        view.update(model);
        rxHolder.resubscribePendingObservable();
    }

    public void destroy() {
        rxHolder.destroy();
    }

    protected <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribePausable(observable, onAttach, onNext, onError, null);
    }

    public void init(M model, MvpView<M> view) {
        this.model = model;
        this.view = view;
    }

    public void initAndSubscribe(M model, MvpView<M> view) {
        init(model, view);
        subscribe();
    }

    public M getModel() {
        return model;
    }

    public MvpView<M> getView() {
        return view;
    }
}