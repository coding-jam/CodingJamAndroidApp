package it.cosenonjaviste.androidtest.dagger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import it.cosenonjaviste.ui.CoseNonJavisteApp;

public class DaggerUtils {
    public static TestComponent createTestComponent() {
        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestComponent component = DaggerTestComponent.builder().build();
        ((CoseNonJavisteApp) app).setComponent(component);
        return component;
    }
}
