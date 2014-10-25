package it.cosenonjaviste;

import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;

public class CoseNonJavisteApp extends DaggerApplication {
    @Override public Object[] getModules() {
        return new Object[]{new AppModule(this)};
    }
}
