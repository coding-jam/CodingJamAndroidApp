package it.cosenonjaviste;

import javax.inject.Inject;

import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.MvpConfigFactory;

public class TestMvpConfigFactory extends MvpConfigFactory {

    private ObjectGraph objectGraph;

    @Inject public TestMvpConfigFactory() {
    }

    @Override public MvpConfig<?, ?, ?> createConfig(Class<? extends MvpConfig<?, ?, ?>> type) {
        return objectGraph.get(type);
    }

    public void init(ObjectGraph objectGraph) {
        this.objectGraph = objectGraph;
    }
}
