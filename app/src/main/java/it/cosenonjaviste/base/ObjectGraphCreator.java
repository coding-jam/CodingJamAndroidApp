package it.cosenonjaviste.base;

import dagger.ObjectGraph;

public interface ObjectGraphCreator {
    ObjectGraph create(DaggerApplication app);
}