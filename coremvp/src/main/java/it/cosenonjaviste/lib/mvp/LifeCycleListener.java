package it.cosenonjaviste.lib.mvp;

public interface LifeCycleListener<V> {
    void resume(V view);

    void pause();

    void detachView();

    void destroy();
}