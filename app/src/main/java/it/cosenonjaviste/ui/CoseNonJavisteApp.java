package it.cosenonjaviste.ui;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.LeakCanary;

import it.cosenonjaviste.lib.mvp.utils.ObjectsMapRetainedFragment;
import rx.functions.Func1;

public class CoseNonJavisteApp extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
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

    public static <C> C createComponent(Fragment fragment, Func1<ApplicationComponent, C> componentFactory) {
        return ObjectsMapRetainedFragment.getOrCreate(
                fragment.getChildFragmentManager(),
                () -> componentFactory.call(getComponent(fragment.getActivity()))
        );
    }
}
