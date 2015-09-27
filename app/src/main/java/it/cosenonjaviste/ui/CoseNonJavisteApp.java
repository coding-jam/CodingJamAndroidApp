package it.cosenonjaviste.ui;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import it.cosenonjaviste.BuildConfig;

public class CoseNonJavisteApp extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        LeakCanary.install(this);
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static ApplicationComponent getComponent(Fragment fragment) {
        return getComponent(fragment.getActivity());
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((CoseNonJavisteApp) context.getApplicationContext()).getComponent();
    }

    public void setComponent(ApplicationComponent component) {
        this.component = component;
    }
}
