package it.cosenonjaviste.lib.mvp;


import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class RxMvpPresenter<M> extends MvpPresenter<M> {

    private final RxHolder rxHolder;

    public RxMvpPresenter(SchedulerManager schedulerManager) {
        rxHolder = new RxHolder(schedulerManager);
    }

    public void pause() {
        super.pause();
        rxHolder.pause();
    }

    @Override public void subscribe(MvpView<M> view) {
        super.subscribe(view);
        rxHolder.resubscribePendingObservable();
    }

    public void destroy() {
        rxHolder.destroy();
    }

    protected <T> void subscribe(Observable<T> observable, Action0 onAttach, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribePausable(observable, onAttach, onNext, onError, null);
    }
}