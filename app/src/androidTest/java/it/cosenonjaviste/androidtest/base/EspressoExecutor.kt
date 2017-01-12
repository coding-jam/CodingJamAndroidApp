package it.cosenonjaviste.androidtest.base

import android.support.test.espresso.Espresso.registerIdlingResources
import android.support.test.espresso.IdlingResource
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object EspressoExecutor : ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, SynchronousQueue<Runnable>()), IdlingResource {

    private var runningTasks: Int = 0
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    init {
        registerIdlingResources(this)
    }

    override fun execute(command: Runnable) {
        super.execute(Runnable {
            try {
                incrementRunningTasks()
                command.run()
            } finally {
                decrementRunningTasks()
            }
        })
    }

    @Synchronized private fun decrementRunningTasks() {
        runningTasks--
        if (runningTasks == 0 && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }
    }

    @Synchronized private fun incrementRunningTasks() {
        runningTasks++
    }

    override fun getName(): String {
        return "EspressoExecutor"
    }

    override fun isIdleNow(): Boolean {
        return runningTasks == 0
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }
}
