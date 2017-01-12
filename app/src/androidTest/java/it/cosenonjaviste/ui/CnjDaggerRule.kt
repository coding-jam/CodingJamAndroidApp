package it.cosenonjaviste.ui

import android.support.test.InstrumentationRegistry
import it.cosenonjaviste.androidtest.base.EspressoExecutor
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.cosenonjaviste.model.TwitterService
import it.cosenonjaviste.model.WordPressService
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaHooks
import rx.schedulers.Schedulers

class CnjDaggerRule : DaggerMockRule<ApplicationComponent>(ApplicationComponent::class.java, AppModule(app)) {
    init {
        providesMock(WordPressService::class.java, TwitterService::class.java)
        set { component -> app.component = component }
    }

    override fun apply(base: Statement, method: FrameworkMethod?, target: Any): Statement {
        val superStatement = super.apply(base, method, target)
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaHooks.setOnIOScheduler { scheduler -> Schedulers.immediate() }
                RxJavaHooks.setOnComputationScheduler { scheduler -> Schedulers.immediate() }
                RxJavaHooks.setOnNewThreadScheduler { scheduler -> Schedulers.immediate() }
                RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
                    override fun getMainThreadScheduler(): Scheduler {
                        return Schedulers.from(EspressoExecutor)
                    }
                })
                try {
                    superStatement.evaluate()
                } finally {
                    RxJavaHooks.reset()
                    RxAndroidPlugins.getInstance().reset()
                }
            }
        }
    }

    companion object {

        val app: CoseNonJavisteApp
            get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as CoseNonJavisteApp
    }
}
