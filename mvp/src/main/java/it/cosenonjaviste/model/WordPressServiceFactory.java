package it.cosenonjaviste.model;

import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class WordPressServiceFactory {
    public static WordPressService create(String endpoint, Executor callbackExecutor, boolean debug) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                        //http calls are executed in background thread using SchedulerManager
                .setExecutors(Runnable::run, callbackExecutor)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        if (debug) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }
}
