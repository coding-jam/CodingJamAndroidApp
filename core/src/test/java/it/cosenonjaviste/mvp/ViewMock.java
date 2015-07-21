package it.cosenonjaviste.mvp;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.mockito.verification.VerificationMode;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import it.cosenonjaviste.lib.mvp.LifeCycleListener;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.lib.mvp.utils.TestSchedulerManager;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ViewMock<V> {

    private V mock;

    private Map<Method, PublishSubject<?>> callbacks = new HashMap<>();

    private Class<V> viewClass;

    private TestSchedulerManager schedulerManager = new TestSchedulerManager();

    public ViewMock(Class<V> viewClass) {
        this.viewClass = viewClass;
        mock = Mockito.mock(viewClass, (Answer) invocation -> {
            if (invocation.getMethod().getReturnType().isAssignableFrom(Observable.class)) {
                Method method = invocation.getMethod();
                PublishSubject<Object> subject = PublishSubject.create();
                callbacks.put(method, subject);
                return subject;
            }
            return null;
        });
    }

    public V get() {
        return mock;
    }

    public V emit(Object value) {
        return Mockito.mock(viewClass, (Answer) invocation -> {
            PublishSubject subject = callbacks.get(invocation.getMethod());
            subject.onNext(value);
            return null;
        });
    }

    public V text(String value) {
        return emit(value);
    }

    public V click() {
        return emit(null);
    }

    public V verify() {
        return Mockito.verify(get());
    }

    public V verify(VerificationMode mode) {
        return Mockito.verify(get(), mode);
    }

    public <M> M initAndResume(RxMvpPresenter<M, V> presenter) {
        M model = presenter.createDefaultModel();
        if (model == null) {
            throw new RuntimeException("createDefaultModel not implemented in " + presenter.getClass().getName());
        }
        initAndResume(model, presenter);
        return model;
    }

    public <M> M initAndResume(M model, RxMvpPresenter<M, V> presenter) {
        presenter.initLifeCycle(schedulerManager);
        presenter.loadState(new LifeCycleListener.ObjectLoader() {
            @Override public <T> T load(String key) {
                return (T) model;
            }
        });
        presenter.resume(mock);
        return model;
    }
}
