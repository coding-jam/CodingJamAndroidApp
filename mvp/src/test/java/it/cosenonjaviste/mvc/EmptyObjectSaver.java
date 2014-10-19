package it.cosenonjaviste.mvc;

import it.cosenonjaviste.mvp.baseCnj.ObjectSaver;

/**
* Created by fabiocollini on 14/09/14.
*/
public class EmptyObjectSaver<M> implements ObjectSaver<M> {
    private M model;

    @Override public void saveInBundle(M model) {
        this.model = model;
    }

    @Override public M loadFromBundle() {
        return model;
    }
}
