package it.cosenonjaviste.stubs;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.functions.Func1;

public class MockWebServerUtils {
    public static void initDispatcher(MockWebServer server, String responseBody) {
        initDispatcher(server, recordedRequest -> new MockResponse().setBody(responseBody));
    }

    public static void initDispatcher(MockWebServer server, final Func1<RecordedRequest, MockResponse> func) {
        server.setDispatcher(new Dispatcher() {
            @Override public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                return func.call(request);
            }
        });
    }

    public static String getUrl(MockWebServer mockWebServer, boolean initInBackgroundThread) {
        if (initInBackgroundThread) {
            try {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                return executorService.submit(() -> getUrlSync(mockWebServer)).get();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            return getUrlSync(mockWebServer);
        }
    }

    private static String getUrlSync(MockWebServer mockWebServer) {
        return mockWebServer.getUrl("/").toString();
    }
}
