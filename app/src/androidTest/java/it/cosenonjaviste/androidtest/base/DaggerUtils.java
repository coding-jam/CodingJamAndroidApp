package it.cosenonjaviste.androidtest.base;

public class DaggerUtils {
    public static TestComponent getComponent() {
        return DaggerTestComponent.builder().build();
    }
}
