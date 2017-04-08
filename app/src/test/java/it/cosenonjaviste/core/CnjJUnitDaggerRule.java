package it.cosenonjaviste.core;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.AppModule;
import it.cosenonjaviste.ui.ApplicationComponent;

public class CnjJUnitDaggerRule extends DaggerMockRule<ApplicationComponent> {
    public CnjJUnitDaggerRule() {
        super(ApplicationComponent.class, new AppModule(null));
        providesMock(WordPressService.class, TwitterService.class);
    }

    @Override public Statement apply(Statement base, FrameworkMethod method, Object target) {
        Statement superStatement = super.apply(base, method, target);
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(
                        scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(
                        scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(
                        scheduler -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                        scheduler -> Schedulers.trampoline());

                try
                {
                    superStatement.evaluate();
                } finally
                {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
