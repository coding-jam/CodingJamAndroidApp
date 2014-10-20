package it.cosenonjaviste.utils;

import it.cosenonjaviste.mvp.base.ObjectSaver;

public class EmptyObjectSaver<M> implements ObjectSaver<M> {
    private M model;

    @Override public void saveInBundle(M model) {
        this.model = model;
    }

    @Override public M loadFromBundle() {
        return model;
    }
}
