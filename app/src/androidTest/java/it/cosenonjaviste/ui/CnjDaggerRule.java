package it.cosenonjaviste.ui;

import android.support.test.InstrumentationRegistry;

import it.cosenonjaviste.androidtest.base.EspressoSchedulerManager;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;

public class CnjDaggerRule extends DaggerMockRule<ApplicationComponent> {
    public CnjDaggerRule() {
        super(ApplicationComponent.class, new AppModule(getApp()));
        override(SchedulerManager.class, new EspressoSchedulerManager());
        set(component -> getApp().setComponent(component));
    }

    public static CoseNonJavisteApp getApp() {
        return (CoseNonJavisteApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
