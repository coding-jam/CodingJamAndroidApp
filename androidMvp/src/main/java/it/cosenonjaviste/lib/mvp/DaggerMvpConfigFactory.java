package it.cosenonjaviste.lib.mvp;

import android.app.Application;

import javax.inject.Inject;

import dagger.ObjectGraph;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.MvpConfigFactory;

public class DaggerMvpConfigFactory extends MvpConfigFactory {

    private Application application;

    @Inject public DaggerMvpConfigFactory(Application application) {
        this.application = application;
    }

    @Override public MvpConfig<?, ?, ?> createConfig(Class<? extends MvpConfig<?, ?, ?>> type) {
        ObjectGraph objectGraph = ObjectGraphHolder.getObjectGraph((DaggerApplication) application);
        return objectGraph.get(type);
    }
}
