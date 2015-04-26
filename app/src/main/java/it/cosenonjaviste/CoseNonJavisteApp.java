package it.cosenonjaviste;

import android.app.Application;
import android.content.Context;

public class CoseNonJavisteApp extends Application {

    public static ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((CoseNonJavisteApp) context.getApplicationContext()).getComponent();
    }
}
