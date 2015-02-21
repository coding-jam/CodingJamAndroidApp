package it.cosenonjaviste.androidtest.base;

public class DaggerUtils {
    public static TestComponent getComponent() {
        return Dagger_TestComponent.builder().build();
    }
}
