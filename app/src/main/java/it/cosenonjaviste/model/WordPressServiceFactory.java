package it.cosenonjaviste.model;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class WordPressServiceFactory {
    public static WordPressService create(String endpoint, boolean debug, GsonConverter converter) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                        //http calls are executed in background thread using SchedulerManager
                .setExecutors(Runnable::run, null)
                .setConverter(converter)
                .build();
        if (debug) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }
}
