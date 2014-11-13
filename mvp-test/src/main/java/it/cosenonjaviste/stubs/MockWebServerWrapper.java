package it.cosenonjaviste.stubs;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Func1;

@Singleton
public class MockWebServerWrapper {

    private final MockWebServer server;

    private LinkedList<RecordedRequest> requests = new LinkedList<>();

    @Inject public MockWebServerWrapper() {
        server = new MockWebServer();
        try {
            server.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initDispatcher(String responseBody) {
        initDispatcher(recordedRequest -> new MockResponse().setBody(responseBody));
    }

    public void initDispatcher(final Func1<RecordedRequest, MockResponse> func) {
        server.setDispatcher(new Dispatcher() {
            @Override public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                requests.add(request);
                return func.call(request);
            }
        });
    }

    public String getUrl(boolean initInBackgroundThread) {
        if (initInBackgroundThread) {
            try {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                return executorService.submit(this::getUrlSync).get();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            return getUrlSync();
        }
    }

    private String getUrlSync() {
        return server.getUrl("/").toString();
    }

    public void shutdown() {
        try {
            server.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRequestCount() {
        return requests.size();
    }

    public String getLastUrl() {
        return requests.getLast().getPath();
    }
}
