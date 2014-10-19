package it.cosenonjaviste.mvp.base;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public abstract class RestAdapterConfigurator {
    public abstract RestAdapter.Builder configure(RestAdapter.Builder builder, String endPoint);

    public <T> T buildRestAdapter(Class<T> serviceClass, String endPoint, boolean debug) {
        return buildRestAdapter(serviceClass, endPoint, debug, null);
    }

    public <T> T buildRestAdapter(Class<T> serviceClass, String endPoint, boolean debug, RequestInterceptor requestInterceptor) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        if (debug) {
            builder = builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        if (requestInterceptor != null) {
            builder.setRequestInterceptor(requestInterceptor);
        }

        builder = configure(builder, endPoint);

        RestAdapter restAdapter = builder.build();

        return restAdapter.create(serviceClass);
    }
}
