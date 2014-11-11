package it.cosenonjaviste.lib.mvp.dagger;

public interface ObjectGraphCreator<T> {
    T create(DaggerApplication app);
}