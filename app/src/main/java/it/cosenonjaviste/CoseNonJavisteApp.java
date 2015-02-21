package it.cosenonjaviste;

import android.app.Application;

public class CoseNonJavisteApp extends Application {

    public static ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = Dagger_ApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
