package it.cosenonjaviste.ui;

import android.support.test.InstrumentationRegistry;

import it.cosenonjaviste.androidtest.base.EspressoSchedulerManager;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;

public class CnjDaggerRule extends DaggerMockRule<ApplicationComponent> {
    public CnjDaggerRule() {
        super(ApplicationComponent.class, new AppModule(getApp()));
        provides(SchedulerManager.class, new EspressoSchedulerManager());
        providesMock(WordPressService.class, TwitterService.class);
        set(component -> getApp().setComponent(component));
    }

    public static CoseNonJavisteApp getApp() {
        return (CoseNonJavisteApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
