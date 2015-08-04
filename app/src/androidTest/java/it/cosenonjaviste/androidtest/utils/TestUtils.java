package it.cosenonjaviste.androidtest.utils;

import rx.functions.Action1;

public class TestUtils {

    public static <T> Action1<T> sleep() {
        return o -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        };
    }
}
