package it.cosenonjaviste.mvp.base;

/**
 * Created by fabiocollini on 02/10/14.
 */
public interface LoadingListener<P, T> {
    void start(P param);

    void done(P param, T result);

    void error(P param, Throwable t);
}
