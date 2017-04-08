package it.cosenonjaviste.androidtest.utils;


import com.annimon.stream.function.Consumer;

public class TestUtils {

    public static <T> Consumer<T> sleepAction() {
        return o -> sleep(1);
    }

    public static void sleep(int seconds) {
//        try {
//            Thread.sleep(seconds * 1000);
//        } catch (InterruptedException ignored) {
//        }
    }
}
