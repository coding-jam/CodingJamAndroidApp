package it.cosenonjaviste.androidtest.base;


import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class MockWebServerWrapper {

    private static MockWebServer server;

    private static LinkedList<RecordedRequest> requests = new LinkedList<>();

    private static Function<RecordedRequest, MockResponse> dispatchFunction;

    public MockWebServerWrapper() {
        if (server == null) {
            server = new MockWebServer();
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            initDispatcher();
        }
    }

    public static void initDispatcher(Function<RecordedRequest, MockResponse> dispatchFunction) {
        MockWebServerWrapper.dispatchFunction = dispatchFunction;
    }

    public void initDispatcher(String responseBody) {
        dispatchFunction = recordedRequest -> new MockResponse().setBody(responseBody);
        requests = new LinkedList<>();
    }

    private void initDispatcher() {
        server.setDispatcher(new Dispatcher() {
            @Override public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                requests.add(request);
                return dispatchFunction.apply(request);
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
        return server.url("/").toString();
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
