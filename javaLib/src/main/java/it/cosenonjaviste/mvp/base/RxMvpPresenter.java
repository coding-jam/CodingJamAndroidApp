package it.cosenonjaviste.mvp.base;


import it.cosenonjaviste.mvp.base.events.EndLoadingModelEvent;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;
import rx.functions.Action2;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public abstract class RxMvpPresenter<M> extends RxMvpPausablePresenter<M> {

    private CompositeSubscription viewSubscriptions = new CompositeSubscription();

    private PublishSubject<ModelEvent<M>> modelUpdates = PublishSubject.create();

    protected void publish(ModelEvent<M> event) {
        modelUpdates.onNext(event);
    }

    public void subscribe(Action2<Observable<ModelEvent<M>>, CompositeSubscription> subscribeToModelUpdates) {
        if (subscribeToModelUpdates != null) {
            subscribeToModelUpdates.call(modelUpdates.asObservable(), viewSubscriptions);
        }

        pausableSubscriptions.resume();
        publish(new EndLoadingModelEvent<>(model));
        if (newModelCreated) {
            loadOnFirstStart();
            newModelCreated = false;
        }
    }

    public void pause() {
        viewSubscriptions.unsubscribe();
        viewSubscriptions = new CompositeSubscription();
        super.pause();
    }
}
