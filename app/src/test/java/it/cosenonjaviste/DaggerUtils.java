package it.cosenonjaviste;

public class DaggerUtils {
    public static TestComponent getComponent() {
        return Dagger_TestComponent.builder().build();
    }
}
