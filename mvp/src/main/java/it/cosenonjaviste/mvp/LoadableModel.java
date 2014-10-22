package it.cosenonjaviste.mvp;

import org.parceler.Transient;

public class LoadableModel<T> {

    @Transient
    T object;

    Throwable throwable;

    public LoadableModel() {
        System.out.println(123);
    }

    public LoadableModel(T object) {
        this.object = object;
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
}
