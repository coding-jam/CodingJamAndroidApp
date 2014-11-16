package it.cosenonjaviste.lib.mvp.dagger;

import android.app.Application;

import dagger.ObjectGraph;

public abstract class DaggerApplication extends Application {
    @Override public void onCreate() {
        ObjectGraphHolder.setObjectGraphCreator(new ObjectGraphCreator() {
            @Override public ObjectGraph create(DaggerApplication app) {
                return ObjectGraph.create(app.getModules());
            }
        });
        super.onCreate();
    }

    public abstract Object[] getModules();
}
