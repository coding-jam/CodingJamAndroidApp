package it.cosenonjaviste.mvp.base;

public interface PresenterArgs {
    <T> T getObject(String key);

    int getInt(String key);

    PresenterArgs putObject(String key, Object value);

    PresenterArgs putInt(String key, int value);
}
