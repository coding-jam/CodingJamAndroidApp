package it.cosenonjaviste.ui;

import android.support.test.InstrumentationRegistry;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import it.cosenonjaviste.androidtest.base.EspressoExecutor;
import it.cosenonjaviste.androidtest.base.EspressoSchedulerManager;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

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

    @Override public Statement apply(Statement base, FrameworkMethod method, Object target) {
        Statement superStatement = super.apply(base, method, target);
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
                RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());
                RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());
                RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                    @Override public Scheduler getMainThreadScheduler() {
                        return Schedulers.from(EspressoExecutor.newCachedThreadPool());
                    }
                });
                try {
                    superStatement.evaluate();
                } finally {
                    RxJavaHooks.reset();
                    RxAndroidPlugins.getInstance().reset();
                }
            }
        };
    }
}
