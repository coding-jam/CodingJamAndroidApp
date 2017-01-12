package it.cosenonjaviste.androidtest.base

import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import java.io.IOException
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MockWebServerWrapper {
    init {
        if (server == null) {
            server = MockWebServer()
            try {
                server!!.start()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }

            initDispatcher()
        }
    }

    fun initDispatcher(responseBody: String) {
        dispatchFunction = { MockResponse().setBody(responseBody) }
        requests = LinkedList<RecordedRequest>()
    }

    private fun initDispatcher() {
        server!!.setDispatcher(object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                requests.add(request)
                return dispatchFunction!!.invoke()
            }
        })
    }

    fun getUrl(initInBackgroundThread: Boolean): String {
        if (initInBackgroundThread) {
            try {
                val executorService = Executors.newSingleThreadExecutor()
                return executorService.submit(Callable<String> { urlSync }).get()
            } catch (e: Throwable) {
                throw RuntimeException(e)
            }

        } else {
            return urlSync
        }
    }

    private val urlSync: String
        get() = server!!.getUrl("/").toString()

    fun shutdown() {
        try {
            server!!.shutdown()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    val requestCount: Int
        get() = requests.size

    val lastUrl: String
        get() = requests.last.path

    companion object {

        private var server: MockWebServer? = null

        private var requests = LinkedList<RecordedRequest>()

        private var dispatchFunction: (() -> MockResponse) ? = null
    }
}
