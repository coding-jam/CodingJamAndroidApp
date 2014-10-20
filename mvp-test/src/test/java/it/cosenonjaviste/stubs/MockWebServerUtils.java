package it.cosenonjaviste.stubs;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

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
}
