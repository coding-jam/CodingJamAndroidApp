package it.cosenonjaviste;

import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.utils.ComponentBuilder;

public class CoseNonJavisteApp extends DaggerApplication {
    private AppComponent component;

    @Override public Object[] getModules() {
        return null;// new Object[]{new AppModule(this)};
    }

    @Override public void onCreate() {
        super.onCreate();
        component = ComponentBuilder.build(AppComponent.class, new AppModule(this));
    }

    public AppComponent getComponent() {
        return component;
    }
}
