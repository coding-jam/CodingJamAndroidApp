package it.cosenonjaviste.mvp.base.optional;

import org.parceler.Transient;

import rx.functions.Action0;
import rx.functions.Action1;

public class OptionalItem<T> {

    @Transient
    T object;

    Throwable throwable;

    public OptionalItem() {
    }

    public OptionalItem(T object) {
        this.object = object;
    }

    public OptionalItem(T object, Throwable throwable) {
        this.object = object;
        this.throwable = throwable;
    }

    public T getObject() {
        return object;
    }

    public void done(T object) {
        throwable = null;
        this.object = object;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void error(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isEmpty() {
        return throwable == null && object == null;
    }

    public boolean isError() {
        return throwable != null;
    }

    public boolean isValueAvalaible() {
        return !isError() && !isEmpty();
    }

    public OptionalItem<T> call(Action1<T> action) {
        if (!isEmpty() && !isError()) {
            action.call(object);
        }
        return this;
    }

    public OptionalItem<T> whenEmpty(Action0 action) {
        if (isEmpty()) {
            action.call();
        }
        return this;
    }

    public OptionalItem<T> whenError(Action1<Throwable> action) {
        if (isError()) {
            action.call(throwable);
        }
        return this;
    }
}
