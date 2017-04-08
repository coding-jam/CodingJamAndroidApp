package it.cosenonjaviste.ui;

import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;

public class CnjDaggerRule extends DaggerMockRule<ApplicationComponent> {
    public CnjDaggerRule() {
        super(ApplicationComponent.class, new AppModule(getApp()));
        providesMock(WordPressService.class, TwitterService.class);
        set(component -> getApp().setComponent(component));
    }

    public static CoseNonJavisteApp getApp() {
        return (CoseNonJavisteApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }

    @Override public Statement apply(Statement base, FrameworkMethod method, Object target) {
        Statement superStatement = super.apply(base, method, target);
        Scheduler asyncTaskScheduler =
                Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(
                        scheduler -> asyncTaskScheduler);
                RxJavaPlugins.setComputationSchedulerHandler(
                        scheduler -> asyncTaskScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(
                        scheduler -> asyncTaskScheduler);

                try {
                    superStatement.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                }
            }
        };
    }
}
