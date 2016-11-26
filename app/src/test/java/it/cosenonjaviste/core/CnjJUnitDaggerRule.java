package it.cosenonjaviste.core;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.AppModule;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class CnjJUnitDaggerRule extends DaggerMockRule<TestApplicationComponent> {
    public CnjJUnitDaggerRule() {
        super(TestApplicationComponent.class, new AppModule(null));
        providesMock(WordPressService.class, TwitterService.class);
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
                        return Schedulers.immediate();
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
