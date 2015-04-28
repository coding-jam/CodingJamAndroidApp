package it.cosenonjaviste.lib.mvp;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.subjects.PublishSubject;

@PresenterScope
public class LifeCycle {

    private Func0<Object> saveObjectFunc;

    public enum EventType {
        RESUME, PAUSE, DESTROY_VIEW, DESTROY_ALL;
    }

    private PublishSubject<EventType> subject = PublishSubject.create();

    @Inject public LifeCycle() {
    }

    public void emit(EventType eventType) {
        subject.onNext(eventType);
    }

    public Observable<EventType> asObservable() {
        return subject.asObservable();
    }

    public Subscription subscribe(EventType eventType, Action0 callback) {
        return subject.asObservable().filter(t -> t == eventType).subscribe(t -> callback.call());
    }

    public void subscribeOnSaveInstanceState(Func0<Object> saveObjectFunc) {
        this.saveObjectFunc = saveObjectFunc;
    }

    public void saveInstanceState(Action1<Object> saver) {
        if (saveObjectFunc != null) {
            Object obj = saveObjectFunc.call();
            if (obj != null) {
                saver.call(obj);
            }
        }
    }
}
