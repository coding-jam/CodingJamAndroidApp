package it.cosenonjaviste.lib.mvp.dagger;

import dagger.ObjectGraph;

public interface ObjectGraphCreator {
    ObjectGraph create(DaggerApplication app);
}