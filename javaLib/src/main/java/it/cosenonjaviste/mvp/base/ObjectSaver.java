package it.cosenonjaviste.mvp.base;

/**
 * Created by fabiocollini on 14/09/14.
 */
public interface ObjectSaver<M> {
    void saveInBundle(M model);

    M loadFromBundle();
}
