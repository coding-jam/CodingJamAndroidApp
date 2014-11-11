package it.cosenonjaviste.lib.mvp.dagger;

import android.app.Application;


public abstract class DaggerApplication<T> extends Application {
    @Override public void onCreate() {
        ObjectGraphHolder.setObjectGraphCreator(new ObjectGraphCreator() {
            @Override public T create(DaggerApplication app) {
                return null;//ObjectGraph.create(app.getModules());
            }
        });
        super.onCreate();
    }

    public abstract Object[] getModules();
}
